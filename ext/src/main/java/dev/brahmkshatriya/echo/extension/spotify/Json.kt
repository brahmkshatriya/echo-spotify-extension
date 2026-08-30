package dev.brahmkshatriya.echo.extension.spotify

import dev.brahmkshatriya.echo.extension.spotify.models.Item
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

class Json {
    val parser = Json {
        ignoreUnknownKeys = true
        isLenient = true
        serializersModule = SerializersModule{
            polymorphic(Item::class) {
                defaultDeserializer { Item.Unknown.serializer() }
            }
        }
    }

    inline fun <reified T> encode(data: T) = parser.encodeToString(data)
    inline fun <reified T> decode(data: String) =
        runCatching { parser.decodeFromString<T>(data) }
            .getOrElse { throw DecodeException(data, it) }

    class DecodeException(data: String, cause: Throwable) : Exception(cause) {
        override val message = "${cause.message}\n$data"
    }
}