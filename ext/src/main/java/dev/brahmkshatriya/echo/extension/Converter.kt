package dev.brahmkshatriya.echo.extension

import dev.brahmkshatriya.echo.common.helpers.Page
import dev.brahmkshatriya.echo.common.helpers.PagedData
import dev.brahmkshatriya.echo.common.models.Album
import dev.brahmkshatriya.echo.common.models.Artist
import dev.brahmkshatriya.echo.common.models.EchoMediaItem
import dev.brahmkshatriya.echo.common.models.EchoMediaItem.Companion.toMediaItem
import dev.brahmkshatriya.echo.common.models.ImageHolder
import dev.brahmkshatriya.echo.common.models.ImageHolder.Companion.toImageHolder
import dev.brahmkshatriya.echo.common.models.Playlist
import dev.brahmkshatriya.echo.common.models.Shelf
import dev.brahmkshatriya.echo.common.models.Tab
import dev.brahmkshatriya.echo.common.models.Track
import dev.brahmkshatriya.echo.common.models.User
import dev.brahmkshatriya.echo.common.settings.Settings
import dev.brahmkshatriya.echo.extension.spotify.Cache
import dev.brahmkshatriya.echo.extension.spotify.Queries
import dev.brahmkshatriya.echo.extension.spotify.models.Artwork
import dev.brahmkshatriya.echo.extension.spotify.models.Item
import dev.brahmkshatriya.echo.extension.spotify.models.Item.Wrapper
import dev.brahmkshatriya.echo.extension.spotify.models.ProfileAttributes
import dev.brahmkshatriya.echo.extension.spotify.models.SearchDesktop
import dev.brahmkshatriya.echo.extension.spotify.models.Sections
import dev.brahmkshatriya.echo.extension.spotify.models.Sections.ItemsItem
import dev.brahmkshatriya.echo.extension.spotify.models.Sections.SectionItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun Settings.toCache(): Cache {
    return object : Cache {
        override var accessToken : String?
            get() = getString("accessToken")
            set(value) {
                putString("accessToken", value)
            }

        override var accessTokenExpiration: Long?
            get() = getString("accessTokenExpiration")?.toLongOrNull()
            set(value) {
                putString("accessTokenExpiration", value.toString())
            }

    }
}

fun Sections.toShelves(queries: Queries): List<Shelf> {
    return items?.mapNotNull { item ->
        if (item.data!!.typename == Sections.Typename.BrowseRelatedSectionData)
            return@mapNotNull item.toCategory(queries)

        val title = item.data.title?.transformedLabel!!
        when (item.data.typename) {
            Sections.Typename.BrowseGenericSectionData -> Shelf.Lists.Items(
                title = title,
                list = item.sectionItems?.items?.mapNotNull { it.content.data.toMediaItem() }!!,
            )

            Sections.Typename.BrowseGridSectionData -> {
                Shelf.Lists.Categories(
                    title = title,
                    list = item.sectionItems?.items?.mapNotNull { it.toCategory(queries) }!!,
                    type = Shelf.Lists.Type.Grid
                )
            }

            Sections.Typename.BrowseRelatedSectionData -> throw IllegalStateException()
            null -> null
        }
    }!!
}

private fun SectionItem.toCategory(api: Queries): Shelf.Category? {
    val item = sectionItems?.items?.firstOrNull() ?: return null
    return item.toCategory(api)
}

private fun Wrapper.toMediaItem(): EchoMediaItem? {
    return data.toMediaItem()
}

