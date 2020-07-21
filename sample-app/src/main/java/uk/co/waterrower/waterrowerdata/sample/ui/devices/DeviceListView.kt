package uk.co.waterrower.waterrowerdata.sample.ui.devices

import androidx.compose.Composable
import androidx.compose.MutableState
import androidx.compose.mutableStateOf
import androidx.ui.core.Modifier
import androidx.ui.foundation.AdapterList
import androidx.ui.foundation.Box
import androidx.ui.foundation.Text
import androidx.ui.foundation.clickable
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.padding
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp

@Composable
fun DeviceListView(
    devices: MutableState<List<Device>>,
    onClick: (Device) -> Unit
) {
    AdapterList(
        data = devices.value
    ) { device ->
        DeviceRow(device = device, onClick = { onClick(device) })
    }
}

@Composable
private fun DeviceRow(
    device: Device,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clickable(
                onClick = onClick,
                enabled = true
            )
            .padding(8.dp)
            .fillMaxWidth(),
        children = {
            Text(text = device.name)
        }
    )
}

@Preview
@Composable
fun DeviceListViewPreview() {
    DeviceListView(
        mutableStateOf(
            (1..20).map {
                Device(address = "Address $it", name = "Device $it")
            }
        ),
        onClick = { println("Clicked $it") }
    )
}
