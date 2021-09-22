package uk.co.waterrower.waterrowerdata.sample.ui.devicedetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uk.co.waterrower.waterrowerdata.ble.RowerData
import uk.co.waterrower.waterrowerdata.sample.ui.devicedetails.ConnectionStatus.Connected
import uk.co.waterrower.waterrowerdata.sample.ui.devicedetails.ConnectionStatus.Connecting
import uk.co.waterrower.waterrowerdata.sample.ui.devicedetails.ConnectionStatus.Disconnected
import uk.co.waterrower.waterrowerdata.sample.ui.devicedetails.ConnectionStatus.Failed
import uk.co.waterrower.waterrowerdata.sample.ui.theming.AppTheme

@Composable
fun DeviceDetailsView(
    viewModel: DeviceDetailsViewModel,
    onUpClick: () -> Unit,
    connectClick: () -> Unit,
    disconnectClick: () -> Unit
) {
    Column {
        DeviceDetailsTopAppBar(
            deviceName = viewModel.deviceName,
            onUpClick = onUpClick
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            ConnectionRow(
                viewModel.connectionStatus,
                connectClick = connectClick,
                disconnectClick = disconnectClick
            )

            if (viewModel.connectionStatus == Connected) {
                DataRow(title = "Stroke rate", value = viewModel.rowerData?.strokeRate)
                DataRow(title = "Stroke count", value = viewModel.rowerData?.strokeCount)
                DataRow(title = "Average stroke rate", value = viewModel.rowerData?.averageStrokeRate)
                DataRow(title = "Total distance", value = viewModel.rowerData?.totalDistanceMeters)
                DataRow(title = "Instantaneous pace", value = viewModel.rowerData?.instantaneousPaceSeconds)
                DataRow(title = "Average pace", value = viewModel.rowerData?.averagePaceSeconds)
                DataRow(title = "Instantaneous power", value = viewModel.rowerData?.instantaneousPowerWatts)
                DataRow(title = "Average power", value = viewModel.rowerData?.averagePowerWatts)
                DataRow(title = "Resistance level", value = viewModel.rowerData?.resistanceLevel)
                DataRow(title = "Total energy", value = viewModel.rowerData?.totalEnergyKiloCalories)
                DataRow(title = "Energy per hour", value = viewModel.rowerData?.energyPerHourKiloCalories)
                DataRow(title = "Energy per minute", value = viewModel.rowerData?.energyPerMinuteKiloCalories)
                DataRow(title = "Heart rate", value = viewModel.rowerData?.heartRate)
                DataRow(title = "Metabolic equivalent", value = viewModel.rowerData?.metabolicEquivalent)
                DataRow(title = "Elapsed time", value = viewModel.rowerData?.elapsedTimeSeconds)
                DataRow(title = "Remaining time", value = viewModel.rowerData?.remainingTimeSeconds)
            }
        }
    }
}

@Composable
private fun DeviceDetailsTopAppBar(
    deviceName: String,
    onUpClick: () -> Unit
) {
    TopAppBar(
        title = { Text(deviceName) },
        navigationIcon = {
            IconButton(onClick = onUpClick) {
                Icon(Icons.Default.ArrowBack, "")
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
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(8.dp),
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
        verticalAlignment = Alignment.CenterVertically,
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
            ),
            onUpClick = { println("Up clicked") },
            connectClick = { println("Connect clicked") },
            disconnectClick = { println("Disconnect clicked") }
        )
    }
}
