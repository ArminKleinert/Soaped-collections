package kleinert.soap.cons

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions
import kotlin.random.Random

class ListPairTest {
    private val oneTwoThree = PersistentList.of(1, 2, 3)
    private val oneTwoThreeOneTwoThree = ListPair.concat(oneTwoThree, oneTwoThree)

    @Test
    fun testConstructor() {
        run {
            Assertions.assertTrue(ListPair.concat(nullCons<Int>(), nullCons()).isEmpty())
            Assertions.assertEquals(nullCons<Int>(), ListPair.concat(nullCons<Int>(), nullCons()))
        }
        run {
            val lst = nullCons<Int>().cons(1).cons(2).cons(3)
            Assertions.assertSame(lst, ListPair.concat(lst, nullCons()))
            Assertions.assertSame(lst, ListPair.concat(nullCons(), lst))
            Assertions.assertSame(lst, ListPair.concat(lst, VList.of()))
            Assertions.assertSame(lst, ListPair.concat(VList.of(), lst))
            Assertions.assertEquals(lst + lst, ListPair.concat(lst, lst))
        }
        run {
            val lst = PersistentList.of(1, 2, 3)
            Assertions.assertSame(lst, ListPair.concat(lst, nullCons()))
            Assertions.assertSame(lst, ListPair.concat(nullCons(), lst))
            Assertions.assertSame(lst, ListPair.concat(lst, VList.of()))
            Assertions.assertSame(lst, ListPair.concat(VList.of(), lst))
            Assertions.assertEquals(lst + lst, ListPair.concat(lst, lst))
        }
        run {
            val lst = PersistentWrapper.of(1, 2, 3)
            Assertions.assertSame(lst, ListPair.concat(lst, nullCons()))
            Assertions.assertSame(lst, ListPair.concat(nullCons(), lst))
            Assertions.assertSame(lst, ListPair.concat(lst, VList.of()))
            Assertions.assertSame(lst, ListPair.concat(VList.of(), lst))
            Assertions.assertEquals(lst + lst, ListPair.concat(lst, lst))
        }
        run {
            val lst = VList.of(1, 2, 3)
            Assertions.assertSame(lst, ListPair.concat(lst, nullCons()))
            Assertions.assertSame(lst, ListPair.concat(nullCons(), lst))
            Assertions.assertSame(lst, ListPair.concat(lst, VList.of()))
            Assertions.assertSame(lst, ListPair.concat(VList.of(), lst))
            Assertions.assertEquals(lst + lst, ListPair.concat(lst, lst))
        }
    }

    @Test
    fun getSize() {
        val lst = oneTwoThreeOneTwoThree
        Assertions.assertEquals(0, ListPair.concat(nullCons<Int>(), nullCons()).size)
        Assertions.assertEquals(oneTwoThree.size * 2, lst.size)
        Assertions.assertEquals(oneTwoThree.size * 3, ListPair.concat(oneTwoThree, lst).size)
    }

    @Test
    fun cons() {
        Assertions.assertEquals(PersistentList.of(1, 1, 2, 3, 1, 2, 3), oneTwoThreeOneTwoThree.cons(1))
    }

    @Test
    fun cdr() {
        val lst = oneTwoThreeOneTwoThree
        Assertions.assertEquals(PersistentList.of(1, 2, 3, 1, 2, 3), lst)
        Assertions.assertEquals(PersistentList.of(2, 3, 1, 2, 3), lst.cdr)
        Assertions.assertEquals(PersistentList.of(3, 1, 2, 3), lst.cdr.cdr)
        Assertions.assertEquals(PersistentList.of(1, 2, 3), lst.cdr.cdr.cdr)
        Assertions.assertSame(oneTwoThree, lst.cdr.cdr.cdr)
        Assertions.assertEquals(oneTwoThree.cdr, lst.cdr.cdr.cdr.cdr)
    }

    @Test
    fun car() {
        Assertions.assertEquals(1, oneTwoThreeOneTwoThree.car)
        Assertions.assertEquals(2, ListPair.concat(PersistentList.of(2, 3), oneTwoThree).car)
        Assertions.assertEquals(3, ListPair.concat(PersistentList.of(3), oneTwoThree).car)
        Assertions.assertEquals(oneTwoThree.car, ListPair.concat(PersistentList.of(), oneTwoThree).car)

    }

