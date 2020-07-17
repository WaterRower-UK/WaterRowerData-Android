package uk.co.waterrower.waterrowerdata.ble

import com.nhaarman.expect.expect
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import uk.co.waterrower.waterrowerdata.ble.testutil.CharacteristicData
import uk.co.waterrower.waterrowerdata.ble.testutil.RowerDataCharacteristicFlags

class RowerDataCharacteristicTest {

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
        fun `averageStrokeRate present results in uint24 value for medium value`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(totalDistancePresent = true)
            val data = CharacteristicData.create(flags, 1, 2, 0) // 1 + 512

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.totalDistanceMeters).toBe(513)
        }

        @Test
        fun `averageStrokeRate present results in uint24 value for high value`() {
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
        fun `instantaneousPace present results in uint16 value for low value`() {
            /* Given */
            val flags = RowerDataCharacteristicFlags.create(instantaneousPacePresent = true)
            val data = CharacteristicData.create(flags, 1, 0)

            /* When */
            val result = RowerDataCharacteristic.decode(data)

            /* Then */
            expect(result.instantaneousPaceSeconds).toBe(1)
        }

        @Test
        fun `instantaneousPace present results in uint16 value for high value`() {
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
    }
}
