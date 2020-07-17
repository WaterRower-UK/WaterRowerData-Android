package uk.co.waterrower.waterrowerdata.ble.testutil

object CharacteristicFlags {

    /**
     Creates a 16 bit data buffer, by putting a `1` on each indicated index:

     [0: true] becomes 0000 0000 0000 0001
     [7: true] becomes 0000 0000 1000 0000
     */
    fun createFlags(
        flags: Map<Int, Boolean>
    ): ByteArray {
        val result = ByteArray(size = 2)

        val flagsValue = flags.entries
            .fold(0) { accumulation, item ->
                if (item.value) {
                    return@fold accumulation + 1.shl(item.key)
                }

                accumulation
            }

        result[0] = flagsValue.and(0xFF).toByte()
        result[1] = flagsValue.shr(8).and(0xFF).toByte()

        return result
    }
}
