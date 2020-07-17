package uk.co.waterrower.waterrowerdata.ble.internal.gattspecification

internal class BitRequirement(
    private val bitIndex: Int,
    private val bitValue: Int
) : Requirement {

    override fun checkIn(bytes: ByteArray): Boolean {
        val flagsValue = bytes[0]
        return flagsValue.toInt().and(bitValue.shl(bitIndex)) != 0
    }
}
