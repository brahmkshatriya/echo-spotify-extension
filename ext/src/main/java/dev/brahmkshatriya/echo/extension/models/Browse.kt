package dev.brahmkshatriya.echo.extension.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.JsonElement

@Serializable
data class Browse (
    val data: BrowseData,
)

@Serializable
data class BrowseData (
    val browse: BrowseClass
)

@Serializable
data class BrowseClass (
    @SerialName("__typename")
    val typename: ContentDataTypename? = null,

    val header: Header? = null,
    val sections: Sections,
    val uri: String? = null
)

@Serializable
data class Header (
    val backgroundImage: JsonElement? = null,
    val color: Color? = null,
    val subtitle: JsonElement? = null,
    val title: Title? = null
)