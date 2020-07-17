package uk.co.waterrower.waterrowerdata.ble.testutil

import com.nhaarman.expect.expect
import org.junit.jupiter.api.Test

internal class CharacteristicDataTest {

    @Test
    fun create_without_flags_or_values() {
        /* Given */
        val flags = CharacteristicFlags.createFlags(emptyMap())

        /* When */
        val result = CharacteristicData.create(flags)

        /* Then */
        expect(result.size).toBe(2)
        expect(result[0]).toBe("00000000".toByte(radix = 2))
        expect(result[1]).toBe("00000000".toByte(radix = 2))
    }

    @Test
    fun create_with_flags_without_values() {
        /* Given */
        val flags = CharacteristicFlags.createFlags(mapOf(1 to true))

        /* When */
        val result = CharacteristicData.create(flags)

        /* Then */
        expect(result.size).toBe(2)
        expect(result[0]).toBe("00000010".toByte(radix = 2))
        expect(result[1]).toBe("00000000".toByte(radix = 2))
    }

    @Test
    fun create_with_flags_with_single_value() {
        /* Given */
        val flags = CharacteristicFlags.createFlags(mapOf(1 to true))

        /* When */
        val result = CharacteristicData.create(flags, 3)

        /* Then */
        expect(result.size).toBe(3)
        expect(result[0]).toBe("00000010".toByte(radix = 2))
        expect(result[1]).toBe("00000000".toByte(radix = 2))
        expect(result[2]).toBe(3)
    }

    @Test
    fun create_with_flags_with_multiple_values() {
        /* Given */
        val flags = CharacteristicFlags.createFlags(mapOf(1 to true))

        /* When */
        val result = CharacteristicData.create(flags, 3, 5)

        /* Then */
        expect(result.size).toBe(4)
        expect(result[0]).toBe("00000010".toByte(radix = 2))
        expect(result[1]).toBe("00000000".toByte(radix = 2))
        expect(result[2]).toBe(3)
        expect(result[3]).toBe(5)
    }
}
