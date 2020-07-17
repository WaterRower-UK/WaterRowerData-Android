package uk.co.waterrower.waterrowerdata.ble

import uk.co.waterrower.waterrowerdata.ble.internal.gattspecification.Field
import uk.co.waterrower.waterrowerdata.ble.internal.gattspecification.readIntValue
import uk.co.waterrower.waterrowerdata.ble.internal.rowerdataspecification.RowerDataAverageStrokeRateField
import uk.co.waterrower.waterrowerdata.ble.internal.rowerdataspecification.RowerDataFlagsField
import uk.co.waterrower.waterrowerdata.ble.internal.rowerdataspecification.RowerDataInstantaneousPaceField
import uk.co.waterrower.waterrowerdata.ble.internal.rowerdataspecification.RowerDataTotalDistanceField

object RowerDataCharacteristic {

    fun decode(bytes: ByteArray): RowerData {
        return RowerData(
            averageStrokeRate = averageStrokeRate(bytes),
            totalDistanceMeters = totalDistanceMeters(bytes),
            instantaneousPaceSeconds = instantaneousPace(bytes)
        )
    }

    private val fields = listOf(
        RowerDataFlagsField,
        RowerDataAverageStrokeRateField,
        RowerDataTotalDistanceField,
        RowerDataInstantaneousPaceField
    )

    private fun averageStrokeRate(data: ByteArray): Double? {
        val intValue = readIntValue(data, RowerDataAverageStrokeRateField)
        if (intValue == null) return null

        return intValue / 2.0
    }

    private fun totalDistanceMeters(data: ByteArray): Int? {
        return readIntValue(data, RowerDataTotalDistanceField)
    }

    private fun instantaneousPace(data: ByteArray): Int? {
        return readIntValue(data, RowerDataInstantaneousPaceField)
    }

    private fun readIntValue(bytes: ByteArray, field: Field): Int? {
        if (!field.isPresentIn(bytes)) {
            return null
        }

        var offset = 0
        for (i in 0..fields.count()) {
            val f = fields[i]
            if (f.name == field.name) {
                val intValue = bytes.readIntValue(field.format, offset)
                return intValue
            }

            if (f.isPresentIn(bytes)) {
                offset += f.format.numberOfBytes()
            }
        }

        return null
    }
}
