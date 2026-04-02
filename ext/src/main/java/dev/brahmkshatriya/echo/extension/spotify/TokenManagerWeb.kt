package dev.brahmkshatriya.echo.extension.spotify

import dev.brahmkshatriya.echo.common.helpers.ContinuationCallback.Companion.await
import dev.brahmkshatriya.echo.extension.spotify.SpotifyApi.Companion.userAgent
import dev.brahmkshatriya.echo.extension.spotify.TOTP.convertToHex
import kotlinx.serialization.Serializable
import okhttp3.Cookie
import okhttp3.FormBody
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import kotlin.io.encoding.ExperimentalEncodingApi

// Was to lazy, so Claude made it. Judge me if you will - Yours Luft

class TokenManagerWeb(
    private val api: SpotifyApi,
) {
    private val json = api.json
    val client = OkHttpClient.Builder()
        .addInterceptor {
            val req = it.request().newBuilder()
            val cookie = api.cookie
            if (cookie != null) req.addHeader("Cookie", cookie)
            req.addHeader(userAgent.first, userAgent.second)
            req.addHeader("Referer", "https://open.spotify.com/")
            it.proceed(req.build())
        }.build()

    var accessToken: String? = null
    private var tokenExpiration: Long = 0

    private suspend fun createAnonymousAccessToken(): String {
        val request = Request.Builder()
            .url(getAnonymousTokenUrl())
            .build()
        client.newCall(request).await().use { response ->
            val body = response.body.string()
            val token = runCatching { json.decode<TokenResponse>(body) }.getOrElse {
                throw runCatching { json.decode<ErrorMessage>(body).error }.getOrElse {
                    Exception(body.ifEmpty { "Token Code ${response.code}" })
                }
            }

            accessToken = token.accessToken
            tokenExpiration = token.accessTokenExpirationTimestampMs - 5 * 60 * 1000
            return accessToken!!
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    private suspend fun getAnonymousTokenUrl(): String {
        val (secret, version) = getDataFromSite()
        val time = System.currentTimeMillis()
        val totp = TOTP.generateTOTP(secret, (time / 30000).toHexString().uppercase())
        return "https://open.spotify.com/api/token" +
                "?reason=init&productType=web-player&totp=$totp&totpServer=$totp&totpVer=$version"
    }

    private val secretsUrl =
        "https://raw.githubusercontent.com/itsmechinmoy/echo-extensions/refs/heads/main/noidea.txt"

    @OptIn(ExperimentalEncodingApi::class)
    private suspend fun getDataFromSite(): Secret {
        val string = client.newCall(
            Request.Builder()
                .url(secretsUrl)
                .build()
        ).await().body.string()
        val (secret, version) = json.decode<Secret>(string)
        return Secret(convertToHex(secret), version)
    }

    companion object {
        private const val DEVICE_AUTH_URL = "https://accounts.spotify.com/oauth2/device/authorize"
        private const val DEVICE_TOKEN_URL = "https://accounts.spotify.com/api/token"
        private const val DEVICE_RESOLVE_URL = "https://accounts.spotify.com/pair/api/resolve"
        private const val DEVICE_CLIENT_ID = "65b708073fc0480ea92a077233ca87bd"
        private const val DEVICE_SCOPE =
            "app-remote-control,playlist-modify,playlist-modify-private,playlist-modify-public," +
                    "playlist-read,playlist-read-collaborative,playlist-read-private,streaming," +
                    "transfer-auth-session,ugc-image-upload,user-follow-modify,user-follow-read," +
                    "user-library-modify,user-library-read,user-modify,user-modify-playback-state," +
                    "user-modify-private,user-personalized,user-read-birthdate," +
                    "user-read-currently-playing,user-read-email,user-read-play-history," +
                    "user-read-playback-position,user-read-playback-state,user-read-private," +
                    "user-read-recently-played,user-top-read"
        private const val DEVICE_GRANT_TYPE = "urn:ietf:params:oauth:grant-type:device_code"
        private const val DEVICE_FLOW_USER_AGENT = "Spotify/126600447 Win32_x86_64/0 (PC laptop)"

        private const val DESKTOP_FLOW_USER_AGENT_HEADER =
            "Spotify/126600447 Win32_x86_64/0 (PC laptop)"
        private val JSON_MEDIA_TYPE = "application/json".toMediaType()

        private val NEXT_DATA_REGEX = Regex(
            """<script id="__NEXT_DATA__" type="application/json"[^>]*>(.*?)</script>""",
            setOf(RegexOption.DOT_MATCHES_ALL),
        )
    }

    suspend fun createDesktopAccessToken(spDc: String): String {
        val aT = createDesktopAccessTokenData(spDc)
        accessToken = aT.accessToken
        tokenExpiration = System.currentTimeMillis() + aT.expiresIn.toLong() * 1000
        return accessToken!!
    }

    suspend fun createDesktopAccessTokenData(spDc: String): DesktopAccessToken {
        val authorization = initiateDesktopDeviceAuthorization()
        val flowClient = newDesktopDeviceFlowClient(spDc)
        val verification = parseDesktopVerificationPage(
            flowClient = flowClient,
            verificationUrl = authorization.verificationUriComplete,
        )

        submitDesktopUserCode(
            flowClient = flowClient,
            userCode = authorization.userCode,
            flowContext = verification.flowContext,
            csrfToken = verification.csrfToken,
            refererUrl = authorization.verificationUriComplete,
        )

        return exchangeDesktopDeviceCode(authorization.deviceCode)
    }

    private suspend fun initiateDesktopDeviceAuthorization(): DesktopDeviceAuthorization {
        val request = Request.Builder()
            .url(DEVICE_AUTH_URL)
            .addHeader("User-Agent", DEVICE_FLOW_USER_AGENT)
            .post(
                FormBody.Builder()
                    .add("client_id", DEVICE_CLIENT_ID)
                    .add("scope", DEVICE_SCOPE)
                    .build()
            )
            .build()

        client.newCall(request).await().use { response ->
            val body = response.readRequiredBody("Desktop device authorization")
            val payload = json.decode<DesktopDeviceAuthorizationResponse>(body)

            return DesktopDeviceAuthorization(
                deviceCode = payload.deviceCode,
                userCode = payload.userCode,
                verificationUriComplete = payload.verificationUriComplete,
            )
        }
    }

    private suspend fun parseDesktopVerificationPage(
        flowClient: OkHttpClient,
        verificationUrl: String,
    ): DesktopVerificationContext {
        val request = Request.Builder()
            .url(verificationUrl)
            .addHeader("User-Agent", DEVICE_FLOW_USER_AGENT)
            .get()
            .build()

        flowClient.newCall(request).await().use { response ->
            val html = response.readRequiredBody("Desktop verification page")
            val flowContext = response.request.url.queryParameter("flow_ctx")
                ?.substringBefore(':')
                ?.takeIf { it.isNotBlank() }
                ?: throw IllegalStateException("Desktop verification page did not contain flow_ctx")

            return DesktopVerificationContext(
                flowContext = flowContext,
                csrfToken = extractDesktopCsrfToken(html),
            )
        }
    }

    private suspend fun submitDesktopUserCode(
        flowClient: OkHttpClient,
        userCode: String,
        flowContext: String,
        csrfToken: String,
        refererUrl: String,
    ) {
        val resolveUrl = DEVICE_RESOLVE_URL.toHttpUrl().newBuilder()
            .addQueryParameter("flow_ctx", "$flowContext:${System.currentTimeMillis() / 1000}")
            .build()

        val request = Request.Builder()
            .url(resolveUrl)
            .addHeader("User-Agent", DEVICE_FLOW_USER_AGENT)
            .addHeader("x-csrf-token", csrfToken)
            .addHeader("referer", refererUrl)
            .addHeader("origin", "https://accounts.spotify.com")
            .post(
                json.encode(DesktopResolveRequest(userCode))
                    .toRequestBody(JSON_MEDIA_TYPE)
            )
            .build()

        flowClient.newCall(request).await().use { response ->
            val body = response.readRequiredBody("Desktop user code submission")
            val payload = json.decode<DesktopResolveResponse>(body)
            if (payload.result != "ok") {
                throw IllegalStateException("Desktop user code submission failed: $body")
            }
        }
    }

    private suspend fun exchangeDesktopDeviceCode(deviceCode: String): DesktopAccessToken {
        val request = Request.Builder()
            .url(DEVICE_TOKEN_URL)
            .addHeader("User-Agent", DEVICE_FLOW_USER_AGENT)
            .post(
                FormBody.Builder()
                    .add("client_id", DEVICE_CLIENT_ID)
                    .add("device_code", deviceCode)
                    .add("grant_type", DEVICE_GRANT_TYPE)
                    .build()
            )
            .build()

        client.newCall(request).await().use { response ->
            val body = response.readRequiredBody("Desktop device token exchange")
            val payload = json.decode<DesktopTokenResponse>(body)

            return DesktopAccessToken(
                accessToken = payload.accessToken,
                expiresIn = payload.expiresIn,
            )
        }
    }

    private fun newDesktopDeviceFlowClient(spDc: String): OkHttpClient {
        val cookieStore = DesktopCookieStore().apply {
            seed(
                Cookie.Builder()
                    .name("sp_dc")
                    .value(spDc)
                    .domain("accounts.spotify.com")
                    .path("/")
                    .secure()
                    .httpOnly()
                    .build()
            )
            seed(
                Cookie.Builder()
                    .name("sp_dc")
                    .value(spDc)
                    .domain("spotify.com")
                    .path("/")
                    .secure()
                    .httpOnly()
                    .build()
            )
        }

        return client.newBuilder()
            .addNetworkInterceptor { chain ->
                val original = chain.request()
                val cookieHeader = mergeCookieHeader(
                    original.header("Cookie"),
                    cookieStore.loadForRequest(original.url),
                )

                val request = original.newBuilder()
                    .header("User-Agent", DESKTOP_FLOW_USER_AGENT_HEADER)
                    .apply {
                        if (cookieHeader.isNotBlank()) {
                            header("Cookie", cookieHeader)
                        }
                        if (original.header("Referer").isNullOrBlank()) {
                            header("Referer", "https://open.spotify.com/")
                        }
                    }
                    .build()

                val response = chain.proceed(request)

                response.headers("Set-Cookie").forEach { raw ->
                    Cookie.parse(request.url, raw)?.let(cookieStore::store)
                }

                response
            }
            .followRedirects(true)
            .followSslRedirects(true)
            .build()
    }

    private fun extractDesktopCsrfToken(html: String): String {
        val scriptBody = NEXT_DATA_REGEX.find(html)?.groupValues?.get(1)
            ?: throw IllegalStateException("Desktop verification page missing __NEXT_DATA__")
        val payload = json.decode<DesktopNextData>(scriptBody)

        return payload.props?.initialToken
            ?.takeIf { it.isNotBlank() }
            ?: throw IllegalStateException("Desktop verification page missing CSRF token")
    }

    data class DesktopAccessToken(
        val accessToken: String,
        val expiresIn: Int,
    )

    private data class DesktopDeviceAuthorization(
        val deviceCode: String,
        val userCode: String,
        val verificationUriComplete: String,
    )

    private data class DesktopVerificationContext(
        val flowContext: String,
        val csrfToken: String,
    )

    @Serializable
    private data class DesktopDeviceAuthorizationResponse(
        val device_code: String,
        val user_code: String,
        val verification_uri_complete: String,
    ) {
        val deviceCode: String
            get() = device_code

        val userCode: String
            get() = user_code

        val verificationUriComplete: String
            get() = verification_uri_complete
    }

    @Serializable
    private data class DesktopResolveRequest(
        val code: String,
    )

    @Serializable
    private data class DesktopResolveResponse(
        val result: String,
    )

    @Serializable
    private data class DesktopTokenResponse(
        val access_token: String,
        val expires_in: Int,
    ) {
        val accessToken: String
            get() = access_token

        val expiresIn: Int
            get() = expires_in
    }

    @Serializable
    private data class DesktopNextData(
        val props: DesktopNextDataProps? = null,
    )

    @Serializable
    private data class DesktopNextDataProps(
        val initialToken: String? = null,
    )

    @Serializable
    data class Secret(
        val secret: String,
        val version: Int,
    )

    @Serializable
    data class TokenResponse(
        val isAnonymous: Boolean,
        val accessTokenExpirationTimestampMs: Long,
        val clientId: String,
        val accessToken: String,
    )

    @Serializable
    data class ErrorMessage(
        val error: Error,
    )

    private fun mergeCookieHeader(
        originalHeader: String?,
        scopedCookies: List<Cookie>,
    ): String {
        val cookies = linkedMapOf<String, String>()
        originalHeader
            ?.split(';')
            ?.map { it.trim() }
            ?.filter { it.isNotBlank() && it.contains('=') }
            ?.forEach {
                cookies[it.substringBefore('=')] = it.substringAfter('=')
            }
        scopedCookies.forEach { cookies[it.name] = it.value }
        return cookies.entries.joinToString("; ") { "${it.key}=${it.value}" }
    }

    private class DesktopCookieStore {
        private val cookies = mutableListOf<Cookie>()

        @Synchronized
        fun seed(cookie: Cookie) = store(cookie)

        @Synchronized
        fun store(cookie: Cookie) {
            if (cookie.expiresAt <= System.currentTimeMillis()) {
                cookies.removeAll { it.name == cookie.name && it.domain == cookie.domain && it.path == cookie.path }
                return
            }

            cookies.removeAll { it.name == cookie.name && it.domain == cookie.domain && it.path == cookie.path }
            cookies += cookie
            cookies.removeAll { it.expiresAt <= System.currentTimeMillis() }
        }

        @Synchronized
        fun loadForRequest(url: HttpUrl): List<Cookie> {
            cookies.removeAll { it.expiresAt <= System.currentTimeMillis() }
            return cookies.filter { it.matches(url) }
        }
    }

    private fun Response.readRequiredBody(step: String): String {
        if (!isSuccessful) {
            val errorBody = body.string().takeIf { it.isNotBlank() } ?: "$code $message"
            throw IllegalStateException("$step failed: $errorBody")
        }

        return body.string().takeIf { it.isNotBlank() }
            ?: throw IllegalStateException("$step returned an empty body")
    }

    suspend fun getToken() =
        if (accessToken == null || !isTokenWorking(tokenExpiration)) {
            val spDc = getSpDc()
            if (spDc.isNullOrBlank()) createAnonymousAccessToken()
            else createDesktopAccessToken(spDc)
        }
        else accessToken!!

    fun clear() {
        accessToken = null
        tokenExpiration = 0
    }

    private fun isTokenWorking(expiry: Long): Boolean {
        return (System.currentTimeMillis() < expiry)
    }

    private fun getSpDc(): String? {
        return api.cookie
            ?.split(';')
            ?.asSequence()
            ?.map { it.trim() }
            ?.firstOrNull { it.startsWith("sp_dc=") }
            ?.substringAfter('=')
            ?.takeIf { it.isNotBlank() }
    }

    @Serializable
    data class Error(
        val code: Int,
        override val message: String,
    ) : Exception(message)
}
