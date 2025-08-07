package kleinert.soap.cons

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions
import kotlin.random.Random

class PersistentListHeadTest {

    private val one = nullCons<Int>().cons(1)
    private val two = one.cons(2)
    private val three = two.cons(3)

    @Test
    fun testConstructor() {
        Assertions.assertEquals(listOf(1), PersistentListHead(listOf(1)))
        Assertions.assertEquals(listOf(1, 2), PersistentListHead(listOf(1, 2)))
        Assertions.assertEquals(listOf(1, 2, 3, 4), PersistentListHead(listOf(1, 2, 3, 4)))
        Assertions.assertEquals(PersistentListHead(1, nullCons()), PersistentListHead(1, nullCons()))
        Assertions.assertEquals(PersistentListHead(1, PersistentListHead(2, nullCons())), PersistentListHead(1, PersistentListHead(2, nullCons())))
    }

    @Test
    fun getSize() {
        Assertions.assertEquals(1, one.size)
        Assertions.assertEquals(2, two.size)
        Assertions.assertEquals(3, three.size)
    }

    @Test
    fun cons() {
        val list = nullCons<Int>()
        Assertions.assertEquals(0, list.size)

        Assertions.assertInstanceOf(PersistentList::class.java, one)
        Assertions.assertEquals(PersistentWrapper(listOf(1)), one)
        Assertions.assertEquals(PersistentWrapper(listOf(2, 1)), two)
        Assertions.assertEquals(PersistentWrapper(listOf(3, 2, 1)), three)
    }

    @Test
    fun cdr() {
        val one = nullCons<Int>().cons(1)
        val two = one.cons(2)
        val three = two.cons(3)
        Assertions.assertInstanceOf(PersistentList::class.java, one.cdr)
        Assertions.assertInstanceOf(PersistentList::class.java, two.cdr)
        Assertions.assertInstanceOf(PersistentList::class.java, three.cdr)

        Assertions.assertSame(one.cdr, two.cdr.cdr)
        Assertions.assertSame(one.cdr, three.cdr.cdr.cdr)
        Assertions.assertSame(one, two.cdr)
        Assertions.assertSame(two, three.cdr)
    }

    @Test
    fun car() {
        val one = nullCons<Int>().cons(1)
        val two = one.cons(2)
        val three = two.cons(3)

        Assertions.assertThrows(NoSuchElementException::class.java) { one.cdr.car }
        Assertions.assertThrows(NoSuchElementException::class.java) { two.cdr.cdr.car }
        Assertions.assertThrows(NoSuchElementException::class.java) { three.cdr.cdr.cdr.car }

        Assertions.assertEquals(1, one.car)
        Assertions.assertEquals(2, two.car)
        Assertions.assertEquals(3, three.car)
        Assertions.assertEquals(1, two.cdr.car)
        Assertions.assertEquals(2, three.cdr.car)
        Assertions.assertEquals(1, three.cdr.cdr.car)

        Assertions.assertSame(one.cdr, three.cdr.cdr.cdr)
        Assertions.assertSame(one, two.cdr)
        Assertions.assertSame(two, three.cdr)
    }

    @Test
    fun contains() {
        Assertions.assertFalse(one.contains(0))
        Assertions.assertTrue(one.contains(1))
        Assertions.assertFalse(one.contains(2))
        Assertions.assertFalse(one.contains(3))
        Assertions.assertFalse(one.contains(4))

        Assertions.assertFalse(two.contains(0))
        Assertions.assertTrue(two.contains(1))
        Assertions.assertTrue(two.contains(2))
        Assertions.assertFalse(two.contains(3))
        Assertions.assertFalse(two.contains(4))

        Assertions.assertFalse(three.contains(0))
        Assertions.assertTrue(three.contains(1))
        Assertions.assertTrue(three.contains(2))
        Assertions.assertTrue(three.contains(3))
        Assertions.assertFalse(three.contains(4))
    }

