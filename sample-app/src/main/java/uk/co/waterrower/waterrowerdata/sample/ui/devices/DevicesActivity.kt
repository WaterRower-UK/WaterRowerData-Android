package uk.co.waterrower.waterrowerdata.sample.ui.devices

import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.mutableStateOf
import androidx.ui.core.setContent
import uk.co.waterrower.waterrowerdata.sample.ui.theming.AppTheme
import uk.co.waterrower.waterrowerdata.sample.util.Cancellable
import uk.co.waterrower.waterrowerdata.sample.waterRowerDataSampleApplication

class DevicesActivity : AppCompatActivity() {

    private var viewModel = DevicesViewModel(devices = emptyList())
        set(value) {
            field = value
            state.value = value
        }

    private val state = mutableStateOf(viewModel)

    private var scanCancellable: Cancellable? = null
        set(value) {
            field?.cancel()
            field = value
        }

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

    override fun onResume() {
        super.onResume()

        scanCancellable = waterRowerDataSampleApplication.appComponent.bleScanner
            .startScan(
                null,
                object : ScanCallback() {

                    override fun onScanResult(callbackType: Int, result: ScanResult) {
                        handleScanResult(result)
                    }
                }
            )

        if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 42)
        }
    }

    private fun handleScanResult(scanResult: ScanResult) {
        if (scanResult.device?.name == null) return

        viewModel = viewModel.withAppended(
            Device(
                id = scanResult.device.address,
                name = scanResult.device.name
            )
        )
    }

    override fun onPause() {
        scanCancellable = null
        super.onPause()
    }
}
