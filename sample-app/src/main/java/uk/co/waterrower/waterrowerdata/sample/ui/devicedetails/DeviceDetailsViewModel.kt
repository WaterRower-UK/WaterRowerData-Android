package uk.co.waterrower.waterrowerdata.sample.ui.devicedetails

import uk.co.waterrower.waterrowerdata.ble.RowerData

data class DeviceDetailsViewModel(
    val deviceName: String,
    val connectionStatus: ConnectionStatus,
    val rowerData: RowerData?
) {

    fun withRowerData(rowerData: RowerData?): DeviceDetailsViewModel {
        if (rowerData == null) return copy(rowerData = null)

        return copy(
            rowerData = rowerData.with(rowerData)
        )
    }
}