    @Test
    fun containsAll() {
        Assertions.assertTrue(one.containsAll(listOf(1)))
        Assertions.assertFalse(one.containsAll(listOf(2)))
        Assertions.assertFalse(one.containsAll(listOf(3)))
        Assertions.assertFalse(one.containsAll(listOf(1, 2)))
        Assertions.assertFalse(one.containsAll(listOf(2, 3)))
        Assertions.assertFalse(one.containsAll(listOf(1, 2, 3)))

        Assertions.assertTrue(two.containsAll(listOf(1)))
        Assertions.assertTrue(two.containsAll(listOf(2)))
        Assertions.assertFalse(two.containsAll(listOf(3)))
        Assertions.assertTrue(two.containsAll(listOf(1, 2)))
        Assertions.assertFalse(two.containsAll(listOf(2, 3)))
        Assertions.assertFalse(two.containsAll(listOf(1, 2, 3)))

        Assertions.assertTrue(three.containsAll(listOf(1)))
        Assertions.assertTrue(three.containsAll(listOf(2)))
        Assertions.assertTrue(three.containsAll(listOf(3)))
        Assertions.assertTrue(three.containsAll(listOf(1, 2)))
        Assertions.assertTrue(three.containsAll(listOf(2, 3)))
        Assertions.assertTrue(three.containsAll(listOf(1, 2, 3)))
    }

    @Test
    fun get() {
        Assertions.assertEquals(1, one[0])
        Assertions.assertThrows(IndexOutOfBoundsException::class.java) { one[1] }
        Assertions.assertThrows(IndexOutOfBoundsException::class.java) { one[2] }
        Assertions.assertThrows(IndexOutOfBoundsException::class.java) { one[3] }

        Assertions.assertEquals(2, two[0])
        Assertions.assertEquals(1, two[1])
        Assertions.assertThrows(IndexOutOfBoundsException::class.java) { two[2] }
        Assertions.assertThrows(IndexOutOfBoundsException::class.java) { two[3] }

        Assertions.assertEquals(3, three[0])
        Assertions.assertEquals(2, three[1])
        Assertions.assertEquals(1, three[2])
        Assertions.assertThrows(IndexOutOfBoundsException::class.java) { three[3] }
    }

    @Test
    fun isEmpty() {
        Assertions.assertFalse(one.isEmpty())
        Assertions.assertTrue(one.cdr.isEmpty())

        Assertions.assertFalse(two.isEmpty())
        Assertions.assertFalse(two.cdr.isEmpty())
        Assertions.assertTrue(two.cdr.cdr.isEmpty())

        Assertions.assertFalse(three.isEmpty())
        Assertions.assertFalse(three.cdr.isEmpty())
        Assertions.assertFalse(three.cdr.cdr.isEmpty())
        Assertions.assertTrue(three.cdr.cdr.cdr.isEmpty())
    }

    @Test
    fun indexOf() {
        Assertions.assertEquals(-1, one.indexOf(0))
        Assertions.assertEquals(0, one.indexOf(1))
        Assertions.assertEquals(-1, one.indexOf(2))
        Assertions.assertEquals(-1, one.indexOf(3))
        Assertions.assertEquals(-1, one.indexOf(4))

        Assertions.assertEquals(-1, two.indexOf(0))
        Assertions.assertEquals(1, two.indexOf(1))
        Assertions.assertEquals(0, two.indexOf(2))
        Assertions.assertEquals(-1, two.indexOf(3))
        Assertions.assertEquals(-1, two.indexOf(4))

        Assertions.assertEquals(-1, three.indexOf(0))
        Assertions.assertEquals(2, three.indexOf(1))
        Assertions.assertEquals(1, three.indexOf(2))
        Assertions.assertEquals(0, three.indexOf(3))
        Assertions.assertEquals(-1, three.indexOf(4))

        val threeConsOne = three.cons(1)
        Assertions.assertEquals(-1, threeConsOne.indexOf(0))
        Assertions.assertEquals(0, threeConsOne.indexOf(1))
        Assertions.assertEquals(2, threeConsOne.indexOf(2))
        Assertions.assertEquals(1, threeConsOne.indexOf(3))
        Assertions.assertEquals(-1, threeConsOne.indexOf(4))
    }