    @Test
    fun contains() {
        Assertions.assertFalse(oneTwoThreeOneTwoThree.contains(0))
        Assertions.assertTrue(oneTwoThreeOneTwoThree.contains(1))
        Assertions.assertTrue(oneTwoThreeOneTwoThree.contains(2))
        Assertions.assertTrue(oneTwoThreeOneTwoThree.contains(3))
        Assertions.assertFalse(oneTwoThreeOneTwoThree.contains(4))

        Assertions.assertTrue(ListPair.concat(oneTwoThree, PersistentList.of(4)).contains(4))
        Assertions.assertTrue(ListPair.concat(PersistentList.of(0), oneTwoThree).contains(0))
    }

    @Test
    fun containsAll() {
        Assertions.assertFalse(oneTwoThreeOneTwoThree.containsAll(listOf(0)))
        Assertions.assertTrue(oneTwoThreeOneTwoThree.containsAll(listOf(1)))
        Assertions.assertTrue(oneTwoThreeOneTwoThree.containsAll(listOf(2, 3)))
        Assertions.assertTrue(oneTwoThreeOneTwoThree.containsAll(listOf(1, 2, 3)))
        Assertions.assertFalse(oneTwoThreeOneTwoThree.containsAll(listOf(4)))

        Assertions.assertFalse(oneTwoThreeOneTwoThree.containsAll(listOf(1, 4)))
        Assertions.assertFalse(oneTwoThreeOneTwoThree.containsAll(listOf(1, 2, 3, 4)))

        Assertions.assertTrue(ListPair.concat(oneTwoThree, PersistentList.of(4)).containsAll(listOf(1, 2, 3, 4)))
        Assertions.assertTrue(ListPair.concat(PersistentList.of(0), oneTwoThree).containsAll(listOf(1, 2, 3, 0)))
    }

    @Test
    fun get() {
        Assertions.assertEquals(oneTwoThree.car, ListPair.concat(oneTwoThree, PersistentList.of(0, 8, 9))[0])
        Assertions.assertEquals(0, ListPair.concat(oneTwoThree, PersistentList.of(0, 8, 9))[3])
        Assertions.assertThrows(IndexOutOfBoundsException::class.java) { oneTwoThreeOneTwoThree[6] }
    }

    @Test
    fun isEmpty() {
        Assertions.assertFalse(oneTwoThreeOneTwoThree.isEmpty())
        Assertions.assertFalse(ListPair.concat(PersistentList.of(0), PersistentList.of(9)).cdr.isEmpty())
        Assertions.assertTrue(ListPair.concat(PersistentList.of(0), PersistentList.of(9)).cdr.cdr.isEmpty())
    }

    @Test
    fun indexOf() {
        Assertions.assertEquals(0, ListPair.concat(PersistentList.of(1, 2, 3), PersistentList.of(1, 2, 3)).indexOf(1))
        Assertions.assertEquals(1, ListPair.concat(PersistentList.of(1, 2, 3), PersistentList.of(1, 2, 3)).indexOf(2))
        Assertions.assertEquals(2, ListPair.concat(PersistentList.of(1, 2, 3), PersistentList.of(1, 2, 3)).indexOf(3))
        Assertions.assertEquals(-1, ListPair.concat(PersistentList.of(1, 2, 3), PersistentList.of(1, 2, 3)).indexOf(4))
    }

    @Test
    fun lastIndexOf() {
        Assertions.assertEquals(3, ListPair.concat(PersistentList.of(1, 2, 3), PersistentList.of(1, 2, 3)).lastIndexOf(1))
        Assertions.assertEquals(4, ListPair.concat(PersistentList.of(1, 2, 3), PersistentList.of(1, 2, 3)).lastIndexOf(2))
        Assertions.assertEquals(5, ListPair.concat(PersistentList.of(1, 2, 3), PersistentList.of(1, 2, 3)).lastIndexOf(3))
        Assertions.assertEquals(-1, ListPair.concat(PersistentList.of(1, 2, 3), PersistentList.of(1, 2, 3)).lastIndexOf(4))
    }

