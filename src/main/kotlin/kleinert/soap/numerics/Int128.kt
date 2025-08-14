package kleinert.soap.numerics

import java.math.BigInteger
import kotlin.math.min


data class Int128(val high: Long, val low: Long) : Comparable<Int128>, Number() {
    companion object {
        val ZERO = Int128(0L, 0L)
        val ONE = Int128(0, 1)
        val MAX_VALUE = Int128(Long.MAX_VALUE, -0x1L)
        val MIN_VALUE = Int128(Long.MIN_VALUE, 0x0000000000000000L)

        fun valueOf(high: Long, low: Long): Int128 = Int128(high, low)
        fun valueOf(high: Long, low: ULong): Int128 = Int128(high, low.toLong())
        fun valueOf(low: Long): Int128 = Int128(0L, low)
        fun valueOf(low: Int): Int128 = Int128(0L, low.toLong())

        fun valueOf(bigInt: BigInteger): Int128 {
            val bigIntOfLongMax = BigInteger.valueOf(ULong.MAX_VALUE.toLong())
            val high = bigInt.and(bigIntOfLongMax.shiftLeft(64)).toLong()
            val low = bigInt.and(bigIntOfLongMax).toLong()
            return Int128(high, low)
        }
    }

    override fun compareTo(other: Int128): Int {
        var result = high.compareTo(other.high)
        if (result == 0) {
            result = java.lang.Long.compareUnsigned(low, other.low)
        }
        return result
    }

    fun isNegative(): Boolean = high < 0
    fun isPositive(): Boolean = high > 0 || high == 0L && low != 0L
    fun isZero(): Boolean = (high or low) == 0L

    infix fun and(b: Int128): Int128 = Int128(high and b.high, low and b.low)
    infix fun or(b: Int128): Int128 = Int128(high or b.high, low or b.low)
    infix fun xor(b: Int128): Int128 = Int128(high xor b.high, low xor b.low)
    operator fun not(): Int128 = Int128(-high, -low)

    fun shiftRight(shift: Int): Int128 {
        val newHigh = high shr min(shift, 63)

        val newLow = if (shift < 64)
            high shl 1 shl 63 - shift or (low ushr shift)
        else
            high shr shift - 64

        return Int128(newHigh, newLow)
    }

    fun shiftLeft(shift: Int): Int128 {
        val newHigh = if (shift < 64)
            high shl shift or (low ushr 1 ushr 63 - shift)
        else
            low shl shift - 64

        val newLow = low shl if (shift < 64) shift else 0

        return Int128(newHigh, newLow)
    }

    fun shiftRightUnsigned(shift: Int): Int128 {
        val newHigh = if (shift < 64) high ushr shift else 0

        val newLow = if (shift < 64)
            high shl 1 shl 63 - shift or (low ushr shift)
        else
            high ushr shift - 64

        return Int128(newHigh, newLow)
    }

    fun numberOfLeadingZeros(): Int {
        var count = high.countLeadingZeroBits()
        if (count == 64) count += low.countLeadingZeroBits()
        return count
    }

    fun numberOfTrailingZeros(): Int {
        var count = low.countTrailingZeroBits()
        if (count == 64) count += high.countTrailingZeroBits()
        return count
    }

    fun bitCount(): Int = high.countOneBits() + low.countOneBits()

    operator fun plus(other: Long): Int128 = plus(valueOf(other))
    operator fun plus(other: Int): Int128 = plus(valueOf(other))
    operator fun plus(other: BigInteger): Int128 = plus(valueOf(other))
    operator fun plus(other: Int128): Int128 {
        var sumHigh = high+other.high
        val sumLow = low + other.low
        if (sumLow < low) sumHigh += 1
        return Int128(sumHigh, sumLow)
    }
    fun plusExact(other: Int128): Int128 {
        var sumHigh = high+other.high
        val sumLow = low + other.low
        if (sumLow < low) sumHigh += 1
        if (sumHigh <high) throw IllegalStateException()
        return Int128(sumHigh, sumLow)
    }

    /*
    PairInt128 subtract_128_bits(int64_t a_high, int64_t a_low, int64_t b_high, int64_t b_low) {
        int64_t borrow = a_low < b_low;
        int64_t diff_low = a_low - b_low - borrow;
        int64_t diff_high = a_high - b_high - (borrow ? 1 : 0);
        return (PairInt128){diff_high, diff_low};
    }
     */
    operator fun minus(other: Long): Int128 = minus(valueOf(other))
    operator fun minus(other: Int): Int128 = minus(valueOf(other))
    operator fun minus(other: BigInteger): Int128 = minus(valueOf(other))
    operator fun minus(other: Int128): Int128 {
        val borrow = if (low < other.low) 1 else 0
        val diffLow = low - other.low - borrow
        val diffHigh = high - other.high - borrow
        return Int128(diffHigh, diffLow)
    }

    operator fun times(other: Int128): Int128 = TODO()
    operator fun times(other: Long): Int128 = times(valueOf(other))
    operator fun times(other: Int): Int128 = times(valueOf(other))
    operator fun times(other: BigInteger): Int128 = times(valueOf(other))

    operator fun div(other: Int128): Int128 = TODO()
    operator fun div(other: Long): Int128 = div(valueOf(other))
    operator fun div(other: Int): Int128 = div(valueOf(other))
    operator fun div(other: BigInteger): Int128 = div(valueOf(other))

    operator fun rem(other: Int128): Int128 = TODO()
    operator fun rem(other: Long): Int128 = div(valueOf(other))
    operator fun rem(other: Int): Int128 = div(valueOf(other))
    operator fun rem(other: BigInteger): Int128 = div(valueOf(other))

    fun increment(): Int128 = if (low == -1L) Int128(high+1, 0L) else Int128(high, low+1)
    fun decrement(): Int128 = minus(ONE)

    override fun toLong(): Long = low
    override fun toByte(): Byte = toLong().toByte()
    override fun toShort(): Short = toLong().toShort()
    override fun toInt(): Int = toLong().toInt()
    override fun toFloat(): Float = toLong().toFloat()
    override fun toDouble(): Double = toLong().toDouble()
}