    @Test
    fun lastIndexOf() {
        Assertions.assertEquals(-1, one.lastIndexOf(0))
        Assertions.assertEquals(0, one.lastIndexOf(1))
        Assertions.assertEquals(-1, one.lastIndexOf(2))
        Assertions.assertEquals(-1, one.lastIndexOf(3))
        Assertions.assertEquals(-1, one.lastIndexOf(4))

        Assertions.assertEquals(-1, two.lastIndexOf(0))
        Assertions.assertEquals(1, two.lastIndexOf(1))
        Assertions.assertEquals(0, two.lastIndexOf(2))
        Assertions.assertEquals(-1, two.lastIndexOf(3))
        Assertions.assertEquals(-1, two.lastIndexOf(4))

        Assertions.assertEquals(-1, three.lastIndexOf(0))
        Assertions.assertEquals(2, three.lastIndexOf(1))
        Assertions.assertEquals(1, three.lastIndexOf(2))
        Assertions.assertEquals(0, three.lastIndexOf(3))
        Assertions.assertEquals(-1, three.lastIndexOf(4))

        val threeConsOne = three.cons(1)
        Assertions.assertEquals(-1, threeConsOne.lastIndexOf(0))
        Assertions.assertEquals(3, threeConsOne.lastIndexOf(1))
        Assertions.assertEquals(2, threeConsOne.lastIndexOf(2))
        Assertions.assertEquals(1, threeConsOne.lastIndexOf(3))
        Assertions.assertEquals(-1, threeConsOne.lastIndexOf(4))
    }

    @Test
    fun iterator() {
        run {
            var counter = 0
            for (e in one.cdr) {
                counter++
            }
            Assertions.assertEquals(0, counter)
        }
        run {
            var counter = 0
            for (e in three) {
                counter++
            }
            Assertions.assertEquals(3, counter)
        }
        run {
            var counter = 0
            PersistentWrapper(three).forEach { _ -> counter++ }
            Assertions.assertEquals(3, counter)
        }

        run {
            val iterator = three.iterator()
            var sum = 0
            while (iterator.hasNext()) {
                sum += iterator.next()
            }
            Assertions.assertEquals(6, sum)
        }

        Assertions.assertEquals(6, three.sum())
        Assertions.assertEquals(6, three.fold(0, Int::plus))
        Assertions.assertEquals(6, three.foldRight(0, Int::plus))
        Assertions.assertEquals(6, three.reduce(Int::plus))
    }

    @Test
    fun listIterator() {
        run {
            var counter = 0
            for (e in one.cdr.listIterator()) {
                counter++
            }
            Assertions.assertEquals(0, counter)
        }
        run {
            var counter = 0
            for (e in three.listIterator()) {
                counter++
            }
            Assertions.assertEquals(3, counter)
        }
        run {
            var counter = 0
            three.listIterator().forEach { _ -> counter++ }
            Assertions.assertEquals(3, counter)
        }

        run {
            val iterator = three.listIterator()
            var sum = 0
            while (iterator.hasNext()) {
                sum += iterator.next()
            }
            Assertions.assertEquals(6, sum)
        }
    }

    @Test
    fun subList() {
        Assertions.assertThrows(IndexOutOfBoundsException::class.java) { three.subList(0, 5) }
        Assertions.assertInstanceOf(PersistentList::class.java, three.subList(0, 2))
        Assertions.assertEquals(three, three.subList(0, 3))
        Assertions.assertEquals(two, three.subList(1, 3))
        Assertions.assertEquals(one, three.subList(2, 3))
    }

    @Test
    fun toMutableList() {
        Assertions.assertEquals(mutableListOf(1), one.toMutableList())
        Assertions.assertEquals(mutableListOf(2, 1), two.toMutableList())
        Assertions.assertEquals(mutableListOf(3, 2, 1), three.toMutableList())
    }

