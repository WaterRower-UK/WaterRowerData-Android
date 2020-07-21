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
import uk.co.waterrower.waterrowerdata.ble.RowerData
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

            if (viewModel.value.connectionStatus == Connected) {
                DataRow(title = "Stroke rate", value = viewModel.value.rowerData?.strokeRate)
                DataRow(title = "Stroke count", value = viewModel.value.rowerData?.strokeCount)
                DataRow(title = "Average stroke rate", value = viewModel.value.rowerData?.averageStrokeRate)
                DataRow(title = "Total distance", value = viewModel.value.rowerData?.totalDistanceMeters)
                DataRow(title = "Instantaneous pace", value = viewModel.value.rowerData?.instantaneousPaceSeconds)
                DataRow(title = "Average pace", value = viewModel.value.rowerData?.averagePaceSeconds)
                DataRow(title = "Instantaneous power", value = viewModel.value.rowerData?.instantaneousPowerWatts)
                DataRow(title = "Average power", value = viewModel.value.rowerData?.averagePowerWatts)
                DataRow(title = "Resistance level", value = viewModel.value.rowerData?.resistanceLevel)
                DataRow(title = "Total energy", value = viewModel.value.rowerData?.totalEnergyKiloCalories)
                DataRow(title = "Energy per hour", value = viewModel.value.rowerData?.energyPerHourKiloCalories)
                DataRow(title = "Energy per minute", value = viewModel.value.rowerData?.energyPerMinuteKiloCalories)
                DataRow(title = "Heart rate", value = viewModel.value.rowerData?.heartRate)
                DataRow(title = "Metabolic equivalent", value = viewModel.value.rowerData?.metabolicEquivalent)
                DataRow(title = "Elapsed time", value = viewModel.value.rowerData?.elapsedTimeSeconds)
                DataRow(title = "Remaining time", value = viewModel.value.rowerData?.remainingTimeSeconds)
            }
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
        ConnectionButton(
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
private fun ConnectionButton(
    connectionStatus: ConnectionStatus,
    connectClick: () -> Unit,
    disconnectClick: () -> Unit
) {
    when (connectionStatus) {
        Disconnected, Connecting, Failed -> ConnectButton(connectionStatus, connectClick = connectClick)
        Connected -> DisconnectButton(disconnectClick = disconnectClick)
    }
}

@Composable
private fun ConnectButton(connectionStatus: ConnectionStatus, connectClick: () -> Unit) {
    TextButton(onClick = connectClick, enabled = connectionStatus == Disconnected) {
        Text("Connect")
    }
}

@Composable
private fun DisconnectButton(disconnectClick: () -> Unit) {
    TextButton(onClick = disconnectClick) {
        Text("Disconnect")
    }
}

@Composable
private fun DataRow(
    title: String,
    value: Any?
) {
    Row(
        verticalGravity = Alignment.CenterVertically,
        modifier = Modifier.padding(8.dp)
    ) {
        Text(title)
        Spacer(modifier = Modifier.weight(1f))
        Text(value?.toString() ?: "-")
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
                    connectionStatus = Connected,
                    rowerData = RowerData(
                        strokeRate = 17.0,
                        strokeCount = 522,
                        averageStrokeRate = 17.8,
                        totalDistanceMeters = 1337,
                        instantaneousPaceSeconds = 524,
                        averagePaceSeconds = null,
                        instantaneousPowerWatts = 510,
                        averagePowerWatts = 478,
                        resistanceLevel = null,
                        totalEnergyKiloCalories = null,
                        energyPerHourKiloCalories = null,
                        energyPerMinuteKiloCalories = null,
                        heartRate = 176,
                        metabolicEquivalent = null,
                        elapsedTimeSeconds = 376,
                        remainingTimeSeconds = null
                    )
                )
            ),
            onUpClick = { println("Up clicked") },
            connectClick = { println("Connect clicked") },
            disconnectClick = { println("Disconnect clicked") }
        )
    }
}
