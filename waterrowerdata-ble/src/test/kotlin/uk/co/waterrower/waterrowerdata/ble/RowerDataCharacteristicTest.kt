package uk.co.waterrower.waterrowerdata.ble

import com.nhaarman.expect.expect
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import uk.co.waterrower.waterrowerdata.ble.testutil.CharacteristicData
import uk.co.waterrower.waterrowerdata.ble.testutil.RowerDataCharacteristicFlags

class RowerDataCharacteristicTest {

    @Nested
    inner class `Average Stroke Rate`() {

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
}