    @Test
    fun reversed() {
        Assertions.assertInstanceOf(PersistentList::class.java, one.reversed())
        Assertions.assertInstanceOf(PersistentList::class.java, two.reversed())
        Assertions.assertInstanceOf(PersistentList::class.java, three.reversed())

        Assertions.assertEquals(three, three.reversed().reversed())

        Assertions.assertEquals(PersistentList.of(1), one.reversed())
        Assertions.assertEquals(PersistentList.of(1, 2), two.reversed())
        Assertions.assertEquals(PersistentList.of(1, 2, 3), three.reversed())
    }

    @Test
    fun map() {
        Assertions.assertInstanceOf(PersistentList::class.java, one.map { it })
        Assertions.assertInstanceOf(PersistentList::class.java, two.map { it })
        Assertions.assertInstanceOf(PersistentList::class.java, three.map { it })

        Assertions.assertEquals(PersistentList.of(1), one.map { it })
        Assertions.assertEquals(PersistentList.of(3, 2), two.map { it + 1 })
    }

    @Test
    fun mapIndexed() {
        Assertions.assertInstanceOf(PersistentList::class.java, one.mapIndexed { i, e -> e + i })
        Assertions.assertInstanceOf(PersistentList::class.java, two.mapIndexed { i, e -> e + i })
        Assertions.assertInstanceOf(PersistentList::class.java, three.mapIndexed { i, e -> e + i })

        Assertions.assertEquals(PersistentList.of(1), one.mapIndexed { i, e -> e + i })
        Assertions.assertEquals(PersistentList.of(2, 2), two.mapIndexed { i, e -> e + i })
        Assertions.assertEquals(PersistentList.of(3, 3, 3), three.mapIndexed { i, e -> e + i })
        Assertions.assertEquals(PersistentList.of(4, 3, 2), three.mapIndexed { _, e -> e + 1 })
    }

    @Test
    fun filter() {
        Assertions.assertInstanceOf(PersistentList::class.java, one.filter { true })
        Assertions.assertInstanceOf(PersistentList::class.java, three.filter { it % 2 != 0 })

        Assertions.assertEquals(PersistentList.of<Int>(), one.filter { it % 2 == 0 })
        Assertions.assertEquals(PersistentList.of(1), one.filter { it % 2 != 0 })
        Assertions.assertEquals(PersistentList.of(1), two.filter { it % 2 != 0 })
        Assertions.assertEquals(PersistentList.of(3, 1), three.filter { it % 2 != 0 })
    }

    @Test
    fun filterNot() {
        Assertions.assertInstanceOf(PersistentList::class.java, one.filterNot { true })
        Assertions.assertInstanceOf(PersistentList::class.java, three.filterNot { it % 2 != 0 })

        Assertions.assertEquals(PersistentList.of<Int>(), one.filterNot { it % 2 != 0 })
        Assertions.assertEquals(PersistentList.of(1), one.filterNot { it % 2 == 0 })
        Assertions.assertEquals(PersistentList.of(1), two.filterNot { it % 2 == 0 })
        Assertions.assertEquals(PersistentList.of(3, 1), three.filterNot { it % 2 == 0 })
    }

    @Test
    fun cadr() {
        Assertions.assertThrows(NoSuchElementException::class.java) { one.cadr }
        Assertions.assertEquals(1, two.cadr)
        Assertions.assertEquals(2, three.cadr)
        Assertions.assertEquals(3, three.cons(4).cadr)
    }

    @Test
    fun caddr() {
        Assertions.assertThrows(NoSuchElementException::class.java) { one.caddr }
        Assertions.assertThrows(NoSuchElementException::class.java) { two.caddr }
        Assertions.assertEquals(1, three.caddr)
        Assertions.assertEquals(2, three.cons(4).caddr)
    }

    @Test
    fun cadddr() {
        Assertions.assertThrows(NoSuchElementException::class.java) { one.cadddr }
        Assertions.assertThrows(NoSuchElementException::class.java) { two.cadddr }
        Assertions.assertThrows(NoSuchElementException::class.java) { three.cadddr }
        Assertions.assertEquals(1, three.cons(4).cadddr)
    }

