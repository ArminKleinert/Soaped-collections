package kleinert.soap.numerics

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Int128Test {

    @Test
    fun compareTo() {
        Assertions.assertTrue(Int128.ONE > Int128.ZERO)
        Assertions.assertTrue(Int128.MAX_VALUE > Int128.ZERO)
        Assertions.assertTrue(Int128.MAX_VALUE > Int128.MIN_VALUE)

        Assertions.assertTrue(Int128.ONE < Int128.ZERO)
        Assertions.assertTrue(Int128.MAX_VALUE < Int128.ZERO)
        Assertions.assertTrue(Int128.MAX_VALUE < Int128.MIN_VALUE)
    }

    @Test
    fun equals() {
        Assertions.assertTrue(Int128.ZERO == Int128.valueOf(0uL, 0uL))
        Assertions.assertTrue(Int128.ONE == Int128.valueOf(0uL, 1uL))
        Assertions.assertTrue(Int128.ONE != Int128.valueOf(0uL, 0uL))
        Assertions.assertTrue(Int128.MAX_VALUE == Int128(Long.MAX_VALUE.toULong(), ULong.MAX_VALUE))
        Assertions.assertTrue(Int128.MIN_VALUE == Int128(Long.MIN_VALUE.toULong(), 0uL))
    }

    @Test
    fun isNegative() {
        Assertions.assertFalse(Int128.ZERO.isNegative())
        Assertions.assertFalse(Int128.ONE.isNegative())
        Assertions.assertFalse(Int128.MAX_VALUE.isNegative())
        Assertions.assertTrue(Int128.MIN_VALUE.isNegative())
    }

    @Test
    fun isPositive() {
        Assertions.assertTrue(Int128.ZERO.isPositive())
        Assertions.assertTrue(Int128.ONE.isPositive())
        Assertions.assertTrue(Int128.MAX_VALUE.isPositive())
        Assertions.assertFalse(Int128.MIN_VALUE.isPositive())
    }

    @Test
    fun isZero() {
        Assertions.assertTrue(Int128.ZERO.isZero())
        Assertions.assertFalse(Int128.ONE.isZero())
        Assertions.assertFalse(Int128.MAX_VALUE.isZero())
        Assertions.assertFalse(Int128.MIN_VALUE.isZero())
    }

    @Test
    fun and() {
        Assertions.assertEquals(Int128.ZERO, Int128.ZERO and Int128.ONE)
        Assertions.assertEquals(Int128.ZERO, Int128.ZERO and Int128.ZERO)
        Assertions.assertEquals(Int128.ZERO, Int128.MAX_VALUE and Int128.ZERO)
        Assertions.assertEquals(Int128.ONE, Int128.ONE and Int128.ONE)
        Assertions.assertEquals(Int128.MAX_VALUE, Int128.MAX_VALUE and Int128.MAX_VALUE)
    }

    @Test
    fun or() {
        Assertions.assertEquals(Int128.ONE, Int128.ZERO or Int128.ONE)
        Assertions.assertEquals(Int128.ZERO, Int128.ZERO or Int128.ZERO)
        Assertions.assertEquals(Int128.MAX_VALUE, Int128.MAX_VALUE or Int128.ZERO)
        Assertions.assertEquals(Int128.ONE, Int128.ONE or Int128.ONE)
        Assertions.assertEquals(Int128.MAX_VALUE, Int128.MAX_VALUE or Int128.MAX_VALUE)
    }

    @Test
    fun xor() {
        TODO()
    }

    @Test
    operator fun not() {
        TODO()
    }

    @Test
    fun shiftRight() {
        TODO()
    }

    @Test
    fun shiftLeft() {
        TODO()
    }

    @Test
    fun shiftRightUnsigned() {
        TODO()
    }

    @Test
    fun numberOfLeadingZeros() {
        Assertions.assertEquals(128, Int128.ZERO.numberOfLeadingZeros())
        Assertions.assertEquals(127, Int128.ONE.numberOfLeadingZeros())
        Assertions.assertEquals(1, Int128.MAX_VALUE.numberOfLeadingZeros())
        Assertions.assertEquals(0, Int128.MIN_VALUE.numberOfLeadingZeros())

        Assertions.assertEquals(126, Int128.valueOf(2L).numberOfLeadingZeros())
    }

    @Test
    fun numberOfTrailingZeros() {
        Assertions.assertEquals(128, Int128.ZERO.numberOfTrailingZeros())
        Assertions.assertEquals(0, Int128.ONE.numberOfTrailingZeros())
        Assertions.assertEquals(0, Int128.MAX_VALUE.numberOfTrailingZeros())
        Assertions.assertEquals(127, Int128.MIN_VALUE.numberOfTrailingZeros())

        Assertions.assertEquals(1, Int128.valueOf(2L).numberOfTrailingZeros())
    }

    @Test
    fun bitCount() {
        Assertions.assertEquals(0, Int128.ZERO.countOneBits())
        Assertions.assertEquals(1, Int128.ONE.countOneBits())
        Assertions.assertEquals(127, Int128.MAX_VALUE.countOneBits())
        Assertions.assertEquals(128, Int128.MIN_VALUE.countOneBits())

        Assertions.assertEquals(1, Int128.valueOf(2L).countOneBits())
        Assertions.assertEquals(1, Int128.valueOf(3L).countOneBits())
    }

    @Test
    fun plusInt128() {
        Assertions.assertEquals(            Int128.ZERO, Int128.ZERO+Int128.ZERO)
        Assertions.assertEquals(            Int128.ONE, Int128.ZERO+Int128.ONE)
        Assertions.assertEquals(            Int128.ONE, Int128.ONE+Int128.ZERO)
        Assertions.assertEquals(            Int128(1uL, 0uL), Int128(0uL, ULong.MAX_VALUE)+Int128.ONE)
        Assertions.assertEquals(            Int128(1uL, 0uL), Int128.ONE+Int128(0uL, ULong.MAX_VALUE))
        Assertions.assertEquals(            Int128(0uL, 0uL), Int128.MAX_VALUE+Int128.ONE)
    }

    @Test
    fun testPlusLong() {
        Assertions.assertEquals(            Int128.ZERO, Int128.ZERO+0L)
        Assertions.assertEquals(            Int128.ONE, Int128.ZERO+1L)
        Assertions.assertEquals(            Int128.ONE, Int128.ONE+0L)
        Assertions.assertEquals(            Int128(1uL, 0uL), Int128(0uL, ULong.MAX_VALUE)+1L)
        Assertions.assertEquals(            Int128(0uL, 0uL), Int128.MAX_VALUE+1L)
    }

    @Test
    fun testPlusInt() {
        Assertions.assertEquals(            Int128.ZERO, Int128.ZERO+0)
        Assertions.assertEquals(            Int128.ONE, Int128.ZERO+1)
        Assertions.assertEquals(            Int128.ONE, Int128.ONE+0)
        Assertions.assertEquals(            Int128(1uL, 0uL), Int128(0uL, ULong.MAX_VALUE)+1)
        Assertions.assertEquals(            Int128(0uL, 0uL), Int128.MAX_VALUE+1u)
    }

    @Test
    fun testPlusUInt() {
        Assertions.assertEquals(            Int128.ZERO, Int128.ZERO+0u)
        Assertions.assertEquals(            Int128.ONE, Int128.ZERO+1u)
        Assertions.assertEquals(            Int128.ONE, Int128.ONE+0u)
        Assertions.assertEquals(            Int128(1uL, 0uL), Int128(0uL, ULong.MAX_VALUE)+1L)
        Assertions.assertEquals(            Int128(0uL, 0uL), Int128.MAX_VALUE+1u)
    }

    @Test
    fun testPlusULong() {
        Assertions.assertEquals(            Int128.ZERO, Int128.ZERO+0L)
        Assertions.assertEquals(            Int128.ONE, Int128.ZERO+1L)
        Assertions.assertEquals(            Int128.ONE, Int128.ONE+0L)
        Assertions.assertEquals(            Int128(1uL, 0uL), Int128(0uL, ULong.MAX_VALUE)+1L)
        Assertions.assertEquals(            Int128(1uL, 0uL), Int128.ONE+ULong.MAX_VALUE)
        Assertions.assertEquals(            Int128(0uL, 0uL), Int128.MAX_VALUE+1uL)
    }

    @Test
    fun testPlusBigInt() {
        TODO()
    }

    @Test
    fun minusInt128() {
        TODO()
    }

    @Test
    fun minusLong() {
        TODO()
    }

    @Test
    fun minusInt() {
        TODO()
    }

    @Test
    fun minusBigInt() {
        TODO()
    }

    @Test
    fun timesInt128() {
        TODO()
    }

    @Test
    fun timesLong() {
        TODO()
    }

    @Test
    fun timesInt() {
        TODO()
    }

    @Test
    fun timesBigInt() {
        TODO()
    }

    @Test
    fun divInt128() {
        TODO()
    }

    @Test
    fun divLong() {
        TODO()
    }

    @Test
    fun divInt() {
        TODO()
    }

    @Test
    fun divBigInt() {
        TODO()
    }

    @Test
    fun remInt128() {
        TODO()
    }

    @Test
    fun remLong() {
        TODO()
    }

    @Test
    fun remInt() {
        TODO()
    }

    @Test
    fun remBigInt() {
        TODO()
    }

    @Test
    fun increment() {
        Assertions.assertEquals(Int128.ONE, Int128.ZERO.increment())
        Assertions.assertEquals(Int128.valueOf(2L), Int128.ONE.increment())
        Assertions.assertEquals(Int128.valueOf(1uL, 1uL), Int128.valueOf(1uL, 0uL).increment())
        Assertions.assertEquals(Int128.MIN_VALUE, Int128.MAX_VALUE.increment()) // TODO check
    }

    @Test
    fun decrement() {
        Assertions.assertEquals(Int128.ZERO, Int128.ONE.decrement())
        Assertions.assertEquals(Int128.ONE, Int128.valueOf(2L).decrement())
        Assertions.assertEquals(Int128.valueOf(1uL, 0uL), Int128.valueOf(1uL, 1uL).decrement())
        Assertions.assertEquals(Int128.MAX_VALUE, Int128.MIN_VALUE.decrement()) // TODO check
    }

    @Test
    fun toByte() {
        Assertions.assertEquals(0.toByte(), Int128.ZERO.toByte())
        Assertions.assertEquals(1.toByte(), Int128.ONE.toByte())
        Assertions.assertEquals(0.toByte(), Int128.valueOf(15L).toByte())

        Assertions.assertEquals(15.toByte(), Int128.valueOf(1uL, 15uL).toByte())
        Assertions.assertEquals((-15).toByte(), Int128.valueOf(1uL, (-15L).toULong()).toByte())
        Assertions.assertEquals(0.toByte(), Int128.MAX_VALUE.toByte())
        Assertions.assertEquals(0.toByte(), Int128.MIN_VALUE.toByte())
    }

    @Test
    fun toDouble() {
        Assertions.assertEquals(0L.toDouble(), Int128.ZERO.toDouble())
        Assertions.assertEquals(1L.toDouble(), Int128.ONE.toDouble())
        Assertions.assertEquals(15L.toDouble(), Int128.valueOf(15L).toDouble())

        Assertions.assertEquals(15L.toDouble(), Int128.valueOf(1uL, 15uL).toDouble())
        Assertions.assertEquals((-15).toDouble(), Int128.valueOf(1uL, (-15L).toULong()).toDouble())
        Assertions.assertEquals(Long.MAX_VALUE.toDouble(), Int128.MAX_VALUE.toDouble())
        Assertions.assertEquals(Long.MAX_VALUE.toDouble(), Int128.MIN_VALUE.toDouble())
    }

    @Test
    fun toFloat() {
        Assertions.assertEquals(0L.toFloat(), Int128.ZERO.toFloat())
        Assertions.assertEquals(1L.toFloat(), Int128.ONE.toFloat())
        Assertions.assertEquals(15L.toFloat(), Int128.valueOf(15L).toFloat())

        Assertions.assertEquals(15L.toFloat(), Int128.valueOf(1uL, 15uL).toFloat())
        Assertions.assertEquals((-15).toFloat(), Int128.valueOf(1uL, (-15L).toULong()).toFloat())
        Assertions.assertEquals(Long.MAX_VALUE.toFloat(), Int128.MAX_VALUE.toFloat())
        Assertions.assertEquals(Long.MAX_VALUE.toFloat(), Int128.MIN_VALUE.toFloat())
    }

    @Test
    fun toInt() {
        Assertions.assertEquals(0, Int128.ZERO.toInt())
        Assertions.assertEquals(1, Int128.ONE.toInt())
        Assertions.assertEquals(15, Int128.valueOf(15L).toInt())

        Assertions.assertEquals(15, Int128.valueOf(1uL, 15uL).toInt())
        Assertions.assertEquals(-15, Int128.valueOf(1uL, (-15L).toULong()).toInt())
        Assertions.assertEquals(Long.MAX_VALUE.toInt(), Int128.MAX_VALUE.toInt())
        Assertions.assertEquals(Long.MAX_VALUE.toInt(), Int128.MIN_VALUE.toInt())
    }

    @Test
    fun toLong() {
        Assertions.assertEquals(0L, Int128.ZERO.toLong())
        Assertions.assertEquals(1L, Int128.ONE.toLong())
        Assertions.assertEquals(15L, Int128.valueOf(15L).toLong())

        Assertions.assertEquals(15L, Int128.valueOf(1uL, 15uL).toShort())
        Assertions.assertEquals(-15, Int128.valueOf(1uL, (-15L).toULong()).toShort())
        Assertions.assertEquals(Long.MAX_VALUE, Int128.MAX_VALUE.toShort())
        Assertions.assertEquals(Long.MAX_VALUE, Int128.MIN_VALUE.toShort())
    }

    @Test
    fun toShort() {
        Assertions.assertEquals(0.toShort(), Int128.ZERO.toShort())
        Assertions.assertEquals(1.toShort(), Int128.ONE.toShort())
        Assertions.assertEquals(0.toShort(), Int128.valueOf(15L).toShort())

        Assertions.assertEquals(15.toShort(), Int128.valueOf(1uL, 15uL).toShort())
        Assertions.assertEquals((-15).toShort(), Int128.valueOf(1uL, (-15L).toULong()).toShort())
        Assertions.assertEquals(0.toShort(), Int128.MAX_VALUE.toShort())
        Assertions.assertEquals(0.toShort(), Int128.MIN_VALUE.toShort())
    }

    @Test
    operator fun component1() {
        run {
            val (h, _) = Int128.valueOf(15L)
            Assertions.assertEquals(0L, h)
        }
        run {
            val (h, _) = Int128.valueOf(15uL, 0uL)
            Assertions.assertEquals(15L, h)
        }
        run {
            val (h, _) = Int128.valueOf(15uL, 45uL)
            Assertions.assertEquals(15L, h)
        }
    }

    @Test
    operator fun component2() {
        run {
            val (_, l) = Int128.valueOf(15L)
            Assertions.assertEquals(15L, l)
        }
        run {
            val (_, l) = Int128.valueOf(15uL, 0uL)
            Assertions.assertEquals(0L, l)
        }
        run {
            val (_, l) = Int128.valueOf(15uL, 45uL)
            Assertions.assertEquals(45L, l)
        }
    }
}