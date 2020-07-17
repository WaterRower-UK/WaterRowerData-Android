package uk.co.waterrower.waterrowerdata.ble.testutil

import com.nhaarman.expect.expect
import org.junit.jupiter.api.Test

internal class RowerDataCharacteristicFlagsTest {

    @Test
    fun `averageStrokeRate present`() {
        /* When */
        val result = RowerDataCharacteristicFlags.create(
            averageStrokeRatePresent = true
        )

        /* Then */
        expect(result[0]).toBe("00000010".toByte(radix = 2))
    }
}
