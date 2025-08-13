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
        Assertions.assertTrue(Int128.ZERO == Int128.valueOf(0L, 0L))
        Assertions.assertTrue(Int128.ONE == Int128.valueOf(0L, 1L))
        Assertions.assertTrue(Int128.ONE != Int128.valueOf(0L, 0L))
        Assertions.assertTrue(Int128.MAX_VALUE == Int128(Long.MAX_VALUE, -0x1L))
        Assertions.assertTrue(Int128.MIN_VALUE == Int128(Long.MIN_VALUE, 0x0000000000000000L))
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
        Assertions.assertEquals(0, Int128.ZERO.numberOfTrailingZeros())
        Assertions.assertEquals(1, Int128.ONE.numberOfTrailingZeros())
        Assertions.assertEquals(127, Int128.MAX_VALUE.numberOfTrailingZeros())
        Assertions.assertEquals(128, Int128.MIN_VALUE.numberOfTrailingZeros())

        Assertions.assertEquals(1, Int128.valueOf(2L).numberOfTrailingZeros())
        Assertions.assertEquals(1, Int128.valueOf(3L).numberOfTrailingZeros())
    }

    @Test
    fun plusInt128() {
        TODO()
    }

    @Test
    fun testPlusLong() {
        TODO()
    }

    @Test
    fun testPlusInt() {
        TODO()
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
        Assertions.assertEquals(Int128.valueOf(1L, 1L), Int128.valueOf(1L, 0L).increment())
        Assertions.assertEquals(Int128.MIN_VALUE, Int128.MAX_VALUE.increment()) // TODO check
    }

    @Test
    fun decrement() {
        Assertions.assertEquals(Int128.ZERO, Int128.ONE.increment())
        Assertions.assertEquals(Int128.ONE, Int128.valueOf(2L).increment())
        Assertions.assertEquals(Int128.valueOf(1L, 0L), Int128.valueOf(1L, 1L).increment())
        Assertions.assertEquals(Int128.MAX_VALUE, Int128.MIN_VALUE.increment()) // TODO check
    }

    @Test
    fun toByte() {
        Assertions.assertEquals(0.toByte(), Int128.ZERO.toByte())
        Assertions.assertEquals(1.toByte(), Int128.ONE.toByte())
        Assertions.assertEquals(0.toByte(), Int128.valueOf(15L).toByte())

        Assertions.assertEquals(15.toByte(), Int128.valueOf(1L, 15L).toByte())
        Assertions.assertEquals((-15).toByte(), Int128.valueOf(1L, -15L).toByte())
        Assertions.assertEquals(0.toByte(), Int128.MAX_VALUE.toByte())
        Assertions.assertEquals(0.toByte(), Int128.MIN_VALUE.toByte())
    }

    @Test
    fun toDouble() {
        Assertions.assertEquals(0L.toDouble(), Int128.ZERO.toDouble())
        Assertions.assertEquals(1L.toDouble(), Int128.ONE.toDouble())
        Assertions.assertEquals(15L.toDouble(), Int128.valueOf(15L).toDouble())

        Assertions.assertEquals(15L.toDouble(), Int128.valueOf(1L, 15L).toDouble())
        Assertions.assertEquals((-15).toDouble(), Int128.valueOf(1L, -15L).toDouble())
        Assertions.assertEquals(Long.MAX_VALUE.toDouble(), Int128.MAX_VALUE.toDouble())
        Assertions.assertEquals(Long.MAX_VALUE.toDouble(), Int128.MIN_VALUE.toDouble())
    }

    @Test
    fun toFloat() {
        Assertions.assertEquals(0L.toFloat(), Int128.ZERO.toFloat())
        Assertions.assertEquals(1L.toFloat(), Int128.ONE.toFloat())
        Assertions.assertEquals(15L.toFloat(), Int128.valueOf(15L).toFloat())

        Assertions.assertEquals(15L.toFloat(), Int128.valueOf(1L, 15L).toFloat())
        Assertions.assertEquals((-15).toFloat(), Int128.valueOf(1L, -15L).toFloat())
        Assertions.assertEquals(Long.MAX_VALUE.toFloat(), Int128.MAX_VALUE.toFloat())
        Assertions.assertEquals(Long.MAX_VALUE.toFloat(), Int128.MIN_VALUE.toFloat())
    }

    @Test
    fun toInt() {
        Assertions.assertEquals(0, Int128.ZERO.toInt())
        Assertions.assertEquals(1, Int128.ONE.toInt())
        Assertions.assertEquals(15, Int128.valueOf(15L).toInt())

        Assertions.assertEquals(15, Int128.valueOf(1L, 15L).toInt())
        Assertions.assertEquals(-15, Int128.valueOf(1L, -15L).toInt())
        Assertions.assertEquals(Long.MAX_VALUE.toInt(), Int128.MAX_VALUE.toInt())
        Assertions.assertEquals(Long.MAX_VALUE.toInt(), Int128.MIN_VALUE.toInt())
    }

    @Test
    fun toLong() {
        Assertions.assertEquals(0L, Int128.ZERO.toLong())
        Assertions.assertEquals(1L, Int128.ONE.toLong())
        Assertions.assertEquals(15L, Int128.valueOf(15L).toLong())

        Assertions.assertEquals(15L, Int128.valueOf(1L, 15L).toShort())
        Assertions.assertEquals(-15, Int128.valueOf(1L, -15L).toShort())
        Assertions.assertEquals(Long.MAX_VALUE, Int128.MAX_VALUE.toShort())
        Assertions.assertEquals(Long.MAX_VALUE, Int128.MIN_VALUE.toShort())
    }

    @Test
    fun toShort() {
        Assertions.assertEquals(0.toShort(), Int128.ZERO.toShort())
        Assertions.assertEquals(1.toShort(), Int128.ONE.toShort())
        Assertions.assertEquals(0.toShort(), Int128.valueOf(15L).toShort())

        Assertions.assertEquals(15.toShort(), Int128.valueOf(1L, 15L).toShort())
        Assertions.assertEquals((-15).toShort(), Int128.valueOf(1L, -15L).toShort())
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
            val (h, _) = Int128.valueOf(15L, 0L)
            Assertions.assertEquals(15L, h)
        }
        run {
            val (h, _) = Int128.valueOf(15L, 45L)
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
            val (_, l) = Int128.valueOf(15L, 0L)
            Assertions.assertEquals(0L, l)
        }
        run {
            val (_, l) = Int128.valueOf(15L, 45L)
            Assertions.assertEquals(45L, l)
        }
    }
}