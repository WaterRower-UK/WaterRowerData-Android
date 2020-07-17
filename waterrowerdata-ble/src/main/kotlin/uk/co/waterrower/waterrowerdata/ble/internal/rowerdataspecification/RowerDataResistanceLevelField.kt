package uk.co.waterrower.waterrowerdata.ble.internal.rowerdataspecification

import uk.co.waterrower.waterrowerdata.ble.internal.gattspecification.BitRequirement
import uk.co.waterrower.waterrowerdata.ble.internal.gattspecification.Field
import uk.co.waterrower.waterrowerdata.ble.internal.gattspecification.Format

internal object RowerDataResistanceLevelField : Field {

    override val name = "Resistance Level"
    override val format = Format.SInt16

    private val requirement = BitRequirement(bitIndex = 7, bitValue = 1)

    override fun isPresentIn(bytes: ByteArray): Boolean {
        return requirement.checkIn(bytes)
    }
}
