package dev.brahmkshatriya.echo.extension.spotify

import kotlinx.serialization.Serializable
import okhttp3.Request

class Authentication(
    private val api: SpotifyApi
) {
    var accessToken: String? = null
    private var tokenExpiration: Long = 0
    private suspend fun createAccessToken(): String {
        val req = Request.Builder()
            .url("https://open.spotify.com/get_access_token?reason=transport&productType=web-player")
        if (api.token != null) req.header("Cookie", "sp_dc=${api.token}")
        val response = with(api) {
            val response = callGetBody(req.build(), true)
            runCatching { json.decode<TokenResponse>(response) }.getOrElse {
                throw json.decode<ErrorMessage>(response).error
            }
        }
        accessToken = response.accessToken
        tokenExpiration = response.accessTokenExpirationTimestampMs - 5 * 60 * 1000
        return response.accessToken
    }

    suspend fun getToken() =
        if (accessToken == null || !isTokenWorking(tokenExpiration)) createAccessToken()
        else accessToken!!

    fun clear() {
        accessToken = null
        tokenExpiration = 0
    }

    private fun isTokenWorking(expiry: Long): Boolean {
        return System.currentTimeMillis() < expiry
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