private fun Item.toMediaItem(): EchoMediaItem? {
    return when (this) {
        is Item.Album -> Album(
            id = uri ?: return null,
            title = name ?: return null,
            subtitle = date?.year?.toString(),
            artists = artists?.items?.mapNotNull {
                val id = it.uri ?: return@mapNotNull null
                val name = it.profile?.name ?: return@mapNotNull null
                Artist(id, name, it.profile.avatar?.toImageHolder())
            } ?: listOf(),
            cover = coverArt?.toImageHolder()
        ).toMediaItem()

        is Item.PreRelease -> Album(
            id = preReleaseContent?.uri ?: return null,
            title = preReleaseContent.name ?: return null,
            subtitle = releaseDate?.isoString?.toTimeString(timezone),
            artists = preReleaseContent.artists?.items?.mapNotNull {
                val id = it.uri ?: return@mapNotNull null
                val name = it.profile?.name ?: return@mapNotNull null
                Artist(id, name, it.profile.avatar?.toImageHolder())
            } ?: listOf(),
            cover = preReleaseContent.coverArt?.toImageHolder()
        ).toMediaItem()


        is Item.Playlist -> Playlist(
            id = uri ?: return null,
            title = name ?: return null,
            isEditable = false,
            subtitle = description.removeHtml(),
            cover = images?.items?.firstOrNull()?.toImageHolder(),
            authors = listOfNotNull(
                (ownerV2?.data?.toMediaItem() as? EchoMediaItem.Profile.UserItem)?.user
            )
        ).toMediaItem()

        is Item.Artist -> Artist(
            id = uri ?: return null,
            name = profile?.name ?: return null,
            cover = profile.avatar?.toImageHolder(),
            subtitle = profile.verified?.let { if (it) "Verified" else null }
        ).toMediaItem()

        is Item.Track -> Track(
            id = uri ?: return null,
            title = name ?: return null,
            artists = artists?.items?.mapNotNull {
                val id = it.uri ?: return@mapNotNull null
                val name = it.profile?.name ?: return@mapNotNull null
                Artist(id, name, it.profile.avatar?.toImageHolder())
            } ?: listOf(),
            album = albumOfTrack?.let {
                Album(
                    id = it.uri ?: return@let null,
                    title = it.name ?: return@let null,
                    cover = it.coverArt?.toImageHolder()
                )
            },
            isExplicit = contentRating?.label == "Explicit",
            duration = duration?.totalMilliseconds,
            plays = playcount?.toInt()
        ).toMediaItem()

        is Item.Episode -> Track(
            id = uri ?: return null,
            title = name ?: return null,
            cover = coverArt?.toImageHolder(),
            description = description,
            artists = listOfNotNull(
                (podcastV2?.toMediaItem() as? EchoMediaItem.Profile.ArtistItem)?.artist
            ),
            isExplicit = contentRating?.label == "Explicit",
            duration = duration?.totalMilliseconds,
            releaseDate = releaseDate?.isoString,
        ).toMediaItem()

        is Item.Podcast -> Artist(
            id = uri ?: return null,
            name = name ?: return null,
            cover = coverArt?.toImageHolder(),
        ).toMediaItem()

        is Item.User -> User(
            id = uri ?: return null,
            name = displayName ?: name ?: username ?: return null,
            cover = avatar?.toImageHolder()
        ).toMediaItem()


        is Item.BrowseClientFeature -> null
        is Item.BrowseSectionContainer -> null
        is Item.Genre -> null
        is Item.NotFound -> null
    }
}

private fun String.toTimeString(timezone: String?): String? {
    val locale = Locale.ENGLISH
    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", locale)
    formatter.timeZone = TimeZone.getTimeZone(timezone ?: return null)
    val targetTime = formatter.parse(this)
    val currentTime = Date()
    val duration = targetTime.time - currentTime.time

    val days = duration / (1000 * 60 * 60 * 24)
    val hours = (duration / (1000 * 60 * 60)) % 24
    val minutes = (duration / (1000 * 60)) % 60

    return StringBuilder().apply {
        if (days > 0) append(String.format(locale, "%02dd ", days))
        if (hours > 0) append(String.format(locale, "%02dh ", hours))
        if (minutes > 0) append(String.format(locale, "%02dm ", minutes))
    }.toString()
}

val htmlRegex = Regex("<[^>]*>")
private fun String?.removeHtml(): String? {
    return this?.replace(htmlRegex, "")
}

private fun Artwork.toImageHolder(): ImageHolder? {
    return this.sources.firstOrNull()?.url?.toImageHolder()
}

fun <T : Any> paged(
    load: suspend (offset: Int) -> Pair<List<T>, Long?>
) = PagedData.Continuous { cont ->
    val offset = cont?.toInt() ?: 0
    val (data, next) = load(offset)
    Page(data, next?.toString())
}

