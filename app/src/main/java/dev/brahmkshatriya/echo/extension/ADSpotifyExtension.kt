package dev.brahmkshatriya.echo.extension

import android.annotation.SuppressLint
import android.app.Application
import dev.brahmkshatriya.echo.common.settings.SettingSwitch
import java.io.File

@Suppress("unused")
class ADSpotifyExtension : SpotifyExtension() {

    @SuppressLint("PrivateApi")
    private fun getApplication(): Application {
        return Class.forName("android.app.ActivityThread").getMethod("currentApplication")
            .invoke(null) as Application
    }

    override val cacheDir: File
        get() = getApplication().cacheDir.resolve("spotify").apply {
            if (!exists()) mkdirs()
        }

    override val showWidevineStreams: Boolean
        get() = setting.getBoolean("show_widevine_streams") ?: super.showWidevineStreams

    override suspend fun getSettingItems(): List<SettingSwitch> {
        return listOf(
            SettingSwitch(
                "Show Widevine Streams",
                "show_widevine_streams",
                "Whether to show Widevine streams in song servers, they use on device drm decryption. Might not be supported on all devices.",
                showWidevineStreams
            )
        ) + super.getSettingItems()
    }
}