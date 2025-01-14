package dev.brahmkshatriya.echo.extension.spotify

import dev.brahmkshatriya.echo.common.helpers.ContinuationCallback.Companion.await
import kotlinx.serialization.Serializable
import okhttp3.OkHttpClient
import okhttp3.Request

class Authentication(
    private val api: SpotifyApi
) {
    private val json = Json()
    var accessToken: String? = null
    private var tokenExpiration: Long = 0
    private val client = OkHttpClient.Builder().build()
    private suspend fun createAccessToken(): String {
        val req = Request.Builder()
            .url("https://open.spotify.com/get_access_token?reason=transport&productType=web-player")
        if (api.token != null) req.header("Cookie", "sp_dc=${api.token}")
        println("Creating access token")
        val body = client.newCall(req.build()).await().body.string()
        println("res: $body")
        val response = runCatching { json.decode<TokenResponse>(body) }.getOrElse {
            throw json.decode<ErrorMessage>(body).error
        }
        accessToken = response.accessToken
        tokenExpiration = response.accessTokenExpirationTimestampMs - 5 * 60 * 1000
        println("Expiry: $tokenExpiration")
        return accessToken!!
    }

    suspend fun getToken() =
        if (accessToken == null || !isTokenWorking(tokenExpiration)) createAccessToken()
        else accessToken!!

    fun clear() {
        println("Clearing Token")
        accessToken = null
        tokenExpiration = 0
    }

    private fun isTokenWorking(expiry: Long): Boolean {
        return (System.currentTimeMillis() < expiry).also {
            println("Checking token $it")
        }
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