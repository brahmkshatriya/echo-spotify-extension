package dev.brahmkshatriya.echo.extension.spotify

import java.lang.reflect.UndeclaredThrowableException
import java.math.BigInteger
import java.security.GeneralSecurityException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import kotlin.math.abs

// Thanks to https://github.com/Adolar0042/ the goat
object TOTP {
    private fun hMacSha(crypto: String, keyBytes: ByteArray, text: ByteArray): ByteArray {
        try {
            val hMac = Mac.getInstance(crypto)
            val macKey = SecretKeySpec(keyBytes, "RAW")
            hMac.init(macKey)
            return hMac.doFinal(text)
        } catch (gse: GeneralSecurityException) {
            throw UndeclaredThrowableException(gse)
        }
    }

    private fun hexToBytes(hex: String): ByteArray {
        val bArray = BigInteger("10$hex", 16).toByteArray()
        val ret = ByteArray(bArray.size - 1)
        for (i in ret.indices) ret[i] = bArray[i + 1]
        return ret
    }

    private val DIGITS_POWER =
        intArrayOf(1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000)

    fun generateTOTP(
        key: String, time: String, returnDigits: Int = 6, crypto: String = "HmacSHA1"
    ): String {
        val t = time.padStart(16, '0')
        val msg = hexToBytes(t)
        val k = hexToBytes(key)

        val hash = hMacSha(crypto, k, msg)
        val offset = hash.last().toInt() and 0xf

        val binary = ((hash[offset].toInt() and 0x7f) shl 24) or
                ((hash[offset + 1].toInt() and 0xff) shl 16) or
                ((hash[offset + 2].toInt() and 0xff) shl 8) or
                (hash[offset + 3].toInt() and 0xff)

        val otp = binary % DIGITS_POWER[returnDigits]
        return otp.toString().padStart(returnDigits, '0')
    }

    fun getCustomFormattedDate(): String {
        val date = Date(1749049455000)
        val sdf = SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss", Locale.ENGLISH)
        val tz = TimeZone.getDefault()
        val offsetInMillis = tz.rawOffset
        val sign = if (offsetInMillis >= 0) "+" else "-"
        val absOffset = abs(offsetInMillis)
        val hours = absOffset / (60 * 60 * 1000)
        val minutes = (absOffset / (60 * 1000)) % 60
        val gmtOffset = String.format(Locale.ENGLISH, "GMT%s%02d%02d", sign, hours, minutes)
        val displayName = tz.getDisplayName(Locale.ENGLISH)
        return "${sdf.format(date)} $gmtOffset (${displayName})"
    }
}