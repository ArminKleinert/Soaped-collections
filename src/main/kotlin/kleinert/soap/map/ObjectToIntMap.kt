package kleinert.soap.map
//
//import kotlin.math.max
//import kotlin.math.min
//
//class ObjectToIntMap<K> : MutableMap<K, Int> {
//    private val nodes: Array<Node<K>?>
//
//    companion object {
//        private fun nextPowerOfTwo(m: Int): Int {
//            var n = m
//            n--
//            n = n or (n shr 1)
//            n = n or (n shr 2)
//            n = n or (n shr 4)
//            n = n or (n shr 8)
//            n = n or (n shr 16)
//            n++
//            return n
//        }
//    }
//
//
//    private constructor(elements: Collection<Map.Entry<K, Int>>) {
//        nodes = Array(min(max(nextPowerOfTwo(elements.size / 2), 8), 512)) { null }
//        for ((key, value) in elements) {
//            val h = hash(key)
//            var nodeAt = nodes[h % nodes.size]
//            var newNode = Node(key, value, null)
//            while (nodeAt != null) {
//                if (key != nodeAt.key) newNode = Node(nodeAt.key, nodeAt.value, newNode)
//                nodeAt = nodeAt.next
//            }
//            nodes[h % nodes.size] = newNode
//        }
//    }
//
//    override val entries: MutableSet<MutableMap.MutableEntry<K, Int>>
//        get() = EntrySet(this)
//
//    override val keys: MutableSet<K>
//        get() = KeySet(this)
//
//    private var bufferedSize: Int = -1
//    override val size: Int
//        get() {
//            if (bufferedSize < 0) {
//                bufferedSize = 0
//                for (node in nodes) {
//                    var currentNode: Node<K>? = node
//                    while (currentNode != null) {
//                        bufferedSize++
//                        currentNode = currentNode.next
//                    }
//                }
//            }
//            return bufferedSize
//        }
//
//    override val values: MutableCollection<Int>
//        get() = asIterable().mapTo(mutableListOf()) { it.value }
//
//    override fun clear() = nodes.fill(null)
//
//    private fun getNode(key: K): Node<K>? {
//        TODO()
//    }
//
//    override fun get(key: K): Int? = getNode(key)?.value
//    override fun containsKey(key: K): Boolean = getNode(key) != null
//    override fun containsValue(value: Int): Boolean = asIterable().any { it.value == value }
//
//
//    override fun isEmpty(): Boolean = nodes.all { it == null }
//
//    override fun remove(key: K): Int? {
//        TODO("Not yet implemented")
//    }
//
//    override fun putAll(from: Map<out K, Int>) {
//        for ((k, v) in from)
//            put(k, v)
//    }
//
//    override fun put(key: K, value: Int): Int? = putInt(key, value)
//
//    override fun putInt(key: K, value: Int): Int? {
//        TODO()
//    }
//
//    private fun hash(key: K): Int {
//        if (key == null) return 0
//        val h = key.hashCode()
//        return h xor (h shr 16)
//    }
//
//    override fun getEntry(key: K): Map.Entry<K, V>? {
//        var subNode = nodes[hash(key) % nodes.size]
//        while (subNode != null) {
//            if (subNode.key == key) return subNode
//            subNode = subNode.next
//        }
//        return null
//    }
//
//    override fun iterator(): MutableIterator<Entry<K>> = iterator {
//        for (node in nodes) {
//            var currentNode: Node<K>? = node
//            while (currentNode != null) {
//                yield(Entry(currentNode.key, currentNode.value))
//                currentNode = currentNode.next
//            }
//        }
//    }
//
//    override fun hashCode(): Int =
//        nodes.contentHashCode()
//
//    override fun equals(other: Any?): Boolean {
//        if (this === other) return true
//        if (other !is Map<*, *>) return false
//        if (size != other.size) return false
//
//        if (other is ObjectToIntMap<*>) {
//            for ((k, v) in this)
//                if (!other.contains(k) || v != other[k])
//                    return false
//            return true
//        }
//
//        for ((k, v) in this)
//            if (!other.contains(k) || v != other[k])
//                return false
//
//        return true
//    }
//
//    internal data class Node<K>(override val key: K, override var value: Int, val next: Node<K>?) :
//        Entry<K> {
//        override fun setIntValue(newValue: Int): Int {
//            val temp = value
//            value = newValue
//            return temp
//        }
//    }
//
//    interface Entry<K> : MutableMap.MutableEntry<K, Int> {
//        override val key: K
//        override val value: Int
//
//        override fun setValue(newValue: Int): Int = setIntValue(newValue)
//        fun setIntValue(newValue: Int): Int
//    }
//
//    class EntrySet<K> internal constructor(private val parent: ObjectToIntMap<K>) :
//        AbstractMutableSet<MutableMap.MutableEntry<K, Int>>() {
//
//        override val size: Int
//            get() = parent.size
//
//        override fun add(element: MutableMap.MutableEntry<K, Int>): Boolean {
//            val old = parent[element.key]
//            parent[element.key] = element.value
//            return old != element.value
//        }
//
//        override fun iterator(): MutableIterator<MutableMap.MutableEntry<K, Int>> {
//            return parent.iterator()
//        }
//    }
//
//    class KeySet<K> internal constructor(private val parent: ObjectToIntMap<K>) :
//        AbstractMutableSet<K>() {
//
//        override val size: Int
//            get() = parent.size
//
//        override fun iterator(): MutableIterator<K> = object : MutableIterator<K> {
//            val parentIter = parent.iterator()
//            override fun hasNext(): Boolean = parentIter.hasNext()
//            override fun next(): K = parentIter.next().key
//            override fun remove() = parentIter.remove()
//        }
//
//        override fun add(element: K): Boolean = throw UnsupportedOperationException()
//    }
//}