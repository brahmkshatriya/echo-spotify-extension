package dev.brahmkshatriya.echo.extension.spotify

import java.lang.reflect.UndeclaredThrowableException
import java.math.BigInteger
import java.security.GeneralSecurityException
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

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

    private fun hexStr2Bytes(hex: String): ByteArray {
        val bArray = BigInteger("10$hex", 16).toByteArray()
        val ret = ByteArray(bArray.size - 1)
        for (i in ret.indices) ret[i] = bArray[i + 1]
        return ret
    }

    private val DIGITS_POWER =
        intArrayOf(1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000)

    fun generateTOTP(
        key: String, time: String, returnDigits: String, crypto: String
    ): String {
        var t = time
        val codeDigits = Integer.decode(returnDigits)
        var result: String?
        while (t.length < 16) t = "0$t"
        val msg = hexStr2Bytes(t)
        val k = hexStr2Bytes(key)

        val hash = hMacSha(crypto, k, msg)
        val offset = hash[hash.size - 1].toInt() and 0xf

        val binary = ((hash[offset].toInt() and 0x7f) shl 24) or
                ((hash[offset + 1].toInt() and 0xff) shl 16) or
                ((hash[offset + 2].toInt() and 0xff) shl 8) or
                (hash[offset + 3].toInt() and 0xff)

        val otp = binary % DIGITS_POWER[codeDigits]

        result = otp.toString()
        while (result!!.length < codeDigits) {
            result = "0$result"
        }
        return result
    }
}