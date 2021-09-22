package uk.co.waterrower.waterrowerdata.sample.ui.devices

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import uk.co.waterrower.waterrowerdata.sample.ui.theming.AppTheme

@Composable
fun DevicesView(
    viewModel: DevicesViewModel,
    onDeviceClick: (Device) -> Unit
) {
    Column {
        TopAppBar(title = { Text("Available devices") })
        DeviceListView(
            devices = viewModel.devices,
            onClick = onDeviceClick
        )
    }
}

@Preview
@Composable
fun DevicesViewPreview() {
    AppTheme {
        DevicesView(
            DevicesViewModel(
                devices = (1..20).map { it: Int ->
                    Device(address = "Address $it", name = "Device $it")
                }
            ),
            onDeviceClick = { device -> println("Device clicked: $device") }
        )
    }
}
