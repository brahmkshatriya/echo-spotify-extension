package dev.brahmkshatriya.echo.extension.models

import kotlinx.serialization.Serializable

class Client {

    @Serializable
    data class Extensions (
        val persistedQuery: PersistedQuery? = null
    )

    @Serializable
    data class PersistedQuery (
        val version: Long? = null,
        val sha256Hash: String? = null
    )
}