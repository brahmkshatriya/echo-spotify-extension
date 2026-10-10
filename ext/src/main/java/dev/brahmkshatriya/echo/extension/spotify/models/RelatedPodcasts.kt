package dev.brahmkshatriya.echo.extension.spotify.models

import dev.brahmkshatriya.echo.extension.spotify.Json
import kotlinx.serialization.Serializable

/** Related podcasts exposed in the publicly rendered Spotify show page.
 *
 * Spotify's SHOW_V4 protobuf only contains show information, not related shows.
 * The public page contains its recommendations in base64-encoded initialState.
 */
internal object RelatedPodcasts {
    @Serializable
    private data class Bootstrap(
        val internalLinkRecommender: Recommender = Recommender(),
    )

    @Serializable
    private data class Recommender(val shows: Shows = Shows())

    @Serializable
    private data class Shows(val data: List<Item.Podcast> = emptyList())

    private val initialState = Regex(
        """<script\b[^>]*\bid=["']initialState["'][^>]*>([^<]+)</script>""",
        RegexOption.IGNORE_CASE,
    )
    private val showUri = Regex("spotify:show:[0-9A-Za-z]{22}")

    // Avoid Okio here: Echo hosts may bundle an older ByteString without
    // ByteString.Companion. Using it crashes inside Echo with NoSuchFieldError.
    // java.util.Base64 is Android API 26+, while this extension supports API 24.
    private fun decodeBase64(input: String): String? {
        if (input.length % 4 != 0) return null
        val result = ByteArray(input.length / 4 * 3)
        var size = 0
        for (offset in input.indices step 4) {
            val a = base64Value(input[offset])
            val b = base64Value(input[offset + 1])
            val third = input[offset + 2]
            val fourth = input[offset + 3]
            val c = if (third == '=') 0 else base64Value(third)
            val d = if (fourth == '=') 0 else base64Value(fourth)
            if (a < 0 || b < 0 || c < 0 || d < 0 ||
                (third == '=' && fourth != '=') ||
                ((third == '=' || fourth == '=') && offset + 4 != input.length)
            ) return null
            result[size++] = ((a shl 2) or (b ushr 4)).toByte()
            if (third != '=') {
                result[size++] = ((b shl 4) or (c ushr 2)).toByte()
                if (fourth != '=') result[size++] = ((c shl 6) or d).toByte()
            }
        }
        return String(result, 0, size, Charsets.UTF_8)
    }

    private fun base64Value(char: Char): Int = when (char) {
        in 'A'..'Z' -> char - 'A'
        in 'a'..'z' -> char - 'a' + 26
        in '0'..'9' -> char - '0' + 52
        '+' -> 62
        '/' -> 63
        else -> -1
    }

    fun fromHtml(html: String, originalUri: String): List<Item.Podcast> {
        val encoded = initialState.find(html)?.groupValues?.get(1)?.trim() ?: return emptyList()
        val decoded = decodeBase64(encoded) ?: return emptyList()
        val shows = Json().decode<Bootstrap>(decoded).internalLinkRecommender.shows.data
        return shows.asSequence()
            .filter { it.typename == "Podcast" && it.name?.isNotBlank() == true }
            .filter { it.uri != originalUri && it.uri?.matches(showUri) == true }
            .distinctBy { it.uri }
            .toList()
    }
}
