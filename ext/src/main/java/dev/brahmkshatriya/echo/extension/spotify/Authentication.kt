package dev.brahmkshatriya.echo.extension.spotify

import kotlinx.serialization.Serializable
import okhttp3.Request

class Authentication(
    private val api: SpotifyApi
) {
    var accessToken: String? = null
    private var expiresIn: Long = 0
    private suspend fun createAccessToken(): String {
        val token = api.cache.accessToken
        val tokenExp = api.cache.accessTokenExpiration ?: 0
        if (accessToken == null && token != null && tokenExp > System.currentTimeMillis()) {
            accessToken = token
            expiresIn = tokenExp
            return token
        }
        val req = Request.Builder()
            .url("https://open.spotify.com/get_access_token?reason=transport&productType=web-player")
        if (api.token != null) req.header("Cookie", "sp_dc=${api.token}")
        val response = with(api) {
            json.decode<TokenResponse>(callGetBody(req.build()))
        }
        expiresIn = response.accessTokenExpirationTimestampMs
        accessToken = response.accessToken
        api.cache.accessToken = accessToken
        api.cache.accessTokenExpiration = expiresIn
        return response.accessToken
    }

    suspend fun getToken() =
        if (accessToken == null || System.currentTimeMillis() > expiresIn) createAccessToken()
        else accessToken!!

    fun clearToken() {
        accessToken = null
        expiresIn = 0
        api.cache.accessToken = null
        api.cache.accessTokenExpiration = null
    }

    @Serializable
    data class TokenResponse(
        val clientId: String,
        val accessToken: String,
        val accessTokenExpirationTimestampMs: Long,
        val isAnonymous: Boolean
    )
}