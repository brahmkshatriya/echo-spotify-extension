package dev.brahmkshatriya.echo.extension

import dev.brahmkshatriya.echo.common.clients.ExtensionClient
import dev.brahmkshatriya.echo.common.clients.LoginClient
import dev.brahmkshatriya.echo.common.clients.SaveToLibraryClient
import dev.brahmkshatriya.echo.common.clients.SearchClient
import dev.brahmkshatriya.echo.common.clients.TrackClient
import dev.brahmkshatriya.echo.common.helpers.ClientException
import dev.brahmkshatriya.echo.common.helpers.PagedData
import dev.brahmkshatriya.echo.common.models.EchoMediaItem
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
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.net.HttpCookie

class SpotifyExtension : ExtensionClient, LoginClient.WebView.Cookie, SearchClient, TrackClient,
    SaveToLibraryClient {

    override suspend fun onExtensionSelected() {}

    override val settingItems: List<Setting> = emptyList()

    private lateinit var setting: Settings
    override fun setSettings(settings: Settings) {
        setting = settings
    }

    private val api by lazy { SpotifyApi(setting.toCache()) }
    val queries by lazy { Queries(api) }

    override val loginWebViewInitialUrl =
        "https://accounts.spotify.com/en/login".toRequest(mapOf(userAgent))

    override val loginWebViewStopUrlRegex = "https://accounts\\.spotify\\.com/en/status".toRegex()

    override suspend fun getCurrentUser(): User? {
        return if (api.token == null) null
        else queries.profileAttributes().toUser()
    }

    override suspend fun onLoginWebviewStop(url: String, data: String): List<User> {
        val parsed = data.split(";").map { HttpCookie.parse(it).first() }
        val token = parsed.find { it.name == "sp_dc" } ?: throw Exception("Token not found")
        api.token = token.value
        val user = getCurrentUser()!!.copy(id = token.value)
        return listOf(user)
    }

    override suspend fun onSetLoginUser(user: User?) {
        api.token = user?.id
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
                val decryption = Streamable.Decryption.Widevine(
                    "https://spclient.wg.spotify.com/widevine-license/v1/audio/license?id=${streamable.id}"
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

    override suspend fun loadTrack(track: Track): Track = coroutineScope {
        val hasPremium = false
        val canvas = async { queries.canvas(track.id).toStreamable() }
        val id = Base62.decode(track.id.substringAfter("spotify:track:"))
        queries.metadata4Track(id).toTrack(
            hasPremium,
            canvas.await()
        )
    }

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
}

