package uk.co.waterrower.waterrowerdata.ble.internal.gattspecification

internal class BitRequirement(
    private val bitIndex: Int,
    private val bitValue: Int
) : Requirement {

    override fun checkIn(bytes: ByteArray): Boolean {
        val flagsValue = bytes.readIntValue(Format.UInt16, offset = 0)
        return flagsValue.and(1.shl(bitIndex)) == (bitValue.shl(bitIndex))
    }
}
