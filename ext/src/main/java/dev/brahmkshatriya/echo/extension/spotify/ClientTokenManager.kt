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
import okio.ByteString.Companion.toByteString

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
        val clientId = api.web.clientId ?: WEB_PLAYER_CLIENT_ID
        val body = json.encode(
            ClientTokenRequest(
                clientData = ClientData(
                    clientVersion = WebPlayerConfig.appVersion,
                    clientId = clientId,
                    jsSdkData = JsSdkData()
                )
            )
        )

        val request = Request.Builder()
            .url(CLIENT_TOKEN_URL)
            .header("User-Agent", WebPlayerConfig.USER_AGENT)
            .header("Accept", "application/json")
            .header("Accept-Language", WebPlayerConfig.ACCEPT_LANGUAGE)
            .header("content-type", "application/json")
            .header("Origin", WebPlayerConfig.ORIGIN)
            .header("Referer", WebPlayerConfig.REFERER)
            .header("Sec-Fetch-Dest", "empty")
            .header("Sec-Fetch-Mode", "cors")
            .header("Sec-Fetch-Site", "same-site")
            .header("Connection", "keep-alive")
            .post(body.toByteArray().toRequestBody("application/json".toMediaType()))
            .build()

        client.newCall(request).await().use { response ->
            if (!response.isSuccessful) {
                throw Exception("Client token request failed: ${response.code}")
            }
            val responseBody = response.body.string()
            val tokenResponse = json.decode<ClientTokenResponse>(responseBody)
            clientToken = tokenResponse.grantedToken.token
            tokenExpiration = System.currentTimeMillis() +
                    tokenResponse.grantedToken.expiresAfterSeconds * 1000L
        }
    }

    fun clear() {
        clientToken = null
        tokenExpiration = 0
    }

    companion object {
        private const val CLIENT_TOKEN_URL =
            "https://clienttoken.spotify.com/v1/clienttoken"
        private const val WEB_PLAYER_CLIENT_ID =
            "d8a5ed958d274c2e8ee717e6a4b0971d"

        private fun generateDeviceId(): String {
            val bytes = ByteArray(16)
            java.security.SecureRandom().nextBytes(bytes)
            return bytes.joinToString("") { "%02x".format(it) }
        }
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
        @EncodeDefault @SerialName("device_brand") val deviceBrand: String = "unknown",
        @EncodeDefault @SerialName("device_id") val deviceId: String = generateDeviceId(),
        @EncodeDefault @SerialName("device_model") val deviceModel: String = "unknown",
        @EncodeDefault @SerialName("device_type") val deviceType: String = "computer",
        @EncodeDefault val os: String = "windows",
        @EncodeDefault @SerialName("os_version") val osVersion: String = "NT 10.0",
    )

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
