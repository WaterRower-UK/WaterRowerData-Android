# WaterRowerData-BLE

The `uk.co.waterrower:waterrowerdata-ble` artifact contains the sources for
reading data from a BLE connected WaterRower device, such as an S5 monitor.


The BLE enabled WaterRower modules use the FTMS GATT service specification
with the RowerData GATT characteristic.
This artifact provides classes that decode raw data received from a GATT
characteristic into useable rower data.


## Usage
Once connected to a WaterRower monitor and receiving data (see
['Receiving data'](#receiving-data)), you'll receive the data as a
`ByteArray` instance.
You can pass these bytes into the static
`RowerDataCharacteristic.decode(bytes: ByteArray)` function to decode
the bytes into a `RowerData` data class:

```kotlin
import uk.co.waterrower.waterrowerdata.ble.*

/* ... */

override fun onCharacteristicChanged(
  gatt: BluetoothGatt,
  characteristic: BluetoothGattCharacteristic
) {
  val bytes = characteristic.value
  val rowerData = RowerDataCharacteristic.decode(value)
  println(rowerData)
}
```

This `RowerData` instance will contain the rower data that was encoded
in the `ByteArray`.

> :warning: &ensp; **Note**:  A single `ByteArray` instance does not always
contain _all_ data values.
Due to restrictions in buffer size some of the `RowerData` properties will
be absent, which is represented as a `null` value.

## Receiving data

TODO
