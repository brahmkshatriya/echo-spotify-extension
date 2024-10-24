package dev.brahmkshatriya.echo.extension

import dev.brahmkshatriya.echo.common.helpers.PagedData
import dev.brahmkshatriya.echo.common.models.ImageHolder.Companion.toImageHolder
import dev.brahmkshatriya.echo.common.models.Shelf
import dev.brahmkshatriya.echo.common.models.User
import dev.brahmkshatriya.echo.extension.SpotifyApi.Companion.applyPagePagination
import dev.brahmkshatriya.echo.extension.SpotifyApi.Companion.applySectionPagination
import dev.brahmkshatriya.echo.extension.models.Browse
import dev.brahmkshatriya.echo.extension.models.ContentTypename
import dev.brahmkshatriya.echo.extension.models.PurpleTypename
import dev.brahmkshatriya.echo.extension.models.SectionItemsItem
import dev.brahmkshatriya.echo.extension.models.Sections
import dev.brahmkshatriya.echo.extension.models.SpotifyUser
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

fun Sections.toShelves(api: SpotifyApi): List<Shelf> {
    return items?.mapNotNull { item ->
        val title = item.data?.title?.transformedLabel!!
        when (item.data.typename) {
            PurpleTypename.BrowseGenericSectionData -> TODO()
            PurpleTypename.BrowseGridSectionData -> {
                Shelf.Lists.Categories(
                    title = title,
                    list = item.sectionItems?.items?.mapNotNull { it.toGridItem(api) }!!,
                    type = Shelf.Lists.Type.Grid
                )
            }

            PurpleTypename.BrowseRelatedSectionData -> TODO()
            null -> null
        }
    }!!
}

fun SectionItemsItem.toGridItem(api: SpotifyApi): Shelf.Category? {
    return when (content.typename) {
        ContentTypename.AlbumResponseWrapper -> TODO()
        ContentTypename.PlaylistResponseWrapper -> TODO()
        ContentTypename.BrowseSectionContainerWrapper -> Shelf.Category(
            title = content.data.data?.cardRepresentation?.title?.transformedLabel!!,
            items = PagedData.Single {
                api.query<Browse>(
                    "browsePage",
                    "d8346883162a16a62a5b69e73e70c66a68c27b14265091cd9e1517f48334bbb3",
                    buildJsonObject {
                        put("uri", uri)
                        applyPagePagination(0, 10)
                        applySectionPagination(0, 10)
                    }
                ).data.browse.sections.toShelves(api)
            }
        )

        ContentTypename.BrowseXlinkResponseWrapper -> null
    }
}

fun SpotifyUser.toUser() = User(
    id = data.me.profile.uri!!,
    name = data.me.profile.name!!,
    cover = data.me.profile.avatar?.sources?.firstOrNull()?.url?.toImageHolder()
)