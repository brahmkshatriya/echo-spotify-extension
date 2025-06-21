package dev.brahmkshatriya.echo.extension.spotify.mercury

import com.google.protobuf.ByteString
import dev.brahmkshatriya.echo.extension.spotify.mercury.CipherPair.Companion.toByteArray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer

class AudioKeyManager(
    private val connection: MercuryConnection
) {
    private var seqHolder = 0
    val packetFlow = MutableSharedFlow<Packet>()

    suspend fun getAudioKey(gid: ByteString, fileId: ByteString): ByteArray {
        return runCatching { getAudioKeyWithoutRetry(gid, fileId) }.getOrElse {
            getAudioKeyWithoutRetry(gid, fileId)
        }
    }

    private var lastSuccessful = 0L
    private val mutex = Mutex()

    @OptIn(ExperimentalStdlibApi::class)
    private suspend fun getAudioKeyWithoutRetry(
        gid: ByteString, fileId: ByteString
    ) = mutex.withLock {
        val currentTime = System.currentTimeMillis() - lastSuccessful
        if (currentTime < 1000) delay(1000 - currentTime)
        withContext(Dispatchers.IO) {
            println(
                "Requesting audio key for gid: ${
                    gid.toByteArray().toHexString()
                }, fileId: ${fileId.toByteArray().toHexString()}"
            )
            val seq = seqHolder++
            val out = ByteArrayOutputStream()
            fileId.writeTo(out)
            gid.writeTo(out)
            out.write(toByteArray(seq))
            out.write(ZERO_SHORT)
            connection.send(Packet.Type.RequestKey, out.toByteArray())

            val packet = withTimeout(2000) {
                packetFlow.first { packet ->
                    val payload = ByteBuffer.wrap(packet.payload)
                    payload.getInt() == seq
                }
            }

            lastSuccessful = System.currentTimeMillis()
            val payload = ByteBuffer.wrap(packet.payload)
            payload.getInt()
            when (packet.type) {
                Packet.Type.AesKey -> {
                    val key = ByteArray(16)
                    payload[key]
                    key
                }

                Packet.Type.AesKeyError -> {
                    val code = payload.getShort().toInt()
                    throw AesKeyError(code)
                }

                else -> throw Exception("Couldn't handle packet, cmd: ${packet.type}, length: ${packet.payload.size}")
            }
        }
    }

    class AesKeyError(val code: Int) : Exception("Error fetching audio key, code: $code")

    companion object {
        private val ZERO_SHORT = byteArrayOf(0, 0)
    }
}