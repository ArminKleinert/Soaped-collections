package kleinert.soap.multiset

interface MultiSet<K> : Iterable<Pair<K, Int>>, Map<K, Int> {
    /**
     * Return 0 if the element is not in the map.
     * Return the amount of the element otherwise.
     */
    fun getCount(element: K): Int

    companion object {
        fun <K> of(vararg elements: K): MultiSet<K> {
            if (elements.size <= 16) return ArrayMultiSet(elements.toList())
            return ArrayMultiSet(elements.toList()) // TODO
        }

        fun <K> fromMap(counts: Map<K, Int>): MultiSet<K> {
            if (counts.size <= 16) return ArrayMultiSet(counts)
            return ArrayMultiSet(counts) // TODO
        }

        fun <K> ofCounts(vararg counts: Pair<K, Int>): MultiSet<K> {
            val counts1 = counts.toMap()
            if (counts1.size <= 16) return ArrayMultiSet(counts1)
            return ArrayMultiSet(counts1) // TODO
        }

        fun <K> from(elements: Iterable<K>): MultiSet<K> {
            val elements1 = elements.toList()
            if (elements1.size <= 16) return ArrayMultiSet(elements1)
            return ArrayMultiSet(elements1) // TODO
        }
    }

    override val size: Int

    override fun iterator(): Iterator<Pair<K, Int>>

    val unrolledSize: Int
        get() {
            var total = 0
            for ((_, count) in this)
                total += count
            return total
        }

    fun toMap(): Map<K, Int> = (this as Map<K, Int>).toMap()

    fun toList(): List<Pair<K, Int>> {
        val res = mutableListOf<Pair<K, Int>>()
        for (pair in iterator())
            res.add(pair)
        return res.toList()
    }

    fun toUnrolledList(): List<K> {
        val lst = mutableListOf<K>()
        for ((element, count) in entries) {
            for (i in 0..<count)
                lst.add(element)
        }
        return lst
    }

    override val entries: Set<Map.Entry<K, Int>>
        get() {
            val res = mutableMapOf<K, Int>()
            for ((element, count) in iterator())
                res[element] = count
            return res.entries
        }

    override val values: Collection<Int>
        get() {
            val res = mutableSetOf<Int>()
            for ((_, count) in iterator())
                res.add(count)
            return res
        }

    override val keys: Set<K>
        get() {
            val res = mutableSetOf<K>()
            for ((element, _) in iterator())
                res.add(element)
            return res
        }

    override fun isEmpty(): Boolean =
        size == 0

    override fun containsKey(key: K): Boolean =
        getCount(key) != 0

    override fun containsValue(value: Int): Boolean {
        for ((_, count) in this)
            if (count == value)
                return true
        return false
    }

    fun contains(element: K): Boolean =
        getCount(element) != 0

    override fun get(key: K): Int? {
        val oldCount = getCount(key)
        if (oldCount == 0) return null
        return oldCount
    }

    fun union(other: MultiSet<K>): MultiSet<K>
    fun difference(other: MultiSet<K>): MultiSet<K>
    fun intersection(other: MultiSet<K>): MultiSet<K>

    fun symmetricDifference(other: MultiSet<K>): MultiSet<K> =
        (minus(other)).union(other.minus(this))

    operator fun plus(element: K): MultiSet<K> = union(of(element))
    operator fun plus(other: MultiSet<K>): MultiSet<K> = union(other)
    operator fun plus(other: Map<K, Int>): MultiSet<K> = union(fromMap(other))
    operator fun plus(elements: Collection<K>): MultiSet<K> = union(from(elements))

    operator fun minus(element: K): MultiSet<K> = difference(of(element))
    operator fun minus(other: MultiSet<K>): MultiSet<K> = difference(other)
    operator fun minus(other: Map<K, Int>): MultiSet<K> = difference(fromMap(other))
    operator fun minus(elements: Collection<K>): MultiSet<K> = difference(from(elements))

    infix fun and(other: MultiSet<K>): MultiSet<K> = intersection(other)
    infix fun and(other: Map<K, Int>): MultiSet<K> = intersection(fromMap(other))

    infix fun xor(other: MultiSet<K>): MultiSet<K> = symmetricDifference(other)

    fun equiv(other: Any?): Boolean {
        if (this === other) return true

        if (other is MultiSet<*>) {
            if (size != other.size) return false
            for ((element, count) in this) {
                if ((other as MultiSet<K>).getCount(element) != count)
                    return false
            }
            return true
        } else if (other is Map<*, *>) {
            if (size != other.size) return false
            for ((element, count) in this)
                if (other[element] != count)
                    return false
            return true
        }
        return false
    }

    enum class MergeMode {
        SUM, FIRST, LAST, MAX, MIN, PRODUCT, SUBTRACT
    }
}