    @Test
    fun iterator() {
        run {
            var counter = 0
            for (e in oneTwoThreeOneTwoThree) {
                counter++
            }
            Assertions.assertEquals(6, counter)
        }
        run {
            var counter = 0
            oneTwoThreeOneTwoThree.forEach { _ -> counter++ }
            Assertions.assertEquals(6, counter)
        }

        run {
            val iterator = oneTwoThreeOneTwoThree.iterator()
            var sum = 0
            while (iterator.hasNext()) {
                sum += iterator.next()
            }
            Assertions.assertEquals(12, sum)
        }

        Assertions.assertEquals(12, oneTwoThreeOneTwoThree.sum())
        Assertions.assertEquals(12, oneTwoThreeOneTwoThree.fold(0, Int::plus))
        Assertions.assertEquals(12, oneTwoThreeOneTwoThree.foldRight(0, Int::plus))
        Assertions.assertEquals(12, oneTwoThreeOneTwoThree.reduce(Int::plus))
    }

    @Test
    fun listIterator() {
        run {
            var counter = 0
            for (e in oneTwoThreeOneTwoThree.listIterator()) {
                counter++
            }
            Assertions.assertEquals(6, counter)
        }
        run {
            var counter = 0
            oneTwoThreeOneTwoThree.listIterator().forEach { _ -> counter++ }
            Assertions.assertEquals(6, counter)
        }

        run {
            val iterator = oneTwoThreeOneTwoThree.listIterator()
            var sum = 0
            while (iterator.hasNext()) {
                sum += iterator.next()
            }
            Assertions.assertEquals(12, sum)
        }
    }

    @Test
    fun subList() {
        Assertions.assertThrows(IndexOutOfBoundsException::class.java) {
            oneTwoThreeOneTwoThree.subList(
                0,
                oneTwoThreeOneTwoThree.size + 1
            )
        }
        Assertions.assertEquals(oneTwoThreeOneTwoThree, oneTwoThreeOneTwoThree.subList(0, oneTwoThreeOneTwoThree.size))
        Assertions.assertEquals(oneTwoThree, oneTwoThreeOneTwoThree.subList(0, oneTwoThree.size))
        Assertions.assertEquals(PersistentList.of(2, 3, 4, 5), ListPair.concat(PersistentList.of(1, 2, 3), PersistentList.of(4, 5, 6)).subList(1, 5))
    }

    @Test
    fun toMutableList() {
        Assertions.assertEquals(
            (oneTwoThree.toMutableList() + oneTwoThree.toMutableList()).toMutableList(),
            oneTwoThreeOneTwoThree.toMutableList()
        )
    }

    @Test
    fun reversed() {
        Assertions.assertInstanceOf(PersistentList::class.java, oneTwoThreeOneTwoThree.reversed())
        Assertions.assertEquals(oneTwoThreeOneTwoThree, oneTwoThreeOneTwoThree.reversed().reversed())
        Assertions.assertEquals(PersistentList.of(6, 5, 4, 3, 2, 1), ListPair.concat(PersistentList.of(1, 2, 3), PersistentList.of(4, 5, 6)).reversed())
    }

    @Test
    fun map() {
        Assertions.assertInstanceOf(PersistentList::class.java, oneTwoThreeOneTwoThree.map { it })
        Assertions.assertEquals(oneTwoThreeOneTwoThree, oneTwoThreeOneTwoThree.map { it })
        Assertions.assertEquals(oneTwoThreeOneTwoThree.toList().map { it + 1 }, oneTwoThreeOneTwoThree.map { it + 1 })
    }

    @Test
    fun mapIndexed() {
        Assertions.assertInstanceOf(PersistentList::class.java, oneTwoThreeOneTwoThree.mapIndexed { i, e -> e + i })
        Assertions.assertEquals(oneTwoThreeOneTwoThree, oneTwoThreeOneTwoThree.mapIndexed { _, e -> e })
        Assertions.assertEquals(
            oneTwoThreeOneTwoThree.toList().mapIndexed { i, e -> e + i },
            oneTwoThreeOneTwoThree.mapIndexed { i, e -> e + i })
    }

