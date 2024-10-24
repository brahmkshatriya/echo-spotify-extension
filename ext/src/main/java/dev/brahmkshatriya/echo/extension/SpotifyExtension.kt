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
import dev.brahmkshatriya.echo.extension.SpotifyApi.Companion.applyPagePagination
import dev.brahmkshatriya.echo.extension.SpotifyApi.Companion.applySectionPagination
import dev.brahmkshatriya.echo.extension.SpotifyApi.Companion.userAgent
import dev.brahmkshatriya.echo.extension.models.BrowseAll
import dev.brahmkshatriya.echo.extension.models.SpotifyUser
import kotlinx.serialization.json.buildJsonObject
import java.net.HttpCookie

class SpotifyExtension : ExtensionClient, LoginClient.WebView.Cookie, SearchClient {

    override suspend fun onExtensionSelected() {}

    override val settingItems: List<Setting> = emptyList()

    private lateinit var setting: Settings
    override fun setSettings(settings: Settings) {
        setting = settings
    }

    private val api by lazy { SpotifyApi(setting) }
    private suspend fun <T> withAuth(block: suspend SpotifyApi.() -> T): T {
        if (api.token == null) throw ClientException.LoginRequired()
        return api.block()
    }

    override val loginWebViewInitialUrl =
        "https://accounts.spotify.com/en/login".toRequest(mapOf(userAgent))

    override val loginWebViewStopUrlRegex = "https://accounts\\.spotify\\.com/en/status".toRegex()

    override suspend fun getCurrentUser(): User? {
        return if (api.token == null) null
        else api.query<SpotifyUser>(
            "profileAttributes",
            "53bcb064f6cd18c23f752bc324a791194d20df612d8e1239c735144ab0399ced"
        ).toUser()
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
    private fun getBrowsePage() = PagedData.Single {
        api.query<BrowseAll>(
            "browseAll",
            "cd6fcd0ce9d1849477645646601a6d444597013355467e24066dad2c1dc9b740",
            buildJsonObject {
                applyPagePagination(0, 10)
                applySectionPagination(0, 99)
            }
        ).data.browseStart.sections.toShelves(api)
    }

    override fun searchFeed(query: String?, tab: Tab?): PagedData<Shelf> {
        if (query == null) return getBrowsePage()
        if (tab == null) return oldSearch ?: getBrowsePage()
        TODO()
    }

    override suspend fun searchTabs(query: String?): List<Tab> {
        if (query == null) return emptyList()
        else {
            TODO()
        }
    }
}