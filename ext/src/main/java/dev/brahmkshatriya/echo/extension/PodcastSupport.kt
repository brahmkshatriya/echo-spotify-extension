package dev.brahmkshatriya.echo.extension

import dev.brahmkshatriya.echo.common.models.Album
import dev.brahmkshatriya.echo.common.models.Artist
import dev.brahmkshatriya.echo.common.models.ImageHolder.Companion.toImageHolder
import dev.brahmkshatriya.echo.common.models.Streamable
import dev.brahmkshatriya.echo.common.models.Track
import dev.brahmkshatriya.echo.extension.spotify.AudioFormat
import dev.brahmkshatriya.echo.extension.spotify.Base62
import spotify.extendedmetadata.audiofiles.AudioFilesExtensionProto.AudioFilesExtensionResponse
import spotify.extendedmetadata.metadata.ExtendedMetadataProto
import spotify.extendedmetadata.metadata.ExtendedMetadataProto.AudioFile
import spotify.extendedmetadata.metadata.ExtendedMetadataProto.BatchedExtensionResponse
import spotify.extendedmetadata.metadata.ExtendedMetadataProto.ExtensionKind
import java.net.URI

/** Spotify's episode catalog is independent of TRACK_V4 and of music's PlayPlay file selection. */
internal object PodcastSupport {
    private fun ByteArray.hex(): String = joinToString("") { "%02x".format(it.toInt() and 255) }

    fun BatchedExtensionResponse.bytes(kind: ExtensionKind, uri: String): ByteArray? {
        val entries = extendedMetadataList.firstOrNull { it.extensionKind == kind }
            ?.extensionDataList.orEmpty()
        // Older metadata responses sometimes omit entity_uri for single-entity queries.
        val entry = entries.firstOrNull { it.entityUri == uri } ?: entries.singleOrNull {
            it.entityUri.isBlank()
        }
        return entry?.takeIf {
            it.hasExtensionData() &&
                (!it.hasHeader() || !it.header.hasStatusCode() || it.header.statusCode in 200..299)
        }?.extensionData?.value?.toByteArray()
    }

    fun BatchedExtensionResponse.episode(uri: String): ExtendedMetadataProto.Episode? =
        bytes(ExtensionKind.EPISODE_V4, uri)?.let(ExtendedMetadataProto.Episode::parseFrom)

    fun BatchedExtensionResponse.show(uri: String): ExtendedMetadataProto.Show? =
        bytes(ExtensionKind.SHOW_V4, uri)?.let(ExtendedMetadataProto.Show::parseFrom)

    fun ExtendedMetadataProto.Show.episodeUris(): List<String> = episodeList
        .asSequence().filter { it.hasGid() && it.gid.size() == 16 }
        .map { "spotify:episode:${Base62.encode(it.gid.toByteArray().hex())}" }
        .distinct().toList()

    fun ExtendedMetadataProto.Show.toAlbum(uri: String, fallback: Album? = null): Album = Album(
        id = uri,
        title = name.ifEmpty { fallback?.title.orEmpty() },
        type = Album.Type.Show,
        subtitle = "Podcast",
        description = description.ifEmpty { fallback?.description },
        cover = coverImage.imageList.lastOrNull { it.hasFileId() }
            ?.fileId?.toByteArray()?.let { "https://i.scdn.co/image/${it.hex()}".toImageHolder() }
            ?: fallback?.cover,
    )

    fun ExtendedMetadataProto.Show.toArtist(uri: String, fallback: Artist? = null): Artist = Artist(
        id = uri,
        name = name.ifEmpty { fallback?.name.orEmpty() },
        subtitle = "Podcast",
        bio = description.ifEmpty { fallback?.bio },
        cover = coverImage.imageList.lastOrNull { it.hasFileId() }
            ?.fileId?.toByteArray()?.let { "https://i.scdn.co/image/${it.hex()}".toImageHolder() }
            ?: fallback?.cover,
        isRadioSupported = false,
    )

