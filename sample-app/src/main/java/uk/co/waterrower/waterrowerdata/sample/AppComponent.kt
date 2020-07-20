package uk.co.waterrower.waterrowerdata.sample

import android.bluetooth.BluetoothAdapter
import uk.co.waterrower.waterrowerdata.sample.bluetooth.discovery.AndroidBleScanner

class AppComponent {

    val bleScanner by lazy {
        AndroidBleScanner(BluetoothAdapter.getDefaultAdapter())
    }
}
