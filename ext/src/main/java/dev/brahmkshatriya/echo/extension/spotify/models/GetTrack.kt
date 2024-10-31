package dev.brahmkshatriya.echo.extension.spotify.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetTrack (
    val data: Data? = null
)

@Serializable
data class Data (
    val trackUnion: TrackUnion? = null
)

@Serializable
data class TrackUnion (
    @SerialName("__typename")
    val typename: String? = null,

    val contentRating: ContentRating? = null,
    val duration: Duration? = null,
    val id: String? = null,
    val name: String? = null,
    val playability: Playability? = null,
    val playcount: String? = null,
    val saved: Boolean? = null,
    val sharingInfo: SharingInfo? = null,
    val trackNumber: Long? = null,
    val uri: String? = null,
    val albumOfTrack: AlbumOfTrack? = null,
    val firstArtist: FirstArtist? = null,
    val otherArtists: OtherArtists? = null
)

@Serializable
data class Copyright (
    val items: List<CopyrightItem>? = null,
    val totalCount: Long? = null
)

@Serializable
data class CopyrightItem (
    val text: String? = null,
    val type: String? = null
)

@Serializable
data class AlbumOfTrackPlayability (
    val playable: Boolean? = null
)

@Serializable
data class SharingInfo (
    val shareId: String? = null,
    val shareUrl: String? = null
)

@Serializable
data class Tracks (
    val items: List<TracksItem>? = null,
    val totalCount: Long? = null
)

@Serializable
data class TracksItem (
    val track: PurpleTrack? = null
)

@Serializable
data class PurpleTrack (
    val trackNumber: Long? = null,
    val uri: String? = null
)

@Serializable
enum class Type(val value: String) {
    @SerialName("EP") Ep("EP"),
    @SerialName("SINGLE") Single("SINGLE");
}


@Serializable
data class FirstArtist (
    val items: List<FirstArtistItem>? = null,
    val totalCount: Long? = null
)

@Serializable
data class FirstArtistItem (
    val discography: Discography? = null,
    val id: String? = null,
    val profile: Profile? = null,
    val relatedContent: RelatedContent? = null,
    val uri: String? = null,
    val visuals: Visuals? = null
)

@Serializable
data class Discography (
    val albums: Copyright? = null,
    val popularReleasesAlbums: OtherArtists? = null,
    val singles: Singles? = null,
    val topTracks: TopTracks? = null
)

@Serializable
data class OtherArtists (
    val items: List<OtherArtistsItem>? = null
)

@Serializable
data class OtherArtistsItem (
    val date: Date? = null,
    val name: String? = null,
    val playability: AlbumOfTrackPlayability? = null,
    val sharingInfo: SharingInfo? = null,
    val tracks: Tracks? = null,
    val type: Type? = null,
    val uri: String? = null,
    val coverArt: Artwork? = null
)

@Serializable
data class Singles (
    val items: List<SinglesItem>? = null,
    val totalCount: Long? = null
)

@Serializable
data class SinglesItem (
    val releases: OtherArtists? = null
)

@Serializable
data class TopTracks (
    val items: List<TopTracksItem>? = null
)

@Serializable
data class TopTracksItem (
    val track: FluffyTrack? = null
)

@Serializable
data class FluffyTrack (
    val albumOfTrack: TrackAlbumOfTrack? = null,
    val artists: Artists? = null,
    val contentRating: ContentRating? = null,
    val duration: Duration? = null,
    val id: String? = null,
    val name: String? = null,
    val playability: AlbumOfTrackPlayability? = null,
    val playcount: String? = null,
    val previews: Previews? = null,
    val uri: String? = null
)

@Serializable
data class TrackAlbumOfTrack (
    val name: String? = null,
    val uri: String? = null,
    val coverArt: Artwork? = null
)

@Serializable
data class Previews (
    val audioPreviews: AudioPreviews? = null
)

@Serializable
data class AudioPreviews (
    val items: List<AudioPreviewsItem>? = null
)

@Serializable
data class AudioPreviewsItem (
    val url: String? = null
)

@Serializable
data class RelatedContent (
    val relatedArtists: RelatedArtists? = null
)

@Serializable
data class RelatedArtists (
    val items: List<RelatedArtistsItem>? = null,
    val totalCount: Long? = null
)

@Serializable
data class RelatedArtistsItem (
    val id: String? = null,
    val profile: Profile? = null,
    val uri: String? = null,
    val visuals: Visuals? = null
)

@Serializable
data class AvatarImage (
    val sources: List<Source>? = null
)