package uk.co.waterrower.waterrowerdata.sample.ui.devicedetails

import androidx.compose.Composable
import androidx.ui.foundation.Text
import androidx.ui.layout.Column
import androidx.ui.material.TopAppBar
import androidx.ui.tooling.preview.Preview
import uk.co.waterrower.waterrowerdata.sample.ui.theming.AppTheme

@Composable
fun DeviceDetailsView(
    deviceName: String,
    connectionStatus: ConnectionStatus
) {
    Column {
        TopAppBar(title = { Text(deviceName) })
        Text("$connectionStatus")
    }
}

@Preview
@Composable
fun DeviceDetailsViewPreview() {
    AppTheme {
        DeviceDetailsView(deviceName = "S4 74", connectionStatus = ConnectionStatus.Disconnected)
    }
}
