package dev.brahmkshatriya.echo.extension.models

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

        val attributes: List<Attribute>? = null,
        val description: String? = null,
        val format: String? = null,
        val images: Images? = null,
        val name: String? = null,
        val ownerV2: OwnerWrapper? = null,
        val uri: String? = null,
    ) : Item()

    @Serializable
    @SerialName("Album")
    data class Album(
        @SerialName("__typename")
        override val typename: String,

        val artists: Artists? = null,
        val coverArt: Artwork? = null,
        val name: String? = null,
        val uri: String? = null,
        val date: Date? = null,
        val playability: Playability? = null,
        val type: String? = null,
    ) : Item()

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
        val releaseDate: ReleaseDate? = null,
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
        val artists: Artists? = null,
        val associations: Associations? = null,
        val contentRating: ContentRating? = null,
        val duration: Duration? = null,
        val id: String? = null,
        val name: String? = null,
        val playability: Playability? = null,
        val playcount: String? = null,
        val uri: String? = null,
    ) : Item()

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

    @Serializable
    data class Wrapper(
        @SerialName("__typename")
        val typename: String,
        val data: Item
    )

    @Serializable
    data class OwnerWrapper(
        @SerialName("__typename")
        val typename: String? = null,
        val data: User? = null
    )
}