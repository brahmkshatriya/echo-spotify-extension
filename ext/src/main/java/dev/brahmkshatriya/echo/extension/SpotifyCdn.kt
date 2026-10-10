package dev.brahmkshatriya.echo.extension

import kotlinx.coroutines.CancellationException
import java.net.URI

/** Signed CDN URLs are opaque and can expire or fail independently. */
internal object SpotifyCdn {
    fun isSafeUrl(url: String): Boolean = try {
        val uri = URI(url)
        uri.scheme == "https" && !uri.host.isNullOrBlank() && uri.userInfo == null
    } catch (_: Exception) {
        false
    }

    /** A range request starting after byte zero must receive 206, not a full 200 response. */
    fun acceptsRange(status: Int, offset: Long): Boolean =
        status == 206 || (offset == 0L && status == 200)

    suspend fun firstWorking(
        urls: List<String>,
        probe: suspend (String) -> Boolean,
    ): String? {
        for (url in urls.distinct()) {
            if (!isSafeUrl(url)) continue
            try {
                if (probe(url)) return url
            } catch (e: CancellationException) {
                throw e
            } catch (_: java.io.IOException) {
                // Try a different location rather than failing the stream.
            }
        }
        return null
    }
}