    /** MP3_* are directly playable; MP3_160_ENC is *not* unencrypted MP3. */
    internal fun isPlainAudio(format: AudioFile.Format): Boolean = format in setOf(
        AudioFile.Format.MP3_96,
        AudioFile.Format.MP3_160,
        AudioFile.Format.MP3_256,
        AudioFile.Format.MP3_320,
    )

    internal fun usesPlayPlay(format: AudioFile.Format): Boolean = format in setOf(
        AudioFile.Format.OGG_VORBIS_96,
        AudioFile.Format.OGG_VORBIS_160,
        AudioFile.Format.OGG_VORBIS_320,
        AudioFile.Format.FLAC_FLAC,
        AudioFile.Format.FLAC_FLAC_24BIT,
    )

    internal fun externalAudioUrl(url: String): String? = try {
        val parsed = URI(url)
        if ((parsed.scheme == "https" || parsed.scheme == "http") &&
            !parsed.host.isNullOrBlank() && parsed.userInfo == null) url else null
    } catch (_: Exception) { null }

    fun ExtendedMetadataProto.Episode.toTrack(
        uri: String,
        fallback: Track? = null,
        extendedFiles: AudioFilesExtensionResponse? = null,
    ): Track {
        val showUri = show.takeIf { it.hasGid() && it.gid.size() == 16 }
            ?.let { "spotify:show:${Base62.encode(it.gid.toByteArray().hex())}" }
        val showArtist = showUri?.let { id ->
            val existing = fallback?.artists?.firstOrNull { it.id == id }
            Artist(
                id = id,
                name = show.name.ifEmpty { existing?.name.orEmpty() },
                subtitle = "Podcast",
                cover = existing?.cover,
                isRadioSupported = false,
            )
        } ?: fallback?.artists?.firstOrNull { it.id.startsWith("spotify:show:") }

        // Spotify may publish different playable file sets in EPISODE_V4 and
        // AUDIO_FILES. Merge them, so a 160-kbps file in either source is not
        // accidentally hidden by a 96-kbps file in the other source.
        // MP3_160_ENC has separate protection; do not send it as plain MP3.
        val supported: (AudioFile) -> Boolean = {
            it.hasFileId() && it.fileId.size() == 20 && it.hasFormat() &&
                (isPlainAudio(it.format) || usesPlayPlay(it.format))
        }
        val catalogFiles = (audioList + extendedFiles?.filesList.orEmpty().map { it.file })
            .filter(supported)
        val fileStreamables = catalogFiles
            .asSequence()
            .distinctBy { it.fileId to it.format }
            .sortedWith(compareByDescending<AudioFile> { AudioFormat.quality(it.format.number) }
                .thenBy { if (isPlainAudio(it.format)) 0 else 1 })
            .map { file ->
                val format = file.format.number
                val fileId = file.fileId.toByteArray().hex()
                Streamable.server(
                    id = fileId,
                    quality = AudioFormat.quality(format),
                    title = file.format.name.replace('_', ' '),
                    extras = mapOf("podcast" to "true", "formatNum" to format.toString()),
                )
            }.toList()
        val external = externalAudioUrl(externalUrl)?.let { url ->
            Streamable.server(
                id = url,
                quality = 1,
                title = "External podcast audio",
                extras = mapOf("podcastExternal" to "true"),
            )
        }

        return Track(
            id = uri,
            title = name.ifEmpty { fallback?.title.orEmpty() },
            type = Track.Type.Podcast,
            isRadioSupported = false,
            cover = coverImage.imageList.lastOrNull { it.hasFileId() }
                ?.fileId?.toByteArray()?.let { "https://i.scdn.co/image/${it.hex()}".toImageHolder() }
                ?: fallback?.cover,
            artists = listOfNotNull(showArtist),
            description = description.ifEmpty { fallback?.description },
            duration = duration.toLong().takeIf { it > 0 } ?: fallback?.duration,
            isExplicit = explicit,
            streamables = fileStreamables + listOfNotNull(external),
        )
    }
}
