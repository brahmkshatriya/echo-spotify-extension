package dev.brahmkshatriya.echo.extension.spotify.models

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
data class Sections(
    val items: List<SectionItem>? = null,
    val pagingInfo: PagingInfo? = null,
    val totalCount: Long? = null
) {

    @Serializable
    data class Container(
        val sections: Sections,
        val uri: String? = null
    )

    @Serializable
    data class SectionItem(
        @SerialName("__typename")
        val typename: String? = null,

        val data: Data? = null,
        val sectionItems: Items? = null,
        val targetLocation: String? = null,
        val uri: String? = null
    )

    @Serializable
    data class Data(
        @SerialName("__typename")
        val typename: Typename? = null,

        val subtitle: Title? = null,
        val title: Title? = null
    )

    @Serializable(with = TypenameSerializer::class)
    enum class Typename {
        HomeShortsSectionData,
        HomeGenericSectionData,
        HomeFeedBaselineSectionData,
        HomeRecentlyPlayedSectionData,
        HomeSpotlightSectionData,
        HomeOnboardingSectionDataV2,
        HomeWatchFeedSectionData,
        BrowseGenericSectionData,
        BrowseGridSectionData,
        BrowseUnsupportedSectionData,
        BrowseRelatedSectionData,
        HomeNativeAdsSectionData,
        HomeYourDJSectionData,
        HomePromotionSectionData,
        Unknown;
    }

    object TypenameSerializer : KSerializer<Typename> {
        override val descriptor: SerialDescriptor =
            PrimitiveSerialDescriptor("Typename", PrimitiveKind.STRING)

        override fun deserialize(decoder: Decoder): Typename {
            val name = decoder.decodeString()
            return Typename.entries.firstOrNull { it.name == name } ?: Typename.Unknown
        }

        override fun serialize(encoder: Encoder, value: Typename) {
            encoder.encodeString(value.name)
        }
    }

    @Serializable
    data class Items(
        val items: List<ItemsItem>? = null,
        val pagingInfo: PagingInfo? = null,
        val totalCount: Long? = null
    )

    @Serializable
    data class ItemsItem(
        val content: Item.Wrapper,
        val uri: String
    )
}
