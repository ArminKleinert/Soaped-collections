package kleinert.soap.numerics

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class IntPairTest {
    @Test
    fun createFromLong() {
        run {
            val ip = IntPair(0uL)
            Assertions.assertEquals(0, ip.first)
            Assertions.assertEquals(0, ip.second)
        }
        run {
            val ip = IntPair(1uL)
            Assertions.assertEquals(0, ip.first)
            Assertions.assertEquals(1, ip.second)
        }
        run {
            val ip = IntPair(1uL shl 32)
            Assertions.assertEquals(1, ip.first)
            Assertions.assertEquals(0, ip.second)
        }
        run {
            val ip = IntPair((1uL shl 32) + 2uL)
            Assertions.assertEquals(1, ip.first)
            Assertions.assertEquals(2, ip.second)
        }
    }

    @Test
    fun createFromInts() {
        run {
            val ip = IntPair.makeIntPair(0, 0)
            Assertions.assertEquals(0, ip.first)
            Assertions.assertEquals(0, ip.second)
        }
        run {
            val ip = IntPair.makeIntPair(1, 0)
            Assertions.assertEquals(1, ip.first)
            Assertions.assertEquals(0, ip.second)
        }
        run {
            val ip = IntPair.makeIntPair(0, 2)
            Assertions.assertEquals(0, ip.first)
            Assertions.assertEquals(2, ip.second)
        }
        run {
            val ip = IntPair.makeIntPair(1, 2)
            Assertions.assertEquals(1, ip.first)
            Assertions.assertEquals(2, ip.second)
        }
        run {
            val ip = IntPair.makeIntPair(-1, -2)
            Assertions.assertEquals(-1, ip.first)
            Assertions.assertEquals(-2, ip.second)
        }
    }

    @Test
    fun createFromInts2() {
        run {
            val ip = IntPair(0, 0)
            Assertions.assertEquals(0, ip.first)
            Assertions.assertEquals(0, ip.second)
        }
        run {
            val ip = IntPair(1, 0)
            Assertions.assertEquals(1, ip.first)
            Assertions.assertEquals(0, ip.second)
        }
        run {
            val ip = IntPair(0, 2)
            Assertions.assertEquals(0, ip.first)
            Assertions.assertEquals(2, ip.second)
        }
        run {
            val ip = IntPair(1, 2)
            Assertions.assertEquals(1, ip.first)
            Assertions.assertEquals(2, ip.second)
        }
        run {
            val ip = IntPair(-1, -2)
            Assertions.assertEquals(-1, ip.first)
            Assertions.assertEquals(-2, ip.second)
        }
        run {
            val ip = IntPair(-1, 1)
            Assertions.assertEquals(-1, ip.first)
            Assertions.assertEquals(1, ip.second)
        }
        run {
            val ip = IntPair(1, -1)
            Assertions.assertEquals(1, ip.first)
            Assertions.assertEquals(-1, ip.second)
        }
    }

    @Test
    fun equals() {
        Assertions.assertEquals(IntPair.makeIntPair(0, 0), IntPair.makeIntPair(0, 0))
        Assertions.assertEquals(IntPair.makeIntPair(1, 0), IntPair.makeIntPair(1, 0))
        Assertions.assertEquals(IntPair.makeIntPair(0, 1), IntPair.makeIntPair(0, 1))
        Assertions.assertEquals(IntPair.makeIntPair(1, 2), IntPair.makeIntPair(1, 2))

        Assertions.assertEquals(IntPair(0uL), IntPair.makeIntPair(0, 0))
        Assertions.assertEquals(IntPair.makeIntPair(1, 0), IntPair.makeIntPair(1, 0))
        Assertions.assertEquals(IntPair(1uL), IntPair.makeIntPair(0, 1))
        Assertions.assertEquals(IntPair.makeIntPair(1, 2), IntPair.makeIntPair(1, 2))
    }

    @Test
    fun unpack() {
        val (f, s) = IntPair.makeIntPair(1, 2)
        Assertions.assertEquals(1, f)
        Assertions.assertEquals(2, s)
    }

    @Test
    fun plus() {
        run {
            val (f, s) = IntPair.makeIntPair(1, 2) + IntPair.makeIntPair(4, 5)
            Assertions.assertEquals(5, f)
            Assertions.assertEquals(7, s)
        }
        run {
            val (f, s) = IntPair((1uL shl 32) + 2u) + IntPair((1uL shl 33) + 9u)
            Assertions.assertEquals(3, f)
            Assertions.assertEquals(11, s)
        }
        run {
            val (f, s) = IntPair((1uL shl 33) + 9u) + IntPair((1uL shl 32) + 2u)
            Assertions.assertEquals(3, f)
            Assertions.assertEquals(11, s)
        }
    }

    @Test
    fun minus() {
        fun f1(x: Int): String {
            val bitPattern = x.toUInt().toString(2)
            return bitPattern.padStart(Int.SIZE_BITS, '0')
        }
        run {
            val (f, s) = IntPair.makeIntPair(1, 2) - IntPair.makeIntPair(4, 1)
            Assertions.assertEquals(-3, f)
            Assertions.assertEquals(1, s)
        }
        run {
            val (f, s) = IntPair((1uL shl 32) + 2uL) - IntPair((2uL shl 32) + 9uL)
            Assertions.assertEquals(-1, f)
            Assertions.assertEquals(-7, s)
        }
        run {
            val (f, s) = IntPair((1uL shl 33) + 9u) - IntPair((1uL shl 32) + 2u)
            Assertions.assertEquals(1, f)
            Assertions.assertEquals(7, s)
        }
    }
}