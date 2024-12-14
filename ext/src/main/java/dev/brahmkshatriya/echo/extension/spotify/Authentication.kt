package dev.brahmkshatriya.echo.extension.spotify

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.add
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.putJsonArray
import okhttp3.Request

class Authentication(
    private val api: SpotifyApi
) {
    var accessToken: String? = null
    private suspend fun createAccessToken(): String {
        val token = api.cache.accessToken
        if (accessToken == null && token != null && isTokenWorking(token)) {
            accessToken = token
            return token
        }
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
        api.cache.accessToken = accessToken
        return response.accessToken
    }

    suspend fun getToken() =
        if (accessToken == null || !isTokenWorking(accessToken)) createAccessToken()
        else accessToken!!

    fun clearToken() {
        accessToken = null
        api.cache.accessToken = null
    }

    private val tokenCheckRequest = api.graphRequest(
        "areEntitiesInLibrary",
        "6ec3f767111e1f88a68058560f961161679d2cd4805ff3b8cb4b25c83ccbd6e0",
        buildJsonObject {
            putJsonArray("uris") {
                add("spotify:track:3z5lNLYtGC6LmvrxSbCQgd")
            }
        }
    ).build()

    private suspend fun isTokenWorking(token: String?): Boolean {
        val oldToken = accessToken
        accessToken = token
        return runCatching {
            api.callGetBody(tokenCheckRequest)
            accessToken = oldToken
        }.isSuccess
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