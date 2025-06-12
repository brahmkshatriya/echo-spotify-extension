package dev.brahmkshatriya.echo.extension

import dev.brahmkshatriya.echo.common.helpers.Page
import dev.brahmkshatriya.echo.common.helpers.PagedData
import dev.brahmkshatriya.echo.common.models.Album
import dev.brahmkshatriya.echo.common.models.Artist
import dev.brahmkshatriya.echo.common.models.Date
import dev.brahmkshatriya.echo.common.models.EchoMediaItem
import dev.brahmkshatriya.echo.common.models.EchoMediaItem.Companion.toMediaItem
import dev.brahmkshatriya.echo.common.models.ImageHolder
import dev.brahmkshatriya.echo.common.models.ImageHolder.Companion.toImageHolder
import dev.brahmkshatriya.echo.common.models.Playlist
import dev.brahmkshatriya.echo.common.models.Radio
import dev.brahmkshatriya.echo.common.models.Shelf
import dev.brahmkshatriya.echo.common.models.Streamable
import dev.brahmkshatriya.echo.common.models.Tab
import dev.brahmkshatriya.echo.common.models.Track
import dev.brahmkshatriya.echo.common.models.User
import dev.brahmkshatriya.echo.extension.spotify.Base62
import dev.brahmkshatriya.echo.extension.spotify.Queries
import dev.brahmkshatriya.echo.extension.spotify.models.Albums
import dev.brahmkshatriya.echo.extension.spotify.models.Artists
import dev.brahmkshatriya.echo.extension.spotify.models.Artwork
import dev.brahmkshatriya.echo.extension.spotify.models.Canvas
import dev.brahmkshatriya.echo.extension.spotify.models.HomeFeed
import dev.brahmkshatriya.echo.extension.spotify.models.IAlbum
import dev.brahmkshatriya.echo.extension.spotify.models.IArtist
import dev.brahmkshatriya.echo.extension.spotify.models.ITrack
import dev.brahmkshatriya.echo.extension.spotify.models.Item
import dev.brahmkshatriya.echo.extension.spotify.models.Item.Wrapper
import dev.brahmkshatriya.echo.extension.spotify.models.ItemsV2
import dev.brahmkshatriya.echo.extension.spotify.models.Label
import dev.brahmkshatriya.echo.extension.spotify.models.LibraryV3
import dev.brahmkshatriya.echo.extension.spotify.models.Metadata4Track
import dev.brahmkshatriya.echo.extension.spotify.models.ProfileAttributes
import dev.brahmkshatriya.echo.extension.spotify.models.Releases
import dev.brahmkshatriya.echo.extension.spotify.models.SearchDesktop
import dev.brahmkshatriya.echo.extension.spotify.models.Sections
import dev.brahmkshatriya.echo.extension.spotify.models.Sections.ItemsItem
import dev.brahmkshatriya.echo.extension.spotify.models.Sections.SectionItem
import dev.brahmkshatriya.echo.extension.spotify.models.TracksV2
import dev.brahmkshatriya.echo.extension.spotify.models.UserFollowers
import dev.brahmkshatriya.echo.extension.spotify.models.UserProfileView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone
import kotlin.math.ceil


fun List<HomeFeed.Chip>.toTabs() = map {
    Tab(it.id!!, it.label?.transformedLabel!!)
}

