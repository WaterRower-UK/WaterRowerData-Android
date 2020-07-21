package uk.co.waterrower.waterrowerdata.sample.ui.devicedetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.ui.core.setContent
import uk.co.waterrower.waterrowerdata.sample.ui.devices.Device
import uk.co.waterrower.waterrowerdata.sample.ui.theming.AppTheme

class DeviceDetailsActivity : AppCompatActivity() {

    private val device: Device
        get() {
            return Device(
                intent.deviceAddress,
                intent.deviceName
            )
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                DeviceDetailsView(
                    deviceName = device.name,
                    connectionStatus = ConnectionStatus.Disconnected
                )
            }
        }
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
