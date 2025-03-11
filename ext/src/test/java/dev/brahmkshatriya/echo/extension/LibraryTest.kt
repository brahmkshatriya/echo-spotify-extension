package dev.brahmkshatriya.echo.extension

import dev.brahmkshatriya.echo.common.models.Album
import dev.brahmkshatriya.echo.common.models.Artist
import dev.brahmkshatriya.echo.common.models.EchoMediaItem
import dev.brahmkshatriya.echo.common.models.EchoMediaItem.Companion.toMediaItem
import dev.brahmkshatriya.echo.common.models.Playlist
import dev.brahmkshatriya.echo.common.models.Shelf
import dev.brahmkshatriya.echo.common.models.Tab
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
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
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

    private suspend fun Shelf.print() {
        when (this) {
            is Shelf.Category -> println(this)
            is Shelf.Item -> println(this)
            is Shelf.Lists<*> -> {
                println("${this.title} : ${this.list.size}")
                println("More: ${this.more?.loadAll()?.size}")
            }
        }
    }

    @Test
    fun testSearchEmpty() = testIn("Testing Empty Search") {
        val tab = extension.searchTabs("").firstOrNull()
        val feed = extension.searchFeed("", tab)
        val shelves = feed.loadAll()
        val shelf = shelves.first() as Shelf.Lists.Categories
        val load = shelf.list.first().items!!.loadFirst()
        load.forEach { it.print() }
    }


    @Test
    fun testSearch() = testIn("Testing Search") {
        val searchQuery = "Vump"
        val quickSearch = extension.quickSearch(searchQuery)
        quickSearch.forEach { println(it) }
        val feed = extension.searchFeed(searchQuery, Tab("USERS", ""))
        val page = feed.loadList(null)
        println("Page ${page.continuation}: ${page.data}")
        val page2 = feed.loadList(page.continuation)
        println("Page ${page.continuation}: ${page2.data}")
    }

    @Test
    fun getTrack() = testIn("Get Track") {
        val track = extension.loadTrack(Track("spotify:track:0TPF2GBCH08gLC40qvDjvD", ""))
        println(track)
        val lyrics = extension.searchTrackLyrics("", track).loadAll()
        println(lyrics)
        track.servers.forEach { streamable ->
            println(streamable)
            val media = extension.loadStreamableMedia(streamable, false)
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
        val actual = "0shcawVbNMznHPKeW9gfmF"
        val gid = Base62.decode(actual)
        println(gid)
        val id = Base62.encode(gid)
        println(id)
        Assert.assertEquals(actual, id)
    }

    @Test
    fun testPlaylist() = testIn("Playlist Test") {
        val playlist = extension.loadPlaylist(
            Playlist("spotify:playlist:6M7CldjyAmTW8UsLIA1mSs", "", false)
        )
        println(playlist)
        val tracks = extension.loadTracks(playlist).loadAll()
        println("Tracks: ${tracks.size}")
//        val shelves = extension.getShelves(playlist).loadFirst()
//        println("Shelves: ${shelves.size}")
//        shelves.forEach { it.print() }
    }

    val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    @Test
    fun testAlbum() = testIn("Album Test") {
        val album = extension.loadAlbum(
            Album("spotify:album:7DVnTw8oQn2p2dD99Zps4i", "")
        )
        println(json.encodeToString(album))
//        val tracks = extension.loadTracks(album).loadFirst()
//        println("Tracks: ${tracks.size}")
//        val shelves = extension.getShelves(album).loadFirst()
//        println("Shelves: ${shelves.size}")
//        shelves.forEach { it.print() }
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
            Track("spotify:track:71NTIlx3GOoJdDDChHcMx3", ""), null
        )
        println(radio)
        val tracks = extension.loadTracks(radio).loadAll()
        println("Tracks: ${tracks.size}")
        tracks.forEach { println(it) }
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
        extension.saveToLibrary(track, true)
    }

    @Test
    fun testRemoveItem() = testIn("Remove Item Test") {
        val track = Track("spotify:track:71NTIlx3GOoJdDDChHcMx3", "").toMediaItem()
        extension.saveToLibrary(track, false)
    }

    @Test
    fun testLibrary() = testIn("Library Test") {
        val tabs = extension.getLibraryTabs()
        val feed = extension.getLibraryFeed(tabs.first()).loadAll()
        val liked =
            ((feed.first() as Shelf.Item).media as EchoMediaItem.Lists.PlaylistItem).playlist
        println(liked)
        val loaded = extension.loadPlaylist(liked)
        println(loaded)
        val tracks = extension.loadTracks(loaded).loadAll()
        println("Tracks: ${tracks.size}")
    }

    @Test
    fun editablePlaylist() = testIn("Editable Playlist Test") {
        val playlists = extension.listEditablePlaylists()
        playlists.forEach {
            println(it)
        }
    }

    @Test
    fun createPlaylist() = testIn("Create Playlist Test") {
        val playlist = extension.createPlaylist("Test Playlist", "Test Descriptionc xdxb xf")
        println(playlist)
        extension.editPlaylistMetadata(playlist, "bruhduish", null)
        println(extension.loadPlaylist(playlist))
        println("Playlists: ${extension.listEditablePlaylists().size}")
        extension.deletePlaylist(playlist)
        println("Playlists: ${extension.listEditablePlaylists().size}")
    }

    @Test
    fun editPlaylist() = testIn("Edit Playlist Test") {
        val new = listOf(
            Track("spotify:track:76I3PmbGZazzNlEwlp1y85", "")
        )
        val playlist = Playlist("spotify:playlist:6yK8Rfoj4WgTFyIowWg0n6", "", true)
        val tracks = extension.loadTracks(playlist).loadAll().toMutableList()
        extension.moveTrackInPlaylist(playlist, tracks, 0, 1)
        tracks.add(1, tracks.removeAt(0))
        extension.removeTracksFromPlaylist(playlist, tracks, listOf(1))
        tracks.removeAt(1)
        extension.addTracksToPlaylist(playlist, tracks, tracks.size, new)
        tracks.addAll(tracks.size, new)
        tracks.forEach { println(it) }
    }

    @Test
    fun likeTrack() = testIn("Like Track Test") {
        val track = Track("spotify:track:76I3PmbGZazzNlEwlp1y85", "")
        println(extension.loadTrack(track).isLiked)
        extension.likeTrack(track, true)
        println(extension.loadTrack(track).isLiked)
        extension.likeTrack(track, false)
        println(extension.loadTrack(track).isLiked)
    }

    @Test
    fun testSongChange() = testIn("Testing Song Change") {
        val track = Track("spotify:track:5h2SrRiHKiONIX3TkYOQII", "")
        val media = extension.loadTrack(track)
        println(media)
    }
}