    @Test
    fun filter() {
        Assertions.assertInstanceOf(PersistentList::class.java, oneTwoThreeOneTwoThree.filter { true })
        Assertions.assertInstanceOf(PersistentList::class.java, oneTwoThreeOneTwoThree.filter { false })

        Assertions.assertEquals(oneTwoThreeOneTwoThree, oneTwoThreeOneTwoThree.filter { true })
        Assertions.assertEquals(PersistentList.of<Int>(), oneTwoThreeOneTwoThree.filter { false })
        Assertions.assertEquals(
            ListPair.concat(oneTwoThree.filter { it % 2 == 1 }, oneTwoThree.filter { it % 2 == 1 }),
            oneTwoThreeOneTwoThree.filter { it % 2 == 1 })
        Assertions.assertEquals(
            ListPair.concat(oneTwoThree.filter { it % 2 == 0 }, oneTwoThree.filter { it % 2 == 0 }),
            oneTwoThreeOneTwoThree.filter { it % 2 == 0 })
    }

    @Test
    fun filterNot() {
        Assertions.assertInstanceOf(PersistentList::class.java, oneTwoThreeOneTwoThree.filterNot { true })
        Assertions.assertInstanceOf(PersistentList::class.java, oneTwoThreeOneTwoThree.filterNot { false })

        Assertions.assertEquals(oneTwoThreeOneTwoThree, oneTwoThreeOneTwoThree.filterNot { false })
        Assertions.assertEquals(PersistentList.of<Int>(), oneTwoThreeOneTwoThree.filterNot { true })
        Assertions.assertEquals(
            ListPair.concat(oneTwoThree.filterNot { it % 2 == 1 }, oneTwoThree.filterNot { it % 2 == 1 }),
            oneTwoThreeOneTwoThree.filterNot { it % 2 == 1 })
        Assertions.assertEquals(
            ListPair.concat(oneTwoThree.filterNot { it % 2 == 0 }, oneTwoThree.filterNot { it % 2 == 0 }),
            oneTwoThreeOneTwoThree.filterNot { it % 2 == 0 })
    }

    @Test
    fun flatMap() {
        Assertions.assertInstanceOf(PersistentList::class.java, oneTwoThreeOneTwoThree.flatMap { listOf(it) })
        Assertions.assertEquals(oneTwoThreeOneTwoThree, oneTwoThreeOneTwoThree.flatMap { listOf(it) })
        Assertions.assertEquals(oneTwoThreeOneTwoThree.map { it + 1 }, oneTwoThreeOneTwoThree.flatMap { listOf(it + 1) })
        Assertions.assertEquals(
            ListPair.concat(oneTwoThree.flatMap { listOf(it, it) }, oneTwoThree.flatMap { listOf(it, it) }),
            ListPair.concat(oneTwoThree, oneTwoThree).flatMap { listOf(it, it) })
        Assertions.assertEquals(
            ListPair.concat(oneTwoThree.flatMap { listOf<Int>() }, oneTwoThree.flatMap { listOf() }),
            ListPair.concat(oneTwoThree, oneTwoThree).flatMap { listOf<Int>() })
    }

    @Test
    fun take() {
        Assertions.assertInstanceOf(PersistentList::class.java, oneTwoThreeOneTwoThree.take(0))
        Assertions.assertInstanceOf(PersistentList::class.java, oneTwoThreeOneTwoThree.take(oneTwoThree.size))

        Assertions.assertEquals(PersistentList.of<Int>(), oneTwoThreeOneTwoThree.take(0))
        Assertions.assertEquals(oneTwoThree, oneTwoThreeOneTwoThree.take(oneTwoThree.size))

        Assertions.assertEquals(PersistentList.of(1, 2), ListPair.concat(PersistentList.of(1, 2, 3), PersistentList.of(4, 5, 6)).take(2))
        Assertions.assertEquals(PersistentList.of(1, 2, 3), ListPair.concat(PersistentList.of(1, 2, 3), PersistentList.of(4, 5, 6)).take(3))
        Assertions.assertEquals(PersistentList.of(1, 2, 3, 4), ListPair.concat(PersistentList.of(1, 2, 3), PersistentList.of(4, 5, 6)).take(4))

        Assertions.assertEquals(oneTwoThreeOneTwoThree, oneTwoThreeOneTwoThree.take(oneTwoThreeOneTwoThree.size))
        Assertions.assertEquals(oneTwoThreeOneTwoThree, oneTwoThreeOneTwoThree.take(999))
    }

