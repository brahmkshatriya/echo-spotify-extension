package dev.brahmkshatriya.echo.extension.spotify

import dev.brahmkshatriya.echo.extension.spotify.SpotifyApi.Companion.applyPagePagination
import dev.brahmkshatriya.echo.extension.spotify.SpotifyApi.Companion.applySectionPagination
import dev.brahmkshatriya.echo.extension.spotify.models.Browse
import dev.brahmkshatriya.echo.extension.spotify.models.BrowseAll
import dev.brahmkshatriya.echo.extension.spotify.models.ProfileAttributes
import dev.brahmkshatriya.echo.extension.spotify.models.SearchDesktop
import kotlinx.serialization.json.JsonObjectBuilder
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class Queries(
    private val api: SpotifyApi
) {

    suspend fun profileAttributes() = api.query<ProfileAttributes>(
        "profileAttributes",
        "53bcb064f6cd18c23f752bc324a791194d20df612d8e1239c735144ab0399ced"
    )

    suspend fun browseAll() = api.query<BrowseAll>("browseAll",
        "cd6fcd0ce9d1849477645646601a6d444597013355467e24066dad2c1dc9b740",
        buildJsonObject {
            applyPagePagination(0, 10)
            applySectionPagination(0, 99)
        }
    )

    suspend fun browsePage(uri: String, offset: Int) = api.query<Browse>(
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

    suspend fun searchDesktop(query: String) = api.query<SearchDesktop>(
        "searchDesktop",
        "2ae11a661a59c58695ad9b8bd6605dce6e3876f900555e21543c19f7a0a0ea6a",
        buildJsonObject {
            applySearchVariables(query, 0)
            put("includeArtistHasConcertsField", false)
            put("includeLocalConcertsField", false)
        }
    )

    suspend fun searchArtist(query: String, offset: Int) = api.query<SearchDesktop>(
        "searchArtists",
        "0e6f9020a66fe15b93b3bb5c7e6484d1d8cb3775963996eaede72bac4d97e909",
        buildJsonObject {
            applySearchVariables(query, offset)
        }

    )

    suspend fun searchAlbum(query: String, offset: Int) = api.query<SearchDesktop>(
        "searchAlbums",
        "a71d2c993fc98e1c880093738a55a38b57e69cc4ce5a8c113e6c5920f9513ee2",
        buildJsonObject {
            applySearchVariables(query, offset)
        }
    )

    suspend fun searchTrack(query: String, offset: Int) = api.query<SearchDesktop>(
        "searchTracks",
        "5307479c18ff24aa1bd70691fdb0e77734bede8cce3bd7d43b6ff7314f52a6b8",
        buildJsonObject {
            applySearchVariables(query, offset)
        }
    )

    suspend fun searchUser(query: String, offset: Int) = api.query<SearchDesktop>(
        "searchUsers",
        "d3f7547835dc86a4fdf3997e0f79314e7580eaf4aaf2f4cb1e71e189c5dfcb1f",
        buildJsonObject {
            applySearchVariables(query, offset)
        }
    )

    suspend fun searchPlaylist(query: String, offset: Int) = api.query<SearchDesktop>(
        "searchPlaylists",
        "fc3a690182167dbad20ac7a03f842b97be4e9737710600874cb903f30112ad58",
        buildJsonObject {
            applySearchVariables(query, offset)
        }
    )

    suspend fun searchFullEpisodes(query: String, offset: Int) = api.query<SearchDesktop>(
        "searchFullEpisodes",
        "37e3f18a893c9969817eb0aa46f4a69479a8b0f7964a36d801e69a8c0ab17fcb",
        buildJsonObject {
            applySearchVariables(query, offset)
        }
    )

    suspend fun searchGenres(query: String, offset: Int) = api.query<SearchDesktop>(
        "searchGenres",
        "9e1c0e056c46239dd1956ea915b988913c87c04ce3dadccdb537774490266f46",
        buildJsonObject {
            applySearchVariables(query, offset)
        }
    )
}