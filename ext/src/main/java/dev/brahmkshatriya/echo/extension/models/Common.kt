package dev.brahmkshatriya.echo.extension.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Color(
    val hex: String? = null
)

@Serializable
data class Title(
    val transformedLabel: String? = null
)

@Serializable
data class Artwork(
    val extractedColors: ExtractedColors? = null,
    val sources: List<Source>
)

@Serializable
data class ExtractedColors(
    val colorDark: ColorDark? = null
)

@Serializable
data class ColorDark(
    val hex: String? = null,
    val isFallback: Boolean? = null
)

@Serializable
data class Source(
    val height: Long? = null,
    val url: String? = null,
    val width: Long? = null
)

@Serializable
data class Profile(
    val avatar: Artwork? = null,
    val avatarBackgroundColor: Long? = null,
    val name: String? = null,
    val uri: String? = null,
    val username: String? = null,
    val verified: Boolean? = null
)

@Serializable
data class AlbumOfTrack(
    val coverArt: Artwork? = null,
    val id: String? = null,
    val name: String? = null,
    val uri: String? = null
)

@Serializable
data class Artists(
    val items: List<ArtistsItem>? = null
)

@Serializable
data class ArtistsItem(
    val profile: Profile? = null,
    val uri: String? = null
)

@Serializable
data class Associations(
    val associatedVideos: AssociatedVideos? = null
)

@Serializable
data class AssociatedVideos(
    val totalCount: Long? = null
)

@Serializable
data class Attribute(
    val key: String? = null,
    val value: String? = null
)

@Serializable
data class BackgroundColor(
    val hex: String? = null
)

@Serializable
data class ContentRating(
    val label: String? = null
)

@Serializable
data class CardData(
    val cardRepresentation: CardRepresentation? = null
)

@Serializable
data class CardRepresentation(
    val artwork: Artwork? = null,
    val backgroundColor: BackgroundColor? = null,
    val title: Title? = null
)

@Serializable
data class Date(
    val year: Long? = null
)

@Serializable
data class Duration(
    val totalMilliseconds: Long? = null
)

@Serializable
data class Images(
    val items: List<Artwork>? = null
)

@Serializable
data class Playability(
    val playable: Boolean? = null,
    val reason: String? = null
)

@Serializable
data class PlayedState(
    val playPositionMilliseconds: Long? = null,
    val state: String? = null
)

@Serializable
data class ReleaseDate(
    val isoString: String? = null,
    val precision: String? = null
)

@Serializable
data class Restrictions(
    val paywallContent: Boolean? = null
)

@Serializable
data class Topics(
    val items: List<TopicsItem>? = null
)

@Serializable
data class TopicsItem(
    @SerialName("__typename")
    val typename: String? = null,

    val title: String? = null,
    val uri: String? = null
)

@Serializable
data class Visuals(
    val avatarImage: Artwork? = null
)

@Serializable
data class PagingInfo(
    val nextOffset: Long? = null
)