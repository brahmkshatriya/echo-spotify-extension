package dev.brahmkshatriya.echo.extension

import dev.brahmkshatriya.echo.common.clients.ExtensionClient
import dev.brahmkshatriya.echo.common.clients.LoginClient
import dev.brahmkshatriya.echo.common.clients.SearchClient
import dev.brahmkshatriya.echo.common.helpers.ClientException
import dev.brahmkshatriya.echo.common.helpers.PagedData
import dev.brahmkshatriya.echo.common.models.QuickSearch
import dev.brahmkshatriya.echo.common.models.Request.Companion.toRequest
import dev.brahmkshatriya.echo.common.models.Shelf
import dev.brahmkshatriya.echo.common.models.Tab
import dev.brahmkshatriya.echo.common.models.User
import dev.brahmkshatriya.echo.common.settings.Setting
import dev.brahmkshatriya.echo.common.settings.Settings
import dev.brahmkshatriya.echo.extension.SpotifyApi.Companion.userAgent
import java.net.HttpCookie

class SpotifyExtension : ExtensionClient, LoginClient.WebView.Cookie, SearchClient {

    override suspend fun onExtensionSelected() {}

    override val settingItems: List<Setting> = emptyList()

    private lateinit var setting: Settings
    override fun setSettings(settings: Settings) {
        setting = settings
    }

    private val api by lazy { SpotifyApi(setting) }
    private val spotifyQuery by lazy { Query(api) }
    private suspend fun <T> withAuth(block: suspend SpotifyApi.() -> T): T {
        if (api.token == null) throw ClientException.LoginRequired()
        return api.block()
    }

    override val loginWebViewInitialUrl =
        "https://accounts.spotify.com/en/login".toRequest(mapOf(userAgent))

    override val loginWebViewStopUrlRegex = "https://accounts\\.spotify\\.com/en/status".toRegex()

    override suspend fun getCurrentUser(): User? {
        return if (api.token == null) null
        else spotifyQuery.profileAttributes().toUser()
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
//        withAuth {
//            mutate<SpotifyUser>(
//                "deleteSearchHistory",
//                "53bcb064f6cd18c23f752bc324a791194d20df612d8e1239c735144ab0399ced",
//                query
//            )
//        }
        TODO("Not yet implemented")
    }

    override suspend fun quickSearch(query: String?): List<QuickSearch> {
        return emptyList()
    }

    private var oldSearch: PagedData<Shelf>? = null
    private fun getBrowsePage(): PagedData<Shelf> = PagedData.Single {
        spotifyQuery.browseAll().data.browseStart.sections.toShelves(spotifyQuery)
    }

    override fun searchFeed(query: String?, tab: Tab?): PagedData<Shelf> {
        if (query == null) return getBrowsePage()
        if (tab == null || tab.id == "ALL") return oldSearch ?: getBrowsePage()
        return paged { offset ->
            when (tab.id) {
                "ARTISTS" ->
                    spotifyQuery.searchArtist(query, offset).data.searchV2.artists.toItemShelves()

                "TRACKS" ->
                    spotifyQuery.searchArtist(query, offset).data.searchV2.tracksV2.toItemShelves()

                "ALBUMS" ->
                    spotifyQuery.searchAlbum(query, offset).data.searchV2.albumsV2.toItemShelves()

                "PLAYLISTS" ->
                    spotifyQuery.searchAlbum(query, offset).data.searchV2.playlists.toItemShelves()

                "GENRES" ->
                    spotifyQuery.searchAlbum(query, offset).data.searchV2.genres
                        .toCategoryShelves(spotifyQuery)

                "PODCASTS" ->
                    spotifyQuery.searchAlbum(query, offset).data.searchV2.podcasts.toItemShelves()

                "EPISODES" ->
                    spotifyQuery.searchAlbum(query, offset).data.searchV2.episodes.toItemShelves()

                "USERS" ->
                    spotifyQuery.searchAlbum(query, offset).data.searchV2.users.toItemShelves()

                else -> emptyList<Shelf>() to null
            }
        }
    }

    override suspend fun searchTabs(query: String?): List<Tab> {
        if (query.isNullOrBlank()) return emptyList()
        val (shelves, tabs) = spotifyQuery.searchDesktop(query).data.searchV2
            .toShelvesAndTabs(spotifyQuery)
        oldSearch = shelves
        return tabs
    }
}

