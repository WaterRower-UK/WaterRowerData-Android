package uk.co.waterrower.waterrowerdata.ble.internal.gattspecification

import uk.co.waterrower.waterrowerdata.ble.internal.gattspecification.Format.UInt16
import uk.co.waterrower.waterrowerdata.ble.internal.gattspecification.Format.UInt24
import uk.co.waterrower.waterrowerdata.ble.internal.gattspecification.Format.UInt8

internal fun ByteArray.readIntValue(
    format: Format,
    offset: Int
): Int {
    return when (format) {
        UInt8 -> unsignedByteToInt(this[offset])
        UInt16 -> unsignedBytesToInt(this[offset], this[offset + 1])
        UInt24 -> unsignedBytesToInt(this[offset], this[offset + 1], this[offset + 2])
    }
}

/**
 * Convert a signed byte to an unsigned int.
 */
private fun unsignedByteToInt(b: Byte): Int {
    return b.toInt() and 0xFF
}

/**
 * Convert signed bytes to a 16-bit unsigned int.
 */
private fun unsignedBytesToInt(b0: Byte, b1: Byte): Int {
    return unsignedByteToInt(b0) + (unsignedByteToInt(b1) shl 8)
}

/**
 * Convert signed bytes to a 24-bit unsigned int.
 */
private fun unsignedBytesToInt(b0: Byte, b1: Byte, b2: Byte): Int {
    return (
        unsignedByteToInt(b0) + (unsignedByteToInt(b1) shl 8) +
            (unsignedByteToInt(b2) shl 16)
        )
}

/**
 * Convert signed bytes to a 32-bit unsigned int.
 */
private fun unsignedBytesToInt(b0: Byte, b1: Byte, b2: Byte, b3: Byte): Int {
    return (
        unsignedByteToInt(b0) + (unsignedByteToInt(b1) shl 8) + (
            unsignedByteToInt(
                b2
            ) shl 16
            ) + (unsignedByteToInt(b3) shl 24)
        )
}

/**
 * Convert an unsigned integer value to a two's-complement encoded
 * signed value.
 */
private fun unsignedToSigned(unsigned: Int, size: Int): Int {
    if (unsigned and (1 shl size - 1) == 0) {
        return unsigned
    }
    return -1 * ((1 shl size - 1) - (unsigned and (1 shl size - 1) - 1))
}
