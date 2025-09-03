package kleinert.soap.numerics

data class Int128(val high: ULong, val low: ULong) : Comparable<Int128>, Number() {
    companion object {
        val ZERO = Int128(0UL, 0UL)
        val ONE = Int128(0UL, 1UL)
        val MINUS_ONE = Int128(ULong.MAX_VALUE, ULong.MAX_VALUE)
        val MAX_VALUE = Int128(Long.MAX_VALUE.toULong(), ULong.MAX_VALUE)
        val MIN_VALUE = Int128(Long.MIN_VALUE.toULong(), 0uL)

        fun valueOf(high: ULong, low: ULong): Int128 = Int128(high, low)
        fun valueOf(low: ULong): Int128 = Int128(0uL, low)
        fun valueOf(low: UInt): Int128 = Int128(0uL, low.toULong())
        fun valueOf(low: Int): Int128 = valueOf(low.toLong())

        fun valueOf(low: Long): Int128 =
            if (low < 0L) Int128(ULong.MAX_VALUE, low.toULong())
            else Int128(0uL, low.toULong())

        fun valueOf(s: String, base: Int = 10): Int128 {
            require(base in 2..36)

            if (s.isEmpty())
                throw NumberFormatException()

            var str = s.trimStart(' ', '\t', '+')
            val neg = str.startsWith('-')
            str = str.trimStart('-', ' ', '\t', '+')

            if (str.isBlank())
                throw NumberFormatException()

            var res = UInt128.ZERO
            for (c in str) {
                if (c == ' ' || c == '\t') break

                var digit = c.code

                digit -= if (base > 10) {
                    when (digit) {
                        in 'A'.code..'Z'.code -> 'A'.code - 10
                        in 'a'.code..'z'.code -> 'a'.code - 10
                        in '0'.code..'9'.code -> '0'.code
                        else -> throw NumberFormatException()
                    }
                } else {
                    if (digit !in '0'.code..(base + '0'.code))
                        throw NumberFormatException()
                    '0'.code
                }

                res *= base.toULong()
                res += digit.toULong()
            }

            val signedRes = Int128(res.high, res.low)
            if (neg) return -signedRes
            return signedRes
        }

        fun valueOfOrNull(s: String, base: Int = 10): Int128? =
            try {
                valueOf(s, base)
            } catch (nfe: NumberFormatException) {
                null
            }
    }

    override fun compareTo(other: Int128): Int {
        if (high == other.high && low == other.low)
            return 0

        val cvt = high.toLong()
        val cvt2 = other.high.toLong()

        if (cvt == cvt2) {
            return low.toLong().compareTo(other.low.toLong())
        }

        return cvt.compareTo(cvt2)
    }

    infix operator fun plus(other: Int): Int128 = plus(valueOf(other))
    infix operator fun plus(other: UInt): Int128 = plus(other.toULong())
    infix operator fun plus(other: Long): Int128 = plus(valueOf(other))

    infix operator fun plus(other: ULong): Int128 {
        val newLow = low + other
        if (newLow < low) return Int128(high + 1u, newLow)
        return Int128(high, newLow)
    }

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

    operator fun unaryMinus(): Int128 = Int128(
        high.inv() + (if (low == 0uL) 1u else 0u),
        low.inv() + 1u
    )

    infix operator fun times(other: Int): Int128 = times(valueOf(other))
    infix operator fun times(other: UInt): Int128 = times(other.toULong())
    infix operator fun times(other: Long): Int128 = times(valueOf(other))
    infix operator fun times(other: ULong): Int128 = times(valueOf(other))

    infix operator fun times(other: Int128): Int128 {
        if (equals(MINUS_ONE))
            return -other

        if (other == MINUS_ONE)
            return -this

        var lhs = this
        var rhs = other
        var resultNegative = false
        if (this < ZERO) {
            resultNegative = true
            lhs = -lhs
        }
        if (rhs < ZERO) {
            resultNegative = !resultNegative
            rhs = -rhs
        }
        val temp = UInt128(lhs.high, lhs.low) * UInt128(rhs.high, rhs.low)
        val result = Int128(temp.high, temp.low)
        if (resultNegative) return -result
        return result
    }