    @Test
    fun cddr() {
        Assertions.assertEquals(nullCons<Int>(), one.cddr)
        Assertions.assertEquals(nullCons<Int>(), two.cddr)
        Assertions.assertEquals(PersistentList.of(1), three.cddr)
        Assertions.assertEquals(PersistentList.of(2, 1), three.cons(4).cddr)
        Assertions.assertEquals(PersistentList.of(3, 2, 1), three.cons(4).cons(5).cddr)
    }

    @Test
    fun cdddr() {
        Assertions.assertEquals(nullCons<Int>(), one.cdddr)
        Assertions.assertEquals(nullCons<Int>(), two.cdddr)
        Assertions.assertEquals(nullCons<Int>(), three.cdddr)
        Assertions.assertEquals(PersistentList.of(1), three.cons(4).cdddr)
        Assertions.assertEquals(PersistentList.of(2, 1), three.cons(4).cons(5).cdddr)
    }

    @Test
    fun cddddr() {
        Assertions.assertEquals(nullCons<Int>(), one.cddddr)
        Assertions.assertEquals(nullCons<Int>(), two.cddddr)
        Assertions.assertEquals(nullCons<Int>(), three.cddddr)
        Assertions.assertEquals(nullCons<Int>(), three.cons(4).cddddr)
        Assertions.assertEquals(PersistentList.of(1), three.cons(4).cons(5).cddddr)
    }

    @Test
    fun flatMap() {
        Assertions.assertInstanceOf(PersistentList::class.java, three.flatMap { listOf(it) })
        Assertions.assertEquals(three, three.flatMap { listOf(it) })
        Assertions.assertEquals(PersistentList.of(3, 3, 2, 2, 1, 1), three.flatMap { listOf(it, it) })
    }

    @Test
    fun take() {
        Assertions.assertInstanceOf(PersistentList::class.java, three.take(0))
        Assertions.assertInstanceOf(PersistentList::class.java, three.take(1))
        Assertions.assertInstanceOf(PersistentList::class.java, three.take(2))
        Assertions.assertInstanceOf(PersistentList::class.java, three.take(22))

        Assertions.assertEquals(PersistentList.of<Int>(), three.take(0))
        Assertions.assertEquals(PersistentList.of(3), three.take(1))
        Assertions.assertEquals(PersistentList.of(3, 2), three.take(2))
        Assertions.assertEquals(PersistentList.of(3, 2, 1), three.take(3))
        Assertions.assertEquals(PersistentList.of(3, 2, 1), three.take(4))
    }

    @Test
    fun drop() {
        Assertions.assertInstanceOf(PersistentList::class.java, three.drop(0))
        Assertions.assertInstanceOf(PersistentList::class.java, three.drop(1))
        Assertions.assertInstanceOf(PersistentList::class.java, three.drop(2))
        Assertions.assertInstanceOf(PersistentList::class.java, three.drop(22))

        Assertions.assertEquals(three, three.drop(0))
        Assertions.assertEquals(two, three.drop(1))
        Assertions.assertEquals(one, three.drop(2))
        Assertions.assertEquals(PersistentList.of<Int>(), three.drop(3))
        Assertions.assertEquals(PersistentList.of<Int>(), three.drop(4))
    }

    @Test
    fun takeWhile() {
        Assertions.assertInstanceOf(PersistentList::class.java, three.takeWhile { true })
        Assertions.assertInstanceOf(PersistentList::class.java, three.takeWhile { false })
        Assertions.assertInstanceOf(PersistentList::class.java, three.takeWhile { it % 2 == 1 })

        Assertions.assertEquals(three, three.takeWhile { true })
        Assertions.assertEquals(PersistentList.of(3), three.takeWhile { it % 2 == 1 })
        Assertions.assertEquals(PersistentList.of<Int>(), three.takeWhile { false })
    }

