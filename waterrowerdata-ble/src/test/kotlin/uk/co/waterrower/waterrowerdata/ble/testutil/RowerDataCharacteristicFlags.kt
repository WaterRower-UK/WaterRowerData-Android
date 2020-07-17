package uk.co.waterrower.waterrowerdata.ble.testutil

object RowerDataCharacteristicFlags {

    fun create(
        moreDataPresent: Boolean = false,
        averageStrokeRatePresent: Boolean = false,
        totalDistancePresent: Boolean = false,
        instantaneousPacePresent: Boolean = false,
        averagePacePresent: Boolean = false,
        instantaneousPowerPresent: Boolean = false,
        averagePowerPresent: Boolean = false,
        resistanceLevelPresent: Boolean = false,
        expendedEnergyPresent: Boolean = false,
        heartRatePresent: Boolean = false,
        metabolicEquivalentPresent: Boolean = false,
        elapsedTimePresent: Boolean = false,
        remainingTimePresent: Boolean = false
    ): ByteArray {
        val flags = mutableMapOf<Int, Boolean>()

        if (!moreDataPresent) {
            flags[0] = true
        }

        if (averageStrokeRatePresent) {
            flags[1] = true
        }

        if (totalDistancePresent) {
            flags[2] = true
        }

        if (instantaneousPacePresent) {
            flags[3] = true
        }

        if (averagePacePresent) {
            flags[4] = true
        }

        if (instantaneousPowerPresent) {
            flags[5] = true
        }

        if (averagePowerPresent) {
            flags[6] = true
        }

        if (resistanceLevelPresent) {
            flags[7] = true
        }

        if (expendedEnergyPresent) {
            flags[8] = true
        }

        if (heartRatePresent) {
            flags[9] = true
        }

        if (metabolicEquivalentPresent) {
            flags[10] = true
        }

        if (elapsedTimePresent) {
            flags[11] = true
        }

        if (remainingTimePresent) {
            flags[12] = true
        }

        return CharacteristicFlags.createFlags(flags)
    }
}
