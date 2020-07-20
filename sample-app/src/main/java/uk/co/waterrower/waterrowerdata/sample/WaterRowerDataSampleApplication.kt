package uk.co.waterrower.waterrowerdata.sample

import android.app.Application
import android.content.Context

val Context.waterRowerDataSampleApplication: WaterRowerDataSampleApplication
    get() = (applicationContext as WaterRowerDataSampleApplication)

class WaterRowerDataSampleApplication : Application() {

    val appComponent by lazy { AppComponent() }
}
