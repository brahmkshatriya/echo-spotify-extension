package dev.brahmkshatriya.echo.extension.spotify.models

import kotlinx.serialization.Serializable

@Serializable
data class BrowseAll (
    val data: Data,
) {

    @Serializable
    data class Data(
        val browseStart: BrowseStart
    )

    @Serializable
    data class BrowseStart(
        val sections: Sections,
        val uri: String
    )
}
