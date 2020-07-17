package uk.co.waterrower.waterrowerdata.ble

import kotlin.experimental.and

object RowerDataCharacteristic {

    fun decode(bytes: ByteArray): RowerData {
        return RowerData(
            averageStrokeRate = averageStrokeRate(bytes)
        )
    }

    private fun averageStrokeRate(data: ByteArray): Double? {
        val flagsValue = data[0]
        val averageStrokeRatePresent = flagsValue.and(0b10).toInt() != 0

        if (averageStrokeRatePresent) {
            return data[2] / 2.0
        }

        return null
    }
}
