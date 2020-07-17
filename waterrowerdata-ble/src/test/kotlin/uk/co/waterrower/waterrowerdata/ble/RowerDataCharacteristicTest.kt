package uk.co.waterrower.waterrowerdata.ble

import com.nhaarman.expect.expect
import org.junit.jupiter.api.Test

class RowerDataCharacteristicTest {

    @Test
    fun `encoding empty data returns null`() {
        /* When */
        val result = RowerDataCharacteristic.decode(byteArrayOf())

        /* Then */
        expect(result).toBeNull()
    }
}
