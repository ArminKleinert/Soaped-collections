package kleinert.soap.numerics

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class Int128Test {

    @Test
    fun compareTo() {
        Assertions.assertTrue(Int128.ONE > Int128.ZERO)
        Assertions.assertTrue(Int128.MAX_VALUE > Int128.ZERO)
        Assertions.assertTrue(Int128.MAX_VALUE > Int128.MIN_VALUE)

        Assertions.assertTrue(Int128.ZERO < Int128.ONE)
        Assertions.assertTrue(Int128.ZERO < Int128.MAX_VALUE)
        Assertions.assertTrue(Int128.MINUS_ONE < Int128.MAX_VALUE)
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
        Assertions.assertEquals(Int128.ONE, Int128.valueOf(3L) and Int128.ONE)
        Assertions.assertEquals(Int128.MAX_VALUE, Int128.MAX_VALUE and Int128.MAX_VALUE)
    }

    @Test
    fun or() {
        Assertions.assertEquals(Int128.ONE, Int128.ZERO or Int128.ONE)
        Assertions.assertEquals(Int128.ZERO, Int128.ZERO or Int128.ZERO)
        Assertions.assertEquals(Int128.MAX_VALUE, Int128.MAX_VALUE or Int128.ZERO)
        Assertions.assertEquals(Int128.ONE, Int128.ONE or Int128.ONE)
        Assertions.assertEquals(Int128.valueOf(3L), Int128.valueOf(3L) or Int128.ONE)
        Assertions.assertEquals(Int128.MAX_VALUE, Int128.MAX_VALUE or Int128.MAX_VALUE)
    }

    @Test
    fun xor() {
        Assertions.assertEquals(Int128.ONE, Int128.ZERO xor Int128.ONE)
        Assertions.assertEquals(Int128.ZERO, Int128.ZERO xor Int128.ZERO)
        Assertions.assertEquals(Int128.MAX_VALUE, Int128.MAX_VALUE xor Int128.ZERO)
        Assertions.assertEquals(Int128.ZERO, Int128.ONE xor Int128.ONE)
        Assertions.assertEquals(Int128.valueOf(2L), Int128.valueOf(3L) xor Int128.ONE)
        Assertions.assertEquals(Int128.ZERO, Int128.MAX_VALUE xor Int128.MAX_VALUE)
    }

    @Test
    fun shiftRight() {
        Assertions.assertEquals(Int128.ZERO, Int128.ZERO shr 1)
        Assertions.assertEquals(Int128.ZERO, Int128.MAX_VALUE shr 256)
        Assertions.assertEquals(Int128.ONE, Int128.ONE shr 0)
        Assertions.assertEquals(Int128.valueOf(2), Int128.valueOf(4) shr 1)
        Assertions.assertEquals(Int128.valueOf(2), Int128.valueOf(5) shr 1)
        Assertions.assertEquals(Int128.valueOf(3), Int128.valueOf(6) shr 1)
        Assertions.assertEquals(Int128.valueOf(3), Int128.valueOf(7) shr 1)
        Assertions.assertEquals(Int128.valueOf(1uL, 0uL), Int128.ONE shr 64)
        Assertions.assertEquals(Int128.valueOf(ULong.MAX_VALUE, 0uL), Int128.MAX_VALUE shr 64)
        Assertions.assertEquals(Int128.valueOf(ULong.MAX_VALUE shr 1, 0uL), Int128.MAX_VALUE shr 65)
        Assertions.assertEquals(Int128.valueOf(ULong.MAX_VALUE, ULong.MAX_VALUE), Int128.MINUS_ONE shr 65)
        Assertions.assertEquals(Int128.valueOf(ULong.MAX_VALUE, ULong.MAX_VALUE shl 62), Int128.MIN_VALUE shr 65)
    }

    @Test
    fun shiftLeft() {
        Assertions.assertEquals(Int128.ZERO, Int128.ZERO shl 1)
        Assertions.assertEquals(Int128.valueOf(0uL, 0uL), Int128.ONE shl 256)
        Assertions.assertEquals(Int128.ONE, Int128.ONE shl 0)
        Assertions.assertEquals(Int128.valueOf(2), Int128.ONE shl 1)
        Assertions.assertEquals(Int128.valueOf(1uL, 0uL), Int128.ONE shl 64)
        Assertions.assertEquals(Int128.valueOf(ULong.MAX_VALUE, 0uL), Int128.MAX_VALUE shl 64)
        Assertions.assertEquals(Int128.valueOf(ULong.MAX_VALUE shl 1, 0uL), Int128.MAX_VALUE shl 65)
    }

    @Test
    fun shiftRightUnsigned() {
        Assertions.assertEquals(Int128.ZERO, Int128.ZERO ushr 1)
        Assertions.assertEquals(Int128.valueOf(0uL, 0uL), Int128.MAX_VALUE ushr 256)
        Assertions.assertEquals(Int128.ONE, Int128.ONE ushr 0)
        Assertions.assertEquals(Int128.valueOf(2), Int128.valueOf(4) ushr 1)
        Assertions.assertEquals(Int128.valueOf(2), Int128.valueOf(5) ushr 1)
        Assertions.assertEquals(Int128.valueOf(3), Int128.valueOf(6) ushr 1)
        Assertions.assertEquals(Int128.valueOf(3), Int128.valueOf(7) ushr 1)
        Assertions.assertEquals(Int128.valueOf(1uL, 0uL), Int128.ONE ushr 64)
        Assertions.assertEquals(Int128.valueOf(ULong.MAX_VALUE, 0uL), Int128.MAX_VALUE ushr 64)
        Assertions.assertEquals(Int128.valueOf(ULong.MAX_VALUE shr 1, 0uL), Int128.MAX_VALUE ushr 65)
        Assertions.assertEquals(Int128.valueOf(ULong.MAX_VALUE shr 1, 0uL), Int128.MINUS_ONE ushr 65)
        Assertions.assertEquals(Int128.valueOf(0uL, 0uL), Int128.MIN_VALUE ushr 65)
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
        Assertions.assertEquals(1, Int128.MIN_VALUE.countOneBits())
        Assertions.assertEquals(128, Int128.MINUS_ONE.countOneBits())

        Assertions.assertEquals(1, Int128.valueOf(2L).countOneBits())

        Assertions.assertEquals(2, Int128.valueOf(3L).countOneBits())
        Assertions.assertEquals(2, Int128.valueOf(1uL, 1uL).countOneBits())
    }

    @Test
    fun plusInt128() {
        Assertions.assertEquals(Int128.ZERO, Int128.ZERO + Int128.ZERO)
        Assertions.assertEquals(Int128.ONE, Int128.ZERO + Int128.ONE)
        Assertions.assertEquals(Int128.ONE, Int128.ONE + Int128.ZERO)
        Assertions.assertEquals(Int128(1uL, 0uL), Int128(0uL, ULong.MAX_VALUE) + Int128.ONE)
        Assertions.assertEquals(Int128(1uL, 0uL), Int128.ONE + Int128(0uL, ULong.MAX_VALUE))
        Assertions.assertEquals(Int128(9223372036854775808uL, 0uL), Int128.MAX_VALUE + Int128.ONE)
    }

    @Test
    fun testPlusLong() {
        Assertions.assertEquals(Int128.ZERO, Int128.ZERO + 0L)
        Assertions.assertEquals(Int128.ONE, Int128.ZERO + 1L)
        Assertions.assertEquals(Int128.ONE, Int128.ONE + 0L)
        Assertions.assertEquals(Int128(1uL, 0uL), Int128(0uL, ULong.MAX_VALUE) + 1L)
        Assertions.assertEquals(Int128(9223372036854775808uL, 0uL), Int128.MAX_VALUE + 1L)
    }

    @Test
    fun testPlusInt() {
        Assertions.assertEquals(Int128.ZERO, Int128.ZERO + 0)
        Assertions.assertEquals(Int128.ONE, Int128.ZERO + 1)
        Assertions.assertEquals(Int128.ONE, Int128.ONE + 0)
        Assertions.assertEquals(Int128(1uL, 0uL), Int128(0uL, ULong.MAX_VALUE) + 1)
        Assertions.assertEquals(Int128(9223372036854775808uL, 0uL), Int128.MAX_VALUE + 1u)
    }

    @Test
    fun testPlusUInt() {
        Assertions.assertEquals(Int128.ZERO, Int128.ZERO + 0u)
        Assertions.assertEquals(Int128.ONE, Int128.ZERO + 1u)
        Assertions.assertEquals(Int128.ONE, Int128.ONE + 0u)
        Assertions.assertEquals(Int128(1uL, 0uL), Int128(0uL, ULong.MAX_VALUE) + 1L)
        Assertions.assertEquals(Int128(9223372036854775808uL, 0uL), Int128.MAX_VALUE + 1u)
    }

    @Test
    fun testPlusULong() {
        Assertions.assertEquals(Int128.ZERO, Int128.ZERO + 0L)
        Assertions.assertEquals(Int128.ONE, Int128.ZERO + 1L)
        Assertions.assertEquals(Int128.ONE, Int128.ONE + 0L)
        Assertions.assertEquals(Int128(1uL, 0uL), Int128(0uL, ULong.MAX_VALUE) + 1L)
        Assertions.assertEquals(Int128(1uL, 0uL), Int128.ONE + ULong.MAX_VALUE)
        Assertions.assertEquals(Int128(9223372036854775808uL, 0uL), Int128.MAX_VALUE + 1uL)
    }

    @Test
    fun minusInt128() {
        Assertions.assertEquals(Int128.ZERO, Int128.ONE - Int128.ONE)
        Assertions.assertEquals(Int128.ONE, Int128.ONE - Int128.ZERO)
        Assertions.assertEquals(Int128.ZERO, Int128.MINUS_ONE - Int128.MINUS_ONE)
        Assertions.assertEquals(Int128.ONE, Int128.ZERO - Int128.MINUS_ONE)
        Assertions.assertEquals(Int128(Long.MAX_VALUE.toULong(), ULong.MAX_VALUE - 1u), Int128.MAX_VALUE - Int128.ONE)
    }

    @Test
    fun minusLong() {
        Assertions.assertEquals(Int128.ZERO, Int128.ONE - 1L)
        Assertions.assertEquals(Int128.ONE, Int128.ONE - 0L)
        Assertions.assertEquals(Int128.ZERO, Int128.MINUS_ONE - -1L)
        Assertions.assertEquals(Int128.ONE, Int128.ZERO - -1L)
        Assertions.assertEquals(Int128(Long.MAX_VALUE.toULong(), ULong.MAX_VALUE - 1u), Int128.MAX_VALUE - 1L)
    }

    @Test
    fun minusInt() {
        Assertions.assertEquals(Int128.ZERO, Int128.ONE - 1)
        Assertions.assertEquals(Int128.ONE, Int128.ONE - 0)
        Assertions.assertEquals(Int128.ZERO, Int128.MINUS_ONE - -1)
        Assertions.assertEquals(Int128.ONE, Int128.ZERO - -1)
        Assertions.assertEquals(Int128(Long.MAX_VALUE.toULong(), ULong.MAX_VALUE - 1u), Int128.MAX_VALUE - 1)
    }

    @Test
    fun timesInt128() {
        Assertions.assertEquals(Int128.ZERO, Int128.ZERO * Int128.MAX_VALUE)
        Assertions.assertEquals(Int128.ZERO, Int128.MAX_VALUE * Int128.ZERO)
        Assertions.assertEquals(Int128.MAX_VALUE, Int128.ONE * Int128.MAX_VALUE)
        Assertions.assertEquals(Int128.MAX_VALUE, Int128.MAX_VALUE * Int128.ONE)
        Assertions.assertEquals(Int128.MINUS_ONE, Int128.MINUS_ONE * Int128.ONE)
        Assertions.assertEquals(Int128.MIN_VALUE + Int128.ONE, Int128.MINUS_ONE * Int128.MAX_VALUE)
    }

    @Test
    fun timesLong() {
        Assertions.assertEquals(Int128.ZERO, Int128.ZERO * 55L)
        Assertions.assertEquals(Int128.ZERO, Int128.MAX_VALUE * 0L)
        Assertions.assertEquals(Int128.valueOf(55L), Int128.ONE * 55L)
        Assertions.assertEquals(Int128.MAX_VALUE, Int128.MAX_VALUE * 1L)
        Assertions.assertEquals(Int128.MINUS_ONE, Int128.MINUS_ONE * 1L)
    }

    @Test
    fun timesInt() {
        Assertions.assertEquals(Int128.ZERO, Int128.ZERO * 55)
        Assertions.assertEquals(Int128.ZERO, Int128.MAX_VALUE * 0)
        Assertions.assertEquals(Int128.valueOf(55), Int128.ONE * 55)
        Assertions.assertEquals(Int128.MAX_VALUE, Int128.MAX_VALUE * 1)
        Assertions.assertEquals(Int128.MINUS_ONE, Int128.MINUS_ONE * 1)
    }

    @Test
    fun divInt128() {
        Assertions.assertThrows(ArithmeticException::class.java) { Int128.MAX_VALUE / Int128.ZERO }
        Assertions.assertEquals(Int128.ZERO, Int128.ONE / Int128.valueOf(2))
        Assertions.assertEquals(Int128.MAX_VALUE, Int128.MAX_VALUE / Int128.ONE)
        Assertions.assertEquals(Int128.ONE, Int128.MAX_VALUE / Int128.MAX_VALUE)
        Assertions.assertEquals(Int128.valueOf(2), Int128.valueOf(4) / Int128.valueOf(2))

        Assertions.assertEquals(Int128.ONE, Int128.MINUS_ONE / Int128.MINUS_ONE)
        Assertions.assertEquals(Int128.MINUS_ONE, Int128.ONE / Int128.MINUS_ONE)
        Assertions.assertEquals(Int128.MINUS_ONE, Int128.MINUS_ONE / Int128.ONE)

        Assertions.assertEquals(Int128.valueOf(2), Int128.valueOf(-4) / Int128.valueOf(-2))
        Assertions.assertEquals(Int128.valueOf(-2), Int128.valueOf(-4) / Int128.valueOf(2))
        Assertions.assertEquals(Int128.valueOf(-2), Int128.valueOf(4) / Int128.valueOf(-2))

        Assertions.assertEquals(Int128.ONE, Int128.valueOf(-6) / Int128.valueOf(-5)) // Round down
        Assertions.assertEquals(Int128.MINUS_ONE, Int128.valueOf(-6) / Int128.valueOf(5)) // Rounding to ZERO
        Assertions.assertEquals(Int128.MINUS_ONE, Int128.valueOf(6) / Int128.valueOf(-5)) // Rounding to ZERO
    }

    @Test
    fun remInt128() {
        Assertions.assertThrows(ArithmeticException::class.java) { Int128.MAX_VALUE % Int128.ZERO }
        Assertions.assertEquals(Int128.ONE, Int128.ONE % Int128.valueOf(2))
        Assertions.assertEquals(Int128.ONE, Int128.valueOf(3) % Int128.valueOf(2))
        Assertions.assertEquals(Int128.ZERO, Int128.MAX_VALUE % Int128.ONE)
        Assertions.assertEquals(Int128.ZERO, Int128.MAX_VALUE % Int128.MAX_VALUE)
        Assertions.assertEquals(Int128.ZERO, Int128.valueOf(4) % Int128.valueOf(2))
        Assertions.assertEquals(Int128.valueOf(4), Int128.valueOf(4) % Int128.valueOf(6))
        Assertions.assertEquals(Int128.valueOf(2), Int128.valueOf(6) % Int128.valueOf(4))

        Assertions.assertEquals(Int128.ZERO, Int128.MINUS_ONE % Int128.MINUS_ONE)
        Assertions.assertEquals(Int128.ZERO, Int128.ONE % Int128.MINUS_ONE)
        Assertions.assertEquals(Int128.ZERO, Int128.MINUS_ONE % Int128.ONE)

        Assertions.assertEquals(Int128.ZERO, Int128.valueOf(-4) % Int128.valueOf(-2))
        Assertions.assertEquals(Int128.ZERO, Int128.valueOf(-4) % Int128.valueOf(2))
        Assertions.assertEquals(Int128.ZERO, Int128.valueOf(4) % Int128.valueOf(-2))

        Assertions.assertEquals(Int128.MINUS_ONE, Int128.valueOf(-6) % Int128.valueOf(-5)) // Round down
        Assertions.assertEquals(Int128.MINUS_ONE, Int128.valueOf(-6) % Int128.valueOf(5)) // Rounding to ZERO
        Assertions.assertEquals(Int128.ONE, Int128.valueOf(6) % Int128.valueOf(-5))
    }

    @Test
    fun divMod() {
        Assertions.assertThrows(ArithmeticException::class.java) { Int128.MAX_VALUE.divMod(Int128.ZERO) }

        Assertions.assertEquals(Int128.ONE to Int128.ZERO, Int128.ONE.divMod(Int128.valueOf(1)))

        Assertions.assertEquals(Int128.MAX_VALUE to Int128.ZERO, Int128.MAX_VALUE.divMod(Int128.ONE))
        Assertions.assertEquals(Int128.ONE to Int128.ZERO, Int128.MAX_VALUE.divMod(Int128.MAX_VALUE))
        Assertions.assertEquals(Int128.valueOf(2) to Int128.ZERO, Int128.valueOf(4).divMod(Int128.valueOf(2)))
        Assertions.assertEquals(Int128.ONE to Int128.ONE, Int128.valueOf(4).divMod(Int128.valueOf(3)))
    }

    @Test
    fun increment() {
        Assertions.assertEquals(Int128.ONE, Int128.ZERO.increment())
        Assertions.assertEquals(Int128.valueOf(2L), Int128.ONE.increment())
        Assertions.assertEquals(Int128.valueOf(1uL, 1uL), Int128.valueOf(1uL, 0uL).increment())
        Assertions.assertEquals(Int128.MIN_VALUE, Int128.MAX_VALUE.increment())
    }

    @Test
    fun decrement() {
        Assertions.assertEquals(Int128.ZERO, Int128.ONE.decrement())
        Assertions.assertEquals(Int128.ONE, Int128.valueOf(2L).decrement())
        Assertions.assertEquals(Int128.valueOf(1uL, 0uL), Int128.valueOf(1uL, 1uL).decrement())
        Assertions.assertEquals(Int128.MAX_VALUE, Int128.MIN_VALUE.decrement())
    }

    @Test
    fun toByte() {
        Assertions.assertEquals(0.toByte(), Int128.ZERO.toByte())
        Assertions.assertEquals(1.toByte(), Int128.ONE.toByte())
        Assertions.assertEquals(15.toByte(), Int128.valueOf(15L).toByte())

        Assertions.assertEquals(15.toByte(), Int128.valueOf(1uL, 15uL).toByte())
        Assertions.assertEquals((-15).toByte(), Int128.valueOf(1uL, (-15L).toULong()).toByte())
        Assertions.assertEquals((-1).toByte(), Int128.MAX_VALUE.toByte())
        Assertions.assertEquals(0.toByte(), Int128.MIN_VALUE.toByte())
    }

    @Test
    fun toDouble() {
        Assertions.assertEquals(0L.toDouble(), Int128.ZERO.toDouble())
        Assertions.assertEquals(1L.toDouble(), Int128.ONE.toDouble())
        Assertions.assertEquals(15L.toDouble(), Int128.valueOf(15L).toDouble())

        Assertions.assertEquals(15L.toDouble(), Int128.valueOf(1uL, 15uL).toDouble())
        Assertions.assertEquals((-15).toDouble(), Int128.valueOf(1uL, (-15L).toULong()).toDouble())
        Assertions.assertEquals(-1.0, Int128.MAX_VALUE.toDouble())
        Assertions.assertEquals(0.0, Int128.MIN_VALUE.toDouble())
    }

    @Test
    fun toFloat() {
        Assertions.assertEquals(0L.toFloat(), Int128.ZERO.toFloat())
        Assertions.assertEquals(1L.toFloat(), Int128.ONE.toFloat())
        Assertions.assertEquals(15L.toFloat(), Int128.valueOf(15L).toFloat())

        Assertions.assertEquals(15L.toFloat(), Int128.valueOf(1uL, 15uL).toFloat())
        Assertions.assertEquals((-15).toFloat(), Int128.valueOf(1uL, (-15L).toULong()).toFloat())
        Assertions.assertEquals((-1.0).toFloat(), Int128.MAX_VALUE.toFloat())
        Assertions.assertEquals(0.0.toFloat(), Int128.MIN_VALUE.toFloat())
    }

    @Test
    fun toInt() {
        Assertions.assertEquals(0, Int128.ZERO.toInt())
        Assertions.assertEquals(1, Int128.ONE.toInt())
        Assertions.assertEquals(15, Int128.valueOf(15L).toInt())

        Assertions.assertEquals(15, Int128.valueOf(1uL, 15uL).toInt())
        Assertions.assertEquals(-15, Int128.valueOf(1uL, (-15L).toULong()).toInt())
        Assertions.assertEquals(Long.MAX_VALUE.toInt(), Int128.MAX_VALUE.toInt())
        Assertions.assertEquals(0, Int128.MIN_VALUE.toInt())
    }

    @Test
    fun toLong() {
        Assertions.assertEquals(0L, Int128.ZERO.toLong())
        Assertions.assertEquals(1L, Int128.ONE.toLong())
        Assertions.assertEquals(15L, Int128.valueOf(15L).toLong())

        Assertions.assertEquals(15L, Int128.valueOf(1uL, 15uL).toLong())
        Assertions.assertEquals(-15, Int128.valueOf(1uL, (-15L).toULong()).toLong())
        Assertions.assertEquals(-1L, Int128.MAX_VALUE.toLong())
        Assertions.assertEquals(0L, Int128.MIN_VALUE.toLong())
    }

    @Test
    fun toShort() {
        Assertions.assertEquals(0.toShort(), Int128.ZERO.toShort())
        Assertions.assertEquals(1.toShort(), Int128.ONE.toShort())
        Assertions.assertEquals(15.toShort(), Int128.valueOf(15L).toShort())

        Assertions.assertEquals(15.toShort(), Int128.valueOf(1uL, 15uL).toShort())
        Assertions.assertEquals((-15).toShort(), Int128.valueOf(1uL, (-15L).toULong()).toShort())
        Assertions.assertEquals((-1).toShort(), Int128.MAX_VALUE.toShort())
        Assertions.assertEquals(0.toShort(), Int128.MIN_VALUE.toShort())
    }

    @Test
    operator fun component1() {
        run {
            val (h, _) = Int128.valueOf(15L)
            Assertions.assertEquals(0uL, h)
        }
        run {
            val (h, _) = Int128.valueOf(15uL, 0uL)
            Assertions.assertEquals(15uL, h)
        }
        run {
            val (h, _) = Int128.valueOf(15uL, 45uL)
            Assertions.assertEquals(15uL, h)
        }
    }

    @Test
    operator fun component2() {
        run {
            val (_, l) = Int128.valueOf(15L)
            Assertions.assertEquals(15uL, l)
        }
        run {
            val (_, l) = Int128.valueOf(15uL, 0uL)
            Assertions.assertEquals(0uL, l)
        }
        run {
            val (_, l) = Int128.valueOf(15uL, 45uL)
            Assertions.assertEquals(45uL, l)
        }
    }
}