package dev.brahmkshatriya.echo.extension

import com.google.protobuf.ByteString
import com.spotify.Authentication
import com.spotify.Authentication.LoginCredentials
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
import xyz.gianlu.librespot.core.Session
import java.io.File
import kotlin.io.encoding.Base64

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
    private val spotifyApi = SpotifyApi(File("cache")) {}

    @Test
    fun testings() {
        runBlocking {
            val gid = "7ab971000ff34d01931e1b66203a83f3"
            val fileId = "6f842a801d0460c56c6fef4368f9ea9026c13db2"

            spotifyApi.setCookie(cookie)
            val accessToken = MercuryAccessToken(spotifyApi).get()
            val stored = MercuryConnection.getStoredToken(accessToken)
//
//            val key = MercuryConnection.getAudioKey(storedToken, gid, fileId)
//            println("Key: ${key.toHexString()}")

            val credentials = LoginCredentials.newBuilder()
                .setUsername(stored.username)
                .setTyp(Authentication.AuthenticationType.AUTHENTICATION_STORED_SPOTIFY_CREDENTIALS)
                .setAuthData(ByteString.copyFrom(Base64.decode(stored.token)))
                .build()

            val sessionConfig = Session.Configuration.Builder()
                .setCacheEnabled(false)
                .setStoreCredentials(false)
                .build()

            val session = Session.Builder(sessionConfig)
                .credentials(credentials)
                .create()

            val audioKey = session.audioKey().getAudioKey(
                ByteString.fromHex(gid), ByteString.fromHex(fileId)
            )
            println("Key: ${audioKey.toHexString()}")
        }
    }
}