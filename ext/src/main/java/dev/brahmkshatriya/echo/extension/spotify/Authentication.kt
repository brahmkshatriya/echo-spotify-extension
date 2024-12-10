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
        if (accessToken == null && token != null && System.currentTimeMillis() < tokenExp) {
            accessToken = token
            expiresIn = tokenExp
            return token
        }
        val req = Request.Builder()
            .url("https://open.spotify.com/get_access_token?reason=transport&productType=web-player")
        if (api.token != null) req.header("Cookie", "sp_dc=${api.token}")
        val response = with(api) {
            val response = callGetBody(req.build())
            runCatching { json.decode<TokenResponse>(response) }.getOrElse {
                throw json.decode<ErrorMessage>(response).error
            }
        }
        expiresIn = response.accessTokenExpirationTimestampMs - 1800000
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
        val isAnonymous: Boolean,
        val accessTokenExpirationTimestampMs: Long,
        val clientId: String,
        val accessToken: String,
    )

    @Serializable
    data class ErrorMessage(
        val error: Error
    )

    @Serializable
    data class Error(
        val code: Int,
        override val message: String
    ) : Exception(message)
}