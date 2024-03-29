package uk.co.waterrower.waterrowerdata.sample.ui.devices

import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import uk.co.waterrower.waterrowerdata.ble.FitnessMachineService
import uk.co.waterrower.waterrowerdata.sample.ui.devicedetails.DeviceDetailsActivity
import uk.co.waterrower.waterrowerdata.sample.ui.theming.AppTheme
import uk.co.waterrower.waterrowerdata.sample.util.Cancellable
import uk.co.waterrower.waterrowerdata.sample.waterRowerDataSampleApplication

class DevicesActivity : AppCompatActivity() {

    private var viewModel by mutableStateOf(
        DevicesViewModel(devices = emptyList())
    )

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
                    viewModel,
                    onDeviceClick = { device ->
                        startActivity(DeviceDetailsActivity.intent(this, device))
                    }
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()

        scanCancellable = waterRowerDataSampleApplication.appComponent.bleScanner
            .startScan(
                listOf(FitnessMachineService.uuid),
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
                address = scanResult.device.address,
                name = scanResult.device.name
            )
        )
    }

    override fun onPause() {
        scanCancellable = null
        super.onPause()
    }
}
