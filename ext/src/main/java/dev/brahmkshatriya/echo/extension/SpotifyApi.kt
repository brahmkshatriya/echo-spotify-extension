package dev.brahmkshatriya.echo.extension

import dev.brahmkshatriya.echo.common.helpers.ContinuationCallback.Companion.await
import dev.brahmkshatriya.echo.extension.models.Client
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.URLEncoder

class SpotifyApi(
    private val token: String
) {
    private val client = OkHttpClient.Builder().build()
    val parser = Json {
        ignoreUnknownKeys = true
    }

    suspend inline fun <reified T> query(
        operationName: String, persistedQuery: String, variables: JsonObject = buildJsonObject { }
    ): T {
        if (accessToken == null) createAccessToken()

        val builder = StringBuilder("https://api-partner.spotify.com/pathfinder/v1/query")
            .append("?operationName=$operationName")
            .append("&variables=${encode(variables)}")
            .append("&extensions=${extensions(persistedQuery)}")

        val request = Request.Builder()
            .url(builder.toString())
            .addHeader("Authorization", "Bearer $accessToken")
            .addHeader(userAgent.first, userAgent.second)
            .build()

        val response = callGetBody(request)

        return if (response.startsWith('{')) parser.decodeFromString<T>(response)
        else {
            createAccessToken()
            val newResponse = callGetBody(request)
            parser.decodeFromString<T>(newResponse)
        }
    }


    var accessToken: String? = null
    suspend fun createAccessToken() {
        val req = Request.Builder()
            .url("https://open.spotify.com/get_access_token?reason=transport&productType=web-player")
            .header("Cookie", "sp_dc=$token")
            .build()
        accessToken = parser.decodeFromString<AccessToken>(callGetBody(req)).accessToken
    }

    inline fun <reified T> encode(data: T): String =
        URLEncoder.encode(parser.encodeToString(data), "UTF-8")

    fun extensions(persistedQuery: String): String {
        val extensions = Client.Extensions(Client.PersistedQuery(1, persistedQuery))
        return encode(extensions)
    }

    suspend fun callGetBody(request: Request) = client.newCall(request).await().body.string()

    @Serializable
    data class AccessToken(
        val clientId: String,
        val accessToken: String,
        val accessTokenExpirationTimestampMs: Long,
        val isAnonymous: Boolean
    )

    companion object {
        val userAgent =
            "User-Agent" to "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.0.0 Safari/537.36"
    }
}