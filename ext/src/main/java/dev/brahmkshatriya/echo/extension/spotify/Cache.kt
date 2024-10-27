package dev.brahmkshatriya.echo.extension.spotify

interface Cache {
    var accessToken: String?
    var accessTokenExpiration: Long?
}