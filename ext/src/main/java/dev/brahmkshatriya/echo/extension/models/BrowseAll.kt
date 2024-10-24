package dev.brahmkshatriya.echo.extension.models

import kotlinx.serialization.Serializable

@Serializable
data class BrowseAll (
    val data: BrowseAllData,
)

@Serializable
data class BrowseAllData (
    val browseStart: BrowseStart
)

@Serializable
data class BrowseStart (
    val sections: Sections,
    val uri: String
)