    @Test
    fun drop() {
        Assertions.assertInstanceOf(PersistentList::class.java, oneTwoThreeOneTwoThree.drop(0))
        Assertions.assertInstanceOf(PersistentList::class.java, oneTwoThreeOneTwoThree.drop(oneTwoThree.size))

        Assertions.assertEquals(oneTwoThreeOneTwoThree, oneTwoThreeOneTwoThree.drop(0))
        Assertions.assertEquals(oneTwoThree, oneTwoThreeOneTwoThree.drop(oneTwoThree.size))

        Assertions.assertEquals(PersistentList.of(3, 4, 5, 6), ListPair.concat(PersistentList.of(1, 2, 3), PersistentList.of(4, 5, 6)).drop(2))
        Assertions.assertEquals(PersistentList.of(4, 5, 6), ListPair.concat(PersistentList.of(1, 2, 3), PersistentList.of(4, 5, 6)).drop(3))
        Assertions.assertEquals(PersistentList.of(5, 6), ListPair.concat(PersistentList.of(1, 2, 3), PersistentList.of(4, 5, 6)).drop(4))

        Assertions.assertEquals(PersistentList.of<Int>(), oneTwoThreeOneTwoThree.drop(oneTwoThreeOneTwoThree.size))
        Assertions.assertEquals(PersistentList.of<Int>(), oneTwoThreeOneTwoThree.drop(999))
    }

    @Test
    fun takeWhile() {
        Assertions.assertInstanceOf(PersistentList::class.java, oneTwoThreeOneTwoThree.takeWhile { true })
        Assertions.assertInstanceOf(PersistentList::class.java, oneTwoThreeOneTwoThree.takeWhile { false })
        Assertions.assertInstanceOf(
            PersistentList::class.java,
            ListPair.concat(PersistentList.of(1, 2, 3), PersistentList.of(4, 5, 6)).takeWhile { it % 2 == 1 })

        Assertions.assertEquals(oneTwoThreeOneTwoThree, oneTwoThreeOneTwoThree.takeWhile { true })
        Assertions.assertEquals(PersistentList.of<Int>(), oneTwoThreeOneTwoThree.takeWhile { false })
        Assertions.assertEquals(PersistentList.of<Int>(), ListPair.concat(PersistentList.of(1, 2, 3), PersistentList.of(4, 5, 6)).takeWhile { it < 0 })
        Assertions.assertEquals(PersistentList.of(1, 2), ListPair.concat(PersistentList.of(1, 2, 3), PersistentList.of(4, 5, 6)).takeWhile { it < 3 })
        Assertions.assertEquals(PersistentList.of(1, 2, 3), ListPair.concat(PersistentList.of(1, 2, 3), PersistentList.of(4, 5, 6)).takeWhile { it < 4 })
        Assertions.assertEquals(PersistentList.of(1, 2, 3, 4), ListPair.concat(PersistentList.of(1, 2, 3), PersistentList.of(4, 5, 6)).takeWhile { it < 5 })
        Assertions.assertEquals(
            PersistentList.of(1, 2, 3, 4, 5, 6),
            ListPair.concat(PersistentList.of(1, 2, 3), PersistentList.of(4, 5, 6)).takeWhile { it < 99 })
    }

