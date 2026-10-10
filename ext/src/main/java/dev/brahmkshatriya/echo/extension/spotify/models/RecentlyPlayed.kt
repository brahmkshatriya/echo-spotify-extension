package dev.brahmkshatriya.echo.extension.spotify.models

import kotlinx.serialization.Serializable

@Serializable
data class RecentlyPlayed (
    val playContexts: List<PlayContext> = emptyList(),
    val items: List<PlayedItem> = emptyList(),
) {

    @Serializable
    data class PlayContext(
        val uri: String,
        val lastPlayedTime: Long? = null,
        val lastPlayedTrackUri: String? = null
    )

    @Serializable
    data class PlayedItem(
        val context: Context? = null,
        val track: Track? = null,
    )

    @Serializable
    data class Context(val uri: String? = null)

    @Serializable
    data class Track(val uri: String? = null)

    fun contentUris(): List<String> = (
        playContexts.map { it.uri } +
            items.mapNotNull { it.context?.uri ?: it.track?.uri }
        ).filter { PLAYABLE_CONTEXT.matches(it) }.distinct()

    companion object {
        private val PLAYABLE_CONTEXT = Regex(
            "spotify:(track|album|playlist|artist|show|episode):[a-zA-Z0-9]{22}"
        )
    }
}