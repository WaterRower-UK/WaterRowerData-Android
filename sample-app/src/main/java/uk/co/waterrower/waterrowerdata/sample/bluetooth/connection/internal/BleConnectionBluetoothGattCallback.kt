package uk.co.waterrower.waterrowerdata.sample.bluetooth.connection.internal

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import android.util.Log.d
import android.util.Log.w
import java.util.UUID

internal class BleConnectionBluetoothGattCallback : BluetoothGattCallback() {

    private var gatt: BluetoothGatt? = null

    private var connectionState: ConnectionState = ConnectionState.Disconnected
        set(value) {
            field = value
            listeners.forEach { it.onConnectionStateChanged(value) }
        }

    private var listeners = listOf<ConnectionStateListener>()

    fun addConnectionStateListener(listener: ConnectionStateListener) {
        listeners += listener
        listener.onConnectionStateChanged(connectionState)
    }

    fun removeConnectionStateListener(listener: ConnectionStateListener) {
        listeners -= listener
    }

    fun onGattAvailable(gatt: BluetoothGatt) {
        this.gatt = gatt
        connectionState = ConnectionState.Connecting
    }

    override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
        this.gatt = gatt
        d("BCBluetoothGattCallback", "Connection state changed: status=${status.toStatusString()}; newState=${newState.toNewStateString()}")

        if (status != BluetoothGatt.GATT_SUCCESS) {
            w("BCBluetoothGattCallback", "Status is not success, aborting.")
            gatt.disconnect()
            gatt.close()
            connectionState = ConnectionState.Failed
            return
        }

        if (connectionState != ConnectionState.Connecting || newState != BluetoothGatt.STATE_CONNECTED) {
            error("Unsupported state change.")
        }

        if (!gatt.discoverServices()) {
            w("BCBluetoothGattCallback", "Could not discover services, aborting.")
            disconnect(gatt, ConnectionState.Failed)
            return
        }

        d("BCBluetoothGattCallback", "Discovering services.")
    }

    override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
        this.gatt = gatt
        d("BCBluetoothGattCallback", "Services discovered: status=${status.toStatusString()}")

        if (status != BluetoothGatt.GATT_SUCCESS) {
            w("BCBluetoothGattCallback", "Status is not success, aborting.")
            disconnect(gatt, ConnectionState.Failed)
            return
        }

        connectionState = ConnectionState.Connected(this)
    }

    fun disconnect() {
        val gatt = gatt ?: error("Gatt not available")
        disconnect(gatt, ConnectionState.Disconnected)
    }

    private fun disconnect(gatt: BluetoothGatt, newState: ConnectionState) {
        gatt.disconnect()
        gatt.close()
        connectionState = newState
    }

    private var characteristicChangedListeners = listOf<(BluetoothGattCharacteristic) -> Unit>()

    fun addCharacteristicChangedListener(listener: (BluetoothGattCharacteristic) -> Unit) {
        characteristicChangedListeners += listener
    }

    fun removeCharacteristicChangedListener(listener: (BluetoothGattCharacteristic) -> Unit) {
        characteristicChangedListeners -= listener
    }

    override fun onCharacteristicChanged(gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic) {
        characteristicChangedListeners.forEach { it.invoke(characteristic) }
    }

    fun enableNotifications(serviceUUID: UUID, characteristicUUID: UUID) {
        val gatt = gatt ?: error("Gatt not available")

        val service = gatt.getService(serviceUUID) ?: run {
            w("ConnectedBleDevice", "service not available: $serviceUUID")
            return
        }
        val characteristic = service.getCharacteristic(characteristicUUID) ?: run {
            w("ConnectedBleDevice", "characteristic not available; $characteristicUUID")
            return
        }
        val descriptor = characteristic.getDescriptor(ClientCharacteristicConfigurationDescriptor.uuid) ?: run {
            w("ConnectedBleDevice", "descriptor not available")
            return
        }

        d("ConnectedBleDevice", "Enabling characteristic notification for [$serviceUUID, $characteristicUUID]")
        descriptor.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
        gatt.writeDescriptor(descriptor)
        gatt.setCharacteristicNotification(characteristic, true)
    }

    interface ConnectionStateListener {

        fun onConnectionStateChanged(connectionState: ConnectionState)
    }

    sealed class ConnectionState {
        object Disconnected : ConnectionState()
        object Connecting : ConnectionState()
        object Failed : ConnectionState()
        class Connected(val callback: BleConnectionBluetoothGattCallback) : ConnectionState()
    }
}