    @Test
    fun dropWhile() {
        Assertions.assertInstanceOf(PersistentList::class.java, three.dropWhile { true })
        Assertions.assertInstanceOf(PersistentList::class.java, three.dropWhile { false })
        Assertions.assertInstanceOf(PersistentList::class.java, three.dropWhile { it % 2 == 1 })

        Assertions.assertEquals(three, three.dropWhile { false })
        Assertions.assertEquals(two, three.dropWhile { it % 2 == 1 })
        Assertions.assertEquals(PersistentList.of<Int>(), three.dropWhile { true })
    }

//    @Test
//    fun sorted() {
//        Assertions.assertInstanceOf(Cons::class.java, (CdrCodedList.of<Int>() as Cons<Int>).sorted())
//        Assertions.assertInstanceOf(Cons::class.java, CdrCodedList.of<Int>().sorted())
//        Assertions.assertEquals(CdrCodedList.of<Int>(), CdrCodedList.of<Int>().sorted())
//        Assertions.assertInstanceOf(Cons::class.java, CdrCodedList.of(1, 2, 3).sorted())
//        Assertions.assertEquals(CdrCodedList.of(1, 2, 3), CdrCodedList.of(1, 2, 3).sorted())
//        Assertions.assertEquals(CdrCodedList.of(1, 2, 3), CdrCodedList.of(3, 2, 1).sorted())
//        Assertions.assertEquals(CdrCodedList.of(1, 2, 3, 3), CdrCodedList.of(3, 2, 3, 1).sorted())
//    }

//    @Test
//    fun sortedDescending() {
//        Assertions.assertInstanceOf(Cons::class.java, CdrCodedList.of<Int>().sortedDescending())
//        Assertions.assertEquals(CdrCodedList.of<Int>(), CdrCodedList.of<Int>().sortedDescending())
//        Assertions.assertInstanceOf(Cons::class.java, CdrCodedList.of(1, 2, 3).sortedDescending())
//        Assertions.assertEquals(CdrCodedList.of(3, 2, 1), CdrCodedList.of(1, 2, 3).sortedDescending())
//        Assertions.assertEquals(CdrCodedList.of(3, 2, 1), CdrCodedList.of(3, 2, 1).sortedDescending())
//        Assertions.assertEquals(CdrCodedList.of(3, 3, 2, 1), CdrCodedList.of(3, 2, 3, 1).sortedDescending())
//    }

    @Test
    fun sortedBy() {
        Assertions.assertInstanceOf(PersistentList::class.java, one.sortedBy { it })
        Assertions.assertInstanceOf(PersistentList::class.java, three.sortedBy { it })

        Assertions.assertEquals(PersistentList.of(1), one.sortedBy { it })
        Assertions.assertEquals(PersistentList.of(1, 2, 3), three.sortedBy { it })
        Assertions.assertEquals(PersistentList.of(1, 2, 3), three.reversed().sortedBy { it })
    }

    @Test
    fun sortedByDescending() {
        Assertions.assertInstanceOf(PersistentList::class.java, one.sortedByDescending { it })
        Assertions.assertInstanceOf(PersistentList::class.java, three.sortedByDescending { it })

        Assertions.assertEquals(PersistentList.of(1), one.sortedByDescending { it })
        Assertions.assertEquals(PersistentList.of(3, 2, 1), three.sortedByDescending { it })
        Assertions.assertEquals(PersistentList.of(3, 2, 1), three.reversed().sortedByDescending { it })
    }

