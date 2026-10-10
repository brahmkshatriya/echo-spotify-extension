package dev.brahmkshatriya.echo.extension.spotify.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Spotify's show playback context contains the actual episode IDs.
 * SHOW_V4 deliberately omits these IDs for many active podcast shows.
 */
@Serializable
data class ShowContext(
    val uri: String? = null,
    val pages: List<Page> = emptyList(),
) {
    @Serializable
    data class Page(val tracks: List<Entry> = emptyList())

    @Serializable
    data class Entry(
        val uri: String? = null,
        val metadata: Metadata = Metadata(),
    )

    @Serializable
    data class Metadata(@SerialName("added_at") val addedAt: String? = null)

    /** Context playback order is oldest-first; show pages should show newest first. */
    fun episodeUris(): List<String> = pages.flatMap { it.tracks }
        .asReversed()
        .sortedWith(compareByDescending<Entry> { it.metadata.addedAt?.toLongOrNull() ?: 0L })
        .mapNotNull { it.uri }
        .filter { EPISODE_URI.matches(it) }
        .distinct()

    companion object {
        private val EPISODE_URI = Regex("spotify:episode:[0-9A-Za-z]{22}")
    }
}
