package uk.co.waterrower.waterrowerdata.ble.internal.rowerdataspecification

import uk.co.waterrower.waterrowerdata.ble.internal.gattspecification.Field
import uk.co.waterrower.waterrowerdata.ble.internal.gattspecification.Format.UInt16

internal object RowerDataFlagsField : Field {

    override val name = "Flags"
    override val format = UInt16

    override fun isPresentIn(bytes: ByteArray): Boolean {
        return true
    }
}
