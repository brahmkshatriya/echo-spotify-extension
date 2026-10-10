package dev.brahmkshatriya.echo.extension

import java.io.ByteArrayOutputStream

/**
 * Native-free PlayPlay request serializer. Field numbers and values follow the
 * observed Spotify Android audio-file request; the playback mode is interactive.
 * The embedded field-2 bytes are a build-specific license-request marker,
 * not an account token. The caller supplies its own OAuth Bearer token.
 */
internal object PlayPlayLicenseRequest {
    private val requestToken = byteArrayOf(
        0x02, 0x19, 0x83.toByte(), 0x16, 0xbe.toByte(), 0xae.toByte(), 0x6d, 0x4a,
        0xf4.toByte(), 0x35, 0x24, 0xd0.toByte(), 0x2c, 0x37, 0x2b, 0x7e,
    )

    fun encode(timestampSeconds: Long = System.currentTimeMillis() / 1000L): ByteArray {
        require(timestampSeconds >= 0) { "Invalid PlayPlay timestamp" }
        return ByteArrayOutputStream(32).apply {
            fun varint(number: Long) {
                var value = number
                while (value and -128L != 0L) {
                    write(((value and 127L) or 128L).toInt())
                    value = value ushr 7
                }
                write(value.toInt())
            }
            write(0x08); varint(5) // version
            write(0x12); varint(requestToken.size.toLong()); write(requestToken)
            write(0x20); varint(1) // interactive
            write(0x28); varint(1) // audio track
            write(0x30); varint(timestampSeconds)
        }.toByteArray()
    }
}
