package uk.co.waterrower.waterrowerdata.ble.testutil

object RowerDataCharacteristicFlags {

    fun create(
        averageStrokeRatePresent: Boolean = false,
        totalDistancePresent: Boolean = false
    ): ByteArray {
        val flags = mutableMapOf<Int, Boolean>()

        if (averageStrokeRatePresent) {
            flags[1] = true
        }

        if (totalDistancePresent) {
            flags[2] = true
        }

        return CharacteristicFlags.createFlags(flags)
    }
}
