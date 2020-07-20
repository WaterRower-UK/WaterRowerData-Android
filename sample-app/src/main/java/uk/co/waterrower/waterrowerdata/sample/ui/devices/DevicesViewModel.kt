package uk.co.waterrower.waterrowerdata.sample.ui.devices

data class DevicesViewModel(
    val devices: List<Device>
) {

    fun withAppended(device: Device): DevicesViewModel {
        if (devices.any { it.id == device.id }) return this

        return DevicesViewModel(devices + device)
    }
}