    @Test
    fun dropWhile() {
        Assertions.assertInstanceOf(PersistentList::class.java, oneTwoThreeOneTwoThree.dropWhile { true })
        Assertions.assertInstanceOf(PersistentList::class.java, oneTwoThreeOneTwoThree.dropWhile { false })
        Assertions.assertInstanceOf(
            PersistentList::class.java,
            ListPair.concat(PersistentList.of(1, 2, 3), PersistentList.of(4, 5, 6)).dropWhile { it % 2 == 1 })

        Assertions.assertEquals(PersistentList.of<Int>(), oneTwoThreeOneTwoThree.dropWhile { true })
        Assertions.assertEquals(oneTwoThreeOneTwoThree, oneTwoThreeOneTwoThree.dropWhile { false })
        Assertions.assertEquals(
            PersistentList.of(1, 2, 3, 4, 5, 6),
            ListPair.concat(PersistentList.of(1, 2, 3), PersistentList.of(4, 5, 6)).dropWhile { it < 0 })
        Assertions.assertEquals(PersistentList.of(3, 4, 5, 6), ListPair.concat(PersistentList.of(1, 2, 3), PersistentList.of(4, 5, 6)).dropWhile { it < 3 })
        Assertions.assertEquals(PersistentList.of(4, 5, 6), ListPair.concat(PersistentList.of(1, 2, 3), PersistentList.of(4, 5, 6)).dropWhile { it < 4 })
        Assertions.assertEquals(PersistentList.of(5, 6), ListPair.concat(PersistentList.of(1, 2, 3), PersistentList.of(4, 5, 6)).dropWhile { it < 5 })
        Assertions.assertEquals(PersistentList.of<Int>(), ListPair.concat(PersistentList.of(1, 2, 3), PersistentList.of(4, 5, 6)).dropWhile { it < 99 })
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
        Assertions.assertInstanceOf(PersistentList::class.java, oneTwoThreeOneTwoThree.sortedBy { it })
        Assertions.assertEquals(PersistentList.of(1, 2, 3, 4, 5, 6), ListPair.concat(PersistentList.of(1, 2, 3), PersistentList.of(4, 5, 6)).sortedBy { it })
        Assertions.assertEquals(PersistentList.of(1, 2, 3, 4, 5, 6), ListPair.concat(PersistentList.of(4, 5, 6), PersistentList.of(1, 2, 3)).sortedBy { it })
        Assertions.assertEquals(PersistentList.of(6, 5, 4, 3, 2, 1), ListPair.concat(PersistentList.of(4, 5, 6), PersistentList.of(1, 2, 3)).sortedBy { -it })
    }

    @Test
    fun sortedByDescending() {
        Assertions.assertInstanceOf(PersistentList::class.java, oneTwoThreeOneTwoThree.sortedByDescending { it })
        Assertions.assertEquals(
            PersistentList.of(6, 5, 4, 3, 2, 1),
            ListPair.concat(PersistentList.of(1, 2, 3), PersistentList.of(4, 5, 6)).sortedByDescending { it })
        Assertions.assertEquals(
            PersistentList.of(6, 5, 4, 3, 2, 1),
            ListPair.concat(PersistentList.of(4, 5, 6), PersistentList.of(1, 2, 3)).sortedByDescending { it })
        Assertions.assertEquals(
            PersistentList.of(1, 2, 3, 4, 5, 6),
            ListPair.concat(PersistentList.of(4, 5, 6), PersistentList.of(1, 2, 3)).sortedByDescending { -it })
    }

    @Test
    fun sortedWith() {
        Assertions.assertInstanceOf(PersistentList::class.java, oneTwoThreeOneTwoThree.sortedWith { n, m -> n.compareTo(m) })
        Assertions.assertEquals(
            PersistentList.of(1, 2, 3, 4, 5, 6),
            ListPair.concat(PersistentList.of(1, 2, 3), PersistentList.of(4, 5, 6)).sortedWith { n, m -> n.compareTo(m) })
        Assertions.assertEquals(
            PersistentList.of(1, 2, 3, 4, 5, 6),
            ListPair.concat(PersistentList.of(4, 5, 6), PersistentList.of(1, 2, 3)).sortedWith { n, m -> n.compareTo(m) })
        Assertions.assertEquals(
            PersistentList.of(6, 5, 4, 3, 2, 1),
            ListPair.concat(PersistentList.of(4, 5, 6), PersistentList.of(1, 2, 3)).sortedWith { n, m -> -n.compareTo(m) })
    }

    @Test
    fun distinct() {
        Assertions.assertInstanceOf(PersistentList::class.java, oneTwoThreeOneTwoThree.distinct())
        Assertions.assertInstanceOf(PersistentList::class.java, ListPair.concat(PersistentList.of(1, 2, 3), PersistentList.of(1, 2, 3)).distinct())
        Assertions.assertEquals(PersistentList.of(1, 2, 3), ListPair.concat(PersistentList.of(1, 2, 3), PersistentList.of(3, 2, 1)).distinct())
        Assertions.assertEquals(PersistentList.of(3, 2, 1), ListPair.concat(PersistentList.of(3, 2, 1), PersistentList.of(1, 2, 3)).distinct())
    }

