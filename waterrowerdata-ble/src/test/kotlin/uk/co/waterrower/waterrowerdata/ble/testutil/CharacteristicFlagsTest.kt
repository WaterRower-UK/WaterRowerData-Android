package uk.co.waterrower.waterrowerdata.ble.testutil

import com.nhaarman.expect.expect
import org.junit.jupiter.api.Test

internal class CharacteristicFlagsTest {

    @Test
    fun `no active flags`() {
        /* When */
        val result = CharacteristicFlags.createFlags(emptyMap())

        /* Then */
        expect(result[0]).toBe("00000000".toInt(radix = 2).toByte())
        expect(result[1]).toBe("00000000".toInt(radix = 2).toByte())
    }

    @Test
    fun `active flag on 0th index`() {
        /* When */
        val result = CharacteristicFlags.createFlags(mapOf(0 to true))

        /* Then */
        expect(result[0]).toBe("00000001".toInt(radix = 2).toByte())
        expect(result[1]).toBe("00000000".toInt(radix = 2).toByte())
    }

    @Test
    fun `active flag on 7th index`() {
        /* When */
        val result = CharacteristicFlags.createFlags(mapOf(7 to true))

        /* Then */
        expect(result[0]).toBe("10000000".toInt(radix = 2).toByte())
        expect(result[1]).toBe("00000000".toInt(radix = 2).toByte())
    }

    @Test
    fun `active flag on 8th index`() {
        /* When */
        val result = CharacteristicFlags.createFlags(mapOf(8 to true))

        /* Then */
        expect(result[0]).toBe("00000000".toInt(radix = 2).toByte())
        expect(result[1]).toBe("00000001".toInt(radix = 2).toByte())
    }

    @Test
    fun `active flag on 15th index`() {
        /* When */
        val result = CharacteristicFlags.createFlags(mapOf(15 to true))

        /* Then */
        expect(result[0]).toBe("00000000".toInt(radix = 2).toByte())
        expect(result[1]).toBe("10000000".toInt(radix = 2).toByte())
    }

    @Test
    fun `all active flags`() {
        /* Given */
        val flags = mapOf(
            0 to true,
            1 to true,
            2 to true,
            3 to true,
            4 to true,
            5 to true,
            6 to true,
            7 to true,
            8 to true,
            9 to true,
            10 to true,
            11 to true,
            12 to true,
            13 to true,
            14 to true,
            15 to true
        )

        /* When */
        val result = CharacteristicFlags.createFlags(flags)

        /* Then */
        expect(result[0]).toBe("11111111".toInt(radix = 2).toByte())
        expect(result[1]).toBe("11111111".toInt(radix = 2).toByte())
    }
}
