package dev.brahmkshatriya.echo.extension.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class Sections(
    val items: List<SectionsItem>? = null,
    val pagingInfo: PagingInfo? = null,
    val totalCount: Long? = null
)
@Serializable
data class PagingInfo (
    val nextOffset: Long? = null
)

@Serializable
data class SectionsItem(
    @SerialName("__typename")
    val typename: ItemTypename? = null,

    val data: ItemData? = null,
    val sectionItems: SectionItems? = null,
    val targetLocation: String? = null,
    val uri: String? = null
)

@Serializable
enum class ItemTypename(val value: String) {
    @SerialName("BrowseSection")
    BrowseSection("BrowseSection");
}

@Serializable
data class ItemData(
    @SerialName("__typename")
    val typename: PurpleTypename? = null,

    val subtitle: JsonElement? = null,
    val title: Title? = null
)

@Serializable
enum class PurpleTypename(val value: String) {
    @SerialName("BrowseGenericSectionData")
    BrowseGenericSectionData("BrowseGenericSectionData"),
    @SerialName("BrowseGridSectionData")
    BrowseGridSectionData("BrowseGridSectionData"),
    @SerialName("BrowseRelatedSectionData")
    BrowseRelatedSectionData("BrowseRelatedSectionData");
}

@Serializable
data class SectionItems(
    val items: List<SectionItemsItem>? = null,
    val totalCount: Long? = null
)

@Serializable
data class SectionItemsItem(
    val content: Content,
    val uri: String
)

@Serializable
data class Content(
    @SerialName("__typename")
    val typename: ContentTypename,
    val data: ContentData
)

@Serializable
enum class ContentTypename(val value: String) {
    @SerialName("AlbumResponseWrapper")
    AlbumResponseWrapper("AlbumResponseWrapper"),
    @SerialName("BrowseSectionContainerWrapper")
    BrowseSectionContainerWrapper("BrowseSectionContainerWrapper"),
    @SerialName("PlaylistResponseWrapper")
    PlaylistResponseWrapper("PlaylistResponseWrapper"),
    @SerialName("BrowseXlinkResponseWrapper")
    BrowseXlinkResponseWrapper("BrowseXlinkResponseWrapper")
}

@Serializable
data class ContentData(
    @SerialName("__typename")
    val typename: ContentDataTypename? = null,

    val attributes: List<Attribute>? = null,
    val description: String? = null,
    val format: Format? = null,
    val images: Images? = null,
    val name: String? = null,
    val ownerV2: OwnerV2? = null,
    val uri: String? = null,
    val data: DataData? = null,
    val artists: Artists? = null,
    val coverArt: Artwork? = null
)

@Serializable
enum class ContentDataTypename(val value: String) {
    @SerialName("Album") Album("Album"),
    @SerialName("BrowseSectionContainer") BrowseSectionContainer("BrowseSectionContainer"),
    @SerialName("BrowseClientFeature") BrowseClientFeature("BrowseClientFeature"),
    @SerialName("NotFound") NotFound("NotFound"),
    @SerialName("Playlist") Playlist("Playlist");
}

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
data class Attribute(
    val key: String? = null,
    val value: String? = null
)

@Serializable
data class DataData(
    val cardRepresentation: CardRepresentation? = null
)

@Serializable
data class CardRepresentation(
    val artwork: Artwork? = null,
    val backgroundColor: Color? = null,
    val title: Title? = null
)

@Serializable
enum class Format(val value: String) {
    @SerialName("chart")
    Chart("chart"),
    @SerialName("editorial")
    Editorial("editorial"),
    @SerialName("format-shows-shuffle")
    FormatShowsShuffle("format-shows-shuffle");
}

@Serializable
data class Images(
    val items: List<Artwork>? = null
)

@Serializable
data class OwnerV2(
    val data: OwnerV2Data? = null
)

@Serializable
data class OwnerV2Data(
    @SerialName("__typename")
    val typename: FluffyTypename? = null,

    val name: Name? = null
)

@Serializable
enum class Name(val value: String) {
    @SerialName("Spotify")
    Spotify("Spotify");
}

@Serializable
enum class FluffyTypename(val value: String) {
    @SerialName("User")
    User("User");
}