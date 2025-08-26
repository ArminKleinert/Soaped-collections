package kleinert.soap.numerics

import java.io.Serializable
import java.math.BigInteger

data class UInt128(val high: ULong, val low: ULong) : Comparable<UInt128>, Number() {
    companion object {
        val ZERO: UInt128 = UInt128(0UL, 0UL)
        val ONE: UInt128 = UInt128(0UL, 1UL)
        val MAX_VALUE: UInt128 = UInt128(ULong.MAX_VALUE, ULong.MAX_VALUE)
        val MIN_VALUE: UInt128 = UInt128(0uL, 0uL)

        fun valueOf(high: ULong, low: ULong): UInt128 = UInt128(high, low)
        fun valueOf(low: ULong): UInt128 = UInt128(0uL, low)
        fun valueOf(low: UInt): UInt128 = UInt128(0uL, low.toULong())
        fun valueOf(low: Int): UInt128 = if (low < 0) throw IllegalArgumentException() else UInt128(0uL, low.toULong())

        fun valueOf(bigInt: BigInteger): UInt128 {
            val bigIntOfLongMax = BigInteger.valueOf(ULong.MAX_VALUE.toLong())
            val bigIntOfLongMaxShifted = bigIntOfLongMax.shiftLeft(64)
            if (bigInt.and(bigIntOfLongMax or bigIntOfLongMaxShifted) != bigInt)
                throw IllegalArgumentException()
            val high = bigInt.and(bigIntOfLongMaxShifted).toLong().toULong()
            val low = bigInt.and(bigIntOfLongMax).toLong().toULong()
            return UInt128(high, low)
        }

        internal inline fun uint128MulHelper(lhs:ULong, rhs:ULong): UInt128 {
            val UINT32_MAX = UInt.MAX_VALUE.toULong()

            val left_lo32 = lhs and UINT32_MAX
            val left_hi32 = lhs shr 32
            val right_lo32 = rhs and UINT32_MAX
            val right_hi32 = rhs shr 32

            // Compute each component of the product as the sum of multiple 32 bit
            // products.
            val lo_lo = left_lo32 * right_lo32
            val lo_hi = left_lo32 * right_hi32
            val hi_lo = left_hi32 * right_lo32
            val hi_hi = left_hi32 * right_hi32

            val carry =        ((lo_lo shr 32) + (lo_hi and UINT32_MAX) + (hi_lo and UINT32_MAX)) shr        32

            // Assemble the final product from these components, adding the carry
            // to the high 64 bits.
            return UInt128(
                hi_hi + (lo_hi shr 32) + (hi_lo shr 32) + carry,
                lo_lo + (lo_hi shl 32) + (hi_lo shl 32),
            )
        }
    }

    override fun compareTo(other: UInt128): Int {
        if (high == other.high) return low.compareTo(other.low)
        return high.compareTo(other.high)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        return when (other) {
            is UInt128 -> high == other.high && low == other.low
            is Byte, is Short, is Int, is Long -> high == 0uL && (other as Number).toLong() >= 0L && low == other
            is UByte, is UShort, is UInt, is ULong -> high == 0uL && other == low
            else -> false
        }
    }

    infix fun plus(other: UInt): UInt128 = plus(UInt128(0uL, other.toULong()))
    infix fun plus(other: ULong): UInt128 = plus(UInt128(0uL, other))
    infix fun plus(other: UInt128): UInt128 {
        val newLow = low + other.low
        var newHigh = high + other.high
        if (newLow < low) newHigh++
        return UInt128(newHigh, newLow)
    }

    infix fun minus(other: UInt): UInt128 = minus(valueOf(other))
    infix fun minus(other: ULong): UInt128 = minus(valueOf(other))
    infix operator fun minus(other: UInt128): UInt128 {
        val resLow = low - other.low
        val carry = (((resLow and other.low) and 1uL) + (other.low shr 1)+(resLow shr 1))shr 63
        return UInt128(high-other.high+carry, resLow)
    }

    infix operator fun times(other:UInt128):UInt128{
        val i = uint128MulHelper(low, other.low)
        val j = uint128MulHelper(low, other.high)
        val k = uint128MulHelper (high, other.low)

        val temp = i.plus (UInt128(j.low, 0uL))
        val result = temp.plus(UInt128(k.low, 0uL))
        return result
    }

    internal fun divMod(other: UInt128): Pair<UInt128,UInt128> {
        if (other == ZERO) throw ArithmeticException("Division by zero.")
        if (compareTo(other) < 0) return ZERO to this
        if (equals(other)) return ONE to ZERO

        var current = ONE
        var result = ZERO
        var divisor = other
        var lhs = this

        while (divisor <= lhs) {
            divisor=divisor shl 1
            current = current shl 1
        }

        divisor = divisor shr 1
        current = current shr 1

        while (current != ZERO) {
            if (lhs >= divisor) {
                lhs -= divisor
                result = result or current
            }
            current = current shr 1
            divisor = divisor shr 1
        }

        return result to lhs}

    infix operator fun div(other:UInt128):UInt128 = divMod(other).first

    infix operator fun rem(other: UInt128): UInt128 = divMod(other).second

    fun inv(): UInt128 = UInt128(high.inv(), low.inv())

    infix fun shl(count: Int): UInt128 {
        val (h, l) = Int128(high, low).shl(count)
        return UInt128(h, l)
    }

    infix fun shr(count: Int): UInt128 {
        if (count >= 128) return ZERO
        if (count >= 64) return UInt128(low shr (count - 64), 0uL)

        return UInt128(
            high shr count,
            (low shr count) or (high shl (64 - count))
        )
    }

    fun and(other: UInt128): UInt128 = UInt128(high and other.high, low and other.low)
    infix fun or(other: UInt128): UInt128 = UInt128(high or other.high, low or other.low)
    fun xor(other: UInt128): UInt128 = UInt128(high xor other.high, low xor other.low)

    fun toULong(): ULong = low
    fun toUByte(): UByte = low.toUByte()
    fun toUShort(): UShort = low.toUShort()
    fun toUInt(): UInt = low.toUInt()
    override fun toLong(): Long = low.toLong()
    override fun toByte(): Byte = toLong().toByte()
    override fun toShort(): Short = toLong().toShort()
    override fun toInt(): Int = toLong().toInt()
    override fun toFloat(): Float = toLong().toFloat()
    override fun toDouble(): Double = toLong().toDouble()
    override fun hashCode(): Int {
        var result = high.hashCode()
        result = 31 * result + low.hashCode()
        return result
    }
}