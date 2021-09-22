package uk.co.waterrower.waterrowerdata.ble

/**
 * A data class that contains decoded Rower Data.
 */
data class RowerData(

    /**
     * The instantaneous stroke rate in strokes/minute.
     */
    val strokeRate: Double?,

    /**
     * The total number of strokes since the beginning of the training session.
     */
    val strokeCount: Int?,

    /**
     * The average stroke rate since the beginning of the training session, in
     * strokes/minute.
     */
    val averageStrokeRate: Double?,

    /**
     * The total distance since the beginning of the training session, in meters.
     */
    val totalDistanceMeters: Int?,

    /**
     * The value of the pace (time per 500 meters) of the user while exercising,
     * in seconds.
     */
    val instantaneousPaceSeconds: Int?,

    /**
     * The value of the average pace (time per 500 meters) since the beginning
     * of the training session, in seconds.
     */
    val averagePaceSeconds: Int?,

    /**
     * The value of the instantaneous power in Watts.
     */
    val instantaneousPowerWatts: Int?,

    /**
     * The value of the average power since the beginning of the training
     * session, in Watts.
     */
    val averagePowerWatts: Int?,

    /**
     * The current value of the resistance level.
     */
    val resistanceLevel: Int?,

    /**
     * The total expended energy of a user since the training session has
     * started, in Kilocalories.
     */
    val totalEnergyKiloCalories: Int?,

    /**
     * The average expended energy of a user during the period of one hour, in
     * Kilocalories.
     */
    val energyPerHourKiloCalories: Int?,

    /**
     * The average expended energy of a user during the period of one minute, in
     * Kilocalories.
     */
    val energyPerMinuteKiloCalories: Int?,

    /**
     * The current heart rate value of the user, in beats per minute.
     */
    val heartRate: Int?,

    /**
     * The metabolic equivalent of the user.
     */
    val metabolicEquivalent: Double?,

    /**
     * The elapsed time of a training session since the training sessino has
     * started, in seconds.
     */
    val elapsedTimeSeconds: Int?,

    /**
     * The remaining time of a selected training session, in seconds.
     */
    val remainingTimeSeconds: Int?
) {

    fun with(rowerData: RowerData): RowerData {
        return RowerData(
            strokeRate = rowerData.strokeRate ?: strokeRate,
            strokeCount = rowerData.strokeCount ?: strokeCount,
            averageStrokeRate = rowerData.averageStrokeRate ?: averageStrokeRate,
            totalDistanceMeters = rowerData.totalDistanceMeters ?: totalDistanceMeters,
            instantaneousPaceSeconds = rowerData.instantaneousPaceSeconds ?: instantaneousPaceSeconds,
            averagePaceSeconds = rowerData.averagePaceSeconds ?: averagePaceSeconds,
            instantaneousPowerWatts = rowerData.instantaneousPowerWatts ?: instantaneousPowerWatts,
            averagePowerWatts = rowerData.averagePowerWatts ?: averagePowerWatts,
            resistanceLevel = rowerData.resistanceLevel ?: resistanceLevel,
            totalEnergyKiloCalories = rowerData.totalEnergyKiloCalories ?: totalEnergyKiloCalories,
            energyPerHourKiloCalories = rowerData.energyPerHourKiloCalories ?: energyPerHourKiloCalories,
            energyPerMinuteKiloCalories = rowerData.energyPerMinuteKiloCalories ?: energyPerMinuteKiloCalories,
            heartRate = rowerData.heartRate ?: heartRate,
            metabolicEquivalent = rowerData.metabolicEquivalent ?: metabolicEquivalent,
            elapsedTimeSeconds = rowerData.elapsedTimeSeconds ?: elapsedTimeSeconds,
            remainingTimeSeconds = rowerData.remainingTimeSeconds ?: remainingTimeSeconds,
        )
    }
}
