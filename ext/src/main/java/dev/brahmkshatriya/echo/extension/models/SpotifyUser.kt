package dev.brahmkshatriya.echo.extension.models

import kotlinx.serialization.Serializable

@Serializable
data class SpotifyUser(
    val data: SpotifyUserData
)

@Serializable
data class SpotifyUserData(
    val me: Me
)

@Serializable
data class Me(
    val profile: Profile
)