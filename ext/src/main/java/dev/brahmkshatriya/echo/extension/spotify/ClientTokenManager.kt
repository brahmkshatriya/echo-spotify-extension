package dev.brahmkshatriya.echo.extension.spotify

import dev.brahmkshatriya.echo.common.helpers.ContinuationCallback.Companion.await
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class ClientTokenManager(
    private val api: SpotifyApi,
) {
    private val json = api.json
    private val mutex = Mutex()

    var clientToken: String? = null
        private set
    private var tokenExpiration: Long = 0

    private val client = OkHttpClient()

    suspend fun ensureValid() {
        mutex.withLock {
            if (clientToken != null && System.currentTimeMillis() < tokenExpiration) return
            fetchClientToken()
        }
    }

    private suspend fun fetchClientToken() {
        val desktop = api.isDesktopPersona
        val deviceId = api.deviceId()

        if (desktop) {
            val attempted = postClientToken(
                clientId = DesktopConfig.CLIENT_ID,
                clientVersion = DesktopConfig.appVersion,
                sdkData = JsSdkData.desktop(deviceId),
                useDesktopHeaders = true,
            )
            if (attempted) return
            postClientToken(
                clientId = WebPlayerConfig.CLIENT_ID,
                clientVersion = WebPlayerConfig.appVersion,
                sdkData = JsSdkData.web(deviceId),
                useDesktopHeaders = false,
                throwOnFailure = true,
            )
        } else {
            postClientToken(
                clientId = api.web.clientId ?: WebPlayerConfig.CLIENT_ID,
                clientVersion = WebPlayerConfig.appVersion,
                sdkData = JsSdkData.web(deviceId),
                useDesktopHeaders = false,
                throwOnFailure = true,
            )
        }
    }

    private suspend fun postClientToken(
        clientId: String,
        clientVersion: String,
        sdkData: JsSdkData,
        useDesktopHeaders: Boolean,
        throwOnFailure: Boolean = false,
    ): Boolean {
        val body = json.encode(
            ClientTokenRequest(
                clientData = ClientData(
                    clientVersion = clientVersion,
                    clientId = clientId,
                    jsSdkData = sdkData,
                )
            )
        )

        val request = Request.Builder()
            .url(CLIENT_TOKEN_URL)
            .apply {
                if (useDesktopHeaders) {
                    header("User-Agent", DesktopConfig.userAgent)
                    header("Accept-Language", DesktopConfig.ACCEPT_LANGUAGE)
                } else {
                    header("User-Agent", WebPlayerConfig.USER_AGENT)
                    header("Accept-Language", WebPlayerConfig.ACCEPT_LANGUAGE)
                    header("Origin", WebPlayerConfig.ORIGIN)
                    header("Referer", WebPlayerConfig.REFERER)
                    header("Sec-Fetch-Dest", "empty")
                    header("Sec-Fetch-Mode", "cors")
                    header("Sec-Fetch-Site", "same-site")
                }
            }
            .header("Accept", "application/json")
            .header("content-type", "application/json")
            .header("Connection", "keep-alive")
            .post(body.toByteArray().toRequestBody("application/json".toMediaType()))
            .build()

        client.newCall(request).await().use { response ->
            if (!response.isSuccessful) {
                if (throwOnFailure) {
                    throw Exception("Client token request failed: ${response.code}")
                }
                return false
            }
            val responseBody = response.body.string()
            val tokenResponse = json.decode<ClientTokenResponse>(responseBody)
            clientToken = tokenResponse.grantedToken.token
            tokenExpiration = System.currentTimeMillis() +
                    tokenResponse.grantedToken.expiresAfterSeconds * 1000L
            return true
        }
    }

    fun clear() {
        clientToken = null
        tokenExpiration = 0
    }

    companion object {
        private const val CLIENT_TOKEN_URL =
            "https://clienttoken.spotify.com/v1/clienttoken"
    }

    @Serializable
    private data class ClientTokenRequest(
        @SerialName("client_data") val clientData: ClientData,
    )

    @Serializable
    private data class ClientData(
        @SerialName("client_version") val clientVersion: String,
        @SerialName("client_id") val clientId: String,
        @SerialName("js_sdk_data") val jsSdkData: JsSdkData,
    )

    @OptIn(ExperimentalSerializationApi::class)
    @Serializable
    private data class JsSdkData(
        @EncodeDefault @SerialName("device_brand") val deviceBrand: String,
        @EncodeDefault @SerialName("device_id") val deviceId: String,
        @EncodeDefault @SerialName("device_model") val deviceModel: String,
        @EncodeDefault @SerialName("device_type") val deviceType: String,
        @EncodeDefault val os: String,
        @EncodeDefault @SerialName("os_version") val osVersion: String,
    ) {
        companion object {
            fun desktop(deviceId: String) = JsSdkData(
                deviceBrand = DesktopConfig.DEVICE_BRAND,
                deviceId = deviceId,
                deviceModel = DesktopConfig.DEVICE_MODEL,
                deviceType = DesktopConfig.DEVICE_TYPE,
                os = DesktopConfig.OS,
                osVersion = DesktopConfig.OS_VERSION,
            )

            fun web(deviceId: String) = JsSdkData(
                deviceBrand = "unknown",
                deviceId = deviceId,
                deviceModel = "unknown",
                deviceType = "computer",
                os = "windows",
                osVersion = "NT 10.0",
            )
        }
    }

    @Serializable
    private data class ClientTokenResponse(
        @SerialName("granted_token") val grantedToken: GrantedToken,
    )

    @Serializable
    private data class GrantedToken(
        val token: String,
        @SerialName("expires_after_seconds") val expiresAfterSeconds: Int,
        @SerialName("refresh_after_seconds") val refreshAfterSeconds: Int = 0,
    )
}
