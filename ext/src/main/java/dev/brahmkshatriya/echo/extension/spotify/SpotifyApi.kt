package dev.brahmkshatriya.echo.extension.spotify

import dev.brahmkshatriya.echo.common.helpers.ClientException
import dev.brahmkshatriya.echo.common.helpers.ContinuationCallback.Companion.await
import dev.brahmkshatriya.echo.common.settings.Settings
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlinx.serialization.json.putJsonObject
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.internal.closeQuietly
import spotify.extendedmetadata.metadata.ExtendedMetadataProto
import spotify.extendedmetadata.metadata.ExtendedMetadataProto.BatchedExtensionResponse
import java.net.URLEncoder
import java.security.SecureRandom

class SpotifyApi {
    val json = Json()

    private val webMutex = Mutex()
    val web = TokenManagerDesktop(this)

    @Volatile var settings: Settings? = null
    private var cachedDeviceId: String? = null

    val cookie get() = _cookie
    private var _cookie: String? = null
    fun setCookie(cookie: String?) {
        _cookie = cookie
        synchronized(web) { web.clear() }
        clientTokenManager.clear()
    }

    val isDesktopPersona: Boolean
        get() = !_cookie.isNullOrBlank()

    fun deviceId(): String {
        cachedDeviceId?.let { return it }
        return synchronized(this) {
            cachedDeviceId?.let { return@synchronized it }
            val stored = settings?.getString(DEVICE_ID_KEY)?.takeIf { it.isNotBlank() }
            val id = stored ?: generateDeviceId().also {
                runCatching { settings?.putString(DEVICE_ID_KEY, it) }
            }
            cachedDeviceId = id
            id
        }
    }

    val clientTokenManager = ClientTokenManager(this)

    val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request()
            val builder = request.newBuilder()
            val host = request.url.host

            val desktop = isDesktopPersona
            if (desktop) applyDesktopHeaders(builder) else applyWebHeaders(builder)

            if (request.header("Accept") == null) {
                builder.header("Accept", "application/json")
            }

            val isClientTokenEndpoint = host.contains("clienttoken")
            if (!isClientTokenEndpoint) {
                web.accessToken?.let {
                    if (request.header("Authorization") == null)
                        builder.header("Authorization", "Bearer $it")
                }
                clientTokenManager.clientToken?.let {
                    builder.header("client-token", it)
                }
                builder.header(
                    "spotify-app-version",
                    if (desktop) DesktopConfig.appVersion else WebPlayerConfig.appVersion,
                )
            }

