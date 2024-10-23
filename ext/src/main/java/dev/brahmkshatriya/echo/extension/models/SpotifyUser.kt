package dev.brahmkshatriya.echo.extension.models

import dev.brahmkshatriya.echo.common.models.ImageHolder.Companion.toImageHolder
import dev.brahmkshatriya.echo.common.models.User
import kotlinx.serialization.Serializable
@Serializable
data class SpotifyUser (
    val data: Data
) {

    @Serializable
    data class Data(
        val me: Me
    )

    @Serializable
    data class Me(
        val profile: Profile
    )

    @Serializable
    data class Profile(
        val avatar: Avatar?,
        val avatarBackgroundColor: Long,
        val name: String,
        val uri: String,
        val username: String
    )

    @Serializable
    data class Avatar(
        val sources: List<Source>?
    )

    @Serializable
    data class Source(
        val height: Long,
        val url: String,
        val width: Long
    )

    fun toUser() = User(
        id = data.me.profile.uri,
        name = data.me.profile.name,
        cover = data.me.profile.avatar?.sources?.firstOrNull()?.url?.toImageHolder()
    )
}