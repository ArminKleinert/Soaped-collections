package kleinert.soap.multiset

import kotlin.math.max
import kotlin.math.min

class ArrayMultiSet<K> : MultiSet<K> {
    private val arr: Array<Node<K>>

//    constructor(counts: Collection<Pair<K, Int>>, mergeMode: MultiSet.MergeMode = MultiSet.MergeMode.FIRST) :
//            this(counts.map { Node(it.first, it.second) }, mergeMode)

    constructor(counts: Map<K, Int>) {
        arr = counts.map{(k,c)->Node(k, c)}.toTypedArray()
    }

    constructor(elements: Iterable<K>) : this(
        mutableMapOf<K, Int>().let {
            for (element in elements) {
                val currentCount = it[element]
                it[element] = currentCount?.inc() ?: 1
            }
            it
        }
    )

    override val size: Int
        get() = arr.size

    override fun getCount(element: K): Int {
        for (node in arr.asIterable())
            if (node.obj == element)
                return node.count
        return 0
    }

    override fun iterator(): Iterator<Pair<K, Int>> = iterator {
        for (node in arr)
            yield(node.obj to node.count)
    }

    override fun intersection(other: MultiSet<K>): MultiSet<K> {
        if (other.isEmpty()) return other
        if (isEmpty()) return this

        val m3 = mutableMapOf<K, Int>()
        for ((k, c) in this) {
            val c2 = other.getCount(k)
            val countMin = min(c, c2)
            if (countMin > 0) m3[k] = countMin
        }
        return MultiSet.fromMap(m3)
    }

    override fun difference(other: MultiSet<K>): MultiSet<K> {
        if (other.isEmpty()) return this
        if (isEmpty()) return other

        val m3 = mutableMapOf<K, Int>()
        for ((k, c) in this) {
            val c2 = other.getCount(k)
            val countDiff = c-c2
            if (countDiff > 0) m3[k] = countDiff
        }
        return MultiSet.fromMap(m3)
    }

    override fun union(other: MultiSet<K>): MultiSet<K> {
        if (other.isEmpty()) return this
        if (isEmpty()) return other

        val m3 = toMutableMap()
        m3.putAll(other)
        for ((k, _) in m3) {
            val c1 = getCount(k)
            val c2 = other.getCount(k)
            val countDiff = c1 + c2
            m3[k] = countDiff
        }
        return MultiSet.fromMap(m3)
    }

    override fun toString(): String =
        joinToString(", ", prefix = "{", postfix = "}") { (k, v) -> "${k}=${v}" }

    internal data class Node<K>(val obj: K, val count: Int)
}