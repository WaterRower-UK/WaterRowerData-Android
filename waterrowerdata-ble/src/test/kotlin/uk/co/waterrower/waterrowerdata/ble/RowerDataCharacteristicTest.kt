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
}
