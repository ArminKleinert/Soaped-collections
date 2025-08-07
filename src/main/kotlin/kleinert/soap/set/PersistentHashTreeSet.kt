package kleinert.soap.set

import kleinert.soap.cons.VList

class PersistentHashTreeSet<T> : PersistentSet<T> {
    private val root: TreeNode<T>

    constructor() : this(TreeNode.leaf())

    private constructor(root: TreeNode<T>) {
        this.root = root
    }

    companion object {
        fun <T> hash(e: T): Int = e.hashCode()

        fun <T> of(vararg elements: T): PersistentHashTreeSet<T> = from(elements.asIterable())

        fun <T> from(elements: Iterable<T>): PersistentHashTreeSet<T> =
            PersistentHashTreeSet<T>().conjAll(elements.distinct())
    }

    private var bufferedSize = -1

    override val size: Int
        get() {
            if (bufferedSize != -1) return bufferedSize
            bufferedSize = asIterable().count()
            return bufferedSize
        }

    override fun iterator(): Iterator<T> =
        toList().iterator()

    fun toList(): List<T> {
        val stack = mutableListOf<TreeNode.Node<T>>()
        val elements = mutableListOf<T>()

        if (root === TreeNode.Leaf)
            return listOf()

        require(root is TreeNode.Node<T>)
        stack.add(root)

        while (stack.isNotEmpty()) {
            val head = stack.last()
            stack.removeLast()

            elements.addAll(head.elements)

            if (head.left is TreeNode.Node<T>) stack.add(head.left)
            if (head.right is TreeNode.Node<T>) stack.add(head.right)
        }

        return elements
    }

    private fun toNodeList(node: TreeNode<T>): MutableList<TreeNode.Node<T>> {
        if (node === TreeNode.Leaf)
            return mutableListOf()

        val stack = mutableListOf<TreeNode.Node<T>>()
        val elements = mutableListOf<TreeNode.Node<T>>()

        require(node is TreeNode.Node<T>)
        stack.add(node)

        while (stack.isNotEmpty()) {
            val head = stack.last()
            stack.removeLast()

            if (head.left is TreeNode.Node<T>) stack.add(head.left)
            elements.add(head)
            if (head.right is TreeNode.Node<T>) stack.add(head.right)
        }

        elements.sortBy { it.hash }

        return elements
    }

    override fun withoutAll(elements: Collection<T>): PersistentHashTreeSet<T> {
        if (root === TreeNode.Leaf)
            return this

        var newRoot: TreeNode<T> = root
        for (element in elements.distinct()) {
            newRoot = removed(hash(element), element, newRoot)
        }

        return PersistentHashTreeSet(newRoot)
    }

    override fun conjAll(elements: Collection<T>): PersistentHashTreeSet<T> {
        var newRoot = root
        for (e in elements) {
            val h = hash(e)
            newRoot = insert(h, e, newRoot)
        }

        if (newRoot === root) return this

        require(newRoot !== TreeNode.Leaf)

        return PersistentHashTreeSet(balance(toNodeList(newRoot)))
    }

    private fun balance(elements: List<TreeNode.Node<T>>): TreeNode<T> {
        if (elements.isEmpty())
            return TreeNode.leaf()

        val halfLength = elements.size / 2
        val elementsLeft = balance(elements.take(halfLength))
        val elementMid = elements[halfLength]
        val elementRight = balance(elements.drop(halfLength + 1))

        return TreeNode.Node(elementMid.hash, elementMid.elements, elementsLeft, elementRight)
    }

    private fun insert(hash: Int, element: T, node: TreeNode<T>): TreeNode<T> =
        when (node) {
            TreeNode.Leaf ->
                leaf(element)

            is TreeNode.Node<T> ->
                if (hash < node.hash)
                    TreeNode.Node(node.hash, node.elements, insert(hash, element, node.left), node.right)
                else if (hash > node.hash)
                    TreeNode.Node(node.hash, node.elements, node.left, insert(hash, element, node.right))
                else if (element in node.elements) node
                else leaf(node.elements.cons(element))
        }

    private fun removed(hash: Int, element: T, node: TreeNode<T>): TreeNode<T> {
        if (node === TreeNode.Leaf) return TreeNode.leaf()
        require(node is TreeNode.Node<T>)

        if (hash < node.hash)
            return TreeNode.Node(node.hash, node.elements, removed(hash, element, node.left), node.right)

        if (hash > node.hash)
            return TreeNode.Node(node.hash, node.elements, node.left, removed(hash, element, node.right))

        if (element !in node.elements)
            return node

        val elements = node.elements - element
        
        if (elements.isNotEmpty())
            return TreeNode.Node(node.hash, VList.toVList(elements), node.left, node.right)

        val nodes = mutableListOf<TreeNode.Node<T>>()
        nodes.addAll(toNodeList(node.left))
        nodes.addAll(toNodeList(node.right))
        return balance(nodes)
    }

    override fun contains(element: T): Boolean =
        find(hash(element), element, root)

    private tailrec fun find(hash: Int, element: T, node: TreeNode<T>): Boolean =
        when (node) {
            TreeNode.Leaf ->
                false

            is TreeNode.Node<T> ->
                if (hash == node.hash) element in node.elements
                else if (hash < node.hash) find(hash, element, node.left)
                else find(hash, element, node.right)
        }

    override fun toString(): String {
        return "PersistentHashTreeSet(root=$root)"
    }

    internal sealed interface TreeNode<T> {
        data class Node<T>(
            val hash: Int, val elements: VList<T>, val left: TreeNode<T>, val right: TreeNode<T>
        ) : TreeNode<T>

        data object Leaf : TreeNode<Any?>

        companion object {
            fun <T> leaf(): TreeNode<T> = Leaf as TreeNode<T>
        }
    }

    private fun leaf(e: T) =
        TreeNode.Node(hash(e), VList.of(e), TreeNode.leaf(), TreeNode.leaf())

    private fun leaf(elements: VList<T>) =
        TreeNode.Node(elements.first().hashCode(), elements, TreeNode.leaf(), TreeNode.leaf())
}
