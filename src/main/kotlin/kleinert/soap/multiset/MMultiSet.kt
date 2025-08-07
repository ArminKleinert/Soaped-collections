package kleinert.soap.multiset

interface MMultiSet<K> : MultiSet<K>, MutableMap<K, Int> {
    /**
     * If count is 0, remove the element from the map.
     * If count is negative, throw IllegalArgumentException.
     * Returns the old count.
     */
    fun setCount(element: K, count: Int): Int

    /**
     * If count is 0, do nothing.
     * Equivalent to setCount(element, count(element) + 1)
     */
    fun addCount(element: K, count: Int): Int

    /**
     * Equivalent to setCount(element, count(element) + 1)
     */
    fun increaseCount(element: K): Int = addCount(element, 1)

    /**
     * Equivalent to setCount(element, count(element) - 1)
     */
    fun decreaseCount(element: K): Int = addCount(element, -1)

    override val entries: MutableSet<MutableMap.MutableEntry<K, Int>>
        get() {
            val res = mutableMapOf<K, Int>()
            for ((element, count) in iterator())
                res[element] = count
            return res.entries
        }

    override val values: MutableCollection<Int>
        get() {
            val res = mutableSetOf<Int>()
            for ((_, count) in iterator())
                res.add(count)
            return res
        }

    override val keys: MutableSet<K>
        get() {
            val res = mutableSetOf<K>()
            for ((element, _) in iterator())
                res.add(element)
            return res
        }

    override fun clear() {
        for (entry in this)
            setCount(entry.first, 0)
    }

    override fun remove(key: K): Int? {
        val oldCount = getCount(key)
        if (oldCount > 0) {
            setCount(key, oldCount - 1)
            return oldCount
        }
        return null
    }

    override fun putAll(from: Map<out K, Int>) {
        for ((k, v) in from)
            put(k, v)
    }

    override fun put(key: K, value: Int): Int? {
        val oldCount = setCount(key, value)
        if (oldCount == 0) return null
        return oldCount
    }

    fun add(element: K): Boolean {
        val c = getCount(element)
        setCount(element, c + 1)
        return c == 0
    }

    fun addAll(elements: Collection<K>): Boolean {
        var res = false
        for (e in elements) res = res || add(e)
        return res
    }
}