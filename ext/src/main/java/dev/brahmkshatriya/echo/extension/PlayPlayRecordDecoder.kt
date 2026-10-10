package dev.brahmkshatriya.echo.extension

/**
 * Pure JVM/Kotlin PlayPlay decoder. [decodeObfuscatedKey] performs the full
 * 16-byte response-field -> AES-128-key transformation through [PlayPlayPipeline].
 * [decodeNativeRecord] remains a separate 28-byte native-record decoder;
 * its input is not interchangeable with a 16-byte PlayPlay response field.
 * All arithmetic wraps at the native 32/64-bit word widths.
 */
internal object PlayPlayRecordDecoder {
    private const val U32_MASK = 0xffffffffL

    /** Entire PlayPlay response-field deobfuscation, performed locally in Kotlin. */
    fun decodeObfuscatedKey(obfuscated: ByteArray): ByteArray {
        require(obfuscated.size == 16) { "PlayPlay obfuscated key must be 16 bytes" }
        return PlayPlayPipeline().decode(obfuscated)
    }

    /** Extract protobuf field 1 (last occurrence wins), validating its 16-byte size. */
    fun parseObfuscatedKey(body: ByteArray): ByteArray {
        require(body.size <= 1024) { "PlayPlay response exceeds size limit" }
        var position = 0
        fun varint(): Long {
            var result = 0L
            var shift = 0
            while (shift <= 63) {
                require(position < body.size) { "Truncated protobuf varint" }
                val byte = body[position++].toInt() and 255
                require(shift != 63 || byte <= 1) { "Protobuf varint overflow" }
                result = result or ((byte and 127).toLong() shl shift)
                if (byte and 128 == 0) return result
                shift += 7
            }
            throw IllegalArgumentException("Protobuf varint overflow")
        }
        fun bytes(length: Long): ByteArray {
            require(length >= 0 && length <= body.size - position) { "Truncated protobuf field" }
            val size = length.toInt()
            return body.copyOfRange(position, position + size).also { position += size }
        }

        var field1: ByteArray? = null
        while (position < body.size) {
            val tag = varint()
            val fieldNumber = tag ushr 3
            require(fieldNumber in 1 until (1L shl 29)) { "Invalid protobuf field number" }
            when ((tag and 7L).toInt()) {
                0 -> varint()
                1 -> bytes(8)
                2 -> {
                    val payload = bytes(varint())
                    if (fieldNumber == 1L) field1 = payload
                }
                5 -> bytes(4)
                else -> throw IllegalArgumentException("Unsupported protobuf wire type")
            }
        }
        return requireNotNull(field1) { "Missing PlayPlay field 1" }.also {
            require(it.size == 16) { "PlayPlay field 1 must be exactly 16 bytes" }
        }
    }

    /** Decode a 28-byte *native record*, not a 16-byte server response field. */
    fun decodeNativeRecord(record: ByteArray): ByteArray {
        require(record.size == 28) { "Native record must be exactly 28 bytes" }
        val words = LongArray(7) { index ->
            val offset = index * 4
            (record[offset].toLong() and 255) or
                ((record[offset + 1].toLong() and 255) shl 8) or
                ((record[offset + 2].toLong() and 255) shl 16) or
                ((record[offset + 3].toLong() and 255) shl 24)
        }
        val salt0 = words[0]
        val salt1 = words[1]
        val key = (3 * (salt0 + salt1) + 4) and U32_MASK
        val bias = (3 * salt1 + 5) and U32_MASK
        for (round in 17 downTo 2) {
            val current = (round - 2) and 3
            val previous = (round - 3) and 3
            val mix = ((8 * (words[previous + 2] + key) + bias + round) and U32_MASK) xor
                (((words[previous + 2] ushr 3) + key + 7) and U32_MASK)
            words[current + 2] = (words[current + 2] - mix) and U32_MASK
        }

        val a = (salt1 shl 32) xor (salt0 * 0x2bfac477L) xor 0x16a51c45L
        val b = (salt0 shl 32) xor (salt1 * 0xe355d543L) xor 0xaf1aca2bL
        var left = (words[2] or (words[3] shl 32)) xor a xor 0x78b6f9ddca35c9edL
        var right = (words[4] or (words[5] shl 32)) xor b xor 0x96f06cc53b91b3a1UL.toLong()
        if ((words[6] and 255L) != 0L) {
            val rounds = longArrayOf(
                0x73e2b993de29abadL, 0x0cfdfadcc532b265L,
                0xf3a3a8f312885bacUL.toLong(), 0x0d01fd18bccecd8dL,
                0x7327a9f17e888924L, 0x0f0602dcaaedbe7dL,
                0xd3e3a9917ef8eb35UL.toLong(), 0xed09fd18d4cec184UL.toLong()
            )
            for (index in rounds.indices step 2) {
                val next = round(left, rounds[index], rounds[index + 1]) xor right
                right = left
                left = next
            }
        }
        val output = ByteArray(16)
        for (index in 0 until 8) {
            output[index] = (left ushr (8 * index)).toByte()
            output[index + 8] = (right ushr (8 * index)).toByte()
        }
        return output
    }

    private fun round(input: Long, roundKey: Long, bias: Long): Long {
        var b = input xor 0x0f859104ff7029e3L
        var a = (input xor 0x13245352688a24a6L) + b
        var c = a + roundKey
        var d = java.lang.Long.rotateLeft(b, 3) xor a
        b = d + bias
        a = c xor java.lang.Long.rotateLeft(roundKey, 16)
        var sum = a + b
        d = java.lang.Long.rotateLeft(d, 30) xor b
        b = java.lang.Long.rotateLeft(a, 1) xor sum
        a = d + java.lang.Long.rotateLeft(c, 1)
        c = a + b
        d = java.lang.Long.rotateLeft(d, 3) xor a
        b = java.lang.Long.rotateLeft(b, 16) xor c
        a = d + java.lang.Long.rotateLeft(sum, 33)
        b = a xor b
        sum = java.lang.Long.rotateLeft(d, 30) xor a
        return b xor java.lang.Long.rotateLeft(c, 1) xor sum
    }
}
