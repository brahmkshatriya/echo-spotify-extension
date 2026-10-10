package dev.brahmkshatriya.echo.extension

import dev.brahmkshatriya.echo.common.models.Streamable
import dev.brahmkshatriya.echo.extension.spotify.AudioFormat
import spotify.extendedmetadata.metadata.ExtendedMetadataProto.AudioFile

/** Per-track fallback for lossless files whose PlayPlay licenses are unavailable. */
internal object AudioStreamFallback {
    private val vorbisFormats = listOf(
        AudioFile.Format.OGG_VORBIS_320,
        AudioFile.Format.OGG_VORBIS_160,
        AudioFile.Format.OGG_VORBIS_96,
    )

    fun isFlac(format: Int): Boolean = format == AudioFormat.FLAC_FLAC ||
        format == AudioFormat.FLAC_FLAC_24BIT

    fun bestVorbisFile(files: List<AudioFile>): AudioFile? =
        vorbisFormats.firstNotNullOfOrNull { format ->
            files.firstOrNull { it.format == format && it.hasFileId() && it.fileId.size() == 20 }
        }

    fun attachFallback(extras: Map<String, String>, file: AudioFile?): Map<String, String> {
        if (file == null || file.fileId.size() != 20 || file.format !in vorbisFormats)
            return extras
        val id = file.fileId.toByteArray().joinToString("") { "%02x".format(it.toInt() and 255) }
        return extras + mapOf(
            "fallbackVorbisId" to id,
            "fallbackVorbisFormat" to file.format.number.toString(),
        )
    }

    fun onLicenseRejection(streamable: Streamable, failure: IllegalStateException): Streamable? {
        val format = streamable.extras["formatNum"]?.toIntOrNull() ?: return null
        if (!isFlac(format)) return null
        if (!isLicenseRejection(failure)) return null
        val fileId = streamable.extras["fallbackVorbisId"]
            ?.takeIf { it.matches(Regex("[a-fA-F0-9]{40}")) } ?: return null
        val fallbackFormat = streamable.extras["fallbackVorbisFormat"]?.toIntOrNull()
            ?.takeIf { it in listOf(
                AudioFormat.OGG_VORBIS_320,
                AudioFormat.OGG_VORBIS_160,
                AudioFormat.OGG_VORBIS_96,
            ) } ?: return null
        if (fileId.equals(streamable.id, ignoreCase = true)) return null
        return Streamable.server(
            id = fileId,
            quality = AudioFormat.quality(fallbackFormat),
            title = AudioFormat.name(fallbackFormat).replace('_', ' '),
            extras = mapOf(
                "fileId" to fileId,
                "formatNum" to fallbackFormat.toString(),
                "formatName" to AudioFormat.name(fallbackFormat),
                "gid" to streamable.extras["gid"].orEmpty(),
            ),
        )
    }

    fun isLicenseRejection(failure: IllegalStateException): Boolean {
        val message = failure.message.orEmpty()
        // Do not conceal unrelated decoder errors, authorization or cancellation.
        return message.contains("PlayPlay request failed") &&
            Regex("(?<!\\d)(?:400|403|404)(?!\\d)").containsMatchIn(message)
    }
}
