package dev.brahmkshatriya.echo.extension

import dev.brahmkshatriya.echo.common.clients.ExtensionClient
import dev.brahmkshatriya.echo.common.clients.LoginClient
import dev.brahmkshatriya.echo.common.helpers.ClientException
import dev.brahmkshatriya.echo.common.models.Request.Companion.toRequest
import dev.brahmkshatriya.echo.common.models.User
import dev.brahmkshatriya.echo.common.settings.Setting
import dev.brahmkshatriya.echo.common.settings.Settings
import dev.brahmkshatriya.echo.extension.SpotifyApi.Companion.userAgent
import dev.brahmkshatriya.echo.extension.models.SpotifyUser
import java.net.HttpCookie

class SpotifyExtension : ExtensionClient, LoginClient.WebView.Cookie {

    override suspend fun onExtensionSelected() {}

    override val settingItems: List<Setting> = emptyList()

    private lateinit var setting: Settings
    override fun setSettings(settings: Settings) {
        setting = settings
    }

    private var api: SpotifyApi? = null
    private suspend fun <T> withApi(block: suspend SpotifyApi.() -> T): T {
        return api?.block() ?: throw ClientException.LoginRequired()
    }

    override val loginWebViewInitialUrl =
        "https://accounts.spotify.com/en/login".toRequest(mapOf(userAgent))

    override val loginWebViewStopUrlRegex = "https://accounts\\.spotify\\.com/en/status".toRegex()

    override suspend fun getCurrentUser() = withApi {
        query<SpotifyUser>(
            "profileAttributes",
            "53bcb064f6cd18c23f752bc324a791194d20df612d8e1239c735144ab0399ced"
        )
    }.toUser()

    override suspend fun onLoginWebviewStop(url: String, data: String): List<User> {
        val parsed = data.split(";").map { HttpCookie.parse(it).first() }
        val token = parsed.find { it.name == "sp_dc" } ?: throw Exception("Token not found")
        api = SpotifyApi(token.value)
        val user = getCurrentUser().copy(id=token.value)
        return listOf(user)
    }

    override suspend fun onSetLoginUser(user: User?) {
        api = if (user != null) SpotifyApi(user.id) else null
    }
}