fun Sections.toShelves(
    queries: Queries,
    emptyTitle: String? = null,
    token: String? = null,
): List<Shelf> {
    return items?.mapNotNull { item ->
        item.data ?: return@mapNotNull null
        if (item.data.typename == Sections.Typename.BrowseRelatedSectionData)
            return@mapNotNull item.toCategory(queries)

        val title = item.data.title?.transformedLabel ?: emptyTitle ?: ""
        val subtitle = item.data.subtitle?.transformedLabel
        when (item.data.typename) {
            null -> null
            Sections.Typename.BrowseGenericSectionData, Sections.Typename.HomeRecentlyPlayedSectionData ->
                Shelf.Lists.Items(
                    title = title,
                    subtitle = subtitle,
                    list = item.sectionItems?.items?.mapNotNull { it.content.toMediaItem() }!!,
                )

            Sections.Typename.HomeGenericSectionData, Sections.Typename.HomeSpotlightSectionData ->
                Shelf.Lists.Items(
                    title = title,
                    subtitle = subtitle,
                    list = item.sectionItems?.items?.mapNotNull { it.content.toMediaItem() }!!,
                    more = if (item.uri != null && (item.sectionItems.totalCount ?: 0) > 3)
                        paged { offset ->
                            val sectionItem = queries.homeSection(item.uri, token, offset).json
                                .data.homeSections.sections.first().sectionItems
                            val next = sectionItem.pagingInfo?.nextOffset
                            sectionItem.items!!.mapNotNull { it.content.toMediaItem() } to next
                        }
                    else null
                )

            Sections.Typename.BrowseGridSectionData -> {
                Shelf.Lists.Categories(
                    title = title,
                    subtitle = subtitle,
                    list = item.sectionItems?.items?.mapNotNull { it.toBrowseCategory(queries) }!!,
                    type = Shelf.Lists.Type.Grid
                )
            }

            Sections.Typename.BrowseRelatedSectionData -> throw IllegalStateException()
            Sections.Typename.HomeShortsSectionData -> Shelf.Lists.Items(
                title = title,
                subtitle = subtitle,
                list = item.sectionItems?.items?.mapNotNull { it.content.toMediaItem() }!!,
                type = Shelf.Lists.Type.Grid
            )

            Sections.Typename.HomeFeedBaselineSectionData -> item.sectionItems?.items
                ?.firstOrNull()?.content?.data?.toMediaItem()?.toShelf()

            Sections.Typename.BrowseUnsupportedSectionData -> null
            Sections.Typename.HomeOnboardingSectionDataV2 -> null
            Sections.Typename.HomeWatchFeedSectionData -> null
        }
    }!!
}

private fun SectionItem.toCategory(api: Queries): Shelf.Category? {
    val item = sectionItems?.items?.firstOrNull() ?: return null
    return item.toBrowseCategory(api)
}

fun ITrack.toTrack(a: Album? = null, url: String? = null): Track? {
    val album = albumOfTrack?.toAlbum(name) ?: a
    return Track(
        id = uri ?: url ?: return null,
        title = name ?: return null,
        cover = album?.cover,
        artists = artists.toArtists(),
        album = album,
        isExplicit = contentRating?.label == Label.EXPLICIT,
        duration = duration?.totalMilliseconds ?: trackDuration?.totalMilliseconds,
        plays = playcount?.toLong(),
        releaseDate = album?.releaseDate,
    )
}

fun Item.Playlist.toPlaylist(): Playlist? {
    val desc = description?.removeHtml()?.ifEmpty { null }
    return Playlist(
        id = uri ?: return null,
        title = name ?: return null,
        isEditable = false,
        description = desc,
        subtitle = ownerV2?.data?.name,
        cover = images?.items?.firstOrNull()?.toImageHolder(),
        authors = listOfNotNull(
            (ownerV2?.data?.toMediaItem() as? EchoMediaItem.Profile.UserItem)?.user
        ),
        tracks = content?.totalCount,
        duration = content?.toDuration(),
        creationDate = content?.items?.map { it.addedAt?.toDate() }?.maxByOrNull { it ?: Date(0) }
    )
}

private fun Item.Playlist.Content.toDuration(): Long? {
    val items = items ?: return null
    val average = items.run {
        sumOf { it.itemV2?.data?.trackDuration?.totalMilliseconds ?: 0 }.toDouble() / items.size
    }
    val count = totalCount ?: return null
    return (average * count).toLong()
}

fun Item.PseudoPlaylist.toPlaylist(): Playlist? {
    return Playlist(
        id = uri ?: return null,
        title = name ?: return null,
        isEditable = true,
        cover = image?.toImageHolder(),
        tracks = count
    )
}

fun Item.Playlist.toRadio(): Radio? {
    return Radio(
        id = uri ?: return null,
        title = name ?: return null,
        subtitle = description?.removeHtml(),
        cover = images?.items?.firstOrNull()?.toImageHolder()
    )
}

