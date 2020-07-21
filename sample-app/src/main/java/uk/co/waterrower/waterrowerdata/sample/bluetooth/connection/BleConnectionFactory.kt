package uk.co.waterrower.waterrowerdata.sample.bluetooth.connection

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.os.Parcel
import android.util.Log.d

/**
 * A class for creating [BleConnection] instances.
 */
class BleConnectionFactory private constructor(
    private val context: Context
) {

    private val connections = mutableMapOf<String, BleConnection>()

    /**
     * Creates a [BleConnection] instance, or reuses a previously created instance.
     *
     * @param address The hardware address of the BLE device, for example "00:11:22:AA:BB:CC"
     */
    fun createConnection(address: String): BleConnection = synchronized(connections) {
        return connections.getOrPut(address) {
            d("BleConnectionFactory", "Creating ble connection for $address")

            val bluetoothDevice = bluetoothDevice(address)
            BleConnection(context, bluetoothDevice)
        }
    }

    /** Constructs a [BluetoothDevice] from an address. */
    private fun bluetoothDevice(address: String): BluetoothDevice {
        val parcel = Parcel.obtain()
        parcel.writeString(address)
        parcel.setDataPosition(0)
        val device = BluetoothDevice.CREATOR.createFromParcel(parcel)
        parcel.recycle()
        return device
    }

    companion object {

        /**
         * Creates a [BleConnectionFactory] instance for given [context].
         *
         * It is generally recommended to use a single [BleConnectionFactory]
         * instance in an application.
         */
        fun from(context: Context): BleConnectionFactory {
            return BleConnectionFactory(context.applicationContext)
        }
    }
}
