package dev.brahmkshatriya.echo.extension

import dev.brahmkshatriya.echo.common.clients.AlbumClient
import dev.brahmkshatriya.echo.common.clients.ArtistClient
import dev.brahmkshatriya.echo.common.clients.ArtistFollowClient
import dev.brahmkshatriya.echo.common.clients.ExtensionClient
import dev.brahmkshatriya.echo.common.clients.HomeFeedClient
import dev.brahmkshatriya.echo.common.clients.LibraryFeedClient
import dev.brahmkshatriya.echo.common.clients.LoginClient
import dev.brahmkshatriya.echo.common.clients.LyricsClient
import dev.brahmkshatriya.echo.common.clients.PlaylistClient
import dev.brahmkshatriya.echo.common.clients.PlaylistEditClient
import dev.brahmkshatriya.echo.common.clients.RadioClient
import dev.brahmkshatriya.echo.common.clients.SaveToLibraryClient
import dev.brahmkshatriya.echo.common.clients.SearchFeedClient
import dev.brahmkshatriya.echo.common.clients.ShareClient
import dev.brahmkshatriya.echo.common.clients.TrackClient
import dev.brahmkshatriya.echo.common.clients.TrackHideClient
import dev.brahmkshatriya.echo.common.clients.TrackLikeClient
import dev.brahmkshatriya.echo.common.helpers.ClientException
import dev.brahmkshatriya.echo.common.helpers.PagedData
import dev.brahmkshatriya.echo.common.helpers.WebViewRequest
import dev.brahmkshatriya.echo.common.models.Album
import dev.brahmkshatriya.echo.common.models.Artist
import dev.brahmkshatriya.echo.common.models.EchoMediaItem
import dev.brahmkshatriya.echo.common.models.EchoMediaItem.Companion.toMediaItem
import dev.brahmkshatriya.echo.common.models.Feed
import dev.brahmkshatriya.echo.common.models.Feed.Companion.toFeed
import dev.brahmkshatriya.echo.common.models.ImageHolder
import dev.brahmkshatriya.echo.common.models.ImageHolder.Companion.toImageHolder
import dev.brahmkshatriya.echo.common.models.Lyrics
import dev.brahmkshatriya.echo.common.models.Playlist
import dev.brahmkshatriya.echo.common.models.QuickSearchItem
import dev.brahmkshatriya.echo.common.models.Radio
import dev.brahmkshatriya.echo.common.models.Request
import dev.brahmkshatriya.echo.common.models.Request.Companion.toRequest
import dev.brahmkshatriya.echo.common.models.Shelf
import dev.brahmkshatriya.echo.common.models.Streamable
import dev.brahmkshatriya.echo.common.models.Streamable.Media.Companion.toMedia
import dev.brahmkshatriya.echo.common.models.Tab
import dev.brahmkshatriya.echo.common.models.Track
import dev.brahmkshatriya.echo.common.models.User
import dev.brahmkshatriya.echo.common.settings.SettingSwitch
import dev.brahmkshatriya.echo.common.settings.Settings
import dev.brahmkshatriya.echo.extension.spotify.Queries
import dev.brahmkshatriya.echo.extension.spotify.SpotifyApi
import dev.brahmkshatriya.echo.extension.spotify.SpotifyApi.Companion.userAgent
import dev.brahmkshatriya.echo.extension.spotify.models.AccountAttributes
import dev.brahmkshatriya.echo.extension.spotify.models.ArtistOverview
import dev.brahmkshatriya.echo.extension.spotify.models.GetAlbum
import dev.brahmkshatriya.echo.extension.spotify.models.HomeFeed
import dev.brahmkshatriya.echo.extension.spotify.models.Item
import dev.brahmkshatriya.echo.extension.spotify.models.Metadata4Track
import dev.brahmkshatriya.echo.extension.spotify.models.UserProfileView
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.io.EOFException
import java.io.File
import java.io.InputStream
import java.net.URLDecoder

