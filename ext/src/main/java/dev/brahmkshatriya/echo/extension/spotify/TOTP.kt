package dev.brahmkshatriya.echo.extension.spotify

import java.lang.reflect.UndeclaredThrowableException
import java.math.BigInteger
import java.security.GeneralSecurityException
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

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
        val byteArray = BigInteger("10$hex", 16).toByteArray()
        val result = ByteArray(byteArray.size - 1)
        for (index in result.indices) result[index] = byteArray[index + 1]
        return result
    }

    private val digitsPower =
        intArrayOf(1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000)

    fun generateTOTP(
        key: String,
        time: String,
        returnDigits: Int = 6,
        crypto: String = "HmacSHA1",
    ): String {
        val paddedTime = time.padStart(16, '0')
        val message = hexToBytes(paddedTime)
        val keyBytes = hexToBytes(key)

        val hash = hMacSha(crypto, keyBytes, message)
        val offset = hash.last().toInt() and 0x0f

        val binary = ((hash[offset].toInt() and 0x7f) shl 24) or
                ((hash[offset + 1].toInt() and 0xff) shl 16) or
                ((hash[offset + 2].toInt() and 0xff) shl 8) or
                (hash[offset + 3].toInt() and 0xff)

        val otp = binary % digitsPower[returnDigits]
        return otp.toString().padStart(returnDigits, '0')
    }

    fun convertToHex(input: String): String {
        val obfuscated = input.mapIndexed { index, char ->
            val key = index % 33 + 9
            char.code xor key
        }
        val decimalString = obfuscated.joinToString("") { it.toString() }
        return decimalString.map {
            it.code.toString(16).padStart(2, '0')
        }.joinToString("")
    }
}
