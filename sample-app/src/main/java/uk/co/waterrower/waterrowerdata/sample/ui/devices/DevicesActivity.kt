package uk.co.waterrower.waterrowerdata.sample.ui.devices

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.mutableStateOf
import androidx.ui.core.setContent
import uk.co.waterrower.waterrowerdata.sample.ui.theming.AppTheme

class DevicesActivity : AppCompatActivity() {

    private var viewModel = DevicesViewModel(
        devices = (1..200).map { Device("Device $it") }
    )
        set(value) {
            field = value
            state.value = value
        }

    private val state = mutableStateOf(viewModel)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                DevicesView(
                    state,
                    onDeviceClick = { device ->
                        Toast.makeText(this, "Clicked ${device.name}", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }
}
