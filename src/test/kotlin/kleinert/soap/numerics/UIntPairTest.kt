package kleinert.soap.numerics

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class UIntPairTest {
    @Test
    fun createFromLong() {
        run {
            val ip = UIntPair(0uL)
            Assertions.assertEquals(0u, ip.first)
            Assertions.assertEquals(0u, ip.second)
        }
        run {
            val ip = UIntPair(1uL)
            Assertions.assertEquals(0u, ip.first)
            Assertions.assertEquals(1u, ip.second)
        }
        run {
            val ip = UIntPair(1uL shl 32)
            Assertions.assertEquals(1u, ip.first)
            Assertions.assertEquals(0u, ip.second)
        }
        run {
            val ip = UIntPair((1uL shl 32) + 2u)
            Assertions.assertEquals(1u, ip.first)
            Assertions.assertEquals(2u, ip.second)
        }
    }

    @Test
    fun createFromInts() {
        run {
            val ip = UIntPair.makeUIntPair(0u, 0u)
            Assertions.assertEquals(0u, ip.first)
            Assertions.assertEquals(0u, ip.second)
        }
        run {
            val ip = UIntPair.makeUIntPair(1u, 0u)
            Assertions.assertEquals(1u, ip.first)
            Assertions.assertEquals(0u, ip.second)
        }
        run {
            val ip = UIntPair.makeUIntPair(0u, 2u)
            Assertions.assertEquals(0u, ip.first)
            Assertions.assertEquals(2u, ip.second)
        }
        run {
            val ip = UIntPair.makeUIntPair(1u, 2u)
            Assertions.assertEquals(1u, ip.first)
            Assertions.assertEquals(2u, ip.second)
        }
    }

    @Test
    fun createFromInts1() {
        run {
            val ip = UIntPair(0u, 0u)
            Assertions.assertEquals(0u, ip.first)
            Assertions.assertEquals(0u, ip.second)
        }
        run {
            val ip = UIntPair(1u, 0u)
            Assertions.assertEquals(1u, ip.first)
            Assertions.assertEquals(0u, ip.second)
        }
        run {
            val ip = UIntPair(0u, 2u)
            Assertions.assertEquals(0u, ip.first)
            Assertions.assertEquals(2u, ip.second)
        }
        run {
            val ip = UIntPair(1u, 2u)
            Assertions.assertEquals(1u, ip.first)
            Assertions.assertEquals(2u, ip.second)
        }
    }

    @Test
    fun equals() {
        Assertions.assertEquals(UIntPair.makeUIntPair(0u, 0u), UIntPair.makeUIntPair(0u, 0u))
        Assertions.assertEquals(UIntPair.makeUIntPair(1u, 0u), UIntPair.makeUIntPair(1u, 0u))
        Assertions.assertEquals(UIntPair.makeUIntPair(0u, 1u), UIntPair.makeUIntPair(0u, 1u))
        Assertions.assertEquals(UIntPair.makeUIntPair(1u, 2u), UIntPair.makeUIntPair(1u, 2u))

        Assertions.assertEquals(UIntPair(0uL), UIntPair.makeUIntPair(0u, 0u))
        Assertions.assertEquals(UIntPair.makeUIntPair(1u, 0u), UIntPair.makeUIntPair(1u, 0u))
        Assertions.assertEquals(UIntPair(1uL), UIntPair.makeUIntPair(0u, 1u))
        Assertions.assertEquals(UIntPair.makeUIntPair(1u, 2u), UIntPair.makeUIntPair(1u, 2u))
    }

    @Test
    fun unpack() {
        val (f, s) = UIntPair.makeUIntPair(1u, 2u)
        Assertions.assertEquals(1u, f)
        Assertions.assertEquals(2u, s)
    }

    @Test
    fun plus() {
        run {
            val (f, s) = UIntPair.makeUIntPair(1u, 2u) + UIntPair.makeUIntPair(4u, 5u)
            Assertions.assertEquals(5u, f)
            Assertions.assertEquals(7u, s)
        }
        run {
            val (f, s) = UIntPair((1uL shl 32) + 2u) + UIntPair((1uL shl 33) + 9u)
            Assertions.assertEquals(3u, f)
            Assertions.assertEquals(11u, s)
        }
        run {
            val (f, s) = UIntPair((1uL shl 33) + 9u) + UIntPair((1uL shl 32) + 2u)
            Assertions.assertEquals(3u, f)
            Assertions.assertEquals(11u, s)
        }
    }

    @Test
    fun minus() {
        fun f1(x: Int): String {
            val bitPattern = x.toUInt().toString(2)
            return bitPattern.padStart(Int.SIZE_BITS, '0')
        }
        run {
            val (f, s) = UIntPair.makeUIntPair(1u, 2u) - UIntPair.makeUIntPair(4u, 1u)
            Assertions.assertEquals((-3).toUInt(), f)
            Assertions.assertEquals(1u, s)
        }
        run {
            val (f, s) = UIntPair((1uL shl 32) + 2uL) - UIntPair((2uL shl 32) + 9uL)
            Assertions.assertEquals((-1).toUInt(), f)
            Assertions.assertEquals((-7).toUInt(), s)
        }
        run {
            val (f, s) = UIntPair((1uL shl 33) + 9u) - UIntPair((1uL shl 32) + 2u)
            Assertions.assertEquals(1u, f)
            Assertions.assertEquals(7u, s)
        }
    }
}