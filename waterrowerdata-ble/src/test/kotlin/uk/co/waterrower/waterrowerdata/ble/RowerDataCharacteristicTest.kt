package uk.co.waterrower.waterrowerdata.ble

import com.nhaarman.expect.expect
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import uk.co.waterrower.waterrowerdata.ble.testutil.CharacteristicData
import uk.co.waterrower.waterrowerdata.ble.testutil.RowerDataCharacteristicFlags

class RowerDataCharacteristicTest {

    @Nested
    inner class `Stroke Rate` {

        @Test
        fun `strokeRate not present results in null value`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(moreDataPresent = false)
            val data = CharacteristicData.create(flags)

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.strokeRate).toBeNull()
        }

        @Test
        fun `strokeRate present results in uint8 value withBinaryExponentMinusOne`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(moreDataPresent = true)
            val data = CharacteristicData.create(
                flags,
                7, // Stroke rate of 3.5
                0, // Stroke count value
                0
            )

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.strokeRate).toBe(3.5)
        }
    }

    @Nested
    inner class `Stroke Count` {

        @Test
        fun `strokeCount not present results in null value`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(moreDataPresent = false)
            val data = CharacteristicData.create(flags)

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.strokeCount).toBeNull()
        }

        @Test
        fun `strokeCount present results in uint16 value lowValue`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(moreDataPresent = true)
            val data = CharacteristicData.create(
                flags,
                0, // Stroke Rate
                1, // Stroke count of 1
                0
            )

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.strokeCount).toBe(1)
        }

        @Test
        fun `strokeCount present results in uint16 value highValue`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(moreDataPresent = true)
            val data = CharacteristicData.create(
                flags,
                0, // Stroke Rate
                1, // Stroke count of 1 + 256
                1
            )

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.strokeCount).toBe(257)
        }
    }

    @Nested
    inner class `Average Stroke Rate` {

        @Test
        fun `averageStrokeRate not present results in null value`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(averageStrokeRatePresent = false)
            val data = CharacteristicData.create(flags)

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.averageStrokeRate).toBeNull()
        }

        @Test
        fun `averageStrokeRate present results in uint8 value with binary exponent minus one`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(averageStrokeRatePresent = true)
            val data = CharacteristicData.create(flags, 7)

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.averageStrokeRate).toBe(3.5)
        }
    }

    @Nested
    inner class `Total Distance` {

        @Test
        fun `totalDistance not present results in null value`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(totalDistancePresent = false)
            val data = CharacteristicData.create(flags)

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.totalDistanceMeters).toBeNull()
        }

        @Test
        fun `averageStrokeRate present results in uint24 value for low value`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(totalDistancePresent = true)
            val data = CharacteristicData.create(flags, 1, 0, 0)

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.totalDistanceMeters).toBe(1)
        }

        @Test
        fun `totalDistance present results in uint24 value for medium value`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(totalDistancePresent = true)
            val data = CharacteristicData.create(flags, 1, 2, 0) // 1 + 512

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.totalDistanceMeters).toBe(513)
        }

        @Test
        fun `totalDistance present results in uint24 value for high value`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(totalDistancePresent = true)
            val data = CharacteristicData.create(flags, 1, 2, 4) // 1 + 512 + 262144

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.totalDistanceMeters).toBe(262657)
        }
    }

    @Nested
    inner class `Instantaneous Pace` {

        @Test
        fun `instantaneousPace not present results in null value`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(instantaneousPacePresent = false)
            val data = CharacteristicData.create(flags)

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.instantaneousPaceSeconds).toBeNull()
        }

        @Test
        fun `instantaneousPace not present results in uint16 value for low value`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(instantaneousPacePresent = true)
            val data = CharacteristicData.create(flags, 1, 0)

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.instantaneousPaceSeconds).toBe(1)
        }

        @Test
        fun `instantaneousPace not present results in uint16 value for high value`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(instantaneousPacePresent = true)
            val data = CharacteristicData.create(flags, 1, 2) // 1 + 512

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.instantaneousPaceSeconds).toBe(513)
        }
    }

    @Nested
    inner class `Average Pace` {

        @Test
        fun `averagePace not present results in null value`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(averagePacePresent = false)
            val data = CharacteristicData.create(flags)

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.averagePaceSeconds).toBeNull()
        }

        @Test
        fun `averagePace present results in uint16 value for low value`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(averagePacePresent = true)
            val data = CharacteristicData.create(flags, 1, 0)

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.averagePaceSeconds).toBe(1)
        }

        @Test
        fun `averagePace present results in uint16 value for high value`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(averagePacePresent = true)
            val data = CharacteristicData.create(flags, 1, 2) // 1 + 512

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.averagePaceSeconds).toBe(513)
        }
    }

    @Nested
    inner class `Instantaneous Power` {

        @Test
        fun `instantaneousPower not present results in null value`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(instantaneousPowerPresent = false)
            val data = CharacteristicData.create(flags)

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.instantaneousPowerWatts).toBeNull()
        }

        @Test
        fun `instantaneousPower present results in sint16Value for low value`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(instantaneousPowerPresent = true)
            val data = CharacteristicData.create(flags, 1, 0)

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.instantaneousPowerWatts).toBe(1)
        }

        @Test
        fun `instantaneousPower present results in sint16Value for high value`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(instantaneousPowerPresent = true)
            val data = CharacteristicData.create(flags, 1, 2) // 1 + 512

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.instantaneousPowerWatts).toBe(513)
        }

        @Test
        fun `instantaneousPower present results in sint16Value forLowNegativeValue`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(instantaneousPowerPresent = true)
            val data = CharacteristicData.create(flags, 0b11111111.toByte(), 0b11111111.toByte())

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.instantaneousPowerWatts).toBe(-1)
        }

        @Test
        fun `instantaneousPower present results in sint16Value forHighNegativeValue`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(instantaneousPowerPresent = true)
            val data = CharacteristicData.create(flags, 0b11111111.toByte(), 0b11111110.toByte())

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.instantaneousPowerWatts).toBe(-257)
        }
    }

    @Nested
    inner class `Average Power` {

        @Test
        fun `averagePower not present results in null value`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(averagePowerPresent = false)
            val data = CharacteristicData.create(flags)

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.averagePowerWatts).toBeNull()
        }

        @Test
        fun `averagePower present results in sint16Value for low value`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(averagePowerPresent = true)
            val data = CharacteristicData.create(flags, 1, 0)

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.averagePowerWatts).toBe(1)
        }

        @Test
        fun `averagePower present results in sint16Value for high value`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(averagePowerPresent = true)
            val data = CharacteristicData.create(flags, 1, 2) // 1 + 512

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.averagePowerWatts).toBe(513)
        }

        @Test
        fun `averagePower present results in sint16Value forLowNegativeValue`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(averagePowerPresent = true)
            val data = CharacteristicData.create(flags, 0b11111111.toByte(), 0b11111111.toByte())

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.averagePowerWatts).toBe(-1)
        }

        @Test
        fun `averagePower present results in sint16Value forHighNegativeValue`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(averagePowerPresent = true)
            val data = CharacteristicData.create(flags, 0b11111111.toByte(), 0b11111110.toByte())

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.averagePowerWatts).toBe(-257)
        }
    }

    @Nested
    inner class `Resistance level` {

        @Test
        fun `resistanceLevel not present results in null value`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(resistanceLevelPresent = false)
            val data = CharacteristicData.create(flags)

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.resistanceLevel).toBeNull()
        }

        @Test
        fun `resistanceLevel present results in sint16Value for low value`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(resistanceLevelPresent = true)
            val data = CharacteristicData.create(flags, 1, 0)

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.resistanceLevel).toBe(1)
        }

        @Test
        fun `resistanceLevel present results in sint16Value for high value`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(resistanceLevelPresent = true)
            val data = CharacteristicData.create(flags, 1, 2) // 1 + 512

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.resistanceLevel).toBe(513)
        }

        @Test
        fun `resistanceLevel present results in sint16Value forLowNegativeValue`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(resistanceLevelPresent = true)
            val data = CharacteristicData.create(flags, 0b11111111.toByte(), 0b11111111.toByte())

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.resistanceLevel).toBe(-1)
        }

        @Test
        fun `resistanceLevel present results in sint16Value forHighNegativeValue`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(resistanceLevelPresent = true)
            val data = CharacteristicData.create(flags, 0b11111111.toByte(), 0b11111110.toByte())

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.resistanceLevel).toBe(-257)
        }
    }

    @Nested
    inner class `Total Energy` {

        @Test
        fun `totalEnergy not present results in null value`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(expendedEnergyPresent = false)
            val data = CharacteristicData.create(flags)

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.totalEnergyKiloCalories).toBeNull()
        }

        @Test
        fun `totalEnergy present results in uint16 value for low value`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(expendedEnergyPresent = true)
            val data = CharacteristicData.create(
                flags,
                1, // Total energy value 1
                0,
                0, // Energy per hour value
                0,
                0, // Energy per minute value
                0
            )

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.totalEnergyKiloCalories).toBe(1)
        }

        @Test
        fun `totalEnergy present results in uint16 value for high value`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(expendedEnergyPresent = true)
            val data = CharacteristicData.create(
                flags,
                1, // Total energy value 1 + 512
                2,
                0, // Energy per hour value
                0,
                0, // Energy per minute value
                0
            )

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.totalEnergyKiloCalories).toBe(513)
        }

        // See section 4.8.1.11 of the FTMS Bluetooth Service specification.
        @Test
        fun `totalEnergy present but not supported results in null value`() {
            // If this field has to be present (i.e., if the Expended Energy Present bit of the Flags field is set to 1)
            // but the Server does not support the calculation of the Total Energy, the Server shall use the special
            // value 0xFFFF (i.e., decimal value of 65535 in UINT16 format), which means ‘Data Not Available’.

            /* Given */
            val flags = RowerDataCharacteristicFlags.create(expendedEnergyPresent = true)
            val data = CharacteristicData.create(
                flags,
                0xFF.toByte(), // Total energy value 65535
                0xFF.toByte(),
                0, // Energy per hour value
                0,
                0, // Energy per minute value
                0
            )

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.totalEnergyKiloCalories).toBeNull()
        }
    }

    @Nested
    inner class `Energy Per Hour` {

        @Test
        fun `energyPerHour not present results in null value`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(expendedEnergyPresent = false)
            val data = CharacteristicData.create(flags)

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.energyPerHourKiloCalories).toBeNull()
        }

        @Test
        fun `energyPerHour present results in uint16 value for low value`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(expendedEnergyPresent = true)
            val data = CharacteristicData.create(
                flags,
                0, // Total energy value
                0,
                1, // Energy per hour value 1
                0,
                0, // Energy per minute value
                0
            )

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.energyPerHourKiloCalories).toBe(1)
        }

        @Test
        fun `energyPerHour present results in uint16 value for high value`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(expendedEnergyPresent = true)
            val data = CharacteristicData.create(
                flags,
                0, // Total energy value
                0,
                1, // Energy per hour value 1 + 512
                2,
                0, // Energy per minute value
                0
            )

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.energyPerHourKiloCalories).toBe(513)
        }

        // See section 4.8.1.12 of the FTMS Bluetooth Service specification.
        @Test
        fun `energyPerHour present but not supported results in null value`() {
            // If this field has to be present (i.e., if the Expended Energy Present bit of the Flags field is set to 1)
            // but the Server does not support the calculation of the Energy per Hour, the Server shall use the special
            // value 0xFFFF (i.e., decimal value of 65535 in UINT16 format), which means ‘Data Not Available’.
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(expendedEnergyPresent = true)
            val data = CharacteristicData.create(
                flags,
                0, // Total energy value
                0,
                0xFF.toByte(), // Energy per hour value 65535
                0xFF.toByte(),
                0, // Energy per minute value
                0
            )

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.energyPerHourKiloCalories).toBeNull()
        }
    }

    @Nested
    inner class `Energy Per Minute` {

        @Test
        fun `energyPerMinute not present results in null value`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(expendedEnergyPresent = false)
            val data = CharacteristicData.create(flags)

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.energyPerMinuteKiloCalories).toBeNull()
        }

        @Test
        fun `energyPerMinute present results in uint8 value`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(expendedEnergyPresent = true)
            val data = CharacteristicData.create(
                flags,
                0, // Total energy value
                0,
                0, // Energy per hour value
                0,
                1 // Energy per minute value 1
            )

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.energyPerMinuteKiloCalories).toBe(1)
        }

        // See section 4.8.1.13 of the FTMS Bluetooth Service specification.
        @Test
        fun `energyPerMinute present but not supported results in null value`() {
            // If this field has to be present (i.e., if the Expended Energy Present bit of the Flags field is set to 1)
            // but the Server does not support the calculation of the Energy per Minute, the Server shall use the special
            // value 0xFF (i.e., decimal value of 255 in UINT16 format), which means ‘Data Not Available’.
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(expendedEnergyPresent = true)
            val data = CharacteristicData.create(
                flags,
                0, // Total energy value
                0,
                0, // Energy per hour value
                0,
                0xFF.toByte() // Energy per minute value 255
            )

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.energyPerMinuteKiloCalories).toBeNull()
        }
    }

    @Nested
    inner class `Heart Rate` {

        @Test
        fun `heartRate not present results in null value`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(heartRatePresent = false)
            val data = CharacteristicData.create(flags)

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.heartRate).toBeNull()
        }

        @Test
        fun `heartRate present results in uint8 value`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(heartRatePresent = true)
            val data = CharacteristicData.create(
                flags,
                170.toByte() // Heart rate of 170
            )

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.heartRate).toBe(170)
        }
    }

    @Nested
    inner class `Metabolic Equivalent` {

        @Test
        fun `metabolicEquivalent not present results in null value`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(metabolicEquivalentPresent = false)
            val data = CharacteristicData.create(flags)

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.metabolicEquivalent).toBeNull()
        }

        @Test
        fun `metabolicEquivalent present results in uint8 value`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(metabolicEquivalentPresent = true)
            val data = CharacteristicData.create(
                flags,
                123 // Metabolic Equivalent of 12.3
            )

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.metabolicEquivalent).toBe(12.3)
        }
    }

    @Nested
    inner class `Elapsed Time` {

        @Test
        fun `elapsedTime not present results in null value`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(elapsedTimePresent = false)
            val data = CharacteristicData.create(flags)

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.elapsedTimeSeconds).toBeNull()
        }

        @Test
        fun `elapsedTime present results in uint16 value for low value`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(elapsedTimePresent = true)
            val data = CharacteristicData.create(
                flags,
                3,
                0
            )

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.elapsedTimeSeconds).toBe(3)
        }

        @Test
        fun `elapsedTime present results in uint16 value for high value`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(elapsedTimePresent = true)
            val data = CharacteristicData.create(
                flags,
                1, // 1 + 512
                2
            )

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.elapsedTimeSeconds).toBe(513)
        }
    }

    @Nested
    inner class `Remaining Time` {

        @Test
        fun `remainingTime not present results in null value`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(remainingTimePresent = false)
            val data = CharacteristicData.create(flags)

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.remainingTimeSeconds).toBeNull()
        }

        @Test
        fun `remainingTime present results in uint16 value for low value`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(remainingTimePresent = true)
            val data = CharacteristicData.create(
                flags,
                3,
                0
            )

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.remainingTimeSeconds).toBe(3)
        }

        @Test
        fun `remainingTime present results in uint16 value for high value`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(remainingTimePresent = true)
            val data = CharacteristicData.create(
                flags,
                1, // 1 + 512
                2
            )

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.remainingTimeSeconds).toBe(513)
        }
    }

    @Nested
    inner class `Multiple properties present` {

        @Test
        fun `multiple properties present properly offsets values for average stroke rate and total distance`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(
                averageStrokeRatePresent = true,
                totalDistancePresent = true
            )
            val data = CharacteristicData.create(
                flags,
                7, // Average stroke rate of 3.5
                16, // Total distance of 16
                0,
                0
            )

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.averageStrokeRate).toBe(3.5)
            expect(result.totalDistanceMeters).toBe(16)
        }

        @Test
        fun `multiple properties present properly offsets values for average stroke rate and instantaneous pace`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(
                averageStrokeRatePresent = true,
                instantaneousPacePresent = true
            )
            val data = CharacteristicData.create(
                flags,
                7, // Average stroke rate of 3.5
                16, // Instantaneous pace of 16
                0
            )

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.averageStrokeRate).toBe(3.5)
            expect(result.instantaneousPaceSeconds).toBe(16)
        }

        @Test
        fun `multiple properties present properly offsets values for total distance and instantaneous pace`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(
                totalDistancePresent = true,
                instantaneousPacePresent = true
            )
            val data = CharacteristicData.create(
                flags,
                32, // Total distance of 32
                0,
                0,
                16, // Instantaneous pace of 16
                0
            )

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.totalDistanceMeters).toBe(32)
            expect(result.instantaneousPaceSeconds).toBe(16)
        }

        // This is not really representative (not enough available bytes in a real world scenario),
        // but a good test to execute anyway to test dependencies.
        @Test
        fun `all properties present`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(
                moreDataPresent = true,
                averageStrokeRatePresent = true,
                totalDistancePresent = true,
                instantaneousPacePresent = true,
                averagePacePresent = true,
                instantaneousPowerPresent = true,
                averagePowerPresent = true,
                resistanceLevelPresent = true,
                expendedEnergyPresent = true,
                heartRatePresent = true,
                metabolicEquivalentPresent = true,
                elapsedTimePresent = true,
                remainingTimePresent = true
            )

            val data = CharacteristicData.create(
                flags,
                1, // Stroke rate of 0.5
                2, // Stroke count of 2
                0,
                3, // Average stroke rate of 1.5
                4, // Total distance of 4
                0,
                0,
                5, // Instantaneous pace of 5
                0,
                6, // Average pace of 6
                0,
                7, // Instantaneous power of 7,
                0,
                8, // Average power of 8
                0,
                9, // Resistance level of 9
                0,
                10, // Total energy of 10
                0,
                11, // Energy per hour of 11
                0,
                12, // Energy per minute of 12
                13, // Heart rate of 13
                14, // Metabolic equivalent of 1.4
                15, // Elapsed time of 15
                0,
                16, // Time remaining of 16
                0
            )

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.strokeRate).toBe(0.5)
            expect(result.strokeCount).toBe(2)
            expect(result.averageStrokeRate).toBe(1.5)
            expect(result.totalDistanceMeters).toBe(4)
            expect(result.instantaneousPaceSeconds).toBe(5)
            expect(result.averagePaceSeconds).toBe(6)
            expect(result.instantaneousPowerWatts).toBe(7)
            expect(result.averagePowerWatts).toBe(8)
            expect(result.resistanceLevel).toBe(9)
            expect(result.totalEnergyKiloCalories).toBe(10)
            expect(result.energyPerHourKiloCalories).toBe(11)
            expect(result.energyPerMinuteKiloCalories).toBe(12)
            expect(result.heartRate).toBe(13)
            expect(result.metabolicEquivalent).toBe(1.4)
            expect(result.elapsedTimeSeconds).toBe(15)
            expect(result.remainingTimeSeconds).toBe(16)
        }
    }
}
