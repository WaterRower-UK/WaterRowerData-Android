package uk.co.waterrower.waterrowerdata.ble

import uk.co.waterrower.waterrowerdata.ble.internal.gattspecification.Field
import uk.co.waterrower.waterrowerdata.ble.internal.gattspecification.readIntValue
import uk.co.waterrower.waterrowerdata.ble.internal.rowerdataspecification.RowerDataAveragePaceField
import uk.co.waterrower.waterrowerdata.ble.internal.rowerdataspecification.RowerDataAveragePowerField
import uk.co.waterrower.waterrowerdata.ble.internal.rowerdataspecification.RowerDataAverageStrokeRateField
import uk.co.waterrower.waterrowerdata.ble.internal.rowerdataspecification.RowerDataElapsedTimeField
import uk.co.waterrower.waterrowerdata.ble.internal.rowerdataspecification.RowerDataEnergyPerHourField
import uk.co.waterrower.waterrowerdata.ble.internal.rowerdataspecification.RowerDataEnergyPerMinuteField
import uk.co.waterrower.waterrowerdata.ble.internal.rowerdataspecification.RowerDataFlagsField
import uk.co.waterrower.waterrowerdata.ble.internal.rowerdataspecification.RowerDataHeartRateField
import uk.co.waterrower.waterrowerdata.ble.internal.rowerdataspecification.RowerDataInstantaneousPaceField
import uk.co.waterrower.waterrowerdata.ble.internal.rowerdataspecification.RowerDataInstantaneousPowerField
import uk.co.waterrower.waterrowerdata.ble.internal.rowerdataspecification.RowerDataMetabolicEquivalentField
import uk.co.waterrower.waterrowerdata.ble.internal.rowerdataspecification.RowerDataRemainingTimeField
import uk.co.waterrower.waterrowerdata.ble.internal.rowerdataspecification.RowerDataResistanceLevelField
import uk.co.waterrower.waterrowerdata.ble.internal.rowerdataspecification.RowerDataStrokeCountField
import uk.co.waterrower.waterrowerdata.ble.internal.rowerdataspecification.RowerDataStrokeRateField
import uk.co.waterrower.waterrowerdata.ble.internal.rowerdataspecification.RowerDataTotalDistanceField
import uk.co.waterrower.waterrowerdata.ble.internal.rowerdataspecification.RowerDataTotalEnergyField
import java.util.UUID

/**
 * A class that can decode raw bytes into [RowerData] instances.
 *
 * This class follows the Rower Data characteristic specification
 * as described in section 4.8 "Rower Data" of the
 * Fitness Machine Service (FTMS) Bluetooth Service specification,
 * revision v1.0.
 *
 * A copy of this specification can be found on
 * https://www.bluetooth.com/specifications/gatt/
 */
object RowerDataCharacteristic {

    /**
     * The UUID value that identifies this characteristic.
     */
    val uuid: UUID = UUID.fromString("00002AD1-0000-1000-8000-00805F9B34FB")

    /**
     * Decodes given [bytes] into a [RowerData] instance.
     *
     * Due to restrictions in the byte buffer size some of the [RowerData]
     * properties will be absent, which is represented as a `nil` value.
     *
     * @param bytes A [ByteArray] instance that contains the encoded data
     *              as described in the Rower Data characteristic specification.
     *
     * @return A [RowerData] instance with the decoded properties.
     *         Properties will be `nil` if not present in the encoded data.
     */
    fun decode(bytes: ByteArray): RowerData {
        return RowerData(
            strokeRate = strokeRate(bytes),
            strokeCount = strokeCount(bytes),
            averageStrokeRate = averageStrokeRate(bytes),
            totalDistanceMeters = totalDistanceMeters(bytes),
            instantaneousPaceSeconds = instantaneousPaceSeconds(bytes),
            averagePaceSeconds = averagePaceSeconds(bytes),
            instantaneousPowerWatts = instantaneousPower(bytes),
            averagePowerWatts = averagePower(bytes),
            resistanceLevel = resistanceLevel(bytes),
            totalEnergyKiloCalories = totalEnergy(bytes),
            energyPerHourKiloCalories = energyPerHour(bytes),
            energyPerMinuteKiloCalories = energyPerMinute(bytes),
            heartRate = heartRate(bytes),
            metabolicEquivalent = metabolicEquivalent(bytes),
            elapsedTimeSeconds = elapsedTime(bytes),
            remainingTimeSeconds = remainingTime(bytes)
        )
    }

