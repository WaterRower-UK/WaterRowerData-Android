package uk.co.waterrower.waterrowerdata.sample.ui.devicedetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import uk.co.waterrower.waterrowerdata.sample.bluetooth.connection.BleConnection
import uk.co.waterrower.waterrowerdata.sample.bluetooth.connection.BleConnectionFactory
import uk.co.waterrower.waterrowerdata.sample.bluetooth.connection.BleConnectionState
import uk.co.waterrower.waterrowerdata.sample.bluetooth.rowerdata.ConnectedRowerDataBleDevice
import uk.co.waterrower.waterrowerdata.sample.ui.devices.Device
import uk.co.waterrower.waterrowerdata.sample.ui.theming.AppTheme
import uk.co.waterrower.waterrowerdata.sample.util.Cancellable

class DeviceDetailsActivity : AppCompatActivity() {

    private var device: Device? = null
        set(value) {
            field = value
            viewModel = viewModel.copy(deviceName = value?.name ?: "")
        }

    private var viewModel by mutableStateOf(
        DeviceDetailsViewModel(
            deviceName = device?.name ?: "",
            connectionStatus = ConnectionStatus.Disconnected,
            rowerData = null,
            batteryLevel = null,
        )
    )

    private val connection by lazy {
        BleConnectionFactory.from(this)
            .createConnection(device!!.address)
    }

    private var connectionStateListenerCancellable: Cancellable? = null
        set(value) {
            field?.cancel()
            field = value
        }

    private var rowerDataCancellable: Cancellable? = null
        set(value) {
            field?.cancel()
            field = value
        }

    private var batteryLevelCancellable: Cancellable? = null
        set(value) {
            field?.cancel()
            field = value
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        device = Device(intent.deviceAddress, intent.deviceName)

        setContent {
            AppTheme {
                DeviceDetailsView(
                    viewModel,
                    onUpClick = { finish() },
                    connectClick = { connection.connect() },
                    disconnectClick = { connection.disconnect() }
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()

        connectionStateListenerCancellable = connection.addConnectionStateListener(
            object : BleConnection.ConnectionStateListener {
                override fun onConnectionStateChanged(connectionState: BleConnectionState) {
                    runOnUiThread { handle(connectionState) }
                }
            }
        )
    }

    private fun handle(connectionState: BleConnectionState) {
        viewModel = viewModel.copy(
            connectionStatus = when (connectionState) {
                is BleConnectionState.Disconnected -> ConnectionStatus.Disconnected
                is BleConnectionState.Connecting -> ConnectionStatus.Connecting
                is BleConnectionState.Connected -> ConnectionStatus.Connected
                is BleConnectionState.Failed -> ConnectionStatus.Failed
            }
        )

        if (connectionState is BleConnectionState.Connected) {
            val connectedDevice = ConnectedRowerDataBleDevice(connectionState.device)
            rowerDataCancellable = connectedDevice.rowerData { rowerData ->
                runOnUiThread {
                    val newRowerData = viewModel.rowerData?.with(rowerData) ?: rowerData
                    viewModel = viewModel.copy(rowerData = newRowerData)
                }
            }
            batteryLevelCancellable = connectedDevice.batteryLevel { batteryLevel ->
                runOnUiThread {
                    viewModel = viewModel.copy(batteryLevel = batteryLevel)
                }
            }
        } else {
            rowerDataCancellable = null
            batteryLevelCancellable = null
            viewModel = viewModel.copy(rowerData = null)
        }
    }

    override fun onPause() {
        connectionStateListenerCancellable = null
        rowerDataCancellable = null
        batteryLevelCancellable = null
        connection.disconnect()

        super.onPause()
    }

    companion object {

        private var Intent.deviceName: String
            get() {
                return getStringExtra("device_name")!!
            }
            set(value) {
                putExtra("device_name", value)
            }

        private var Intent.deviceAddress: String
            get() {
                return getStringExtra("device_address")!!
            }
            set(value) {
                putExtra("device_address", value)
            }

        fun intent(context: Context, device: Device): Intent {
            val intent = Intent(context, DeviceDetailsActivity::class.java)
            intent.deviceName = device.name
            intent.deviceAddress = device.address
            return intent
        }
    }
}
