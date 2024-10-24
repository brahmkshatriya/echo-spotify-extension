package dev.brahmkshatriya.echo.extension

import dev.brahmkshatriya.echo.common.models.Shelf
import dev.brahmkshatriya.echo.common.models.User
import kotlinx.coroutines.CoroutineScope
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

@OptIn(DelicateCoroutinesApi::class)
@ExperimentalCoroutinesApi
class LibraryTest {
    private val extension = SpotifyExtension()
    private val searchQuery = "Skrillex"

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    private val token = System.getenv("SPOTIFY_TOKEN")!!
    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        extension.setSettings(MockedSettings())
        runBlocking {
            extension.onExtensionSelected()
            extension.onSetLoginUser(User(token, ""))
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
    fun testSearch() = testIn("Testing Empty Search") {
        val tab = extension.searchTabs(null).firstOrNull()
        val feed = extension.searchFeed(null, tab)
        val shelves = feed.loadAll()
        val shelf = shelves.first() as Shelf.Lists.Categories
        val load = shelf.list.first().items!!.loadAll()
        load.forEach { it.print() }
    }

}