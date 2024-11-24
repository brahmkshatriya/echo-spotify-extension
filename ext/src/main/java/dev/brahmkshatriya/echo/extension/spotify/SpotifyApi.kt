package dev.brahmkshatriya.echo.extension.spotify

import dev.brahmkshatriya.echo.common.helpers.ContinuationCallback.Companion.await
import kotlinx.io.IOException
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlinx.serialization.json.putJsonObject
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.internal.commonIsSuccessful
import java.net.URLEncoder

class SpotifyApi(
    val cache: Cache,
    val onError: SpotifyApi.(Authentication.Error) -> Unit
) {
    val auth = Authentication(this)
    var token: String? = null
        set(value) {
            field = value
            auth.clearToken()
        }
    val json = Json()

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val builder = chain.request().newBuilder()
            builder.addHeader(userAgent.first, userAgent.second)
            builder.addHeader("Accept", "application/json")
            builder.addHeader("App-Platform", "WebPlayer")
            auth.accessToken?.let {
                builder.addHeader("Authorization", "Bearer $it")
            }
            val request = builder.build()
            chain.proceed(request)
        }
        .build()

    data class Response<T>(
        val json: T,
        val raw: String
    )

    suspend inline fun <reified T> graphQuery(
        operationName: String,
        persistedQuery: String,
        variables: JsonObject = buildJsonObject { },
        print: Boolean = false
    ): Response<T> {
        val builder = StringBuilder("https://api-partner.spotify.com/pathfinder/v1/query")
            .append("?operationName=${operationName}")
            .append("&variables=${urlEncode(variables)}")
            .append("&extensions=${urlEncode((extensions(persistedQuery)))}")
        val request = Request.Builder().url(builder.toString()).build()
        val raw = call(request)
        if (print) println(raw)
        return Response(json.decode<T>(raw), raw)
    }

    suspend fun graphMutate(
        operationName: String,
        persistedQuery: String,
        variables: JsonObject
    ): String {
        val request = Request.Builder()
            .url("https://api-partner.spotify.com/pathfinder/v1/query")
            .post(
                buildJsonObject {
                    put("operationName", operationName)
                    put("variables", variables)
                    put("extensions", extensions(persistedQuery))
                }.toString().toRequestBody("application/json".toMediaType())
            )
        return call(request.build())
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
        runCatching { auth.getToken() }.getOrElse {
            if (it is Authentication.Error) onError(it)
            throw it
        }
        val response = callGetBody(request)
        return if (response.startsWith('{')) response else {
            throw IOException("Invalid response: $response")
        }
    }

    fun urlEncode(data: JsonObject) = urlEncode(data.toString())
    fun urlEncode(data: String): String = URLEncoder.encode(data, "UTF-8")

    fun extensions(persistedQuery: String): JsonObject {
        return buildJsonObject {
            putJsonObject("persistedQuery") {
                put("version", 1)
                put("sha256Hash", persistedQuery)
            }
        }
    }

    suspend fun callGetBody(request: Request) = run {
        val res = client.newCall(request).await()
        if (res.commonIsSuccessful) res.body.string()
        else throw IOException("Failed to call ${request.url}: ${res.code}")
    }

    companion object {
        val userAgent =
            "User-Agent" to "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.0.0 Safari/537.36"
    }
}