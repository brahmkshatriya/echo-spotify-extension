package dev.brahmkshatriya.echo.extension

import dev.brahmkshatriya.echo.common.clients.AlbumClient
import dev.brahmkshatriya.echo.common.clients.ExtensionClient
import dev.brahmkshatriya.echo.common.clients.HomeFeedClient
import dev.brahmkshatriya.echo.common.clients.LoginClient
import dev.brahmkshatriya.echo.common.clients.LyricsClient
import dev.brahmkshatriya.echo.common.clients.PlaylistClient
import dev.brahmkshatriya.echo.common.clients.SaveToLibraryClient
import dev.brahmkshatriya.echo.common.clients.SearchClient
import dev.brahmkshatriya.echo.common.clients.TrackClient
import dev.brahmkshatriya.echo.common.clients.TrackHideClient
import dev.brahmkshatriya.echo.common.clients.TrackLikeClient
import dev.brahmkshatriya.echo.common.helpers.ClientException
import dev.brahmkshatriya.echo.common.helpers.PagedData
import dev.brahmkshatriya.echo.common.models.Album
import dev.brahmkshatriya.echo.common.models.EchoMediaItem
import dev.brahmkshatriya.echo.common.models.ImageHolder
import dev.brahmkshatriya.echo.common.models.Lyrics
import dev.brahmkshatriya.echo.common.models.Playlist
import dev.brahmkshatriya.echo.common.models.QuickSearch
import dev.brahmkshatriya.echo.common.models.Request.Companion.toRequest
import dev.brahmkshatriya.echo.common.models.Shelf
import dev.brahmkshatriya.echo.common.models.Streamable
import dev.brahmkshatriya.echo.common.models.Streamable.Media.Companion.toMedia
import dev.brahmkshatriya.echo.common.models.Tab
import dev.brahmkshatriya.echo.common.models.Track
import dev.brahmkshatriya.echo.common.models.User
import dev.brahmkshatriya.echo.common.settings.Setting
import dev.brahmkshatriya.echo.common.settings.Settings
import dev.brahmkshatriya.echo.extension.spotify.Base62
import dev.brahmkshatriya.echo.extension.spotify.Queries
import dev.brahmkshatriya.echo.extension.spotify.SpotifyApi
import dev.brahmkshatriya.echo.extension.spotify.SpotifyApi.Companion.userAgent
import dev.brahmkshatriya.echo.extension.spotify.models.AccountAttributes
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.net.HttpCookie

