package dev.brahmkshatriya.echo.extension.spotify

import dev.brahmkshatriya.echo.extension.spotify.SpotifyApi.Companion.applyPagePagination
import dev.brahmkshatriya.echo.extension.spotify.SpotifyApi.Companion.applySectionPagination
import dev.brahmkshatriya.echo.extension.spotify.models.AccountAttributes
import dev.brahmkshatriya.echo.extension.spotify.models.AreEntitiesInLibrary
import dev.brahmkshatriya.echo.extension.spotify.models.Browse
import dev.brahmkshatriya.echo.extension.spotify.models.BrowseAll
import dev.brahmkshatriya.echo.extension.spotify.models.Canvas
import dev.brahmkshatriya.echo.extension.spotify.models.ColorLyrics
import dev.brahmkshatriya.echo.extension.spotify.models.FetchPlaylist
import dev.brahmkshatriya.echo.extension.spotify.models.GetAlbum
import dev.brahmkshatriya.echo.extension.spotify.models.HomeFeed
import dev.brahmkshatriya.echo.extension.spotify.models.Metadata4Track
import dev.brahmkshatriya.echo.extension.spotify.models.ProfileAttributes
import dev.brahmkshatriya.echo.extension.spotify.models.SearchDesktop
import dev.brahmkshatriya.echo.extension.spotify.models.StorageResolve
import kotlinx.serialization.json.JsonObjectBuilder
import kotlinx.serialization.json.add
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlinx.serialization.json.putJsonArray
import okhttp3.Request
import java.util.TimeZone

