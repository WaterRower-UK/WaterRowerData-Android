package uk.co.waterrower.waterrowerdata.ble.internal.rowerdataspecification

import uk.co.waterrower.waterrowerdata.ble.internal.gattspecification.BitRequirement
import uk.co.waterrower.waterrowerdata.ble.internal.gattspecification.Field
import uk.co.waterrower.waterrowerdata.ble.internal.gattspecification.Format

internal object RowerDataHeartRateField : Field {

    override val name = "Heart Rate"
    override val format = Format.UInt8

    private val requirement = BitRequirement(bitIndex = 9, bitValue = 1)

    override fun isPresentIn(bytes: ByteArray): Boolean {
        return requirement.checkIn(bytes)
    }
}
