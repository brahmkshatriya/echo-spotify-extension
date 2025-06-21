package dev.brahmkshatriya.echo.extension

import dev.brahmkshatriya.echo.extension.spotify.MercuryAccessToken
import dev.brahmkshatriya.echo.extension.spotify.SpotifyApi
import dev.brahmkshatriya.echo.extension.spotify.mercury.MercuryConnection
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
import java.io.File

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

    private val cookie = ""
    private val spotifyApi = SpotifyApi(File("cache")) {}

    @Test
    fun testings() {
        runBlocking {
            spotifyApi.setCookie(cookie)
            val accessToken = MercuryAccessToken(spotifyApi).get()
            val conn = MercuryConnection()
            val stored = conn.getStoredToken(accessToken)
            conn.authenticate(stored)

            val gid = "7ab971000ff34d01931e1b66203a83f3"
            val fileId = "6f842a801d0460c56c6fef4368f9ea9026c13db2"
            val key = conn.getAudioKey(gid, fileId)
            println("Key: ${key.toHexString()}")
        }
    }
}