            chain.proceed(builder.build())
        }
        .build()

    private fun applyDesktopHeaders(builder: Request.Builder) {
        builder.header("User-Agent", DesktopConfig.userAgent)
        builder.header("App-Platform", DesktopConfig.PLATFORM_HEADER)
        builder.header("Accept-Language", DesktopConfig.ACCEPT_LANGUAGE)
    }

    private fun applyWebHeaders(builder: Request.Builder) {
        builder.header("User-Agent", WebPlayerConfig.USER_AGENT)
        builder.header("sec-ch-ua", WebPlayerConfig.SEC_CH_UA)
        builder.header("sec-ch-ua-mobile", WebPlayerConfig.SEC_CH_UA_MOBILE)
        builder.header("sec-ch-ua-platform", WebPlayerConfig.SEC_CH_UA_PLATFORM)
        builder.header("Origin", WebPlayerConfig.ORIGIN)
        builder.header("Referer", WebPlayerConfig.REFERER)
        builder.header("Sec-Fetch-Dest", "empty")
        builder.header("Sec-Fetch-Mode", "cors")
        builder.header("Sec-Fetch-Site", "same-site")
        builder.header("Accept-Language", WebPlayerConfig.ACCEPT_LANGUAGE)
        builder.header("App-Platform", WebPlayerConfig.APP_PLATFORM)
    }

    data class Response<T>(
        val json: T,
        val raw: String,
    )

    suspend fun graphCall(
        operationName: String,
        persistedQuery: String,
        variables: JsonObject = buildJsonObject { },
    ): String {
        val req = Request.Builder()
            .url("https://api-partner.spotify.com/pathfinder/v2/query")
            .post(
                buildJsonObject {
                    put("operationName", operationName)
                    put("variables", variables)
                    put("extensions", extensions(persistedQuery))
                }.toString().toRequestBody("application/json".toMediaType())
            )
        return callGetBody(req.build())
    }

    suspend inline fun <reified T> graphQuery(
        operationName: String,
        persistedQuery: String,
        variables: JsonObject = buildJsonObject { },
        print: Boolean = false,
    ): Response<T> {
        val raw = graphCall(operationName, persistedQuery, variables)
        if (print) println(raw)
        return Response(json.decode<T>(raw), raw)
    }

    suspend inline fun <reified T> clientQuery(path: String): Response<T> {
        val raw = callGetBody(
            Request.Builder()
                .url("https://spclient.wg.spotify.com/$path")
                .build()
        )
        return Response(json.decode<T>(raw), raw)
    }

    suspend inline fun <reified T> clientMutate(path: String, data: JsonObject): Response<T> {
        val raw = callGetBody(
            Request.Builder()
                .url("https://spclient.wg.spotify.com/$path")
                .post(data.toString().toRequestBody("application/json".toMediaType()))
                .build()
        )
        return Response(json.decode<T>(raw), raw)
    }

    fun buildExtendedMetadataRequest(
        entityUris: List<String>,
        extensionKinds: List<ExtendedMetadataProto.ExtensionKind>
    ): ByteArray {
        val request = ExtendedMetadataProto.BatchedEntityRequest.newBuilder()
            .setHeader(ExtendedMetadataProto.BatchedEntityRequestHeader.getDefaultInstance())
            .addAllEntityRequest(
                entityUris.map { uri ->
                    ExtendedMetadataProto.EntityRequest.newBuilder()
                        .setEntityUri(uri)
                        .addAllQuery(
                            extensionKinds.map { kind ->
                                ExtendedMetadataProto.ExtensionQuery.newBuilder()
                                    .setExtensionKind(kind)
                                    .build()
                            }
                        )
                        .build()
                }
            )
            .build()

        return request.toByteArray()
    }


    suspend inline fun clientMutateProto(path: String, mediaId: String): BatchedExtensionResponse {
        val requestBytes = buildExtendedMetadataRequest(entityUris = listOf(mediaId), extensionKinds = listOf(
            ExtendedMetadataProto.ExtensionKind.TRACK_V4,
            ExtendedMetadataProto.ExtensionKind.AUDIO_FILES
        ))
        val raw = callGetBodyBytes(
            Request.Builder()
                .url("https://spclient.wg.spotify.com/$path")
                .header("Accept", "application/x-protobuf")
                .header("Content-Type", "application/x-protobuf")
                .post(requestBytes.toRequestBody("application/x-protobuf".toMediaType()))
                .build()
        )

        return BatchedExtensionResponse.parseFrom(raw)
    }

    suspend fun callGetBody(request: Request): String {
        runCatching {
            webMutex.withLock { web.getToken() }
            clientTokenManager.ensureValid()
        }.getOrElse {
            val id = userId
            if (id != null && it is TokenManagerDesktop.Error) throw ClientException.Unauthorized(id)
            throw it
        }
        val response = call(request).body.string()
        return if (response.startsWith('{')) response else {
            throw Exception("Invalid response: $response")
        }
    }

    suspend fun callGetBodyBytes(request: Request): ByteArray {
        runCatching {
            webMutex.withLock { web.getToken() }
            clientTokenManager.ensureValid()
        }.getOrElse {
            val id = userId
            if (id != null && it is TokenManagerDesktop.Error) throw ClientException.Unauthorized(id)
            throw it
        }
        val response = call(request)
        return if (!response.isSuccessful) {
            throw RuntimeException(
                "Extended metadata request failed: ${response.code} ${response.message}"
            )
        } else response.body.bytes()
    }


    private fun extensions(persistedQuery: String): JsonObject {
        return buildJsonObject {
            putJsonObject("persistedQuery") {
                put("version", 1)
                put("sha256Hash", persistedQuery)
            }
        }
    }

    private suspend fun call(
        request: Request, ignore: Boolean = false, auth: String? = null,
    ) = run {
        val req = if (auth == null) request
        else request.newBuilder().addHeader("Authorization", "Bearer $auth").build()
        val res = client.newCall(req).await()
        if (ignore || res.isSuccessful) res
        else {
            res.closeQuietly()
            throw Exception("${res.code}: Failed to call - ${req.url}")
        }
    }

    suspend fun getWebAccessToken(): String {
        return webMutex.withLock { web.getToken() }
    }

    var userId: String? = null
    fun setUser(id: String?) {
        userId = id
    }

    companion object {
        private const val DEVICE_ID_KEY = "5911f7cd0d3cb8e1fdf731f0c57303cd353c96d8"

        private fun generateDeviceId(): String {
            val bytes = ByteArray(16)
            SecureRandom().nextBytes(bytes)
            return bytes.joinToString("") { "%02x".format(it) }
        }

        fun urlEncode(data: String): String = URLEncoder.encode(data, "UTF-8")
    }
}
