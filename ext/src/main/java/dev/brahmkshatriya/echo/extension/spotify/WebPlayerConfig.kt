package dev.brahmkshatriya.echo.extension.spotify

object WebPlayerConfig {
    const val USER_AGENT =
        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36"

    const val SEC_CH_UA =
        "\"Google Chrome\";v=\"131\", \"Chromium\";v=\"131\", \"Not_A Brand\";v=\"24\""
    const val SEC_CH_UA_MOBILE = "?0"
    const val SEC_CH_UA_PLATFORM = "\"Windows\""

    const val ORIGIN = "https://open.spotify.com"
    const val REFERER = "https://open.spotify.com/"
    const val APP_PLATFORM = "WebPlayer"
    const val ACCEPT_LANGUAGE = "en-US,en;q=0.9"

    @Volatile
    var appVersion: String = "1.2.88.248.g539a4816"
}
