package dev.brahmkshatriya.echo.extension.spotify

import dev.brahmkshatriya.echo.common.helpers.ContinuationCallback.Companion.await
import kotlinx.io.IOException
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonObjectBuilder
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlinx.serialization.json.putJsonObject
import okhttp3.OkHttpClient
import okhttp3.Request
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

    val client = OkHttpClient.Builder()
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

    suspend inline fun <reified T> graphQuery(
        operationName: String,
        persistedQuery: String,
        variables: JsonObject = buildJsonObject { },
        print: Boolean = false
    ): T {
        val builder = StringBuilder("https://api-partner.spotify.com/pathfinder/v1/query")
            .append("?operationName=${operationName}")
            .append("&variables=${urlEncode(variables)}")
            .append("&extensions=${extensions(persistedQuery)}")
        val request = Request.Builder().url(builder.toString()).build()
        return json.decode<T>(
            call(request).also { if (print) println(it) }
        )
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

    fun extensions(persistedQuery: String): String {
        val extensions = buildJsonObject {
            putJsonObject("persistedQuery") {
                put("version", 1)
                put("sha256Hash", persistedQuery)
            }
        }
        return urlEncode(extensions)
    }

    suspend fun callGetBody(request: Request) = client.newCall(request).await().body.string()

    companion object {
        val userAgent =
            "User-Agent" to "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.0.0 Safari/537.36"

        fun JsonObjectBuilder.applyPagePagination(offset: Int, limit: Int) = apply {
            putJsonObject("pagePagination") { put("offset", offset); put("limit", limit) }
        }

        fun JsonObjectBuilder.applySectionPagination(offset: Int, limit: Int) = apply {
            putJsonObject("sectionPagination") { put("offset", offset); put("limit", limit) }
        }
    }
}