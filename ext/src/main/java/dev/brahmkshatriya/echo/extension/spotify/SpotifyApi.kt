package dev.brahmkshatriya.echo.extension.spotify

import dev.brahmkshatriya.echo.common.helpers.ContinuationCallback.Companion.await
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
import okhttp3.internal.commonIsSuccessful
import java.io.File
import java.net.URLEncoder

class SpotifyApi(
    val cacheDir: File,
    val onError: SpotifyApi.(Authentication.Error) -> Unit
) {

    val cookie get() = _cookie

    private val authMutex = Mutex()
    private val auth = Authentication(this)
    private var _cookie: String? = null
    suspend fun setCookie(cookie: String?) {
        _cookie = cookie
        authMutex.withLock { auth.clear() }
    }

    val json = Json()

    val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request()
            val builder = chain.request().newBuilder()
            builder.addHeader(userAgent.first, userAgent.second)
            builder.addHeader("Accept", "application/json")
            builder.addHeader("App-Platform", "WebPlayer")
            auth.accessToken?.let {
                if (request.headers["Authorization"] == null)
                    builder.addHeader("Authorization", "Bearer $it")
            }
            auth.clientToken?.let {
                builder.addHeader("Client-Token", it)
            }
            chain.proceed(builder.build())
        }
        .build()

    data class Response<T>(
        val json: T,
        val raw: String
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
        return call(req.build())
    }

    suspend inline fun <reified T> graphQuery(
        operationName: String,
        persistedQuery: String,
        variables: JsonObject = buildJsonObject { },
        print: Boolean = false
    ): Response<T> {
        val raw = graphCall(operationName, persistedQuery, variables)
        if (print) println(raw)
        return Response(json.decode<T>(raw), raw)
    }

    suspend inline fun <reified T> clientQuery(path: String): Response<T> {
        val raw = call(
            Request.Builder()
                .url("https://spclient.wg.spotify.com/$path")
                .build()
        )
        return Response(json.decode<T>(raw), raw)
    }

    suspend inline fun <reified T> clientMutate(path: String, data: JsonObject): Response<T> {
        val raw = call(
            Request.Builder()
                .url("https://spclient.wg.spotify.com/$path")
                .post(data.toString().toRequestBody("application/json".toMediaType()))
                .build()
        )
        return Response(json.decode<T>(raw), raw)
    }

    suspend fun call(request: Request): String {
        runCatching { authMutex.withLock { auth.getToken() } }.getOrElse {
            if (it is Authentication.Error) onError(it)
            throw it
        }
        val response = callGetBody(request)
        return if (response.startsWith('{')) response else {
            throw Exception("Invalid response: $response")
        }
    }


    private fun extensions(persistedQuery: String): JsonObject {
        return buildJsonObject {
            putJsonObject("persistedQuery") {
                put("version", 1)
                put("sha256Hash", persistedQuery)
            }
        }
    }

    private suspend fun callGetBody(
        request: Request, ignore: Boolean = false, auth: String? = null
    ) = run {
        val req = if (auth == null) request
        else request.newBuilder().addHeader("Authorization", "Bearer $auth").build()
        val res = client.newCall(req).await()
        if (ignore || res.commonIsSuccessful) res.body.string()
        else {
            res.closeQuietly()
            throw Exception("${res.code}: Failed to call - ${req.url}")
        }
    }

    suspend fun getAccessToken(): String {
        return authMutex.withLock { auth.getToken() }
    }

    companion object {
        fun urlEncode(data: String): String = URLEncoder.encode(data, "UTF-8")
        val userAgent =
            "User-Agent" to "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/136.0.0.0 Safari/537.36"
    }
}