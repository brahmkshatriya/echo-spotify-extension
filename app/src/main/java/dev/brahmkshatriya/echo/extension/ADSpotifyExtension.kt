package dev.brahmkshatriya.echo.extension

import android.annotation.SuppressLint
import android.app.Application
import com.luftnos.unplayplay.PCdm.playPlayRequest
import com.luftnos.unplayplay.PCdm.playPlayResponse
import com.luftnos.unplayplay.Unplayplay
import com.luftnos.unplayplay.Unplayplay.to16ByteArray
import dev.brahmkshatriya.echo.common.helpers.ContinuationCallback.Companion.await
import dev.brahmkshatriya.echo.common.models.Streamable
import dev.brahmkshatriya.echo.common.models.Streamable.Media.Companion.toMedia
import dev.brahmkshatriya.echo.common.models.Streamable.Source.Companion.toSource
import dev.brahmkshatriya.echo.common.settings.SettingSwitch
import dev.brahmkshatriya.echo.extension.spotify.models.Metadata4Track.Format
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.InputStream
import java.math.BigInteger
import javax.crypto.Cipher
import javax.crypto.CipherInputStream
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

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
    override val supportsPlayPlay: Boolean = true
    override val showWidevineStreams: Boolean
        get() = setting.getBoolean("show_widevine_streams") ?: super.showWidevineStreams

    override val settingItems
        get() = listOf(
            SettingSwitch(
                "Show Widevine Streams",
                "show_widevine_streams",
                "Whether to show Widevine streams in song servers, they use on device drm decryption. Might not be supported on all devices.",
                false
            )
        ) + super.settingItems

    override suspend fun loadStreamableMedia(
        streamable: Streamable,
        isDownload: Boolean
    ): Streamable.Media {
        if (streamable.type != Streamable.MediaType.Server)
            return super.loadStreamableMedia(streamable, isDownload)
        val format = Format.valueOf(streamable.extras["format"]!!)
        return when (format) {
            Format.OGG_VORBIS_320,
            Format.OGG_VORBIS_160,
            Format.OGG_VORBIS_96,
            Format.AAC_24 ->   stream(streamable)
            else -> super.loadStreamableMedia(streamable, isDownload)
        }
    }

    private suspend fun getPlayPlayKey(
        fileId: String
    ): ByteArray {
        val raw = api.client.newCall(
            Request.Builder()
                .url(Unplayplay.getPlayPlayUrl(fileId))
                .post(playPlayRequest(Unplayplay.token).toRequestBody())
                .build()
        ).await()
        val resp = raw.body.bytes()
        return playPlayResponse(resp)
    }

    private suspend fun stream(streamable: Streamable): Streamable.Media {
        val fileId = streamable.id
        val key = Unplayplay.deobfuscateKey(fileId.hexToByteArray(), getPlayPlayKey(fileId))
        val url = queries.storageResolve(streamable.id).json.cdnUrl.random()
        return Streamable.InputProvider { position, length ->
            decryptFromPosition(key, position, length) { pos, len ->
                val range = "bytes=$pos-${len?.toString() ?: ""}"
                val request = Request.Builder().url(url)
                    .header("Range", range)
                    .build()
                val resp = api.client.newCall(request).await()
                val actualLength = resp.header("Content-Length")?.toLong() ?: -1L
                resp.body.byteStream() to actualLength
            }
        }.toSource().toMedia()
    }

    private suspend fun decryptFromPosition(
        key: ByteArray,
        position: Long,
        length: Long,
        provider: suspend (Long, Long?) -> Pair<InputStream, Long>
    ): Pair<InputStream, Long> {
        val newPos = position + 0xA7
        val alignedPos = newPos - (newPos % 16)
        val blockOffset = (newPos % 16).toInt()
        val len = if (length < 0) null else length + newPos - 1
        val (input, contentLength) = provider(alignedPos, len)

        val ivCounter = Unplayplay.AUDIO_STREAMER_IV.add(BigInteger.valueOf(alignedPos / 16))
        val ivBytes = ivCounter.to16ByteArray()

        val cipher = Cipher.getInstance("AES/CTR/NoPadding")
        cipher.init(
            Cipher.DECRYPT_MODE,
            SecretKeySpec(key, "AES"),
            IvParameterSpec(ivBytes)
        )

        val cipherStream = CipherInputStream(input, cipher)

        cipherStream.skipBytes(blockOffset)
        return cipherStream to contentLength - blockOffset
    }
}