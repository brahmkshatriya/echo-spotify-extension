package dev.brahmkshatriya.echo.extension.spotify

import dev.brahmkshatriya.echo.common.helpers.ContinuationCallback.Companion.await
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import okhttp3.FormBody
import okhttp3.Request
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.random.Random

class MercuryAccessToken(
    private val spotifyApi: SpotifyApi,
) {
    suspend fun get(): String {
        val client = spotifyApi.auth.httpClient

        val codeVerifier = generateCode()
        val challenge = generateCodeChallenge(codeVerifier)

        val authUrl = Request.Builder().url(getAuthUrl(challenge)).head().build()
        val cookies = client.newCall(authUrl).await().headers("set-cookie")
        val crsfToken = cookies.firstOrNull { it.startsWith("csrf_token=") }
            ?.substringAfter("csrf_token=")?.substringBefore(";")
            ?: throw Exception("CRSF Token not found")

        val codeReq = Request.Builder()
            .url("https://accounts.spotify.com/en/authorize/accept?ajax_redirect=1")
            .addHeader("Cookie", "csrf_token=$crsfToken")
            .post(
                FormBody.Builder()
                    .add("request", "undefined")
                    .add("response_type", "code")
                    .add("client_id", "65b708073fc0480ea92a077233ca87bd")
                    .add("redirect_uri", "http://127.0.0.1:5588/login")
                    .add("code_challenge", challenge)
                    .add("code_challenge_method", "S256")
                    .add("scope", "streaming")
                    .add("csrf_token", crsfToken)
                    .build()
            )
            .build()
        val code = client.newCall(codeReq).await()
            .header("Location")?.substringAfter("code=")
            ?: throw Exception("Location header not found")

        val accessTokenJson = client.newCall(
            Request.Builder()
                .url("https://accounts.spotify.com/api/token")
                .post(
                    FormBody.Builder()
                        .add("grant_type", "authorization_code")
                        .add("client_id", "65b708073fc0480ea92a077233ca87bd")
                        .add("redirect_uri", "http://127.0.0.1:5588/login")
                        .add("code", code)
                        .add("code_verifier", codeVerifier)
                        .build()
                )
                .build()
        ).await().body.string()
        return spotifyApi.json.decode<Response>(accessTokenJson).accessToken
            ?: throw Exception("Access Token not found")
    }

    private val possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    private fun generateCode() = buildString {
        repeat(128) { append(possible[Random.nextInt(possible.length)]) }
    }

    @OptIn(ExperimentalEncodingApi::class)
    private fun generateCodeChallenge(code: String): String {

        val digest = MessageDigest.getInstance("SHA-256")!!
        val hashed = digest.digest(code.toByteArray(StandardCharsets.UTF_8))
        return Base64.encode(hashed).replace("=", "")
            .replace("+", "-")
            .replace("/", "_")
    }

    @Serializable
    data class Response(
        @SerialName("access_token")
        val accessToken: String? = null,
        @SerialName("token_type")
        val tokenType: String? = null,
        @SerialName("expires_in")
        val expiresIn: Long? = null,
        @SerialName("refresh_token")
        val refreshToken: String? = null,
        val scope: String? = null
    )

    private fun getAuthUrl(challenge: String) =
        "https://accounts.spotify.com/en/authorize?response_type=code&client_id=65b708073fc0480ea92a077233ca87bd&redirect_uri=http://127.0.0.1:5588/login&code_challenge=$challenge&code_challenge_method=S256&scope=streaming"

}