fun IAlbum.toAlbum(n: String? = null): Album? {
    return Album(
        id = uri ?: return null,
        title = name ?: n ?: return null,
        subtitle = date?.year?.toString(),
        artists = artists.toArtists(),
        cover = coverArt?.toImageHolder(),
        tracks = tracksV2?.totalCount,
        duration = tracksV2?.toDuration(),
        releaseDate = date?.toDate(),
        description = buildString {
            if (!courtesyLine.isNullOrBlank()) appendLine(courtesyLine)
            copyright?.items?.forEach {
                appendLine(it.text)
            }
        },
        publisher = label
    )
}

fun TracksV2.toDuration(): Long? {
    val average = items?.run {
        sumOf { it.track?.duration?.totalMilliseconds ?: 0 }.toDouble() / items.size
    } ?: return null
    val count = totalCount ?: return null
    return (average * count).toLong()
}

fun dev.brahmkshatriya.echo.extension.spotify.models.Date.toDate(): Date? {
    if (isoString != null) return isoString.toDate(precision)
    if (year != null) return Date(year)
    return null
}

private fun String.toDate(precision: String? = null): Date {
    val locale = Locale.ENGLISH
    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", locale)
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    val targetTime = runCatching { formatter.parse(this) }.getOrElse {
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", locale).run {
            timeZone = TimeZone.getTimeZone("UTC")
            parse(this@toDate)
        }
    }
    val calendar = Calendar.getInstance()
    calendar.time = targetTime
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH) + 1
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    if (precision == "YEAR") return Date(year)
    if (precision == "MONTH") return Date(year, month)
    return Date(year, month, day)
}

fun IArtist.toArtist(subtitle: String? = null): Artist? {
    return Artist(
        id = uri ?: return null,
        name = profile?.name ?: return null,
        cover = visuals?.avatarImage?.toImageHolder(),
        subtitle = subtitle,
        followers = stats?.followers?.toInt(),
        description = profile?.biography?.text?.removeHtml(),
        isFollowing = saved ?: false
    )
}

fun Artists?.toArtists(subtitle: String? = null) =
    this?.items?.mapNotNull { it.toArtist(subtitle) } ?: listOf()

fun Item.Podcast.toArtist(): Artist? {
    return Artist(
        id = uri ?: return null,
        name = name ?: return null,
        subtitle = "Podcast",
        cover = coverArt?.toImageHolder(),
    )
}

fun Item.Audiobook.toAlbum(): Album? {
    return Album(
        id = uri ?: return null,
        title = name ?: return null,
        cover = coverArt?.toImageHolder(),
        description = description?.removeHtml(),
        subtitle = "Audiobook",
        releaseDate = publishDate?.toDate(),
    )
}

fun Artists.toShelf(
    title: String, subtitle: String? = null, more: PagedData<EchoMediaItem>
): Shelf? {
    if (items.isNullOrEmpty()) return null
    return Shelf.Lists.Items(
        title = title,
        subtitle = subtitle,
        list = items.mapNotNull { it.toArtist(subtitle)?.toMediaItem() },
        more = if (items.size > 3) more else null
    )
}

fun Albums.toShelf(title: String, more: PagedData<EchoMediaItem>): Shelf? {
    if (items.isNullOrEmpty()) return null
    return Shelf.Lists.Items(
        title = title,
        list = items.mapNotNull { it.releases?.items?.firstOrNull()?.toAlbum()?.toMediaItem() },
        more = if (items.size > 3) more else null
    )
}

fun Releases.toShelf(title: String, more: PagedData<EchoMediaItem>): Shelf? {
    if (items.isNullOrEmpty()) return null
    return Shelf.Lists.Items(
        title = title,
        list = items.mapNotNull { it.toAlbum()?.toMediaItem() },
        more = if (items.size > 3) more else null
    )
}

