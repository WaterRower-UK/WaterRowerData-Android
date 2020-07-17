package uk.co.waterrower.waterrowerdata.ble.testutil

import com.nhaarman.expect.expect
import org.junit.jupiter.api.Test

internal class RowerDataCharacteristicFlagsTest {

    @Test
    fun moreData_present() {
        /* When */
        val result = RowerDataCharacteristicFlags.create(
            moreDataPresent = true
        )

        /* Then */
        expect(result[0]).toBe("00000000".toByte(radix = 2))
    }

    @Test
    fun moreData_notPresent() {
        /* When */
        val result = RowerDataCharacteristicFlags.create(
            moreDataPresent = false
        )

        /* Then */
        expect(result[0]).toBe("00000001".toByte(radix = 2))
    }

    @Test
    fun averageStrokeRate_present() {
        /* When */
        val result = RowerDataCharacteristicFlags.create(
            moreDataPresent = true,
            averageStrokeRatePresent = true
        )

        /* Then */
        expect(result[0]).toBe("00000010".toByte(radix = 2))
    }

    @Test
    fun totalDistance_present() {
        /* When */
        val result = RowerDataCharacteristicFlags.create(
            moreDataPresent = true,
            totalDistancePresent = true
        )

        /* Then */
        expect(result[0]).toBe("00000100".toByte(radix = 2))
    }

    @Test
    fun instantaneousPace_present() {
        /* When */
        val result = RowerDataCharacteristicFlags.create(
            moreDataPresent = true,
            instantaneousPacePresent = true
        )

        /* Then */
        expect(result[0]).toBe("00001000".toByte(radix = 2))
    }

    @Test
    fun averagePace_present() {
        /* When */
        val result = RowerDataCharacteristicFlags.create(
            moreDataPresent = true,
            averagePacePresent = true
        )

        /* Then */
        expect(result[0]).toBe("00010000".toByte(radix = 2))
    }

    @Test
    fun instantaneousPower_present() {
        /* When */
        val result = RowerDataCharacteristicFlags.create(
            moreDataPresent = true,
            instantaneousPowerPresent = true
        )

        /* Then */
        expect(result[0]).toBe("00100000".toByte(radix = 2))
    }

    @Test
    fun averagePower_present() {
        /* When */
        val result = RowerDataCharacteristicFlags.create(
            moreDataPresent = true,
            averagePowerPresent = true
        )

        /* Then */
        expect(result[0]).toBe("01000000".toByte(radix = 2))
    }

    @Test
    fun resistanceLevel_present() {
        /* When */
        val result = RowerDataCharacteristicFlags.create(
            moreDataPresent = true,
            resistanceLevelPresent = true
        )

        /* Then */
        expect(result[0]).toBe("10000000".toInt(radix = 2).toByte())
    }

    @Test
    fun expendedEnergy_present() {
        /* When */
        val result = RowerDataCharacteristicFlags.create(
            expendedEnergyPresent = true
        )

        /* Then */
        expect(result[1]).toBe("00000001".toByte(radix = 2))
    }

    @Test
    fun heartRate_present() {
        /* When */
        val result = RowerDataCharacteristicFlags.create(
            heartRatePresent = true
        )

        /* Then */
        expect(result[1]).toBe("00000010".toByte(radix = 2))
    }

    @Test
    fun metabolicEquivalent_present() {
        /* When */
        val result = RowerDataCharacteristicFlags.create(
            metabolicEquivalentPresent = true
        )

        /* Then */
        expect(result[1]).toBe("00000100".toByte(radix = 2))
    }

    @Test
    fun elapsedTime_present() {
        /* When */
        val result = RowerDataCharacteristicFlags.create(
            elapsedTimePresent = true
        )

        /* Then */
        expect(result[1]).toBe("00001000".toByte(radix = 2))
    }

    @Test
    fun remainingTime_present() {
        /* When */
        val result = RowerDataCharacteristicFlags.create(
            remainingTimePresent = true
        )

        /* Then */
        expect(result[1]).toBe("00010000".toByte(radix = 2))
    }
}
