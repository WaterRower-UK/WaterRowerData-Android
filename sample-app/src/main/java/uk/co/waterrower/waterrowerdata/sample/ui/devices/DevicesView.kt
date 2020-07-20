package uk.co.waterrower.waterrowerdata.sample.ui.devices

import androidx.compose.Composable
import androidx.compose.MutableState
import androidx.compose.mutableStateOf
import androidx.ui.foundation.Text
import androidx.ui.layout.Column
import androidx.ui.material.TopAppBar
import androidx.ui.tooling.preview.Preview
import uk.co.waterrower.waterrowerdata.sample.util.map

@Composable
fun DevicesView(viewModel: MutableState<DevicesViewModel>, onDeviceClick: (Device) -> Unit) {
    Column {
        TopAppBar(title = { Text("Available devices") })
        DeviceListView(
            devices = viewModel.map { it.devices },
            onClick = onDeviceClick
        )
    }
}

@Preview
@Composable
fun DevicesViewPreview() {
    DevicesView(
        mutableStateOf(
            DevicesViewModel(
                (1..20).map {
                    Device("Device $it")
                }
            )
        ),
        onDeviceClick = { device -> println("Device clicked: $device") }
    )
}
