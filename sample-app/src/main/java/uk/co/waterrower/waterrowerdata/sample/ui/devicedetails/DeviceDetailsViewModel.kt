package uk.co.waterrower.waterrowerdata.sample.ui.devicedetails

import uk.co.waterrower.waterrowerdata.ble.RowerData

data class DeviceDetailsViewModel(
    val deviceName: String,
    val connectionStatus: ConnectionStatus,
    val rowerData: RowerData?
)
