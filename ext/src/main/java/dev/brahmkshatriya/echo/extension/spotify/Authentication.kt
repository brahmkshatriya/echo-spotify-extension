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
        req.addHeader("Referer", "https://open.spotify.com/")
        it.proceed(req.build())
    }.build()

    private var clientId: String? = null
    var accessToken: String? = null
    private var tokenExpiration: Long = 0

    private suspend fun createAccessToken(): String {
        val req = Request.Builder().url(generateUrl())
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

    private suspend fun generateUrl(): String {
        val (serverTime, secret, buildVer, buildDate) = getTimeAndSecret()
        val time = System.currentTimeMillis()
        val totp = TOTP.generateTOTP(
            secret, toHexString(time / 30000).uppercase(Locale.getDefault()), 6, "HmacSHA1"
        )
        val serverTotp = TOTP.generateTOTP(
            secret, toHexString(serverTime / 30).uppercase(Locale.getDefault()), 6, "HmacSHA1"
        )
        val url =
            "https://open.spotify.com/get_access_token?reason=init&productType=web-player&totp=${totp}&totpServer=${serverTotp}&totpVer=5&sTime=${serverTime}&cTime=${time}&buildVer=${buildVer}&buildDate=${buildDate}"
        return url
    }

    private val serverTimeRegex = Regex("\"serverTime\":([^}]+)\\}")
    private val playerJsRegex =
        Regex("https://open\\.spotifycdn\\.com/cdn/build/web-player/web-player\\..{8}\\.js")
    private val seedRegex = Regex("\\[(([0-9]{2},){16}[0-9]{2})]")
    private val buildRegex = Regex("buildVer:\"([^\"]+)\",buildDate:\"([^\"]+)\"")

    data class Data(
        val serverTime: Long,
        val seed: String,
        val buildVer: String,
        val buildDate: String
    )

    private suspend fun getTimeAndSecret(): Data {
        val body = client.newCall(Request.Builder().url("https://open.spotify.com/").build())
            .await().body.string()
        val serverTime = serverTimeRegex.find(body)?.groupValues?.get(1)?.toLongOrNull()
            ?: throw IllegalStateException("Failed to get server time")
        val playerJs = playerJsRegex.find(body)?.value
            ?: throw IllegalStateException("Failed to get player js")
        val jsBody = client.newCall(Request.Builder().url(playerJs).build()).await().body.string()
        val seed = seedRegex.find(jsBody)?.groupValues?.get(1)?.split(",")?.map { it.toInt() }
            ?: throw IllegalStateException("Failed to get seed")
        val (buildVer, buildDate) = buildRegex.find(jsBody)?.destructured
            ?: throw IllegalStateException("Failed to get build info")
        return Data(serverTime, seedToSecret(seed), buildVer, buildDate)
    }

    private fun seedToSecret(list: List<Int>): String {
        return list.mapIndexed { index, byte -> byte xor ((index % 33) + 9) }
            .joinToString("")
            .toByteArray(Charsets.UTF_8)
            .joinToString("") { it.toUByte().toString(16) }
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
}