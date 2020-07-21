package uk.co.waterrower.waterrowerdata.sample.ui.devicedetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.MutableState
import androidx.compose.mutableStateOf
import androidx.ui.core.setContent
import uk.co.waterrower.waterrowerdata.sample.bluetooth.connection.BleConnection
import uk.co.waterrower.waterrowerdata.sample.bluetooth.connection.BleConnectionFactory
import uk.co.waterrower.waterrowerdata.sample.bluetooth.connection.BleConnectionState
import uk.co.waterrower.waterrowerdata.sample.ui.devices.Device
import uk.co.waterrower.waterrowerdata.sample.ui.theming.AppTheme
import uk.co.waterrower.waterrowerdata.sample.util.Cancellable

class DeviceDetailsActivity : AppCompatActivity() {

    private val device: Device
        get() {
            return Device(
                intent.deviceAddress,
                intent.deviceName
            )
        }

    private val state: MutableState<DeviceDetailsViewModel> by lazy {
        mutableStateOf(
            DeviceDetailsViewModel(
                deviceName = device.name,
                connectionStatus = ConnectionStatus.Disconnected
            )
        )
    }

    private val connection by lazy {
        BleConnectionFactory.from(this)
            .createConnection(device.address)
    }

    private var connectionStateListenerCancellable: Cancellable? = null
        set(value) {
            field?.cancel()
            field = value
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                DeviceDetailsView(
                    state,
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
                    runOnUiThread {
                        state.value = state.value.copy(
                            connectionStatus = when (connectionState) {
                                is BleConnectionState.Disconnected -> ConnectionStatus.Disconnected
                                is BleConnectionState.Connecting -> ConnectionStatus.Connecting
                                is BleConnectionState.Connected -> ConnectionStatus.Connected
                                is BleConnectionState.Failed -> ConnectionStatus.Failed
                            }
                        )
                    }
                }
            }
        )
    }

    override fun onPause() {
        connectionStateListenerCancellable = null
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
