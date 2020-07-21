package uk.co.waterrower.waterrowerdata.sample.ui.devicedetails

import androidx.compose.Composable
import androidx.compose.MutableState
import androidx.compose.mutableStateOf
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.Column
import androidx.ui.layout.Row
import androidx.ui.layout.Spacer
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.padding
import androidx.ui.material.IconButton
import androidx.ui.material.TextButton
import androidx.ui.material.TopAppBar
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.ArrowBack
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import uk.co.waterrower.waterrowerdata.sample.ui.devicedetails.ConnectionStatus.Connected
import uk.co.waterrower.waterrowerdata.sample.ui.devicedetails.ConnectionStatus.Connecting
import uk.co.waterrower.waterrowerdata.sample.ui.devicedetails.ConnectionStatus.Disconnected
import uk.co.waterrower.waterrowerdata.sample.ui.devicedetails.ConnectionStatus.Failed
import uk.co.waterrower.waterrowerdata.sample.ui.theming.AppTheme
import uk.co.waterrower.waterrowerdata.sample.util.map

@Composable
fun DeviceDetailsView(
    viewModel: MutableState<DeviceDetailsViewModel>,
    onUpClick: () -> Unit,
    connectClick: () -> Unit,
    disconnectClick: () -> Unit
) {
    Column {
        DeviceDetailsTopAppBar(
            deviceName = viewModel.map { it.deviceName },
            onUpClick = onUpClick
        )
        VerticalScroller(
            modifier = Modifier.fillMaxWidth()
        ) {
            ConnectionRow(
                viewModel.value.connectionStatus,
                connectClick = connectClick,
                disconnectClick = disconnectClick
            )
        }
    }
}

@Composable
private fun DeviceDetailsTopAppBar(
    deviceName: MutableState<String>,
    onUpClick: () -> Unit
) {
    TopAppBar(
        title = { Text(deviceName.value) },
        navigationIcon = {
            IconButton(onClick = onUpClick) {
                Icon(asset = Icons.Filled.ArrowBack)
            }
        }
    )
}

@Composable
private fun ConnectionRow(
    connectionStatus: ConnectionStatus,
    connectClick: () -> Unit,
    disconnectClick: () -> Unit
) {
    Row(
        verticalGravity = Alignment.CenterVertically,
        modifier = Modifier.padding(8.dp)
    ) {
        ConnectionStatusText(connectionStatus)
        Spacer(modifier = Modifier.weight(1f))
        connectionButton(
            connectionStatus,
            connectClick = connectClick,
            disconnectClick = disconnectClick
        )
    }
}

@Composable
private fun ConnectionStatusText(connectionStatus: ConnectionStatus) {
    Text(connectionStatus.name)
}

@Composable
private fun connectionButton(
    connectionStatus: ConnectionStatus,
    connectClick: () -> Unit,
    disconnectClick: () -> Unit
) {
    when (connectionStatus) {
        Disconnected, Connecting, Failed -> connectButton(connectionStatus, connectClick = connectClick)
        Connected -> disconnectButton(disconnectClick = disconnectClick)
    }
}

@Composable
private fun connectButton(connectionStatus: ConnectionStatus, connectClick: () -> Unit) {
    TextButton(onClick = connectClick, enabled = connectionStatus == Disconnected) {
        Text("Connect")
    }
}

@Composable
private fun disconnectButton(disconnectClick: () -> Unit) {
    TextButton(onClick = disconnectClick) {
        Text("Disconnect")
    }
}

@Preview
@Composable
fun DeviceDetailsViewPreview() {
    AppTheme {
        DeviceDetailsView(
            mutableStateOf(
                DeviceDetailsViewModel(
                    deviceName = "S4 74",
                    connectionStatus = Disconnected
                )
            ),
            onUpClick = { println("Up clicked") },
            connectClick = { println("Connect clicked") },
            disconnectClick = { println("Disconnect clicked") }
        )
    }
}
