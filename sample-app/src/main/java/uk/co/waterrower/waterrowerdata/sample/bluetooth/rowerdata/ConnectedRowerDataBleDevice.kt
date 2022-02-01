package uk.co.waterrower.waterrowerdata.sample.bluetooth.rowerdata

import uk.co.waterrower.waterrowerdata.ble.FitnessMachineService
import uk.co.waterrower.waterrowerdata.ble.RowerData
import uk.co.waterrower.waterrowerdata.ble.RowerDataCharacteristic
import uk.co.waterrower.waterrowerdata.sample.bluetooth.connection.ConnectedBleDevice
import uk.co.waterrower.waterrowerdata.sample.util.Cancellable

class ConnectedRowerDataBleDevice(
    private val connectedBleDevice: ConnectedBleDevice
) {

    fun rowerData(callback: (RowerData) -> Unit): Cancellable {
        return connectedBleDevice.listen(
            serviceUUID = FitnessMachineService.uuid,
            characteristicUUID = RowerDataCharacteristic.uuid
        ) { data ->
            callback(RowerDataCharacteristic.decode(data))
        }
    }

    fun batteryLevel(callback: (Int) -> Unit): Cancellable {
        return connectedBleDevice.listen(
            serviceUUID = BatteryService.uuid,
            characteristicUUID = BatteryLevelCharacteristic.uuid
        ) { data ->
            val value = data[0].toInt() and 0xFF
            callback(value)
        }
    }
}