class Queries(
    val api: SpotifyApi,
) {

    suspend fun profileAttributes() = api.graphQuery<ProfileAttributes>(
        "profileAttributes",
        "53bcb064f6cd18c23f752bc324a791194d20df612d8e1239c735144ab0399ced"
    )

    suspend fun accountAttributes() = api.graphQuery<AccountAttributes>(
        "accountAttributes",
        "4fbd57be3c6ec2157adcc5b8573ec571f61412de23bbb798d8f6a156b7d34cdf"
    )

    suspend fun browseAll() = api.graphQuery<BrowseAll>(
        "browseAll",
        "cd6fcd0ce9d1849477645646601a6d444597013355467e24066dad2c1dc9b740",
        buildJsonObject {
            applyPagePagination(0, 10)
            applySectionPagination(0, 99)
        }
    )

    suspend fun browsePage(uri: String, offset: Int) = api.graphQuery<Browse>(
        "browsePage",
        "d8346883162a16a62a5b69e73e70c66a68c27b14265091cd9e1517f48334bbb3",
        buildJsonObject {
            put("uri", uri)
            applyPagePagination(offset, 10)
            applySectionPagination(0, 10)
        }
    )

    private fun JsonObjectBuilder.applySearchVariables(query: String, offset: Int) {
        put("searchTerm", query)
        put("offset", offset)
        put("limit", 10)
        put("numberOfTopResults", 5)
        put("includeAudiobooks", true)
        put("includePreReleases", true)
    }

    suspend fun searchDesktop(query: String) = api.graphQuery<SearchDesktop>(
        "searchDesktop",
        "2ae11a661a59c58695ad9b8bd6605dce6e3876f900555e21543c19f7a0a0ea6a",
        buildJsonObject {
            applySearchVariables(query, 0)
            put("includeArtistHasConcertsField", false)
            put("includeLocalConcertsField", false)
        }
    )

    suspend fun searchArtist(query: String, offset: Int) = api.graphQuery<SearchDesktop>(
        "searchArtists",
        "0e6f9020a66fe15b93b3bb5c7e6484d1d8cb3775963996eaede72bac4d97e909",
        buildJsonObject {
            applySearchVariables(query, offset)
        },
    )

    suspend fun searchAlbum(query: String, offset: Int) = api.graphQuery<SearchDesktop>(
        "searchAlbums",
        "a71d2c993fc98e1c880093738a55a38b57e69cc4ce5a8c113e6c5920f9513ee2",
        buildJsonObject {
            applySearchVariables(query, offset)
        }
    )

    suspend fun searchTrack(query: String, offset: Int) = api.graphQuery<SearchDesktop>(
        "searchTracks",
        "5307479c18ff24aa1bd70691fdb0e77734bede8cce3bd7d43b6ff7314f52a6b8",
        buildJsonObject {
            applySearchVariables(query, offset)
        }
    )

    suspend fun searchUser(query: String, offset: Int) = api.graphQuery<SearchDesktop>(
        "searchUsers",
        "d3f7547835dc86a4fdf3997e0f79314e7580eaf4aaf2f4cb1e71e189c5dfcb1f",
        buildJsonObject {
            applySearchVariables(query, offset)
        }
    )

    suspend fun searchPlaylist(query: String, offset: Int) = api.graphQuery<SearchDesktop>(
        "searchPlaylists",
        "fc3a690182167dbad20ac7a03f842b97be4e9737710600874cb903f30112ad58",
        buildJsonObject {
            applySearchVariables(query, offset)
        }
    )

    suspend fun searchFullEpisodes(query: String, offset: Int) = api.graphQuery<SearchDesktop>(
        "searchFullEpisodes",
        "37e3f18a893c9969817eb0aa46f4a69479a8b0f7964a36d801e69a8c0ab17fcb",
        buildJsonObject {
            applySearchVariables(query, offset)
        }
    )

    suspend fun searchGenres(query: String, offset: Int) = api.graphQuery<SearchDesktop>(
        "searchGenres",
        "9e1c0e056c46239dd1956ea915b988913c87c04ce3dadccdb537774490266f46",
        buildJsonObject {
            applySearchVariables(query, offset)
        }
    )

    suspend fun fetchPlaylist(uri: String) = api.graphQuery<FetchPlaylist>(
        "fetchPlaylistWithGatedEntityRelations",
        "19ff1327c29e99c208c86d7a9d8f1929cfdf3d3202a0ff4253c821f1901aa94d",
        buildJsonObject {
            put("uri", uri)
            put("offset", 9999)
        }
    )

    suspend fun fetchPlaylistContent(uri: String, offset: Int) = api.graphQuery<FetchPlaylist>(
        "fetchPlaylistContentsWithGatedEntityRelations",
        "19ff1327c29e99c208c86d7a9d8f1929cfdf3d3202a0ff4253c821f1901aa94d",
        buildJsonObject {
            put("uri", uri)
            put("offset", offset)
            put("limit", 25)
        }
    )

    suspend fun getAlbum(uri: String) = api.graphQuery<GetAlbum>(
        "getAlbum",
        "8f4cd5650f9d80349dbe68684057476d8bf27a5c51687b2b1686099ab5631589",
        buildJsonObject {
            put("uri", uri)
            put("locale", "")
            put("offset", 0)
            put("limit", 50)
        }
    )

    suspend fun queryAlbumTracks(uri: String, offset: Int) = api.graphQuery<GetAlbum>(
        "queryAlbumTracks",
        "8f4cd5650f9d80349dbe68684057476d8bf27a5c51687b2b1686099ab5631589",
        buildJsonObject {
            put("uri", uri)
            put("offset", offset)
            put("limit", 50)
        }
    )

    suspend fun areEntitiesInLibrary(vararg uris: String) = api.graphQuery<AreEntitiesInLibrary>(
        "areEntitiesInLibrary",
        "6ec3f767111e1f88a68058560f961161679d2cd4805ff3b8cb4b25c83ccbd6e0",
        buildJsonObject {
            putJsonArray("uris") {
                uris.forEach { add(it) }
            }
        }
    )

    suspend fun canvas(uri: String) = api.graphQuery<Canvas>(
        "canvas",
        "1b1e1915481c99f4349af88268c6b49a2b601cf0db7bca8749b5dd75088486fc",
        buildJsonObject {
            put("uri", uri)
        }
    )

//    suspend fun getTrack(uri:String) = api.query<GetTrack>(
//        "getTrack",
//        "ae85b52abb74d20a4c331d4143d4772c95f34757bfa8c625474b912b9055b5c0",
//        buildJsonObject {
//            put("uri", uri)
//        }
//    )

    suspend fun metadata4Track(id: String) = api.call(
        Request.Builder()
            .url("https://spclient.wg.spotify.com/metadata/4/track/$id").build()
    ).let { api.json.decode<Metadata4Track>(it) }

    suspend fun storageResolve(id: String) = api.call(
        Request.Builder()
            .url("https://spclient.wg.spotify.com/storage-resolve/v2/files/audio/interactive/10/$id?version=10000000&product=9&platform=39&alt=json")
            .build()
    ).let { api.json.decode<StorageResolve>(it) }

    suspend fun colorLyrics(id: String, img: String) = api.call(
        Request.Builder()
            .url(
                "https://spclient.wg.spotify.com/color-lyrics/v2/track/$id/image/${
                    api.urlEncode(
                        img
                    )
                }?format=json&vocalRemoval=false&market=from_token"
            )
            .build()
    ).let { api.json.decode<ColorLyrics>(it) }


    suspend fun homeFeedChips(token: String? = null) = api.graphQuery<HomeFeed>(
        "homeFeedChips",
        "26794052084a1d604a9644ab869b9e467afbd4e29a38edaaed9073e70834df81",
        buildJsonObject {
            put("timeZone", TimeZone.getDefault().id!!)
            put("sp_t", token ?: "")
        },
        true
    )

    suspend fun homeSubfeed(facet: String?, token: String? = null) = api.graphQuery<HomeFeed>(
        "homeSubfeed",
        "26794052084a1d604a9644ab869b9e467afbd4e29a38edaaed9073e70834df81",
        buildJsonObject {
            put("timeZone", TimeZone.getDefault().id!!)
            put("sp_t", token ?: "")
            put("facet", facet ?: "")
            put("sectionItemsLimit", 10)
        },
        true
    )
}