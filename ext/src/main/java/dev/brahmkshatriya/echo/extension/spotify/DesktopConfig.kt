package dev.brahmkshatriya.echo.extension.spotify

import dev.brahmkshatriya.echo.common.helpers.ContinuationCallback.Companion.await
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.atomic.AtomicBoolean

object DesktopConfig {
    const val CLIENT_ID = "65b708073fc0480ea92a077233ca87bd"
    const val OS = "windows"
    const val OS_VERSION = "NT 10.0"
    const val DEVICE_BRAND = "Microsoft"
    const val DEVICE_MODEL = "PC"
    const val DEVICE_TYPE = "computer"
    const val PLATFORM_HEADER = "Win32_x86_64"
    const val ACCEPT_LANGUAGE = "en"

    private const val DEFAULT_APP_VERSION = "1.2.66.510.gd0327113"

    @Volatile var appVersion: String = DEFAULT_APP_VERSION
        private set

    val build: String
        get() = buildFromAppVersion(appVersion)

    val userAgent: String get() = "Spotify/$build $PLATFORM_HEADER/0 (PC laptop)"

    private fun buildFromAppVersion(version: String): String {
        val parts = version.split('.')
        if (parts.size < 4) return version.filter { it.isDigit() }
        val (major, minor, patch, subPatch) = parts
        return "$major$minor${patch}00$subPatch"
    }

    private val refreshAttempted = AtomicBoolean(false)

    suspend fun maybeRefresh(client: OkHttpClient) {
        if (!refreshAttempted.compareAndSet(false, true)) return
        runCatching {
            val request = Request.Builder()
                .url("https://loadspot.pages.dev/versions.json")
                .build()
            client.newCall(request).await().use { response ->
                if (!response.isSuccessful) return
                val body = response.body.string()
                VERSION_REGEX.find(body)?.groupValues?.get(1)
                    ?.takeIf { it.matches(Regex("\\d+\\.\\d+\\.\\d+\\.\\d+\\.g[0-9a-f]+")) }
                    ?.let { appVersion = it }
            }
        }
    }

    private val VERSION_REGEX = Regex(""""fullversion"\s*:\s*"([^"]+)"""")
}
