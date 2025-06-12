package dev.brahmkshatriya.echo.extension.spotify.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Metadata4Track(
    val gid: String? = null,
    val name: String? = null,
    val album: Album? = null,
    val artist: List<Artist>? = null,
    val number: Long? = null,

    @SerialName("disc_number")
    val discNumber: Long? = null,

    val duration: Long? = null,
    val popularity: Long? = null,

    @SerialName("external_id")
    val externalId: List<ExternalId>? = null,

    val file: List<File>? = null,
    val preview: List<File>? = null,
    val alternative: List<Alternative>? = null,

    @SerialName("earliest_live_timestamp")
    val earliestLiveTimestamp: Long? = null,

    val licensor: Licensor? = null,

    @SerialName("language_of_performance")
    val languageOfPerformance: List<String>? = null,

    @SerialName("original_audio")
    val originalAudio: Licensor? = null,

    @SerialName("original_title")
    val originalTitle: String? = null,

    @SerialName("artist_with_role")
    val artistWithRole: List<ArtistWithRole>? = null,

    @SerialName("canonical_uri")
    val canonicalUri: String? = null,

    @SerialName("content_authorization_attributes")
    val contentAuthorizationAttributes: String? = null
) {

    @Serializable
    data class Album(
        val gid: String? = null,
        val name: String? = null,
        val artist: List<Artist>? = null,
        val label: String? = null,
        val date: Date? = null,

        @SerialName("cover_group")
        val coverGroup: CoverGroup? = null,

        val licensor: Licensor? = null
    )

    @Serializable
    data class Artist(
        val gid: String? = null,
        val name: String? = null
    )

    @Serializable
    data class CoverGroup(
        val image: List<Image>? = null
    )

    @Serializable
    data class Image(
        @SerialName("file_id")
        val fileId: String? = null,

        val size: String? = null,
        val width: Long? = null,
        val height: Long? = null
    )

    @Serializable
    data class Date(
        val year: Int? = null,
        val month: Int? = null,
        val day: Int? = null
    )

    @Serializable
    data class Licensor(
        val uuid: String? = null
    )

    @Serializable
    data class ArtistWithRole(
        @SerialName("artist_gid")
        val artistGid: String? = null,

        @SerialName("artist_name")
        val artistName: String? = null,

        val role: String? = null
    )

    @Serializable
    data class ExternalId(
        val type: String? = null,
        val id: String? = null
    )

    @Serializable
    data class Alternative(
        val gid: String? = null,
        val file: List<File>? = null,
        val preview: List<File>? = null
    )

    @Serializable
    data class File(
        @SerialName("file_id")
        val fileId: String? = null,

        val format: Format? = null
    )

    @Suppress("unused")
    @Serializable
    enum class Format(val quality: Int) {
        OGG_VORBIS_320(320),
        OGG_VORBIS_160(160),
        OGG_VORBIS_96(96),
        MP4_256_DUAL(256),
        MP4_128_DUAL(128),
        MP4_256(256),
        MP4_128(128),
        AAC_24(240),
        //Preview
        MP3_96(96)
    }
}

