package dev.brahmkshatriya.echo.extension.spotify.models

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonClassDiscriminator("__typename")
sealed class Item {
    @SerialName("__typename")
    abstract val typename: String

    @Serializable
    @SerialName("BrowseSectionContainer")
    data class BrowseSectionContainer(
        @SerialName("__typename")
        override val typename: String,
        val data: CardData? = null,
    ) : Item()

    @Suppress("unused")
    @Serializable
    @SerialName("BrowseClientFeature")
    data class BrowseClientFeature(
        @SerialName("__typename")
        override val typename: String,

        val artwork: Artwork? = null,
        val backgroundColor: BackgroundColor? = null,
        val featureUri: String? = null,
        val iconOverlay: Artwork? = null,
    ) : Item()

    @Serializable
    @SerialName("Playlist")
    data class Playlist(
        @SerialName("__typename")
        override val typename: String,

        val content: Content? = null,
        val attributes: List<Attribute>? = null,
        val description: String? = null,
        val format: String? = null,
        val images: Images? = null,
        val name: String? = null,
        val ownerV2: OwnerWrapper? = null,
        val uri: String? = null,
    ) : Item() {
        @Serializable
        data class Content(
            val items: List<Item>? = null,
            val pagingInfo: PagingInfo? = null,
            val totalCount: Int? = null,
        )

        @Serializable
        data class Item(
            val itemV2: TrackWrapper? = null,
            val addedAt: Date? = null,
            val addedBy: OwnerWrapper? = null,
            val attributes: List<Attribute>? = null,
            val uid: String? = null,
        )
    }

    @Serializable
    @SerialName("Album")
    data class Album(
        @SerialName("__typename")
        override val typename: String,
        val copyright: Copyright? = null,
        val courtesyLine: String? = null,
        val label: String? = null,
        val saved: Boolean? = null,
        val tracksV2: TracksV2? = null,
        val discs: Discs? = null,
        val artists: Artists? = null,
        val coverArt: Artwork? = null,
        val name: String? = null,
        val uri: String? = null,
        val date: Date? = null,
        val playability: Playability? = null,
        val type: String? = null,
    ) : Item() {
        @Serializable
        data class TracksV2(
            val items: List<TrackItem>? = null,
            val totalCount: Int? = null,
        )

        @Serializable
        data class TrackItem(
            val track: Track? = null,
            val uid: String? = null,
        )

        @Serializable
        data class Track(
            override val artists: Artists?,
            override val contentRating: ContentRating?,
            override val duration: Duration?,
            override val name: String?,
            override val playability: Playability?,
            override val playcount: String?,
            override val uri: String?
        ) : ITrack
    }

    @Serializable
    @SerialName("PreRelease")
    data class PreRelease(
        @SerialName("__typename")
        override val typename: String,

        val preReleaseContent: Content? = null,
        val preSaved: Boolean? = null,
        val releaseDate: Date? = null,
        val timezone: String? = null,
        val uri: String? = null,
    ) : Item() {
        @Serializable
        data class Content(
            val artists: Artists? = null,
            val coverArt: Artwork? = null,
            val name: String? = null,
            val type: String? = null,
            val uri: String? = null,
        )
    }

    @Serializable
    @SerialName("Artist")
    data class Artist(
        @SerialName("__typename")
        override val typename: String,

        val profile: Profile? = null,
        val uri: String? = null,
        val visuals: Visuals? = null,
    ) : Item()

    @Serializable
    @SerialName("Episode")
    data class Episode(
        @SerialName("__typename")
        override val typename: String,

        val contentRating: ContentRating? = null,
        val coverArt: Artwork? = null,
        val description: String? = null,
        val duration: Duration? = null,
        val mediaTypes: List<String>? = null,
        val name: String? = null,
        val playability: Playability? = null,
        val playedState: PlayedState? = null,
        val podcastV2: Wrapper? = null,
        val releaseDate: Date? = null,
        val restrictions: Restrictions? = null,
        val uri: String? = null,
    ) : Item()

    @Serializable
    @SerialName("Podcast")
    data class Podcast(
        @SerialName("__typename")
        override val typename: String,

        val coverArt: Artwork? = null,
        val mediaTypes: List<String>? = null,
        val name: String? = null,
        val publisher: Profile? = null,
        val topics: Topics? = null,
        val uri: String? = null,
    ) : Item()

    @Serializable
    @SerialName("Track")
    data class Track(
        @SerialName("__typename")
        override val typename: String,

        val albumOfTrack: AlbumOfTrack? = null,
        override val artists: Artists? = null,
        val associations: Associations? = null,
        override val contentRating: ContentRating? = null,
        override val duration: Duration? = null,
        val id: String? = null,
        override val name: String? = null,
        override val playability: Playability? = null,
        override val playcount: String? = null,
        override val uri: String? = null,
    ) : Item(), ITrack

    @Serializable
    @SerialName("User")
    data class User(
        @SerialName("__typename")
        override val typename: String,

        val avatar: Artwork? = null,
        val name: String? = null,
        val displayName: String? = null,
        val id: String? = null,
        val username: String? = null,
        val uri: String? = null,
    ) : Item()

    @Serializable
    @SerialName("Genre")
    data class Genre(
        @SerialName("__typename")
        override val typename: String,

        val image: Artwork? = null,
        val name: String? = null,
        val uri: String? = null,
    ) : Item()

    @Suppress("unused")
    @Serializable
    @SerialName("NotFound")
    data class NotFound(
        @SerialName("__typename")
        override val typename: String,

        val message: String? = null,
    ) : Item()

    @Suppress("unused")
    @Serializable
    @SerialName("RestrictedContent")
    data class RestrictedContent(
        @SerialName("__typename")
        override val typename: String,
    ) : Item()

    @Serializable
    data class Wrapper(
        @SerialName("__typename")
        val typename: String,
        val data: Item? = null,
    )

    @Serializable
    data class OwnerWrapper(
        @SerialName("__typename")
        val typename: String? = null,
        val data: User? = null
    )

    @Serializable
    data class TrackWrapper(
        @SerialName("__typename")
        val typename: String? = null,
        val data: Track? = null
    )
}