    fun divMod(other: Int128): Pair<Int128, Int128> {
        if (other == ZERO) throw ArithmeticException("Division by zero.")
        if (equals(other)) return ONE to ZERO

        var divResult: Int128? = null
        if (equals(MIN_VALUE)) divResult = ZERO

        var remResult: Int128? = null
        if (other == MIN_VALUE) remResult = this

        // This case can never be true here.
        // if (remResult != null && divResult != null) return divResult to remResult

        var divNegative = false
        var remNegative = false
        var lhs = this
        var rhs = other
        if (lhs < ZERO) {
            divNegative = true
            remNegative = true
            lhs = -lhs
        }
        if (rhs < ZERO) {
            divNegative = !divNegative
            rhs = -rhs
        }

        val uLhs = UInt128(lhs.high, lhs.low)
        val uRhs = UInt128(rhs.high, rhs.low)
        val (tempDivRes, tempRemRes) = uLhs.divMod(uRhs)

        if (divResult == null) {
            val (divHigh, divLow) = tempDivRes
            divResult = Int128(divHigh, divLow)
            if (divNegative) divResult = -divResult
        }
        if (remResult == null) {
            val (remHigh, remLow) = tempRemRes
            remResult = Int128(remHigh, remLow)
            if (remNegative) remResult = -remResult
        }

        return divResult to remResult
    }

    infix operator fun div(other: Int128): Int128 =divMod(other).first

    operator fun rem(other: Int128): Int128 =divMod(other).second

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
        return low.countTrailingZeroBits()
    }

    fun countOneBits(): Int = high.countOneBits() + low.countOneBits()

    infix fun shl(count: Int): Int128 {
        if (count == 0) return this
        if (count >= 128) return ZERO
        if (count >= 64) return Int128(low shl (count - 64), 0u)
        return Int128((high shl count) or (low shr (64 - count)), low shl count)
    }

    infix fun shr(rhs: Int): Int128 {
        if (rhs == 0)
            return this

        val lhs = this
        if (lhs >= Int128(0uL, 0uL)) {
            val (h, l) = ushr(rhs)
            return Int128(h, l)
        }

        if (rhs == 64)
            return Int128(ULong.MAX_VALUE, high)

        if (rhs > 64) return Int128(
            ULong.MAX_VALUE,
            (lhs.high shr (rhs - 64)) or (ULong.MAX_VALUE shl (64 - (rhs - 64)))
        )

        return Int128(
            (lhs.high shr rhs) or (0uL.inv() shl (64 - rhs)),
            (low shr rhs) or (high shl (64 - rhs))
        )
    }

    infix fun ushr(count: Int): Int128 {
        if (count >= 128) return ZERO
        if (count >= 64) return Int128(low shr (count - 64), 0uL)

        return Int128(
            high shr count,
            (low shr count) or (high shl (64 - count))
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

    fun isNegative(): Boolean = (high and 0x8000_0000_0000_0000uL) != 0uL
    fun isPositive(): Boolean = (high and 0x8000_0000_0000_0000uL) == 0uL
    fun isZero(): Boolean = high == 0uL && low == 0uL

    fun increment(): Int128 = plus(ONE)
    fun decrement(): Int128 = minus(ONE)

//    fun toString(base: Int): String {
//        require((base in 2..32) || base == 64) { "Base must be between 2..32 or 64" }
//
//        if (isNegative()) {
//            val (hi, lo) = -this
//            return "-" + UInt128(hi, lo).toString(base)
//        }
//        val (hi, lo) = this
//        return UInt128(hi, lo).toString(base)
//    }

    fun toString(base: Int): String {
        require((base in 2..32) || base == 64) { "Base must be between 2..32 or 64" }
return "Int128(${high.toString(16)}, ${low.toString(16)})"
//        if (isNegative()) {
//            val (hi, lo) = -this
//            return "-" + UInt128(hi, lo).toString(base)
//        }
//        val (hi, lo) = this
//        return UInt128(hi, lo).toString(base)
    }

    override fun toString(): String = toString(10)
}
