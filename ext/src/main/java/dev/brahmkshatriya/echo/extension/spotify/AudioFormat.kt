package dev.brahmkshatriya.echo.extension.spotify

import spotify.extendedmetadata.metadata.ExtendedMetadataProto.AudioFile

object AudioFormat {
    const val OGG_VORBIS_96 = 0
    const val OGG_VORBIS_160 = 1
    const val OGG_VORBIS_320 = 2
    const val MP3_256 = 3
    const val MP3_320 = 4
    const val MP3_160 = 5
    const val MP3_96 = 6
    const val MP3_160_ENC = 7
    const val AAC_24 = 8
    const val AAC_48 = 9
    const val MP4_128 = 10
    const val MP4_256 = 11
    const val MP4_128_DUAL = 12
    const val MP4_256_DUAL = 13
    const val MP4_128_CBCS = 14
    const val MP4_256_CBCS = 15
    const val FLAC_FLAC = 16
    const val MP4_FLAC = 17
    const val XHE_AAC_24 = 18
    const val XHE_AAC_16 = 19
    const val XHE_AAC_12 = 20
    const val HE_AAC_64 = 21
    const val FLAC_FLAC_24BIT = 22
    const val MP4_FLAC_24BIT = 23

    fun name(format: Int): String = AudioFile.Format.forNumber(format)?.name ?: "UNKNOWN"

    fun quality(format: Int): Int = when (format) {
        OGG_VORBIS_96 -> 0
        OGG_VORBIS_160 -> 1
        OGG_VORBIS_320 -> 2
        MP3_96 -> 0
        MP3_160, MP3_160_ENC -> 1
        MP3_256 -> 2
        MP3_320 -> 2
        AAC_24 -> 0
        AAC_48 -> 1
        HE_AAC_64 -> 1
        XHE_AAC_12 -> 0
        XHE_AAC_16 -> 0
        XHE_AAC_24 -> 1
        MP4_128, MP4_128_DUAL, MP4_128_CBCS -> 1
        MP4_256, MP4_256_DUAL, MP4_256_CBCS -> 2
        FLAC_FLAC, MP4_FLAC -> 3
        FLAC_FLAC_24BIT, MP4_FLAC_24BIT -> 4
        else -> 0
    }
}
