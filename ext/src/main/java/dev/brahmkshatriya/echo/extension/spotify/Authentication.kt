package dev.brahmkshatriya.echo.extension.spotify

import dev.brahmkshatriya.echo.common.helpers.ContinuationCallback.Companion.await
import dev.brahmkshatriya.echo.extension.spotify.SpotifyApi.Companion.userAgent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class Authentication(
    private val api: SpotifyApi
) {
    private val json = Json()
    private val httpClient = OkHttpClient.Builder().addInterceptor {
        val req = it.request().newBuilder()
        val cookie = api.cookie
        if (cookie != null) req.addHeader("Cookie", cookie)
        req.addHeader(userAgent.first, userAgent.second)
        req.addHeader("Accept", "application/json")
        req.addHeader("Referer", "https://open.spotify.com/")
        it.proceed(req.build())
    }.build()

    var accessToken: String? = null
    private var tokenExpiration: Long = 0
    var clientToken: String? = null
    private var spT: String? = null

    private suspend fun createAccessToken(): String {
        val (url, clientVersion, clientId) = getUrlAndClient()
        val req = Request.Builder().url(url)
        val body = httpClient.newCall(req.build()).await().body.string()
        val response = runCatching { json.decode<TokenResponse>(body) }.getOrElse {
            throw runCatching { json.decode<ErrorMessage>(body).error }.getOrElse {
                Exception(body)
            }
        }
        val deviceId = response.clientId
        val postData =
            """{"client_data":{"client_version":"$clientVersion","client_id":"$clientId","js_sdk_data":{"device_brand":"unknown","device_model":"unknown","os":"windows","os_version":"NT 10.0","device_id":"$deviceId","device_type":"computer"}}}"""
                .toByteArray()
        val clientTokenUrl = Request.Builder()
            .url("https://clienttoken.spotify.com/v1/clienttoken")
            .post(
                postData.toRequestBody("application/json".toMediaType(), 0, postData.size)
            ).build()

        val clientTokenResponse = httpClient.newCall(clientTokenUrl).await().body.string()
        clientToken = json.decode<ClientTokenResponse>(clientTokenResponse).grantedToken.token

        accessToken = response.accessToken
        tokenExpiration = response.accessTokenExpirationTimestampMs - 5 * 60 * 1000
        return accessToken!!
    }

    @OptIn(ExperimentalStdlibApi::class)
    private suspend fun getUrlAndClient(): Triple<String, String, String> {
        val (serverTime, secret, buildVer, buildDate, clientId, clientVersion) = getDataFromSite()
        val time = System.currentTimeMillis()
        val totp = TOTP.generateTOTP(secret, (time / 30000).toHexString().uppercase())
        val serverTotp = TOTP.generateTOTP(secret, (serverTime / 30).toHexString().uppercase())
        val url =
            "https://open.spotify.com/api/token?reason=init&productType=web-player&totp=${totp}&totpServer=${serverTotp}&totpVer=5&sTime=${serverTime}&cTime=${time}&buildVer=${buildVer}&buildDate=${buildDate}"
        return Triple(url, clientVersion, clientId)
    }

    private val configRegex =
        Regex("<script id=\"appServerConfig\" type=\"text/plain\">(.+?)</script>")
    private val serverTimeRegex = Regex("\"serverTime\":([^}]+)\\}")
    private val playerJsRegex =
        Regex("https://open\\.spotifycdn\\.com/cdn/build/web-player/web-player\\..{8}\\.js")
    private val seedRegex = Regex("\\[(([0-9]{2},){16}[0-9]{2})]")
    private val buildRegex = Regex("buildVer:\"([^\"]+)\",buildDate:\"([^\"]+)\"")
    private val clientVersionRegex = Regex(",clientId:\"(.{32})\",clientVersion:\"(.*)\",product")

    data class Data(
        val serverTime: Long,
        val seed: String,
        val buildVer: String,
        val buildDate: String,
        val clientId: String,
        val clientVersion: String
    )

    @OptIn(ExperimentalEncodingApi::class)
    private suspend fun getDataFromSite(): Data {
        val res = httpClient.newCall(Request.Builder().url("https://open.spotify.com/").build())
            .await()
        val body = res.body.string()
        val configB64 = configRegex.find(body)?.groupValues?.get(1)
            ?: throw IllegalStateException("Account Suspended, please check your mail and click on \"Contact Support\" button.\n(Failed to get config)")
        val config = String(Base64.decode(configB64))
        val serverTime = serverTimeRegex.find(config)?.groupValues?.get(1)?.toLongOrNull()
            ?: throw IllegalStateException("Failed to get server time")
        val playerJs = playerJsRegex.find(body)?.value
            ?: throw IllegalStateException("Failed to get player js")
        spT = res.headers("Set-Cookie")
            .firstOrNull { it.startsWith("sp_t=") }
            ?.substringAfter("sp_t=")
            ?.substringBefore(";")

        val file = File(api.cacheDir.absolutePath, "${playerJs.hashCode()}")
        val jsBody = if (file.exists()) file.readText() else {
            file.parentFile.deleteRecursively()
            file.parentFile.mkdirs()
            val js =
                httpClient.newCall(Request.Builder().url(playerJs).build()).await().body.string()
            file.writeText(js)
            js
        }
        val seed = seedRegex.find(jsBody)?.groupValues?.get(1)?.split(",")?.map { it.toInt() }
            ?: throw IllegalStateException("Failed to get seed")
        val (buildVer, buildDate) = buildRegex.find(jsBody)?.destructured
            ?: throw IllegalStateException("Failed to get build info")
        val (client, clientVersion) = clientVersionRegex.find(jsBody)?.destructured
            ?: throw IllegalStateException("Failed to get client version")

        return Data(serverTime, seedToSecret(seed), buildVer, buildDate, client, clientVersion)
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

    @Serializable
    data class ClientTokenResponse(
        @SerialName("granted_token")
        val grantedToken: GrantedToken
    )

    @Serializable
    data class GrantedToken(val token: String)
}