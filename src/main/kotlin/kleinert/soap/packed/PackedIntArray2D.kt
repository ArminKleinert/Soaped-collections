package kleinert.soap.packed

import java.util.*

class PackedIntArray2D private constructor(private val packed: IntArray, val numSubArrays: Int) :
    AbstractList<MutableList<Int>>(), RandomAccess {
    override val size: Int
        get() = numSubArrays

    val subArraySize: Int
        get() = packed.size / size

    val packedArraySize: Int
        get() = packed.size / size

    companion object {
        fun invoke(numSubArrays: Int, subArraySize: Int, initFn: (Int, Int) -> Int): PackedIntArray2D {
            val packed = PackedIntArray2D(IntArray(numSubArrays * subArraySize), numSubArrays)

            for (i in 0..numSubArrays) {
                for (j in 0..subArraySize) {
                    val index = i * subArraySize + j
                    packed.packed[index] = initFn(i, j)
                }
            }

            return packed
        }

        fun invoke(numSubArrays: Int, subArraySize: Int, fill: Int = 0): PackedIntArray2D {
            val packed = PackedIntArray2D(IntArray(numSubArrays * subArraySize) {fill}, numSubArrays)
            return packed
        }
    }

    override fun isEmpty(): Boolean = size == 0

    fun flatIterator(startIndex: Int = 0): IntIterator = object : IntIterator() {
        private var cursor = 0
        override fun hasNext(): Boolean = cursor < packed.size

        override fun nextInt(): Int {
            val temp = packed[cursor]
            cursor++
            return temp
        }
    }

    override fun get(index: Int): IntArraySegment =
        IntArraySegment(packed, size * subArraySize, size * subArraySize + subArraySize)

    class IntArraySegment(
        private val original: IntArray,
        private val fromIndex: Int,
        private val toIndexExclusive: Int
    ) :
        AbstractMutableList<Int>() {
        override val size: Int
            get() = toIndexExclusive - fromIndex

        override fun get(index: Int): Int = original[index + fromIndex]

        override fun set(index: Int, element: Int): Int {
            val old = original[index + fromIndex]
            original[index + fromIndex] = element
            return old
        }

        override fun add(index: Int, element: Int): Unit = throw UnsupportedOperationException()
        override fun removeAt(index: Int): Int = throw UnsupportedOperationException()

        override fun listIterator(index: Int): MutableListIterator<Int> =
            object : IntIterator(), MutableListIterator<Int> {
                private var cursor = 0
                override fun hasNext(): Boolean = cursor < size
                override fun hasPrevious(): Boolean = cursor > 0
                override fun nextIndex(): Int = cursor
                override fun previousIndex(): Int = cursor - 1

                override fun nextInt(): Int {
                    val temp = get(cursor)
                    cursor++
                    return temp
                }

                fun setInt(element: Int) {
                    set(cursor, element)
                }

                override fun previous(): Int {
                    cursor--
                    return get(cursor)
                }

                override fun set(element: Int) {
                    set(cursor, element)
                }

                override fun add(element: Int) = throw UnsupportedOperationException()
                override fun remove() = throw UnsupportedOperationException()
            }
    }
}