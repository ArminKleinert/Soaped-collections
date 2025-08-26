package kleinert.soap.numerics

data class Int128(val high: ULong, val low: ULong) : Comparable<Int128>, Number() {
    companion object {
        val ZERO = Int128(0UL, 0UL)
        val ONE = Int128(0UL, 1UL)
        val MAX_VALUE = Int128(Long.MAX_VALUE.toULong(), ULong.MAX_VALUE)
        val MIN_VALUE = Int128(Long.MIN_VALUE.toULong(), 0uL)

        fun valueOf(high: ULong, low: ULong): Int128 = Int128(high, low)
        fun valueOf(low: ULong): Int128 = Int128(0uL, low)
        fun valueOf(low: UInt): Int128 = Int128(0uL, low.toULong())
        fun valueOf(low: Int): Int128 = valueOf(low.toLong())
        fun valueOf(low: Long): Int128 =
            if (low < 0L) Int128(ULong.MAX_VALUE, low.toULong())
            else Int128(0uL, low.toULong())
    }

    override fun compareTo(other: Int128): Int {
        var result = high.compareTo(other.high)
        if (result == 0) result = low.compareTo(other.low)
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true
        return when (other) {
            is Int128 -> high == other.high && low == other.low
            is Byte, is Short, is Int, is Long -> high == 0uL && low == (other as Number).toLong().toULong()
            is UByte -> high == 0uL && low >= 0uL && low == other.toULong()
            is UShort -> high == 0uL && low >= 0uL && low == other.toULong()
            is UInt -> high == 0uL && low >= 0uL && low == other.toULong()
            is ULong -> high == 0uL && low >= 0uL && low == other.toULong()
            else -> false
        }
    }

    infix operator fun plus(other: Int): Int128 = plus(valueOf(other))
    infix operator fun plus(other: UInt): Int128 = plus(valueOf(other))
    infix operator fun plus(other: Long): Int128 = plus(valueOf(other))
    infix operator fun plus(other: ULong): Int128 = plus(valueOf(other))
    infix operator fun plus(other: Int128): Int128 {
        val newLow = low + other.low
        var newHigh = high + other.high
        if (newLow < low) newHigh++
        return Int128(newHigh, newLow)
    }

    infix operator fun minus(other: Int): Int128 = plus(-valueOf(other))
    infix operator fun minus(other: Long): Int128 = plus(-valueOf(other))
    infix operator fun minus(other: UInt): Int128 = plus(-valueOf(other))
    infix operator fun minus(other: ULong): Int128 = plus(-valueOf(other))
    infix operator fun minus(other: Int128): Int128 = plus(-other)

    operator fun unaryMinus(): Int128 = Int128(high.inv() + (if (low == 0uL) 1u else 0u), low.inv() + 1u)

    fun inv(): Int128 = Int128(high.inv(), low.inv())

    infix fun and(other: Int128): Int128 = Int128(high and other.high, low and other.low)
    infix fun or(other: Int128): Int128 = Int128(high or other.high, low or other.low)
    infix fun xor(other: Int128): Int128 = Int128(high xor other.high, low xor other.low)

    fun numberOfLeadingZeros(): Int {
        if (high == 0uL) return low.countLeadingZeroBits() + 64
        return high.countLeadingZeroBits()
    }

    fun numberOfTrailingZeros(): Int {
        if (low == 0uL) return high.countTrailingZeroBits() + 64
        return low.countLeadingZeroBits()
    }

    fun countOneBits(): Int = high.countOneBits() + low.countOneBits()

    infix fun shl(count: Int): Int128 {
        if (count >= 64) return Int128(low shl (count - 64), 0u)
        return Int128((high shl count) or (low shr (64 - count)), low shl count)
    }

    fun shr(rhs: Int): Int128 {
        val lhs = this
        if (lhs > Int128(0uL, 0uL)) {
            val (h, l) = UInt128(high, low).shr(rhs)
            return Int128(h, l)
        }

        if (rhs > 64) return Int128(
            ULong.MAX_VALUE,
            (lhs.high shr (rhs - 64)) or (ULong.MAX_VALUE shl (64 - (rhs - 64)))
        )
        if (rhs == 64) return Int128(ULong.MAX_VALUE, high)

        return Int128(
            (lhs.high shr rhs) or (ULong.MAX_VALUE shl (64 - rhs)),
            (low shr rhs) or (high shl (64 - rhs))
        )
    }


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

    fun isNegative(): Boolean = (high and 0x8000_0000_0000_0000uL) != 0uL
    fun isPositive(): Boolean = (high and 0x8000_0000_0000_0000uL) == 0uL
    fun isZero(): Boolean = high == 0uL && low == 0uL

    fun increment(): Int128 = plus(Int128.ONE)
    fun decrement(): Int128 = minus(Int128.ONE)
}