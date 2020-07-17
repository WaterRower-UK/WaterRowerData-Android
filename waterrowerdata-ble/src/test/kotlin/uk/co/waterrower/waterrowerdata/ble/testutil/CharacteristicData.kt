package uk.co.waterrower.waterrowerdata.ble.testutil

object CharacteristicData {

    fun create(
        flags: ByteArray,
        vararg values: Byte
    ): ByteArray {
        return flags + values
    }
}
