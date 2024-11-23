package dev.brahmkshatriya.echo.extension

import dev.brahmkshatriya.echo.common.models.Album
import dev.brahmkshatriya.echo.common.models.Artist
import dev.brahmkshatriya.echo.common.models.EchoMediaItem.Companion.toMediaItem
import dev.brahmkshatriya.echo.common.models.Playlist
import dev.brahmkshatriya.echo.common.models.Shelf
import dev.brahmkshatriya.echo.common.models.Track
import dev.brahmkshatriya.echo.common.models.User
import dev.brahmkshatriya.echo.extension.spotify.Base62
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(DelicateCoroutinesApi::class)
@ExperimentalCoroutinesApi
class LibraryTest {
    private val extension = SpotifyExtension()

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    private val token = System.getenv("SPOTIFY_TOKEN")!!
    private val user = User(token, "")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        extension.setSettings(MockedSettings())
        runBlocking {
            extension.onExtensionSelected()
            extension.onSetLoginUser(user)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    private fun testIn(title: String, block: suspend CoroutineScope.() -> Unit) = runBlocking {
        println("\n-- $title --")
        block.invoke(this)
        println("\n")
    }

    @Test
    fun testCurrentUser() = testIn("Testing current user") {
        val user = extension.getCurrentUser()
        println(user)
        println(extension.queries.metadata4Track("9ddc535dfd344ebbb280514a0d7dc5c3"))
    }

    private fun Shelf.print() {
        when (this) {
            is Shelf.Category -> println(this)
            is Shelf.Item -> println(this)
            is Shelf.Lists<*> -> {
                println("${this.title} : ${this.list.size}")
                this.list.forEach { println(it) }
            }
        }
    }

    @Test
    fun testSearchEmpty() = testIn("Testing Empty Search") {
        val tab = extension.searchTabs(null).firstOrNull()
        val feed = extension.searchFeed(null, tab)
        val shelves = feed.loadAll()
        val shelf = shelves.first() as Shelf.Lists.Categories
        val load = shelf.list.first().items!!.loadFirst()
        load.forEach { it.print() }
    }


    @Test
    fun testSearch() = testIn("Testing Search") {
        val searchQuery = "hero"
        val quickSearch = extension.quickSearch(searchQuery)
        quickSearch.forEach { println(it) }
        val tabs = extension.searchTabs(searchQuery)
        tabs.forEach { tab ->
            println("Tab: $tab")
            val feed = extension.searchFeed(searchQuery, tab)
            val shelves = feed.loadFirst()
            shelves.forEach { it.print() }
        }
    }

    @Test
    fun getTrack() = testIn("Get Track") {
        val track = extension.loadTrack(Track("spotify:track:71NTIlx3GOoJdDDChHcMx3", ""))
        println(track)
        val lyrics = extension.searchTrackLyrics("", track).loadAll()
        println(lyrics)
        track.sources.forEach { streamable ->
            println(streamable)
            val media = extension.getStreamableMedia(streamable)
            println(media)
        }
    }

    @Test
    fun trackShelves() = testIn("Track Shelves") {
        val track = extension.loadTrack(Track("spotify:track:71NTIlx3GOoJdDDChHcMx3", ""))
        val shelves = extension.getShelves(track).loadFirst()
        shelves.forEach { it.print() }
    }

    @Test
    fun testGid() = testIn("GID Test") {
        val actual = "4eNGmBfayErwCHSKW3F0ek"
        val gid = Base62.decode(actual)
        println(gid)
        val id = Base62.encode(gid)
        println(id)
        Assert.assertEquals(actual, id)
    }

    @Test
    fun testPlaylist() = testIn("Playlist Test") {
        val playlist = extension.loadPlaylist(
            Playlist("spotify:playlist:6ZBHUwSlPqlvLsw0MKJtLU", "", false)
        )
        println(playlist)
        val tracks = extension.loadTracks(playlist).loadFirst()
        println("Tracks: ${tracks.size}")
        val shelves = extension.getShelves(playlist).loadFirst()
        println("Shelves: ${shelves.size}")
        shelves.forEach { it.print() }
    }

    @Test
    fun testAlbum() = testIn("Album Test") {
        val album = extension.loadAlbum(
            Album("spotify:album:6a6wiQNPcQMV8K17HDKtrC", "")
        )
        println(album)
        val tracks = extension.loadTracks(album).loadFirst()
        println("Tracks: ${tracks.size}")
        val shelves = extension.getShelves(album).loadFirst()
        println("Shelves: ${shelves.size}")
        shelves.forEach { it.print() }
    }

    @Test
    fun testHomeFeed() = testIn("Home Feed Test") {
        val tabs = extension.getHomeTabs()
        println(tabs)
        extension.getHomeFeed(null).loadAll().forEach { it.print() }
        tabs.forEach { tab ->
            extension.getHomeFeed(tab).loadAll().forEach { it.print() }
        }
    }

    @Test
    fun testRadio() = testIn("Radio Test") {
        val radio = extension.radio(
            Album("spotify:album:6a6wiQNPcQMV8K17HDKtrC", "")
        )
        println(radio)
        val tracks = extension.loadTracks(radio).loadAll()
        println("Tracks: ${tracks.size}")
    }

    @Test
    fun testArtist() = testIn("Artist Test") {
        val artist = extension.loadArtist(
            Artist("spotify:artist:3mVL1qynaYs31rgyDTytkS", "")
        )
        println(artist)
        val shelves = extension.getShelves(artist).loadFirst()
        println("Shelves: ${shelves.size}")
        shelves.forEach { it.print() }
    }

    @Test
    fun testUser() = testIn("User Test") {
        val user = extension.loadArtist(
            Artist("spotify:user:aeivypek9coyo5quqvlgn4x3g", "")
        )
        println(user)
        val shelves = extension.getShelves(user).loadFirst()
        println("Shelves: ${shelves.size}")
        shelves.forEach { it.print() }
    }

    @Test
    fun testSaveItem() = testIn("Save Item Test") {
        val track = Track("spotify:track:71NTIlx3GOoJdDDChHcMx3", "").toMediaItem()
        extension.saveToLibrary(track)
    }

    @Test
    fun testRemoveItem() = testIn("Remove Item Test") {
        val track = Track("spotify:track:71NTIlx3GOoJdDDChHcMx3", "").toMediaItem()
        extension.removeFromLibrary(track)
    }

    @Test
    fun testLibrary() = testIn("Library Test"){
        val tabs = extension.getLibraryTabs()
        println(tabs)
        tabs.forEach { tab ->
            val feed = extension.getLibraryFeed(tab).loadAll()
            println("Tab: $tab - ${feed.size}")
        }
    }
}