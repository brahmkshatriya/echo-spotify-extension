package dev.brahmkshatriya.echo.extension

import dev.brahmkshatriya.echo.common.helpers.ContinuationCallback.Companion.await
import dev.brahmkshatriya.echo.common.settings.Settings
import kotlinx.io.IOException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonObjectBuilder
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlinx.serialization.json.putJsonObject
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.URLEncoder

class SpotifyApi(
    val settings: Settings
) {
    var token: String? = null
    val auth = Authentication(this)
    val json = Json()

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
            request.addHeader(userAgent.first, userAgent.second)
            auth.accessToken?.let {
                request.addHeader("Authorization", "Bearer $it")
            }
            chain.proceed(request.build())
        }
        .build()

    suspend inline fun <reified T> query(
        operationName: String, persistedQuery: String, variables: JsonObject = buildJsonObject { }
    ): T {
        auth.checkToken()
        val builder = StringBuilder("https://api-partner.spotify.com/pathfinder/v1/query")
            .append("?operationName=$operationName")
            .append("&variables=${urlEncode(variables)}")
            .append("&extensions=${extensions(persistedQuery)}")
        val request = Request.Builder().url(builder.toString()).build()
        val response = callGetBody(request)

        return if (response.startsWith('{')) json.decode<T>(response)
        else { throw IOException("Invalid response: $response") }
    }

    inline fun <reified T> urlEncode(data: T): String =
        URLEncoder.encode(json.encode(data), "UTF-8")

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