private fun ItemsV2.toShelf(title: String, more: PagedData<EchoMediaItem>): Shelf? {
    if (items.isNullOrEmpty()) return null
    return Shelf.Lists.Items(
        title = title,
        list = items.mapNotNull { it.toMediaItem() },
        more = if (items.size > 3) more else null
    )
}

fun TracksV2.toTrackShelf(title: String): Shelf? {
    if (items.isNullOrEmpty()) return null
    return Shelf.Lists.Tracks(
        title = title,
        list = items.mapNotNull { it.track?.toTrack() }
    )
}

fun pagedItemsV2(
    block: suspend (offset: Int) -> ItemsV2
): PagedData<EchoMediaItem> {
    var count = 0L
    return paged { offset ->
        val res = block(offset)
        val items = res.items?.mapNotNull { it.toMediaItem() } ?: emptyList()
        val total = res.totalCount ?: 0
        count += items.size
        items to if (count < total) count else null
    }
}

fun pagedArtists(
    block: suspend (offset: Int) -> Artists
): PagedData<EchoMediaItem> {
    var count = 0L
    return paged { offset ->
        val res = block(offset)
        val items = res.items?.mapNotNull { it.toArtist()?.toMediaItem() } ?: emptyList()
        val total = res.totalCount ?: 0
        count += items.size
        items to if (count < total) count else null
    }
}

fun pagedAlbums(
    block: suspend (offset: Int) -> Albums
): PagedData<EchoMediaItem> {
    var count = 0L
    return paged { offset ->
        val res = block(offset)
        val items = res.items?.map {
            it.releases?.items?.firstOrNull()?.toAlbum()?.toMediaItem()!!
        } ?: emptyList()
        val total = res.totalCount ?: 0
        count += items.size
        items to if (count < total) count else null
    }
}

fun IArtist.toShelves(queries: Queries): List<Shelf> {
    val uri = uri ?: return emptyList()
    return listOfNotNull(
        discography?.topTracks?.toTrackShelf("Popular Tracks"),
        discography?.latest?.toAlbum()?.toMediaItem()?.toShelf(true),
        discography?.popularReleasesAlbums?.toShelf(
            "Popular Albums",
            pagedAlbums {
                queries.queryArtistDiscographyAll(uri, it)
                    .json.data.artistUnion.discography?.all!!
            }
        ),
        relatedContent?.featuringV2?.toShelf(
            "Featuring ${profile?.name}",
            pagedItemsV2 {
                queries.queryArtistFeaturing(uri, it)
                    .json.data.artistUnion.relatedContent?.featuringV2!!
            }
        ),
        discography?.albums?.toShelf(
            "Albums",
            pagedAlbums {
                queries.queryArtistDiscographyAlbums(uri, it)
                    .json.data.artistUnion.discography?.albums!!
            }
        ),
        discography?.singles?.toShelf(
            "Singles",
            pagedAlbums {
                queries.queryArtistDiscographySingles(uri, it)
                    .json.data.artistUnion.discography?.singles!!
            }
        ),
        discography?.compilations?.toShelf(
            "Compilations",
            pagedAlbums {
                queries.queryArtistDiscographyCompilations(uri, it)
                    .json.data.artistUnion.discography?.compilations!!
            }
        ),
        profile?.playlistsV2?.toShelf(
            "Playlists",
            pagedItemsV2 {
                queries.queryArtistPlaylists(uri, it)
                    .json.data.artistUnion.profile?.playlistsV2!!
            }
        ),
        relatedContent?.appearsOn?.toShelf(
            "Appears On",
            pagedAlbums {
                queries.queryArtistAppearsOn(uri, it)
                    .json.data.artistUnion.relatedContent?.appearsOn!!
            }
        ),
        relatedContent?.discoveredOnV2?.toShelf(
            "Discovered On",
            pagedItemsV2 {
                queries.queryArtistDiscoveredOn(uri, it)
                    .json.data.artistUnion.relatedContent?.discoveredOnV2!!
            }
        ),
        relatedContent?.relatedArtists?.toShelf(
            "Artist", "Artist",
            pagedArtists {
                queries.queryArtistRelated(uri, it)
                    .json.data.artistUnion.relatedContent?.relatedArtists!!
            }
        )
    )
}

