package dev.brahmkshatriya.echo.extension.models

import kotlinx.serialization.Serializable

@Serializable
data class Color(
    val hex: String? = null
)

@Serializable
data class Title(
    val transformedLabel: String? = null
)

@Serializable
data class Artwork(
    val sources: List<Source>
)

@Serializable
data class Source(
    val height: Long,
    val url: String,
    val width: Long
)

@Serializable
data class Profile(
    val avatar: Artwork? = null,
    val avatarBackgroundColor: Long? = null,
    val name: String? = null,
    val uri: String? = null,
    val username: String? = null
)