    @Test
    fun shuffled() {
        Assertions.assertInstanceOf(PersistentList::class.java, oneTwoThreeOneTwoThree.shuffled())

        val seed = 0xDEADBEEF
        val rand = Random(seed)

        val oneOneOne = PersistentList.of(1, 1, 1, 1, 1)
        val oneOneOneTimesTwo = ListPair.concat(oneOneOne, oneOneOne)
        Assertions.assertEquals(oneOneOneTimesTwo, oneOneOneTimesTwo.shuffled(rand))
    }

    @Test
    fun asSequence() {
        Assertions.assertInstanceOf(Sequence::class.java, oneTwoThreeOneTwoThree.asSequence())
        Assertions.assertInstanceOf(Sequence::class.java, oneTwoThreeOneTwoThree.asSequence().map { it + 1 })

        Assertions.assertEquals(oneTwoThreeOneTwoThree.toList(), oneTwoThreeOneTwoThree.asSequence().toList())

        Assertions.assertEquals(
            listOf(1, 2, 3, 1, 2, 3),
            ListPair.concat(PersistentList.of(1, 2, 3), PersistentList.of(1, 2, 3)).asSequence().toList()
        )
        Assertions.assertEquals(
            listOf(2, 3, 4, 2, 3, 4),
            ListPair.concat(PersistentList.of(1, 2, 3), PersistentList.of(1, 2, 3)).asSequence().map { it + 1 }.toList()
        )
    }

    @Test
    fun plusElement() {
        Assertions.assertInstanceOf(PersistentList::class.java, ListPair.concat(PersistentList.of(1, 2, 3), PersistentList.of(1, 2, 3)) + 1)

        Assertions.assertEquals(
            ListPair.concat(PersistentList.of(1, 2, 3), PersistentList.of(1, 2, 3, 1)),
            ListPair.concat(PersistentList.of(1, 2, 3), PersistentList.of(1, 2, 3)) + 1
        )
    }

    @Test
    fun plusIterable() {
        Assertions.assertInstanceOf(PersistentList::class.java, ListPair.concat(PersistentList.of(1, 2, 3), PersistentList.of(1, 2, 3)) + listOf(1))

        Assertions.assertEquals(
            ListPair.concat(PersistentList.of(1, 2, 3), PersistentList.of(1, 2, 3, 1)),
            ListPair.concat(PersistentList.of(1, 2, 3), PersistentList.of(1, 2, 3)) + listOf(1)
        )
        Assertions.assertEquals(
            ListPair.concat(PersistentList.of(1, 2, 3), PersistentList.of(1, 2, 3, 1, 2, 3)),
            ListPair.concat(PersistentList.of(1, 2, 3), PersistentList.of(1, 2, 3)) + listOf(1, 2, 3)
        )
        Assertions.assertEquals(
            ListPair.concat(PersistentList.of(1, 2, 3), PersistentList.of(1, 2, 3, 1, 2, 3)),
            ListPair.concat(PersistentList.of(1, 2, 3), PersistentList.of(1, 2, 3)) + (1..3)
        )
        Assertions.assertEquals(
            ListPair.concat(PersistentList.of(1, 2, 3), PersistentList.of(1, 2, 3, 1, 2, 3)),
            ListPair.concat(PersistentList.of(1, 2, 3), PersistentList.of(1, 2, 3)) + sequenceOf(1,2,3)
        )
        Assertions.assertEquals(
            ListPair.concat(PersistentList.of(1, 2, 3), PersistentList.of(1, 2, 3, 1, 2, 3)),
            ListPair.concat(PersistentList.of(1, 2, 3), PersistentList.of(1, 2, 3)) + PersistentList.of(1,2,3)
        )
    }

    @Test
    fun isSingleton() {
        Assertions.assertFalse(ListPair.concat(PersistentList.of(1, 2, 3), PersistentList.of(1, 2, 3)).isSingleton())
        Assertions.assertFalse(ListPair.concat(PersistentList.of(1, 2, 3), PersistentList.of(1, 2, 3)).drop(3).isSingleton())
        Assertions.assertTrue(ListPair.concat(PersistentList.of(1, 2, 3), PersistentList.of(1, 2, 3)).drop(5).isSingleton())
        Assertions.assertFalse(ListPair.concat(PersistentList.of(1, 2, 3), PersistentList.of(1, 2, 3)).drop(6).isSingleton())
    }
}