val likedPlaylist = Playlist(
    "spotify:collection:tracks",
    "Liked Songs",
    true,
    "https://misc.scdn.co/liked-songs/liked-songs-300.png".toImageHolder()
)

fun Wrapper.toMediaItem() = when (uri) {
    "spotify:user:@:collection" -> likedPlaylist.toMediaItem()

    else -> data?.toMediaItem()
}

fun Item.toMediaItem(): EchoMediaItem? {
    return when (this) {
        is Item.Album -> toAlbum()?.toMediaItem()

        is Item.PreRelease -> Album(
            id = preReleaseContent?.uri ?: return null,
            title = preReleaseContent.name ?: return null,
            subtitle = "Pre-Release",
            artists = preReleaseContent.artists.toArtists(),
            cover = preReleaseContent.coverArt?.toImageHolder()
        ).toMediaItem()

        is Item.PseudoPlaylist -> toPlaylist()?.toMediaItem()

        is Item.Playlist -> toPlaylist()?.toMediaItem()

        is Item.Artist -> toArtist("Artist")?.toMediaItem()

        is Item.Track -> toTrack()?.toMediaItem()

        is Item.Episode -> Track(
            id = uri ?: return null,
            title = name ?: return null,
            cover = coverArt?.toImageHolder(),
            description = description?.removeHtml(),
            artists = listOfNotNull(
                podcastV2?.data?.toArtist()?.toMediaItem()?.artist
            ),
            isExplicit = contentRating?.label == Label.EXPLICIT,
            duration = duration?.totalMilliseconds,
            releaseDate = releaseDate?.toDate(),
        ).toMediaItem()

        is Item.Podcast -> toArtist()?.toMediaItem()

        is Item.Chapter -> Track(
            id = uri ?: return null,
            title = name ?: return null,
            cover = coverArt?.toImageHolder(),
            description = description?.removeHtml(),
            album = audiobookV2?.data?.toAlbum(),
            isExplicit = contentRating?.label == Label.EXPLICIT,
            duration = duration?.totalMilliseconds,
        ).toMediaItem()

        is Item.Audiobook -> toAlbum()?.toMediaItem()


        is Item.User -> Artist(
            id = uri ?: return null,
            name = displayName ?: name ?: username ?: return null,
            subtitle = "User",
            cover = avatar?.toImageHolder()
        ).toMediaItem()

        is Item.BrowseClientFeature -> null
        is Item.BrowseSectionContainer -> null
        is Item.Genre -> null
        is Item.Folder -> null
        is Item.Merch -> null
        is Item.NotFound -> null
        is Item.RestrictedContent -> null
        is Item.BrowseSpacesHub -> null
        is Item.GenericError -> null
        is Item.DiscoveryFeed -> null
    }
}

val htmlRegex = Regex("<[^>]*>")
private fun String?.removeHtml(): String? {
    return this?.replace(htmlRegex, "")
}

private fun Artwork.toImageHolder(): ImageHolder? {
    return this.sources.sortedBy { it.height }.middleOrNull()?.url?.toImageHolder()
}

private fun <T> List<T>.middleOrNull() = getOrNull(size.ceilDiv(2)) ?: lastOrNull()
private fun Int.ceilDiv(other: Int) = ceil(this.toDouble() / other).toInt()

fun <T : Any> paged(
    load: suspend (offset: Int) -> Pair<List<T>, Long?>
) = PagedData.Continuous { cont ->
    val offset = cont?.toInt() ?: 0
    val (data, next) = load(offset)
    Page(data, next?.toString())
}

fun ItemsItem.toBrowseCategory(queries: Queries): Shelf.Category? {
    val uri = uri
    val item = content.data
    if (item !is Item.BrowseSectionContainer) return null
    return Shelf.Category(
        title = item.data?.cardRepresentation?.title?.transformedLabel!!,
        items = paged {
            val sections = queries.browsePage(uri, it).json.data.browse.sections
            val next = sections.pagingInfo?.nextOffset
            sections.toShelves(queries) to next
        }
    )
}

