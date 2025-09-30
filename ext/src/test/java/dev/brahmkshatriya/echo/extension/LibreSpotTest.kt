package dev.brahmkshatriya.echo.extension

import dev.brahmkshatriya.echo.extension.spotify.SpotifyApi
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class, ExperimentalStdlibApi::class)
class LibreSpotTest {
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() = Dispatchers.setMain(mainThreadSurrogate)

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    private val cookie = "sp_dc="
    private val spotifyApi = SpotifyApi()

    @Test
    fun testings() {
        runBlocking {
            val gid = "7ab971000ff34d01931e1b66203a83f3"
            val fileId = "6f842a801d0460c56c6fef4368f9ea9026c13db2"

            spotifyApi.setCookie(cookie)
            val manager = spotifyApi.app
            val token = manager.getRefreshToken()
            spotifyApi.refreshToken = token
            val accessToken = spotifyApi.getAppAccessToken()
            val accessToken2 = spotifyApi.getAppAccessToken()
            println("Access Token: $accessToken")
            println("Access Token2: $accessToken2")
        }
    }
}