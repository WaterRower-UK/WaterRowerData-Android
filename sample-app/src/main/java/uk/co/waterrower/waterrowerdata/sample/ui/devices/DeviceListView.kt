package uk.co.waterrower.waterrowerdata.sample.ui.devices

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DeviceListView(
    devices: List<Device>,
    onClick: (Device) -> Unit
) {
    LazyColumn {
        items(devices) { device ->
            DeviceRow(
                device = device,
                onClick = { onClick(device) }
            )
        }
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
        content = {
            Text(text = device.name ?: "Unknown device")
        }
    )
}

@Preview
@Composable
fun DeviceListViewPreview() {
    DeviceListView(
        devices = (1..20).map {
            Device(address = "Address $it", name = "Device $it")
        },
        onClick = { println("Clicked $it") }
    )
}
