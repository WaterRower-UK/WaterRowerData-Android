package uk.co.waterrower.waterrowerdata.ble.internal.gattspecification

internal interface Field {

    val name: String
    val format: Format

    fun isPresentIn(bytes: ByteArray): Boolean
}
