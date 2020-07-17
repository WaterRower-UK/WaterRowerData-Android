package uk.co.waterrower.waterrowerdata.ble.internal.gattspecification

internal interface Requirement {

    fun checkIn(bytes: ByteArray): Boolean
}
