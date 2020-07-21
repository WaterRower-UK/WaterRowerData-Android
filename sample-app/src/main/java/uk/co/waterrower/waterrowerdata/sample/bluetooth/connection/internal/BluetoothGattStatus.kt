package uk.co.waterrower.waterrowerdata.sample.bluetooth.connection.internal

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothGatt

internal fun Int.toStatusString() = when (this) {
    BluetoothGatt.GATT_SUCCESS -> "GATT_SUCCESS($this)"
    BluetoothGatt.GATT_READ_NOT_PERMITTED -> "GATT_READ_NOT_PERMITTED($this)"
    BluetoothGatt.GATT_WRITE_NOT_PERMITTED -> "GATT_WRITE_NOT_PERMITTED($this)"
    BluetoothGatt.GATT_INSUFFICIENT_AUTHENTICATION -> "GATT_INSUFFICIENT_AUTHENTICATION($this)"
    BluetoothGatt.GATT_REQUEST_NOT_SUPPORTED -> "GATT_REQUEST_NOT_SUPPORTED($this)"
    BluetoothGatt.GATT_INSUFFICIENT_ENCRYPTION -> "GATT_INSUFFICIENT_ENCRYPTION($this)"
    BluetoothGatt.GATT_INVALID_OFFSET -> "GATT_INVALID_OFFSET($this)"
    BluetoothGatt.GATT_INVALID_ATTRIBUTE_LENGTH -> "GATT_INVALID_ATTRIBUTE_LENGTH($this)"
    BluetoothGatt.GATT_FAILURE -> "GATT_FAILURE($this)"
    GATT_TERMINATED_BY_PEER -> "GATT_TERMINATED_BY_PEER($this)"
    GATT_CONN_TERMINATE_LOCAL_HOST -> "GATT_CONN_TERMINATE_LOCAL_HOST($this)"
    BLE_HCI_STATUS_CODE_LMP_RESPONSE_TIMEOUT -> "BLE_HCI_STATUS_CODE_LMP_RESPONSE_TIMEOUT($this)"
    GATT_CONN_TIMEOUT -> "GATT_CONN_TIMEOUT($this)"
    GATT_ERROR -> "GATT_ERROR($this)"
    GATT_CONNECTION_CONGESTED -> "GATT_CONNECTION_CONGESTED($this)"
    else -> "Unknown($this)"
}

internal fun Int.toNewStateString() = when (this) {
    BluetoothAdapter.STATE_CONNECTED -> "Connected($this)"
    BluetoothAdapter.STATE_DISCONNECTED -> "Disconnected($this)"
    else -> "Unknown($this)"
}

internal const val GATT_CONN_TIMEOUT = 8
internal const val GATT_TERMINATED_BY_PEER = 19
internal const val GATT_CONN_TERMINATE_LOCAL_HOST = 22
internal const val BLE_HCI_STATUS_CODE_LMP_RESPONSE_TIMEOUT = 34
internal const val GATT_ERROR = 133
internal const val GATT_CONNECTION_CONGESTED = 143
