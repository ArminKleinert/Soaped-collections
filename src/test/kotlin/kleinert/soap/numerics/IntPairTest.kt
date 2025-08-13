package kleinert.soap.numerics

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class IntPairTest {
    @Test
    fun createFromLong() {
        run {
            val ip = IntPair(0L)
            Assertions.assertEquals(0, ip.first)
            Assertions.assertEquals(0, ip.second)
        }
        run {
            val ip = IntPair(1L)
            Assertions.assertEquals(0, ip.first)
            Assertions.assertEquals(1, ip.second)
        }
        run {
            val ip = IntPair(1L shl 32)
            Assertions.assertEquals(1, ip.first)
            Assertions.assertEquals(0, ip.second)
        }
        run {
            val ip = IntPair((1L shl 32) + 2L)
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
    }

    @Test
    fun equals() {
        Assertions.assertEquals(IntPair.makeIntPair(0, 0), IntPair.makeIntPair(0, 0))
        Assertions.assertEquals(IntPair.makeIntPair(1, 0), IntPair.makeIntPair(1, 0))
        Assertions.assertEquals(IntPair.makeIntPair(0, 1), IntPair.makeIntPair(0, 1))
        Assertions.assertEquals(IntPair.makeIntPair(1, 2), IntPair.makeIntPair(1, 2))

        Assertions.assertEquals(IntPair(0L), IntPair.makeIntPair(0, 0))
        Assertions.assertEquals(IntPair.makeIntPair(1, 0), IntPair.makeIntPair(1, 0))
        Assertions.assertEquals(IntPair(1L), IntPair.makeIntPair(0, 1))
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
            val (f, s) = IntPair((1L shl 32) + 2) + IntPair((1L shl 33) + 9)
            Assertions.assertEquals(3, f)
            Assertions.assertEquals(11, s)
        }
        run {
            val (f, s) = IntPair((1L shl 33) + 9) + IntPair((1L shl 32) + 2)
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
            val (f, s) = IntPair((1L shl 32) + 2L) - IntPair((2L shl 32) + 9L)
            Assertions.assertEquals(-1, f)
            Assertions.assertEquals(-7, s)
        }
        run {
            val (f, s) = IntPair((1L shl 33) + 9) - IntPair((1L shl 32) + 2)
            Assertions.assertEquals(1, f)
            Assertions.assertEquals(7, s)
        }
    }
}