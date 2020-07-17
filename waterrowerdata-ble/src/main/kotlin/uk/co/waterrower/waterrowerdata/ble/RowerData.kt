package uk.co.waterrower.waterrowerdata.ble

data class RowerData(
    val strokeRate: Double?,
    val strokeCount: Int?,
    val averageStrokeRate: Double?,
    val totalDistanceMeters: Int?,
    val instantaneousPaceSeconds: Int?,
    val averagePaceSeconds: Int?,
    val instantaneousPowerWatts: Int?,
    val averagePowerWatts: Int?,
    val resistanceLevel: Int?,
    val totalEnergyKiloCalories: Int?,
    val energyPerHourKiloCalories: Int?,
    val energyPerMinuteKiloCalories: Int?,
    val heartRate: Int?,
    val metabolicEquivalent: Double?,
    val elapsedTimeSeconds: Int?,
    val remainingTimeSeconds: Int?
)