fun ItemsItem.toCategory(queries: Queries): Shelf.Category? {
    val uri = uri
    val item = content.data
    if (item !is Item.BrowseSectionContainer) return null
    return Shelf.Category(
        title = item.data?.cardRepresentation?.title?.transformedLabel!!,
        items = paged {
            val sections = queries.browsePage(uri, it).data.browse.sections
            val next = sections.pagingInfo?.nextOffset
            sections.toShelves(queries) to next
        }
    )
}

fun ProfileAttributes.toUser() = User(
    id = data.me.profile.uri!!,
    name = data.me.profile.name!!,
    cover = data.me.profile.avatar?.sources?.firstOrNull()?.url?.toImageHolder()
)

private val filteredCategory = listOf("PODCASTS", "AUDIOBOOKS")
fun SearchDesktop.SearchV2.toShelvesAndTabs(queries: Queries): Pair<PagedData<Shelf>, List<Tab>> {
    val tabs = listOf(Tab("ALL", "All")) + chipOrder?.items?.map { chip ->
        Tab(chip.typeName!!, chip.typeName.lowercase().replaceFirstChar { it.uppercaseChar() })
    }!!

    val shelves = listOfNotNull(
        topResultsV2?.itemsV2?.firstOrNull()?.item?.toMediaItem()?.toShelf(),
        tracksV2?.items?.toTrackShelf("Songs"),
        topResultsV2?.featured?.toMediaShelf("Featured"),
        artists?.toMediaShelf("Artists"),
        albumsV2?.toMediaShelf("Albums"),
        playlists?.toMediaShelf("Playlists"),
        podcasts?.toMediaShelf("Podcasts"),
        episodes?.toMediaShelf("Episodes"),
        users?.toMediaShelf("Users"),
        genres?.toCategoryShelf("Genres", queries)
    )
    return PagedData.Single { shelves } to tabs.filter { it.id !in filteredCategory }
}

private fun SearchDesktop.SearchItems.toCategoryShelf(title: String, queries: Queries): Shelf? {
    if (items.isNullOrEmpty()) return null
    val items = items.mapNotNull { it.toGenreCategory(queries) }
    return Shelf.Lists.Categories(title = title, list = items, type = Shelf.Lists.Type.Grid)
}

fun SearchDesktop.SearchItems?.toItemShelves(): Pair<List<Shelf>, Long?> {
    if (this == null || items == null) return emptyList<Shelf>() to null
    val items = items
    val next = pagingInfo?.nextOffset
    return items.mapNotNull { item ->
        item.data.toMediaItem()?.toShelf()
    } to next
}

fun SearchDesktop.TracksV2?.toItemShelves(): Pair<List<Shelf>, Long?> {
    if (this == null || items == null) return emptyList<Shelf>() to null
    val items = items
    val next = pagingInfo?.nextOffset
    return items.mapNotNull { item ->
        item.item?.toMediaItem()?.toShelf()
    } to next
}

fun SearchDesktop.SearchItems?.toCategoryShelves(queries: Queries): Pair<List<Shelf>, Long?> {
    if (this == null || items == null) return emptyList<Shelf>() to null
    val items = items
    val next = pagingInfo?.nextOffset
    return items.mapNotNull { item ->
        item.toGenreCategory(queries)
    } to next
}

private fun Wrapper.toGenreCategory(queries: Queries): Shelf.Category? {
    val item = data
    if (item !is Item.Genre) return null
    val uri = item.uri!!
    return Shelf.Category(
        title = item.name ?: return null,
        items = paged {
            val sections = queries.browsePage(uri, it).data.browse.sections
            val next = sections.pagingInfo?.nextOffset
            sections.toShelves(queries) to next
        }
    )
}

private fun SearchDesktop.SearchItems.toMediaShelf(title: String) = items?.toMediaShelf(title)

private fun List<Wrapper>?.toMediaShelf(title: String): Shelf? {
    if (this.isNullOrEmpty()) return null
    return Shelf.Lists.Items(
        title = title,
        list = mapNotNull { it.toMediaItem() }
    )
}

private fun List<SearchDesktop.ItemWrapperWrapper>?.toTrackShelf(title: String): Shelf? {
    if (this.isNullOrEmpty()) return null
    return Shelf.Lists.Tracks(
        title = title,
        list = mapNotNull { (it.item?.toMediaItem() as? EchoMediaItem.TrackItem)?.track }
    )
}