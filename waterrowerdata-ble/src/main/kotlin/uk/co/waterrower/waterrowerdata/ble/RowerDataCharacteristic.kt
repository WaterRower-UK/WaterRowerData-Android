package uk.co.waterrower.waterrowerdata.ble

import kotlin.experimental.and

object RowerDataCharacteristic {

    fun decode(bytes: ByteArray): RowerData {
        return RowerData(
            averageStrokeRate = averageStrokeRate(bytes),
            totalDistanceMeters = totalDistanceMeters(bytes)
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

    private fun totalDistanceMeters(data: ByteArray): Int? {
        val flagsValue = data[0]
        val totalDistancePresent = flagsValue.and(0b100).toInt() != 0

        if (totalDistancePresent) {
            return data[2] + data[3].toInt().shl(8) + data[4].toInt().shl(16)
        }

        return null
    }
}