open class SpotifyExtension : ExtensionClient, LoginClient.WebView,
    SearchFeedClient, HomeFeedClient, LibraryFeedClient, LyricsClient, ShareClient,
    TrackClient, TrackLikeClient, TrackHideClient, RadioClient, SaveToLibraryClient,
    AlbumClient, PlaylistClient, ArtistClient, ArtistFollowClient, PlaylistEditClient {

    override suspend fun onExtensionSelected() {}
    override val settingItems
        get() = listOf(
            SettingSwitch(
                "Show Canvas",
                "show_canvas",
                "Whether to show background video canvas in songs",
                showCanvas
            )
        )

    private val showCanvas get() = setting.getBoolean("show_canvas") ?: true

    lateinit var setting: Settings
    override fun setSettings(settings: Settings) {
        setting = settings
    }

    open val cacheDir = File("cache")
    val api by lazy {
        SpotifyApi(cacheDir) {
            val token = cookie
            if (it.code == 401 && token != null) throw ClientException.Unauthorized(token)
            else throw it
        }
    }
    val queries by lazy { Queries(api) }

    override val webViewRequest = object : WebViewRequest.Cookie<List<User>> {
        override val initialUrl =
            "https://accounts.spotify.com/en/login".toRequest(mapOf(userAgent))
        override val stopUrlRegex =
            Regex("(https://accounts\\.spotify\\.com/en/status)|(https://open\\.spotify\\.com)")

        val emailRegex = Regex("remember=([^;]+)")
        override suspend fun onStop(url: Request, cookie: String): List<User> {
            if (!cookie.contains("sp_dc")) throw Exception("Token not found")
            api.setCookie(cookie)
            val email = emailRegex.find(cookie)?.groups?.get(1)?.value?.let {
                URLDecoder.decode(it, "UTF-8")
            }
            val user = queries.profileAttributes().json.toUser()
                .copy(extras = mapOf("cookie" to cookie), subtitle = email)
            return listOf(user)
        }
    }

    override suspend fun onSetLoginUser(user: User?) {
        val cookie = if (user == null) null
        else user.extras["cookie"] ?: throw ClientException.Unauthorized(user.id)
        api.setCookie(cookie)
        this.user = user
        this.product = null
    }

    private var user: User? = null
    override suspend fun getCurrentUser(): User? {
        return user?.copy(extras = mapOf())
    }

    private var product: AccountAttributes.Product? = null
    private suspend fun hasPremium(): Boolean {
        if (api.cookie == null) return false
        if (product == null) product = queries.accountAttributes().json.data.me.account.product
        return product != AccountAttributes.Product.FREE
    }

    override suspend fun deleteQuickSearch(item: QuickSearchItem) {
        val history = getHistory().toMutableList()
        history.remove(item.title)
        setting.putString("search_history", history.joinToString(","))
    }

    private fun getHistory() = setting.getString("search_history")
        ?.split(",")?.distinct()?.take(5)
        ?: emptyList()

    private fun saveInHistory(query: String) {
        val history = getHistory().toMutableList()
        history.add(0, query)
        setting.putString("search_history", history.joinToString(","))
    }

    override suspend fun quickSearch(query: String): List<QuickSearchItem> {
        if (query.isBlank()) return getHistory().map { QuickSearchItem.Query(it, true) }
        val results = queries.searchDesktop(query, 10).json.data.searchV2.topResultsV2?.itemsV2
        return results?.mapNotNull {
            val item = it.item?.toMediaItem() ?: return@mapNotNull null
            QuickSearchItem.Media(item, false)
        }.orEmpty()
    }

    private var oldSearch: Feed? = null
    private fun getBrowsePage(): Feed = PagedData.Single {
        queries.browseAll().json.data.browseStart.sections.toShelves(queries)
    }.toFeed()

    override fun searchFeed(query: String, tab: Tab?): Feed {
        if (query.isBlank()) return getBrowsePage()
        saveInHistory(query)
        if (tab == null || tab.id == "ALL") return oldSearch ?: getBrowsePage()
        return paged { offset ->
            when (tab.id) {
                "ARTISTS" -> queries.searchArtist(query, offset).json.data.searchV2.artists
                    .toItemShelves()

                "TRACKS" -> queries.searchTrack(query, offset).json.data.searchV2.tracksV2
                    .toItemShelves()

                "ALBUMS" -> queries.searchAlbum(query, offset).json.data.searchV2.albumsV2
                    .toItemShelves()

                "PLAYLISTS" -> queries.searchPlaylist(query, offset).json.data.searchV2.playlists
                    .toItemShelves()

                "GENRES" -> queries.searchGenres(query, offset).json.data.searchV2.genres
                    .toCategoryShelves(queries)

                "EPISODES" -> queries.searchFullEpisodes(query, offset).json.data.searchV2.episodes
                    .toItemShelves()

                "USERS" -> queries.searchUser(query, offset).json.data.searchV2.users
                    .toItemShelves()

                else -> emptyList<Shelf>() to null
            }
        }.toFeed()
    }

    override suspend fun searchTabs(query: String): List<Tab> {
        if (query.isBlank()) return emptyList()
        val (shelves, tabs) = queries.searchDesktop(query).json.data.searchV2
            .toShelvesAndTabs(queries)
        oldSearch = shelves.toFeed()
        return tabs
    }

    override fun getShelves(track: Track) = PagedData.Single {
        val (union, rec) = coroutineScope {
            val a = async { queries.getTrack(track.id).json.data.trackUnion }
            val b = queries.internalLinkRecommenderTrack(track.id).json.data.seoRecommendedTrack
            a.await() to b
        }
        val list = rec.items.mapNotNull { it.data?.toTrack() }.takeIf { it.isNotEmpty() }?.let {
            listOf(Shelf.Lists.Tracks("More like this", it))
        } ?: emptyList()
        val first = union.firstArtist?.items?.firstOrNull()?.toShelves(queries) ?: emptyList()
        val other = union.otherArtists?.items.orEmpty().map { it.toShelves(queries) }.flatten()
        list + first + other
    }

    override suspend fun loadStreamableMedia(
        streamable: Streamable, isDownload: Boolean
    ): Streamable.Media {
        return when (streamable.type) {
            Streamable.MediaType.Server -> {
                api.cookie ?: throw ClientException.LoginRequired()
                val format = Metadata4Track.Format.valueOf(streamable.extras["format"]!!)
                if (format != Metadata4Track.Format.MP4_128 && format != Metadata4Track.Format.MP4_256) throw ClientException.NotSupported(
                    format.name
                )
                val accessToken = api.getAccessToken()
                val url = queries.storageResolve(streamable.id).json.cdnUrl.first()
                val time = "time=${System.currentTimeMillis()}"
                val decryption = Streamable.Decryption.Widevine(
                    "https://spclient.wg.spotify.com/widevine-license/v1/audio/license?$time"
                        .toRequest(mapOf("Authorization" to "Bearer $accessToken")),
                    true
                )
                Streamable.Source.Http(
                    request = url.toRequest(),
                    decryption = decryption,
                ).toMedia()
            }

            Streamable.MediaType.Background ->
                Streamable.Media.Background(streamable.id.toRequest())

            else -> throw IllegalStateException("Unsupported Streamable : $streamable")
        }
    }

    open val supportsPlayPlay = false
    open val showWidevineStreams = false

    override suspend fun loadTrack(track: Track): Track = coroutineScope {
        val hasPremium = hasPremium()
        val canvas =
            if (showCanvas) async { queries.canvas(track.id).json.toStreamable() } else null
        val isLiked = async { isSavedToLibrary(track.toMediaItem()) }
        queries.metadata4Track(track.id).json.toTrack(
            hasPremium,
            supportsPlayPlay && !showWidevineStreams,
            showWidevineStreams,
            canvas?.await()
        ).copy(
            isExplicit = track.isExplicit,
            isLiked = isLiked.await()
        )
    }

    private suspend fun createRadio(id: String): Radio {
        val radioId = queries.seedToPlaylist(id).json.mediaItems.first().uri
        return queries.fetchPlaylist(radioId).json.data.playlistV2.toRadio()!!
    }

    override fun loadTracks(radio: Radio) = loadPlaylistTracks(radio.id, true)
    override suspend fun radio(track: Track, context: EchoMediaItem?) = createRadio(track.id)
    override suspend fun radio(album: Album) = createRadio(album.id)
    override suspend fun radio(artist: Artist) = createRadio(artist.id)
    override suspend fun radio(user: User) = createRadio(user.id)
    override suspend fun radio(playlist: Playlist) = createRadio(playlist.id)

    override suspend fun loadPlaylist(playlist: Playlist) =
        when (val type = playlist.id.substringAfter(":").substringBefore(":")) {
            "playlist" -> {
                val new = queries.fetchPlaylist(playlist.id).json.data.playlistV2.toPlaylist()!!
                new.copy(
                    isEditable = runCatching {
                        val id = getPlaylistId(new)
                        queries.editPlaylistMetadata(id, new.title, new.description).json
                            .capabilities?.canEditItems
                    }.getOrNull() ?: false
                )
            }

            "collection" -> {
                val totalSongs = if (playlist.tracks == null)
                    queries.fetchLibraryTracks(0).json.data.me.library.tracks.totalCount
                else playlist.tracks

                likedPlaylist.copy(
                    cover = "https://misc.scdn.co/liked-songs/liked-songs-640.jpg".toImageHolder(),
                    tracks = totalSongs?.toInt()
                )
            }

            else -> throw ClientException.NotSupported("Unsupported playlist type: $type")
        }

    private fun loadPlaylistTracks(id: String, skipFirst: Boolean = false) = paged { offset ->
        val content = queries.fetchPlaylistContent(id, offset).json.data.playlistV2.content!!
        val tracks = content.items!!.mapNotNull {
            val track = it.itemV2?.data?.toTrack() ?: return@mapNotNull null
            if (it.uid != null) track.copy(extras = mapOf("uid" to it.uid)) else track
        }.let {
            if (skipFirst && offset == 0) it.drop(1) else it
        }
        val page = content.pagingInfo!!
        val next = page.offset!! + page.limit!!
        tracks to if (content.totalCount!! > next) next else null
    }

    private fun loadLikedTracks() = paged { offset ->
        val content = queries.fetchLibraryTracks(offset).json.data.me.library.tracks
        val tracks = content.items.map { it.track.data?.toTrack(url = it.track.uri)!! }
        val page = content.pagingInfo!!
        val next = page.offset!! + page.limit!!
        tracks to if (content.totalCount!! > next) next else null
    }

    override fun loadTracks(playlist: Playlist) =
        when (val type = playlist.id.substringAfter(":").substringBefore(":")) {
            "playlist" -> loadPlaylistTracks(playlist.id)
            "collection" -> loadLikedTracks()
            else -> throw ClientException.NotSupported("Unsupported playlist type: $type")
        }

    override suspend fun moveTrackInPlaylist(
        playlist: Playlist,
        tracks: List<Track>,
        fromIndex: Int,
        toIndex: Int
    ) {
        getPlaylistId(playlist)
        val uid = tracks[fromIndex].extras["uid"]!!
        val before = if (fromIndex - toIndex > 0) 0 else 1
        val fromUid = tracks.getOrNull(toIndex + before)?.extras?.get("uid")
        queries.moveItemsInPlaylist(playlist.id, uid, fromUid)
    }

    override suspend fun removeTracksFromPlaylist(
        playlist: Playlist,
        tracks: List<Track>,
        indexes: List<Int>
    ) {
        if (api.cookie == null) throw ClientException.LoginRequired()
        when (val type = playlist.id.substringAfter(":").substringBefore(":")) {
            "playlist" -> {
                val uids = indexes.map { tracks[it].extras["uid"]!! }.toTypedArray()
                queries.removeFromPlaylist(playlist.id, *uids)
            }

            "collection" -> {
                val uris = indexes.map { tracks[it].id }.toTypedArray()
                queries.removeFromLibrary(*uris)
            }

            else -> throw ClientException.NotSupported("Unsupported playlist type: $type")
        }
    }

    override suspend fun addTracksToPlaylist(
        playlist: Playlist,
        tracks: List<Track>,
        index: Int,
        new: List<Track>
    ) {
        if (api.cookie == null) throw ClientException.LoginRequired()
        when (val type = playlist.id.substringAfter(":").substringBefore(":")) {
            "playlist" -> {
                val uris = new.map { it.id }.toTypedArray()
                val fromUid = tracks.getOrNull(index)?.extras?.get("uid")
                queries.addToPlaylist(playlist.id, fromUid, *uris)
            }

            "collection" -> {
                val uris = new.map { it.id }.toTypedArray()
                queries.addToLibrary(*uris)
            }

            else -> throw ClientException.NotSupported("Unsupported playlist type: $type")
        }

    }

    override suspend fun createPlaylist(title: String, description: String?): Playlist {
        if (api.cookie == null) throw ClientException.LoginRequired()
        val uri = queries.createPlaylist(title, description).json.uri
        val userId = getCurrentUser()!!.id.substringAfter("spotify:user:")
        queries.playlistToLibrary(userId, uri)
        return loadPlaylist(Playlist(uri, title, true))
    }

    private fun getPlaylistId(playlist: Playlist): String {
        if (api.cookie == null) throw ClientException.LoginRequired()
        if (!playlist.id.startsWith("spotify:playlist:"))
            throw ClientException.NotSupported("Unsupported playlist type: ${playlist.id}")
        return playlist.id.substringAfter("spotify:playlist:")
    }

    override suspend fun deletePlaylist(playlist: Playlist) {
        getPlaylistId(playlist)
        val userId = getCurrentUser()!!.id.substringAfter("spotify:user:")
        queries.deletePlaylist(userId, playlist.id)
    }

    override suspend fun editPlaylistMetadata(
        playlist: Playlist, title: String, description: String?
    ) {
        val id = getPlaylistId(playlist)
        queries.editPlaylistMetadata(id, title, description).raw
    }

    override suspend fun listEditablePlaylists(track: Track?): List<Pair<Playlist, Boolean>> {
        return editablePlaylists(queries, track?.id).loadAll()
    }

    override fun getShelves(playlist: Playlist): PagedData<Shelf> = PagedData.Single {
        val playlists =
            queries.seoRecommendedPlaylist(playlist.id).json.data.seoRecommendedPlaylist.items.mapNotNull {
                it.toMediaItem()
            }
        if (playlists.isEmpty()) return@Single emptyList()
        listOf(
            Shelf.Lists.Items(
                title = "More Playlists like this",
                list = playlists,
            )
        )
    }

    override suspend fun loadAlbum(album: Album): Album {
        val res = queries.getAlbum(album.id)
        return res.json.data.albumUnion.toAlbum()!!.copy(
            extras = mapOf("raw" to res.raw)
        )
    }


    override fun getShelves(album: Album): PagedData<Shelf> = PagedData.Single {
        val union = api.json.decode<GetAlbum>(album.extras["raw"]!!).data.albumUnion
        union.moreAlbumsByArtist?.items.orEmpty().map { it.toShelves(queries) }.flatten()
    }

    override fun loadTracks(album: Album): PagedData.Continuous<Track> {
        var next = 0L
        return paged { offset ->
            val content = queries.queryAlbumTracks(album.id, offset).json.data.albumUnion.tracksV2!!
            val tracks = content.items!!.map {
                it.track?.toTrack(album)!!
            }
            next += tracks.size
            tracks to if (content.totalCount!! > next) next else null
        }
    }

    private var allHomeFeed: HomeFeed.Home? = null
    override suspend fun getHomeTabs(): List<Tab> {
        if (api.cookie == null) return emptyList()
        val all = listOf(Tab("", "All"))
        val home = queries.home(null).json.data?.home!!
        allHomeFeed = home
        return all + home.homeChips?.toTabs()!!
    }

    override fun getHomeFeed(tab: Tab?) = PagedData.Single {
        val home = if (tab != null && tab.id != "") queries.home(tab.id).json.data?.home!!
        else allHomeFeed ?: queries.home(null).json.data?.home!!
        home.toShelves(queries)
    }.toFeed()

    override suspend fun onShare(item: EchoMediaItem): String {
        val type = item.id.substringAfter("spotify:").substringBefore(":")
        val id = item.id.substringAfter("spotify:$type:")
        return "https://open.spotify.com/$type/$id"
    }

    override fun getLibraryFeed(tab: Tab?): Feed {
        if (api.cookie == null) throw ClientException.LoginRequired()
        return when (tab?.id) {
            "All", null -> pagedLibrary(queries)
            "You" -> PagedData.Single {
                val top = queries.userTopContent().json.data.me.profile

                val id = getCurrentUser()!!.id.substringAfter("spotify:user:")
                val uris = queries.recentlyPlayed(id).json.playContexts.map { it.uri }
                listOfNotNull(
                    Shelf.Lists.Items(
                        "Top Artist",
                        top.topArtists?.items.orEmpty().mapNotNull {
                            it.toMediaItem()
                        }
                    ),
                    Shelf.Lists.Tracks(
                        "Top Tracks",
                        top.topTracks?.items.orEmpty().mapNotNull {
                            (it.data as Item.Track).toTrack()
                        }
                    )
                ) + queries.fetchEntitiesForRecentlyPlayed(uris).json.data.lookup.mapNotNull {
                    it.data?.toMediaItem()?.toShelf()
                }
            }

            else -> pagedLibrary(queries, tab.id)
        }.toFeed()
    }

    override suspend fun getLibraryTabs(): List<Tab> {
        if (api.cookie == null) return emptyList()
        val filters = queries.libraryV3(0).json.data?.me?.libraryV3?.availableFilters
            ?.mapNotNull { it.name }.orEmpty()
        return (listOf("All", "You") + filters).map { Tab(it, it) }
    }

    override suspend fun isSavedToLibrary(mediaItem: EchoMediaItem): Boolean {
        if (api.cookie == null) return false
        val isSaved = queries.areEntitiesInLibrary(mediaItem.id)
            .json.data?.lookup?.firstOrNull()?.data?.saved
        return isSaved ?: false
    }

    override suspend fun saveToLibrary(mediaItem: EchoMediaItem, save: Boolean) {
        if (api.cookie == null) throw ClientException.LoginRequired()
        if (save) queries.addToLibrary(mediaItem.id)
        else queries.removeFromLibrary(mediaItem.id)
    }

    override suspend fun likeTrack(track: Track, isLiked: Boolean) {
        if (isLiked) queries.addToLibrary(track.id)
        else queries.removeFromLibrary(track.id)
    }

    override suspend fun hideTrack(track: Track, isHidden: Boolean) {
        TODO("Find a way to hide tracks, cause it's not available on web.")
    }

    override suspend fun followArtist(artist: Artist, follow: Boolean) {
        if (api.cookie == null) throw ClientException.LoginRequired()
        when (val type = artist.id.substringAfter(":").substringBefore(":")) {
            "artist" -> {
                if (follow) queries.addToLibrary(artist.id)
                else queries.removeFromLibrary(artist.id)
            }

            "user" -> {
                val id = artist.id.substringAfter("spotify:user:")
                if (follow) queries.followUsers(id)
                else queries.unfollowUsers(id)
            }

            else -> throw IllegalArgumentException("Unsupported artist type: $type")
        }
    }

    override fun getShelves(artist: Artist) = PagedData.Single {
        when (val type = artist.id.substringAfter(":").substringBefore(":")) {
            "artist" -> {
                val res = api.json.decode<ArtistOverview>(artist.extras["raw"]!!)
                res.data.artistUnion.toShelves(queries)
            }

            "user" -> {
                val res = api.json.decode<UserProfileView>(artist.extras["raw"]!!)
                val id = artist.id.substringAfter("spotify:user:")
                listOfNotNull(
                    res.toShelf(),
                    queries.profileFollowers(id).json.toShelf("Followers"),
                    queries.profileFollowing(id).json.toShelf("Following")
                )
            }

            else -> throw IllegalArgumentException("Unsupported artist type: $type")
        }
    }

    override suspend fun loadArtist(artist: Artist): Artist {
        when (val type = artist.id.substringAfter(":").substringBefore(":")) {
            "artist" -> {
                val res = queries.queryArtistOverview(artist.id)
                return res.json.data.artistUnion.toArtist()!!.copy(
                    extras = mapOf("raw" to res.raw)
                )
            }

            "user" -> {
                val id = artist.id.substringAfter("spotify:user:")
                val profile = queries.profileWithPlaylists(id)
                return profile.json.toArtist()!!.copy(
                    extras = mapOf("raw" to profile.raw)
                )
            }

            else -> throw IllegalArgumentException("Unsupported artist type: $type")
        }
    }

    override fun searchTrackLyrics(clientId: String, track: Track) = PagedData.Single {
        val id = track.id.substringAfter("spotify:track:")
        val image = track.cover as ImageHolder.UrlRequestImageHolder
        val lyrics = runCatching { queries.colorLyrics(id, image.request.url).json.lyrics }
            .getOrNull() ?: return@Single emptyList<Lyrics>()
        var last = Long.MAX_VALUE
        val list = lyrics.lines?.reversed()?.mapNotNull {
            val start = it.startTimeMs?.toLong()!!
            val item = Lyrics.Item(
                it.words ?: return@mapNotNull null,
                startTime = start,
                endTime = last,
            )
            last = start
            item
        }?.reversed() ?: return@Single emptyList<Lyrics>()
        listOf(
            Lyrics(
                id = track.id,
                title = track.title,
                subtitle = lyrics.providerDisplayName,
                lyrics = Lyrics.Timed(list)
            )
        )
    }

    override suspend fun loadLyrics(lyrics: Lyrics) = lyrics

    companion object {
        fun InputStream.skipBytes(len: Int) {
            var remaining = len
            val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
            while (remaining > 0) {
                val toRead = minOf(remaining, buffer.size)
                val read = read(buffer, 0, toRead)
                if (read == -1) break // EOF
                remaining -= read
            }
            if (remaining > 0) throw EOFException("Reached end of stream before reading $len bytes")
        }


        fun String.hexToByteArray(): ByteArray {
            require(length % 2 == 0) { "Hex string must have an even length" }
            return ByteArray(length / 2) { i ->
                substring(2 * i, 2 * i + 2).toInt(16).toByte()
            }
        }
    }
}