    private val fields = listOf(
        RowerDataFlagsField,
        RowerDataStrokeRateField,
        RowerDataStrokeCountField,
        RowerDataAverageStrokeRateField,
        RowerDataTotalDistanceField,
        RowerDataInstantaneousPaceField,
        RowerDataAveragePaceField,
        RowerDataInstantaneousPowerField,
        RowerDataAveragePowerField,
        RowerDataResistanceLevelField,
        RowerDataTotalEnergyField,
        RowerDataEnergyPerHourField,
        RowerDataEnergyPerMinuteField,
        RowerDataHeartRateField,
        RowerDataMetabolicEquivalentField,
        RowerDataElapsedTimeField,
        RowerDataRemainingTimeField
    )

    private fun strokeRate(bytes: ByteArray): Double? {
        val intValue = readIntValue(bytes, RowerDataStrokeRateField) ?: return null

        return intValue / 2.0
    }

    private fun strokeCount(bytes: ByteArray): Int? {
        return readIntValue(bytes, RowerDataStrokeCountField)
    }

    private fun averageStrokeRate(bytes: ByteArray): Double? {
        val intValue = readIntValue(bytes, RowerDataAverageStrokeRateField) ?: return null

        return intValue / 2.0
    }

    private fun totalDistanceMeters(bytes: ByteArray): Int? {
        return readIntValue(bytes, RowerDataTotalDistanceField)
    }

    private fun instantaneousPaceSeconds(bytes: ByteArray): Int? {
        val result = readIntValue(bytes, RowerDataInstantaneousPaceField)
        if (result == 65535) { // Invalid value
            return null
        }
        return result
    }

    private fun averagePaceSeconds(bytes: ByteArray): Int? {
        return readIntValue(bytes, RowerDataAveragePaceField)
    }

    private fun instantaneousPower(bytes: ByteArray): Int? {
        return readIntValue(bytes, RowerDataInstantaneousPowerField)
    }

    private fun averagePower(bytes: ByteArray): Int? {
        return readIntValue(bytes, RowerDataAveragePowerField)
    }

    private fun resistanceLevel(bytes: ByteArray): Int? {
        return readIntValue(bytes, RowerDataResistanceLevelField)
    }

    private fun totalEnergy(bytes: ByteArray): Int? {
        val result = readIntValue(bytes, RowerDataTotalEnergyField)
        if (result == 0xFFFF) {
            return null
        }
        return result
    }

    private fun energyPerHour(bytes: ByteArray): Int? {
        val result = readIntValue(bytes, RowerDataEnergyPerHourField)
        if (result == 0xFFFF) {
            return null
        }
        return result
    }

    private fun energyPerMinute(bytes: ByteArray): Int? {
        val result = readIntValue(bytes, RowerDataEnergyPerMinuteField)
        if (result == 0xFF) {
            return null
        }
        return result
    }

    private fun heartRate(bytes: ByteArray): Int? {
        return readIntValue(bytes, RowerDataHeartRateField)
    }

    private fun metabolicEquivalent(bytes: ByteArray): Double? {
        val intValue = readIntValue(bytes, RowerDataMetabolicEquivalentField) ?: return null

        return intValue / 10.0
    }

    private fun elapsedTime(bytes: ByteArray): Int? {
        return readIntValue(bytes, RowerDataElapsedTimeField)
    }

    private fun remainingTime(bytes: ByteArray): Int? {
        return readIntValue(bytes, RowerDataRemainingTimeField)
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
