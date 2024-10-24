package dev.brahmkshatriya.echo.extension

import kotlinx.serialization.Serializable
import okhttp3.Request

class Authentication(
    private val api: SpotifyApi
) {
    var accessToken: String? = null
    private var expiresIn: Long = 0
    private suspend fun createAccessToken() {
        val token = api.settings.getString("token")
        val tokenExp = api.settings.getString("tokenExp")?.toLongOrNull() ?: 0
        if (accessToken == null && token != null && tokenExp > System.currentTimeMillis()) {
            accessToken = api.settings.getString("token")
            expiresIn = tokenExp
            return
        }
        val req = Request.Builder()
            .url("https://open.spotify.com/get_access_token?reason=transport&productType=web-player")
        if(api.token != null) req.header("Cookie", "sp_dc=${api.token}")
        val response = with(api) {
            json.decode<TokenResponse>(callGetBody(req.build()))
        }
        expiresIn = response.accessTokenExpirationTimestampMs
        accessToken = response.accessToken
        api.settings.putString("token", accessToken)
        api.settings.putString("tokenExp", expiresIn.toString())
    }

    suspend fun checkToken() {
        if (accessToken == null || System.currentTimeMillis() > expiresIn) createAccessToken()
    }

    @Serializable
    data class TokenResponse(
        val clientId: String,
        val accessToken: String,
        val accessTokenExpirationTimestampMs: Long,
        val isAnonymous: Boolean
    )
}