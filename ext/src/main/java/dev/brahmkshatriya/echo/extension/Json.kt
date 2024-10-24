package dev.brahmkshatriya.echo.extension

import kotlinx.io.IOException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Json {
    val parser = Json {
        ignoreUnknownKeys = true
    }

    inline fun <reified T> encode(data: T) = parser.encodeToString(data)
    inline fun <reified T> decode(data: String) =
        runCatching { parser.decodeFromString<T>(data) }
            .getOrElse { throw IOException("${it.message}\n$data", it) }

    class DecodeException(data: String, cause: Throwable) : IOException(cause) {
        override val message = "${cause.message}\n$data"
    }
}