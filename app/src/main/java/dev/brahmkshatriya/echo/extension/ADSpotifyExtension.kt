package dev.brahmkshatriya.echo.extension

import android.annotation.SuppressLint
import android.app.Application
import com.luftnos.unplayplay.PCdm.playPlayRequest
import com.luftnos.unplayplay.PCdm.playPlayResponse
import com.luftnos.unplayplay.Unplayplay
import dev.brahmkshatriya.echo.common.helpers.ContinuationCallback.Companion.await
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import kotlin.text.hexToByteArray

@Suppress("unused")
class ADSpotifyExtension : SpotifyExtension() {

    @SuppressLint("PrivateApi")
    private fun getApplication(): Application {
        return Class.forName("android.app.ActivityThread").getMethod("currentApplication")
            .invoke(null) as Application
    }

    override val filesDir by lazy { File(getApplication().filesDir, "spotify") }
    override val showWidevineStreams = false

    private val client = OkHttpClient()

    override suspend fun getKey(accessToken: String, fileId: String): ByteArray {
        val request = Request.Builder()
        request.url(Unplayplay.getPlayPlayUrl(fileId))
        request.addHeader("Authorization", "Bearer $accessToken")
        request.post(playPlayRequest(Unplayplay.token).toRequestBody())
        val raw = client.newCall(request.build()).await()
        val key = Unplayplay.deobfuscateKey(fileId.hexToByteArray(), playPlayResponse(raw.body.bytes()))
        return key
    }
}