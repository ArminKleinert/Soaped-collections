package kleinert.soap.numerics

@JvmInline
value class UIntPair(private val inner: ULong) {
    companion object {
        fun makeUIntPair(first: UInt, second: UInt) = UIntPair((first.toULong() shl 32) or second.toULong())
    }

    val first: UInt
        get() = (inner shr 32).toUInt()
    val second: UInt
        get() = (inner and 0xFFFFFFFFL.toULong()).toUInt()

    operator fun component1() = first
    operator fun component2() = second
    infix operator fun plus(other:UIntPair) = makeUIntPair(first + other.first, second + other.second)
    infix operator fun minus(other:UIntPair) = makeUIntPair(first - other.first, second - other.second)
}

@JvmInline
value class IntPair(private val inner: Long) {
    companion object {
        fun makeIntPair(first: Int, second: Int) = IntPair(((first.toLong() shl 32) or (second and -1).toLong()))
    }

    val first: Int
        get() = (inner shr 32).toInt()
    val second: Int
        get() = (inner and 0xFFFFFFFFL).toInt()

    operator fun component1() = first
    operator fun component2() = second

    infix operator fun plus(other:IntPair) =makeIntPair(first+other.first, second+other.second)
    infix operator fun minus(other:IntPair) =makeIntPair(first-other.first, second-other.second)
}

data class IntQuadruple(
    val first: Int, val second: Int, val third: Int, val fourth: Int
)