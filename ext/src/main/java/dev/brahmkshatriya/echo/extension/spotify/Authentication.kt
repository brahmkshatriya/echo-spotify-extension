package dev.brahmkshatriya.echo.extension.spotify

import dev.brahmkshatriya.echo.common.helpers.ContinuationCallback.Companion.await
import dev.brahmkshatriya.echo.extension.spotify.SpotifyApi.Companion.userAgent
import kotlinx.serialization.Serializable
import okhttp3.Cookie
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.lang.Long.toHexString
import java.util.Locale

class Authentication(
    private val api: SpotifyApi
) {
    private val json = Json()
    private val client = OkHttpClient.Builder().addInterceptor {
        val req = it.request().newBuilder()
        if (api.token != null) req.header("Cookie", "sp_dc=${api.token}")
        req.addHeader(userAgent.first, userAgent.second)
        req.addHeader("Accept", "application/json")
        it.proceed(req.build())
    }.build()

    private var clientId: String? = null
    var accessToken: String? = null
    private var tokenExpiration: Long = 0

    private suspend fun createAccessToken(): String {
        val time = System.currentTimeMillis()
        val steps = toHexString(time / 30000).uppercase(Locale.getDefault())
        val totp = TOTP.generateTOTP(SEED, steps, "6", "HmacSHA1")
        val req = Request.Builder()
            .url("https://open.spotify.com/get_access_token?reason=transport&productType=web-player&totp=$totp&totpVer=5&ts=${time}")

        val body = client.newCall(req.build()).await().body.string()
        val response = runCatching { json.decode<TokenResponse>(body) }.getOrElse {
            throw runCatching { json.decode<ErrorMessage>(body).error }.getOrElse {
                IOException(body)
            }
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
            .url("https://www.spotify.com/api/masthead/v1/masthead?language=en")
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

    companion object {
        // Thanks to https://github.com/Adolar0042/ the goat
        const val SEED = "35353037313435383533343837343939353932323438363330333239333437"
    }
}