class SpotifyExtension : ExtensionClient, LoginClient.WebView.Cookie,
    SearchClient, HomeFeedClient,
    TrackClient, TrackLikeClient, TrackHideClient, LyricsClient,
    AlbumClient, SaveToLibraryClient, PlaylistClient {

    override suspend fun onExtensionSelected() {}
    override val settingItems: List<Setting> = emptyList()


    private lateinit var setting: Settings
    override fun setSettings(settings: Settings) {
        setting = settings
    }

    private val api by lazy {
        SpotifyApi(setting.toCache()) {
            val token = token
            println("Token: $token")
            if (it.code == 401 && token != null) throw ClientException.Unauthorized(token)
            else throw it
        }
    }
    val queries by lazy { Queries(api) }

    override val loginWebViewInitialUrl =
        "https://accounts.spotify.com/en/login".toRequest(mapOf(userAgent))

    override val loginWebViewStopUrlRegex = "https://accounts\\.spotify\\.com/en/status".toRegex()

    override suspend fun onLoginWebviewStop(url: String, data: String): List<User> {
        val parsed = data.split(";").mapNotNull {
            if (it.isBlank()) null else HttpCookie.parse(it).firstOrNull()
        }
        val token = parsed.find { it.name == "sp_dc" } ?: throw Exception("Token not found")
        api.token = token.value
        val user = getCurrentUser()!!.copy(id = token.value)
        return listOf(user)
    }


    override suspend fun onSetLoginUser(user: User?) {
        api.token = user?.id
    }

    private var user: User? = null
    override suspend fun getCurrentUser(): User? {
        if (user == null)
            user = if (api.token == null) null else queries.profileAttributes().toUser()
        return user
    }

    private var product: AccountAttributes.Product? = null
    private suspend fun hasPremium(): Boolean {
        if (api.token == null) return false
        if (product == null) product = queries.accountAttributes().data.me.account.product
        return product != AccountAttributes.Product.FREE
    }

    override suspend fun deleteSearchHistory(query: QuickSearch.QueryItem) {
        //TODO
    }

    override suspend fun quickSearch(query: String?): List<QuickSearch> {
        //TODO
        return emptyList()
    }

    private var oldSearch: PagedData<Shelf>? = null
    private fun getBrowsePage(): PagedData<Shelf> = PagedData.Single {
        queries.browseAll().data.browseStart.sections.toShelves(queries)
    }

    override fun searchFeed(query: String?, tab: Tab?): PagedData<Shelf> {
        if (query == null) return getBrowsePage()
        if (tab == null || tab.id == "ALL") return oldSearch ?: getBrowsePage()
        return paged { offset ->
            when (tab.id) {
                "ARTISTS" -> queries.searchArtist(query, offset).data.searchV2.artists
                    .toItemShelves()

                "TRACKS" -> queries.searchTrack(query, offset).data.searchV2.tracksV2
                    .toItemShelves()

                "ALBUMS" -> queries.searchAlbum(query, offset).data.searchV2.albumsV2
                    .toItemShelves()

                "PLAYLISTS" -> queries.searchPlaylist(query, offset).data.searchV2.playlists
                    .toItemShelves()

                "GENRES" -> queries.searchGenres(query, offset).data.searchV2.genres
                    .toCategoryShelves(queries)

                "EPISODES" -> queries.searchFullEpisodes(query, offset).data.searchV2.episodes
                    .toItemShelves()

                "USERS" -> queries.searchUser(query, offset).data.searchV2.users
                    .toItemShelves()

                else -> emptyList<Shelf>() to null
            }
        }
    }

    override suspend fun searchTabs(query: String?): List<Tab> {
        if (query.isNullOrBlank()) return emptyList()
        val (shelves, tabs) = queries.searchDesktop(query).data.searchV2
            .toShelvesAndTabs(queries)
        oldSearch = shelves
        return tabs
    }

    override fun getShelves(track: Track): PagedData<Shelf> {
        TODO("Not yet implemented")
    }

    override suspend fun getStreamableMedia(streamable: Streamable): Streamable.Media {
        return when (streamable.type) {
            Streamable.MediaType.Source -> {
                val url = queries.storageResolve(streamable.id).cdnUrl.first()
                api.token ?: throw ClientException.LoginRequired()
                val accessToken = api.auth.getToken()
                val time = "time=${System.currentTimeMillis()}"
                val decryption = Streamable.Decryption.Widevine(
                    "https://spclient.wg.spotify.com/widevine-license/v1/audio/license?$time"
                        .toRequest(mapOf("Authorization" to "Bearer $accessToken")),
                    true
                )
                Streamable.Source.Http(
                    request = "$url&$time".toRequest(),
                    decryption = decryption,
                ).toMedia()
            }

            Streamable.MediaType.Background ->
                Streamable.Media.Background(streamable.id.toRequest())

            else -> throw IllegalStateException("Unsupported Streamable : $streamable")
        }
    }

    override suspend fun loadTrack(track: Track): Track = coroutineScope {
        val hasPremium = hasPremium()
        val canvas = async { queries.canvas(track.id).toStreamable() }
        val id = Base62.decode(track.id.substringAfter("spotify:track:"))
        queries.metadata4Track(id).toTrack(
            hasPremium,
            canvas.await()
        )
    }

    override fun searchTrackLyrics(clientId: String, track: Track) = PagedData.Single {
        val id = track.id.substringAfter("spotify:track:")
        val image = track.cover as ImageHolder.UrlRequestImageHolder
        val lyrics = runCatching { queries.colorLyrics(id, image.request.url).lyrics }
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

    override suspend fun isSavedToLibrary(mediaItem: EchoMediaItem): Boolean {
        if (api.token == null) return false
        val isSaved = queries.areEntitiesInLibrary(mediaItem.id)
            .data?.lookup?.firstOrNull()?.data?.saved
        return isSaved ?: false
    }

    override suspend fun removeFromLibrary(mediaItem: EchoMediaItem) {
        TODO("Not yet implemented")
    }

    override suspend fun saveToLibrary(mediaItem: EchoMediaItem) {
        TODO("Not yet implemented")
    }

    override suspend fun likeTrack(track: Track, isLiked: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun hideTrack(track: Track, isHidden: Boolean) {
        TODO("Not yet implemented")
    }


    override suspend fun loadPlaylist(playlist: Playlist): Playlist {
        return queries.fetchPlaylist(playlist.id).data.playlistV2.toPlaylist()!!
    }

    override fun loadTracks(playlist: Playlist) = paged { offset ->
        val content = queries.fetchPlaylistContent(playlist.id, offset).data.playlistV2.content!!
        val tracks = content.items!!.map { it.itemV2?.data?.toTrack()!! }
        val page = content.pagingInfo!!
        val next = page.offset!! + page.limit!!
        tracks to if (content.totalCount!! > next) next else null
    }

    override fun getShelves(playlist: Playlist): PagedData<Shelf> = PagedData.Single {
        TODO("Not yet implemented")
    }

    override suspend fun loadAlbum(album: Album): Album {
        return queries.getAlbum(album.id).data.albumUnion.toAlbum()!!
    }

    override fun loadTracks(album: Album): PagedData.Continuous<Track> {
        var next = 0L
        return paged { offset ->
            val content = queries.queryAlbumTracks(album.id, offset).data.albumUnion.tracksV2!!
            val tracks = content.items!!.map {
                it.track?.toTrack(album)!!
            }
            next += tracks.size
            tracks to if (content.totalCount!! > next) next else null
        }
    }

    override fun getShelves(album: Album): PagedData<Shelf> = PagedData.Single {
        TODO("Not yet implemented")
    }

    override suspend fun getHomeTabs(): List<Tab> {
        if (api.token == null) return emptyList()
        val all = listOf(Tab("", "All"))
        return all + queries.homeFeedChips().data?.home?.homeChips?.toTabs()!!
    }

    override fun getHomeFeed(tab: Tab?) = PagedData.Single {
        val home = if (tab == null || tab.id == "") queries.home(null).data?.home!!
        else queries.homeSubfeed(tab.id).data?.home!!
        home.run {
            sectionContainer?.sections?.toShelves(
                queries,
                greeting?.transformedLabel ?: "What's on your mind?"
            )!!
        }
    }

}