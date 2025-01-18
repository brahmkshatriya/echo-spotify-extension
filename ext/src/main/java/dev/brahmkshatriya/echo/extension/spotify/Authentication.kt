package dev.brahmkshatriya.echo.extension.spotify

import dev.brahmkshatriya.echo.common.helpers.ContinuationCallback.Companion.await
import kotlinx.serialization.Serializable
import okhttp3.Cookie
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request

class Authentication(
    private val api: SpotifyApi
) {
    private val json = Json()
    private val client = OkHttpClient.Builder().addInterceptor {
        val req = it.request().newBuilder()
        if (api.token != null) req.header("Cookie", "sp_dc=${api.token}")
        it.proceed(req.build())
    }.build()

    private var clientId: String? = null
    var accessToken: String? = null
    private var tokenExpiration: Long = 0

    private suspend fun createAccessToken(): String {
        val req = Request.Builder()
            .url("https://open.spotify.com/get_access_token?reason=transport&productType=web-player")
        val body = client.newCall(req.build()).await().body.string()
        val response = runCatching { json.decode<TokenResponse>(body) }.getOrElse {
            throw json.decode<ErrorMessage>(body).error
        }
        clientId = response.clientId
        accessToken = response.accessToken
        tokenExpiration = response.accessTokenExpirationTimestampMs - 5 * 60 * 1000
        return accessToken!!
    }

    suspend fun getToken() =
        if (accessToken == null || !isTokenWorking(tokenExpiration)) createAccessToken()
        else accessToken!!

    fun clear() {
        accessToken = null
        tokenExpiration = 0
    }

    private fun isTokenWorking(expiry: Long): Boolean {
        return (System.currentTimeMillis() < expiry)
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


    private var cookie: Cookie? = null
    private suspend fun createCookie(): Cookie {
        val req = Request.Builder()
            .url("https://www.spotify.com/api/masthead/v1/masthead?market=in&language=en")
        val cookie = client.newCall(req.build()).await().headers("Set-Cookie").mapNotNull {
            Cookie.parse("https://open.spotify.com/".toHttpUrl(), it)
        }.find { it.name == "sp_t" }!!
        return cookie
    }

    @Suppress("unused")
    suspend fun getSpT(): String {
        val cookie = cookie
        if (cookie != null && cookie.expiresAt > System.currentTimeMillis()) return cookie.value
        val new = createCookie()
        this.cookie = new
        return new.value
    }
}