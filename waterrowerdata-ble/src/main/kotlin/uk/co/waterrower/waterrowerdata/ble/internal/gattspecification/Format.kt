package uk.co.waterrower.waterrowerdata.ble.internal.gattspecification

internal enum class Format {
    UInt8,
    UInt16,
    UInt24;

    fun numberOfBytes(): Int {
        return when (this) {
            UInt8 -> 1
            UInt16 -> 2
            UInt24 -> 3
        }
    }
}