    @Test
    fun sortedWith() {
        Assertions.assertInstanceOf(PersistentList::class.java, one.sortedWith { n, m -> n.compareTo(m) })
        Assertions.assertInstanceOf(PersistentList::class.java, three.sortedWith { n, m -> n.compareTo(m) })
        Assertions.assertInstanceOf(PersistentList::class.java, one.sortedWith { n, m -> -n.compareTo(m) })
        Assertions.assertInstanceOf(PersistentList::class.java, three.sortedWith { n, m -> -n.compareTo(m) })

        Assertions.assertEquals(PersistentList.of(1), one.sortedWith { n, m -> n.compareTo(m) })
        Assertions.assertEquals(PersistentList.of(1, 2, 3), three.sortedWith { n, m -> n.compareTo(m) })
        Assertions.assertEquals(PersistentList.of(1, 2, 3), three.reversed().sortedWith { n, m -> n.compareTo(m) })
        Assertions.assertEquals(PersistentList.of(1), one.sortedWith { n, m -> -n.compareTo(m) })
        Assertions.assertEquals(PersistentList.of(3, 2, 1), three.sortedWith { n, m -> -n.compareTo(m) })
        Assertions.assertEquals(PersistentList.of(3, 2, 1), three.reversed().sortedWith { n, m -> -n.compareTo(m) })
    }

    @Test
    fun distinct() {
        Assertions.assertInstanceOf(PersistentList::class.java, three.distinct())
        Assertions.assertInstanceOf(PersistentList::class.java, two.cons(1).distinct())

        Assertions.assertEquals(three, three.distinct())
        Assertions.assertEquals(nullCons<Int>().cons(2).cons(1), two.cons(1).distinct())
    }

    @Test
    fun shuffled() {
        Assertions.assertInstanceOf(PersistentList::class.java, three.shuffled())

        val seed = 0xDEADBEEF
        val rand = Random(seed)

        Assertions.assertEquals(one, one.shuffled(rand))

        val oneOneOne = nullCons<Int>().cons(1).cons(1).cons(1)
        Assertions.assertEquals(oneOneOne, oneOneOne.shuffled(rand))
    }

    @Test
    fun asSequence() {
        Assertions.assertInstanceOf(Sequence::class.java, one.asSequence())
        Assertions.assertInstanceOf(Sequence::class.java, two.asSequence())
        Assertions.assertInstanceOf(Sequence::class.java, three.asSequence())
        Assertions.assertInstanceOf(Sequence::class.java, three.asSequence().map { it + 1 })

        Assertions.assertEquals(listOf(1), one.asSequence().toList())
        Assertions.assertEquals(listOf(2, 1), two.asSequence().toList())
        Assertions.assertEquals(listOf(4, 3, 2), three.asSequence().map { it + 1 }.toList())
    }

    @Test
    fun plusElement() {
        Assertions.assertInstanceOf(PersistentList::class.java, one + 1)
        Assertions.assertInstanceOf(PersistentList::class.java, two + 2)

        Assertions.assertEquals(PersistentList.of(1, 1), one + 1)
        Assertions.assertEquals(PersistentList.of(3, 2, 1, 2), three + 2)
    }

    @Test
    fun plusIterable() {
        Assertions.assertInstanceOf(PersistentList::class.java, one + listOf(2, 3))

        Assertions.assertEquals(three, three + listOf())

        Assertions.assertEquals(PersistentList.of(2, 1), nullCons<Int>().cons(2) + listOf(1))
        Assertions.assertEquals(PersistentList.of(1, 2, 3), nullCons<Int>().cons(1) + listOf(2, 3))

        Assertions.assertEquals(PersistentList.of(1, 2, 3, 4, 5), one + (2..5))
        Assertions.assertEquals(PersistentList.of(1, 2, 3, 4, 5), one + (2..<6))
        Assertions.assertEquals(PersistentList.of(1, 2, 3, 4, 5), one + sequenceOf(2, 3, 4, 5))

        Assertions.assertEquals(PersistentList.of(1, 2, 3, 4, 5), one + PersistentList.of(2, 3, 4, 5))
        Assertions.assertEquals(PersistentList.of(3, 2, 1, 3, 4), three + PersistentWrapper(listOf(3, 4)))
    }

    @Test
    fun isSingleton() {
        Assertions.assertFalse(one.cdr.isSingleton())
        Assertions.assertTrue(one.isSingleton())
        Assertions.assertFalse(two.isSingleton())
        Assertions.assertFalse(three.isSingleton())
    }
}