fun ProfileAttributes.toUser() = User(
    id = data.me.profile.uri!!,
    name = data.me.profile.name!!,
    cover = data.me.profile.avatar?.sources?.lastOrNull()?.url?.toImageHolder()
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
        item.data?.toMediaItem()?.toShelf()
    } to next
}

fun SearchDesktop.TracksV2?.toItemShelves(): Pair<List<Shelf>, Long?> {
    if (this == null || items == null) return emptyList<Shelf>() to null
    val items = items
    val next = pagingInfo?.nextOffset
    return items.mapNotNull { item ->
        item.item?.data?.toTrack()?.toMediaItem()?.toShelf()
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
            val sections = queries.browsePage(uri, it).json.data.browse.sections
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

private fun List<SearchDesktop.TrackWrapperWrapper>?.toTrackShelf(title: String): Shelf? {
    if (this.isNullOrEmpty()) return null
    return Shelf.Lists.Tracks(
        title = title,
        list = mapNotNull { it.item?.data?.toTrack() }
    )
}

fun Canvas.toStreamable(): Streamable? {
    val canvas = data?.trackUnion?.canvas ?: return null
    if (!canvas.type.orEmpty().startsWith("VIDEO")) return null
    val url = canvas.url ?: return null
    return Streamable.background(
        id = url,
        title = canvas.type ?: return null,
        quality = 0
    )
}

private fun Metadata4Track.Date.toReleaseDate() =
    if (year != null) Date(year, month, day) else null

fun Metadata4Track.Format.show(
    hasPremium: Boolean, supportsPlayPlay: Boolean, showWidevineStreams: Boolean
) = when (this) {
    Metadata4Track.Format.OGG_VORBIS_320 -> hasPremium && supportsPlayPlay
    Metadata4Track.Format.OGG_VORBIS_160 -> supportsPlayPlay
    Metadata4Track.Format.OGG_VORBIS_96 -> supportsPlayPlay
    Metadata4Track.Format.MP4_256_DUAL -> false
    Metadata4Track.Format.MP4_128_DUAL -> false
    Metadata4Track.Format.MP4_256 -> hasPremium && showWidevineStreams
    Metadata4Track.Format.MP4_128 -> showWidevineStreams
    Metadata4Track.Format.AAC_24 -> false
    Metadata4Track.Format.MP3_96 -> false
}

fun Metadata4Track.toTrack(
    hasPremium: Boolean,
    supportsPlayPlay: Boolean,
    showWidevineStreams: Boolean,
    canvas: Streamable?
): Track {
    val id = "spotify:track:${Base62.encode(gid!!)}"
    val title = name!!
    val streamables = (file ?: alternative?.firstOrNull()?.file).orEmpty().mapNotNull {
        val fileId = it.fileId ?: return@mapNotNull null
        val format = it.format ?: return@mapNotNull null

        if (!format.show(hasPremium, supportsPlayPlay, showWidevineStreams)) return@mapNotNull null
        Streamable.server(
            id = fileId,
            quality = format.quality,
            title = format.name.replace('_', ' '),
            extras = mapOf(
                "format" to it.format.name
            )
        )
    }
    val alb = album?.let { album ->
        val gid = album.gid ?: return@let null
        val albumId = Base62.encode(gid)
        Album(
            id = "spotify:album:$albumId",
            title = album.name ?: return@let null,
            cover = album.coverGroup?.image?.lastOrNull()?.fileId?.let {
                "https://i.scdn.co/image/$it".toImageHolder()
            },
            releaseDate = album.date?.toReleaseDate(),
        )
    }
    return Track(
        id = id,
        title = title,
        cover = alb?.cover,
        streamables = if (canvas != null) streamables + canvas else streamables,
        duration = duration,
        artists = artistWithRole?.mapNotNull {
            val gid = it.artistGid ?: return@mapNotNull null
            val artistId = Base62.encode(gid)
            val name = it.artistName ?: return@mapNotNull null
            val subtitle = it.role?.split('_')?.joinToString(" ") { s ->
                s.lowercase().replaceFirstChar { char -> char.uppercaseChar() }
            }
            Artist("spotify:artist:$artistId", name, subtitle = subtitle)
        } ?: listOf(),
        album = alb,
        releaseDate = alb?.releaseDate,
        description = album?.label,
    )
}

fun UserProfileView.toArtist(): Artist? {
    return Artist(
        id = uri ?: return null,
        name = name ?: return null,
        cover = imageUrl?.toImageHolder(),
        followers = followersCount?.toInt(),
        description = "Total Public Playlists Count : $totalPublicPlaylistsCount",
    )
}

private fun String.toImage() = when (val type = substringAfter(':').substringBefore(':')) {
    "image" -> "https://i.scdn.co/image/${substringAfter("image:")}"
    "mosaic" -> "https://mosaic.scdn.co/300/${substringAfter("mosaic:").replace(":", "")}"
    else -> throw IllegalArgumentException("Invalid image type: $type")
}

fun UserProfileView.toShelf(): Shelf? {
    if (publicPlaylists.isNullOrEmpty()) return null
    val playlists = publicPlaylists.mapNotNull {
        val owner = it.ownerUri?.let { uri ->
            User(
                id = uri,
                name = it.ownerName!!
            )
        }
        Playlist(
            id = it.uri ?: return@mapNotNull null,
            title = it.name ?: return@mapNotNull null,
            false,
            cover = it.imageUrl?.toImage()?.toImageHolder(),
            authors = listOfNotNull(owner)
        ).toMediaItem()
    }
    return Shelf.Lists.Items("Public Playlists", playlists)
}

fun UserFollowers.toShelf(title: String): Shelf? {
    if (profiles.isNullOrEmpty()) return null
    val users = profiles.mapNotNull {
        Artist(
            id = it.uri ?: return@mapNotNull null,
            name = it.name ?: return@mapNotNull null,
            cover = it.imageUrl?.toImageHolder()
        ).toMediaItem()
    }
    return Shelf.Lists.Items(title, users)
}

fun pagedLibrary(
    queries: Queries, filter: String? = null, folderUri: String? = null
) = paged { offset ->
    val res = queries.libraryV3(offset, filter, folderUri)
    val library = res.json.data?.me?.libraryV3!!
    val shelves = library.items.mapNotNull { it.toShelf(queries) }
    val page = library.pagingInfo!!
    val next = page.offset!! + page.limit!!
    shelves to if (library.totalCount!! > next) next else null
}

fun LibraryV3.Item.toShelf(queries: Queries): Shelf? {
    return if (item?.typename == "LibraryFolderResponseWrapper") {
        val folder = item.data as Item.Folder
        val folderUri = folder.uri ?: return null
        Shelf.Category(
            folder.name!!, pagedLibrary(queries, null, folderUri)
        )
    } else item?.data?.toMediaItem()?.toShelf()
}

fun editablePlaylists(
    queries: Queries,
    track: String?,
    folderUri: String? = null
): PagedData<Pair<Playlist, Boolean>> =
    paged { offset ->
        val res = queries.editablePlaylists(
            offset, folderUri, track ?: "spotify:track:3z5lNLYtGC6LmvrxSbCQgd"
        ).json.data.me.editablePlaylists!!
        val playlists = res.items.mapNotNull {
            when (val item = it.item.data) {
                is Item.PseudoPlaylist -> {
                    val curated = it.curates ?: return@mapNotNull null
                    val playlist = item.toPlaylist() ?: return@mapNotNull null
                    listOfNotNull(playlist to curated)
                }

                is Item.Playlist -> {
                    val curated = it.curates ?: return@mapNotNull null
                    val playlist = item.toPlaylist() ?: return@mapNotNull null
                    listOfNotNull(playlist to curated)
                }

                is Item.Folder -> {
                    val uri = item.uri ?: return@mapNotNull null
                    editablePlaylists(queries, track, uri).loadAll()
                }

                else -> null
            }
        }.flatten()
        val page = res.pagingInfo!!
        val next = page.offset!! + page.limit!!
        playlists to if (res.totalCount!! > next) next else null
    }