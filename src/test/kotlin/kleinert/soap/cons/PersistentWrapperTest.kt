package kleinert.soap.cons

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import kotlin.random.Random

class PersistentWrapperTest {
    @Test
    fun testConstructor() {
        Assertions.assertTrue(PersistentWrapper.of<Int>() == PersistentWrapper(listOf<Int>()))
        Assertions.assertTrue(PersistentWrapper<Int>(listOf()) == PersistentWrapper(listOf<Int>()))
        Assertions.assertTrue(PersistentWrapper(listOf<Int>().asIterable()) == PersistentWrapper(listOf<Int>()))
        Assertions.assertTrue(PersistentWrapper(PersistentWrapper.of<Int>()) == PersistentWrapper(listOf<Int>()))

        Assertions.assertTrue(PersistentWrapper(listOf(1)) == PersistentWrapper(listOf(1)))
        Assertions.assertTrue(PersistentWrapper(listOf(1).asIterable()) == PersistentWrapper(listOf(1)))

        Assertions.assertTrue(PersistentWrapper(listOf(1, 2, 3, 4, 5)) == PersistentWrapper(listOf(1, 2, 3, 4, 5)))
        Assertions.assertTrue(PersistentWrapper(listOf(1, 2, 3, 4, 5).asIterable()) == PersistentWrapper(listOf(1, 2, 3, 4, 5)))
    }

    @Test
    fun getSize() {
        Assertions.assertEquals(0, PersistentWrapper.of<Int>().size)
        Assertions.assertEquals(0, PersistentWrapper.of<Int>().size)
        Assertions.assertEquals(1, PersistentWrapper.of(99).size)
        Assertions.assertEquals(5, PersistentWrapper.of(1, 2, 3, 4, 5).size)
        Assertions.assertEquals(55, PersistentWrapper((1..55).toList()).size)
    }

    @Test
    fun cons() {
        val list = PersistentWrapper.of<Int>()
        Assertions.assertEquals(0, list.size)

        Assertions.assertInstanceOf(PersistentList::class.java, list.cons(1))
        Assertions.assertEquals(PersistentWrapper(listOf(1)), list.cons(1))
        Assertions.assertEquals(PersistentWrapper(listOf(2, 1)), list.cons(1).cons(2))
        Assertions.assertEquals(PersistentWrapper(listOf(3, 2, 1)), list.cons(1).cons(2).cons(3))
    }

    @Test
    fun cdr() {
        Assertions.assertInstanceOf(PersistentWrapper::class.java, PersistentWrapper.of<Int>().cdr)
        Assertions.assertInstanceOf(PersistentWrapper::class.java, PersistentWrapper.of(2, 1).cdr)
        Assertions.assertEquals(PersistentWrapper.of<Int>(), PersistentWrapper.of<Int>().cdr)
        Assertions.assertEquals(PersistentWrapper.of(1), PersistentWrapper.of(2, 1).cdr)
        Assertions.assertEquals(PersistentWrapper(2..5), PersistentWrapper(1..5).cdr)
    }

    @Test
    fun car() {
        Assertions.assertThrows(NoSuchElementException::class.java) { PersistentWrapper.of<Int>().car }
        Assertions.assertEquals(1, PersistentWrapper.of(1).car)
        Assertions.assertEquals(2, PersistentWrapper.of(2, 1).car)
        Assertions.assertEquals((0..99).first(), PersistentWrapper(0..99).car)
        Assertions.assertEquals(5, PersistentWrapper.of(1, 2, 3, 4).cons(5).car)

        Assertions.assertThrows(NoSuchElementException::class.java) { PersistentWrapper.of<Int>().first() }
        Assertions.assertEquals(1, PersistentWrapper.of(1).first())
        Assertions.assertEquals(2, PersistentWrapper.of(2, 1).first())
        Assertions.assertEquals((0..99).first(), PersistentWrapper(0..99).first())
        Assertions.assertEquals(5, PersistentWrapper.of(1, 2, 3, 4).cons(5).first())
    }

    @Test
    fun contains() {
        Assertions.assertFalse(PersistentWrapper.of<Int>().contains(1))
        Assertions.assertFalse(PersistentWrapper.of<String>().contains(""))
        Assertions.assertFalse(PersistentWrapper<Int>(listOf()).contains(1))
        Assertions.assertFalse(PersistentWrapper(listOf(2, 1, 3)).contains(5))

        Assertions.assertTrue(PersistentWrapper(listOf(1)).contains(1))
        Assertions.assertTrue(PersistentWrapper(listOf(2, 1, 3)).contains(1))
    }

    @Test
    fun containsAll() {
        Assertions.assertTrue(PersistentWrapper.of<Int>().containsAll(listOf()))

        Assertions.assertFalse(PersistentWrapper.of<Int>().containsAll(listOf(1)))
        Assertions.assertFalse(PersistentWrapper.of<String>().containsAll(listOf("")))
        Assertions.assertFalse(PersistentWrapper<Int>(listOf()).containsAll(listOf(1)))
        Assertions.assertFalse(PersistentWrapper(listOf(2, 1, 3)).containsAll(listOf(5)))

        Assertions.assertTrue(PersistentWrapper(listOf(1)).containsAll(listOf(1)))
        Assertions.assertTrue(PersistentWrapper(listOf(2, 1, 3)).containsAll(listOf(1)))

        Assertions.assertTrue(PersistentWrapper(0..99).containsAll(listOf(10, 20, 30, 50, 60, 70, 80, 90)))
        Assertions.assertTrue(PersistentWrapper(0..99).containsAll(setOf(10, 20, 30, 50, 60, 70, 80, 90)))
        Assertions.assertTrue(PersistentWrapper(0..99).containsAll(PersistentWrapper.of(10, 20, 30, 50, 60, 70, 80, 90)))
    }

    @Test
    fun get() {
        Assertions.assertThrows(IndexOutOfBoundsException::class.java) { PersistentWrapper.of<Int>()[0] }
        Assertions.assertThrows(IndexOutOfBoundsException::class.java) { PersistentWrapper.of(1)[1] }
        Assertions.assertThrows(IndexOutOfBoundsException::class.java) { PersistentWrapper.of(1, 2, 3, 4, 5)[10] }

        Assertions.assertEquals(1, PersistentWrapper.of(1)[0])
        Assertions.assertEquals(2, PersistentWrapper.of(2, 1)[0])
        Assertions.assertEquals((0..99).first(), PersistentWrapper(0..99)[0])
        Assertions.assertEquals(5, PersistentWrapper.of(1, 2, 3, 4).cons(5)[0])

        Assertions.assertEquals(2, PersistentWrapper.of(3, 2, 1)[1])
        Assertions.assertEquals(1, PersistentWrapper.of(3, 2, 1)[2])
        Assertions.assertEquals(5, PersistentWrapper.of(1, 2, 3, 4).cons(5)[0])
        Assertions.assertEquals(50, PersistentWrapper(0..99)[50])
    }

    @Test
    fun isEmpty() {
        Assertions.assertTrue(PersistentWrapper.of<Int>().isEmpty())
        Assertions.assertTrue(PersistentWrapper.of<String>().isEmpty())
        Assertions.assertTrue(PersistentWrapper<Int>(listOf()).isEmpty())
        Assertions.assertTrue(PersistentWrapper(listOf(1)).cdr.isEmpty())

        Assertions.assertFalse(PersistentWrapper.of<Int>().cons(1).isEmpty())
        Assertions.assertFalse(PersistentWrapper.of<Int>().cons(1).cons(2).isEmpty())
        Assertions.assertFalse(PersistentWrapper.of<Int>().cons(1).cons(2).cons(3).isEmpty())
        Assertions.assertFalse(PersistentWrapper(listOf(2, 1, 3)).isEmpty())

        Assertions.assertFalse(PersistentWrapper.of<Int>().isNotEmpty())
        Assertions.assertFalse(PersistentWrapper.of<String>().isNotEmpty())
        Assertions.assertFalse(PersistentWrapper<Int>(listOf()).isNotEmpty())
        Assertions.assertFalse(PersistentWrapper(listOf(1)).cdr.isNotEmpty())

        Assertions.assertTrue(PersistentWrapper.of<Int>().cons(1).isNotEmpty())
        Assertions.assertTrue(PersistentWrapper.of<Int>().cons(1).cons(2).isNotEmpty())
        Assertions.assertTrue(PersistentWrapper.of<Int>().cons(1).cons(2).cons(3).isNotEmpty())
        Assertions.assertTrue(PersistentWrapper(listOf(2, 1, 3)).isNotEmpty())
    }

    @Test
    fun indexOf() {
        Assertions.assertEquals(-1, PersistentWrapper.of(1).indexOf(0))
        Assertions.assertEquals(-1, PersistentWrapper(listOf(2, 1, 3, 2, 1, 3)).indexOf(4))
        Assertions.assertEquals(0, PersistentWrapper.of(1).indexOf(1))
        Assertions.assertEquals(0, PersistentWrapper.of<Int>().cons(1).indexOf(1))
        Assertions.assertEquals(2, PersistentWrapper(listOf(2, 1, 3)).indexOf(3))
        Assertions.assertEquals(2, PersistentWrapper(listOf(2, 1, 3, 2, 1, 3)).indexOf(3))
        Assertions.assertEquals(2, PersistentWrapper(listOf(2, 1, 3, 2, 1, 3, 15)).indexOf(3))
        Assertions.assertEquals(0, PersistentWrapper(listOf(1, 1, 1, 1, 1, 1)).indexOf(1))
    }

    @Test
    fun lastIndexOf() {
        Assertions.assertEquals(-1, PersistentWrapper.of(1).lastIndexOf(0))
        Assertions.assertEquals(-1, PersistentWrapper(listOf(2, 1, 3, 2, 1, 3)).lastIndexOf(4))
        Assertions.assertEquals(0, PersistentWrapper.of(1).lastIndexOf(1))
        Assertions.assertEquals(0, PersistentWrapper.of<Int>().cons(1).lastIndexOf(1))
        Assertions.assertEquals(2, PersistentWrapper(listOf(2, 1, 3)).lastIndexOf(3))
        Assertions.assertEquals(5, PersistentWrapper(listOf(2, 1, 3, 2, 1, 3)).lastIndexOf(3))
        Assertions.assertEquals(5, PersistentWrapper(listOf(2, 1, 3, 2, 1, 3, 15)).lastIndexOf(3))
        Assertions.assertEquals(5, PersistentWrapper(listOf(1, 1, 1, 1, 1, 1)).lastIndexOf(1))
    }

    @Test
    fun iterator() {
        run {
            var counter = 0
            for (e in PersistentWrapper.of<Int>()) {
                counter++
            }
            Assertions.assertEquals(0, counter)
        }
        run {
            var counter = 0
            for (e in PersistentWrapper(listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))) {
                counter++
            }
            Assertions.assertEquals(10, counter)
        }
        run {
            var counter = 0
            PersistentWrapper(listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)).forEach { _ -> counter++ }
            Assertions.assertEquals(10, counter)
        }

        run {
            val iterator = PersistentWrapper.of(1, 2, 3, 4, 5).iterator()
            var sum = 0
            while (iterator.hasNext()) {
                sum += iterator.next()
            }
            Assertions.assertEquals(15, sum)
        }

        Assertions.assertEquals(15, PersistentWrapper.of(1, 2, 3, 4, 5).sum())
        Assertions.assertEquals(15, PersistentWrapper.of(1, 2, 3, 4, 5).fold(0, Int::plus))
        Assertions.assertEquals(15, PersistentWrapper.of(1, 2, 3, 4, 5).foldRight(0, Int::plus))
        Assertions.assertEquals(15, PersistentWrapper.of(1, 2, 3, 4, 5).reduce(Int::plus))
    }

    @Test
    fun listIterator() {
        run {
            var counter = 0
            for (e in PersistentWrapper.of<Int>().listIterator()) {
                counter++
            }
            Assertions.assertEquals(0, counter)
        }
        run {
            var counter = 0
            for (e in PersistentWrapper(listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)).listIterator()) {
                counter++
            }
            Assertions.assertEquals(10, counter)
        }
        run {
            var counter = 0
            PersistentWrapper(listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)).listIterator().forEach { _ -> counter++ }
            Assertions.assertEquals(10, counter)
        }

        run {
            val iterator = PersistentWrapper.of(1, 2, 3, 4, 5).listIterator()
            var sum = 0
            while (iterator.hasNext()) {
                sum += iterator.next()
            }
            Assertions.assertEquals(15, sum)
        }
    }

    @Test
    fun subList() {
        Assertions.assertThrows(IndexOutOfBoundsException::class.java) { PersistentWrapper.of<Int>().subList(3, 6).size }
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentWrapper.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).subList(3, 6))
        Assertions.assertEquals(listOf<Int>(), PersistentWrapper.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).subList(0, 0))
        Assertions.assertEquals(listOf(4, 5, 6), PersistentWrapper.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).subList(3, 6))
        Assertions.assertEquals(3, PersistentWrapper.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).subList(3, 6).size)
    }

    @Test
    fun toMutableList() {
        Assertions.assertTrue(PersistentWrapper.of<Int>().toMutableList().isEmpty())
        Assertions.assertEquals(5, PersistentWrapper.of(1, 2, 3, 4, 5).toMutableList().size)
    }

    @Test
    fun reversed() {
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentWrapper.of<Int>().reversed())
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentWrapper.of(3, 2, 1).reversed())

        Assertions.assertEquals(PersistentWrapper.of<Int>(), PersistentWrapper.of<Int>().reversed())
        Assertions.assertEquals(PersistentWrapper.of(1, 2, 3), PersistentWrapper.of(3, 2, 1).reversed())
        Assertions.assertEquals(PersistentWrapper.of(1, 2, 3), PersistentWrapper.of<Int>().cons(1).cons(2).cons(3).reversed())
    }

    @Test
    fun map() {
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentWrapper.of<Int>().map { it })
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentWrapper.of(1).map { it })

        Assertions.assertEquals(PersistentWrapper.of(1), PersistentWrapper.of(1).map { it })
        Assertions.assertEquals(PersistentWrapper.of(1, 2), PersistentWrapper.of(1, 2).map { it })
        Assertions.assertEquals(PersistentWrapper.of(2, 3), PersistentWrapper.of(1, 2).map { it + 1 })
        Assertions.assertEquals(PersistentWrapper.of(1, 2, 3), PersistentWrapper.of(1, 2, 3).map { it })
        Assertions.assertEquals(PersistentWrapper.of(2, 3, 4), PersistentWrapper.of(1, 2, 3).map { it + 1 })
    }

    @Test
    fun mapIndexed() {
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentWrapper.of<Int>().mapIndexed { _, elem -> elem })
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentWrapper.of(1).mapIndexed { _, elem -> elem })

        Assertions.assertEquals(PersistentWrapper.of(1), PersistentWrapper.of(1).mapIndexed { _, elem -> elem })
        Assertions.assertEquals(PersistentWrapper.of(1, 2), PersistentWrapper.of(1, 2).mapIndexed { _, elem -> elem })
        Assertions.assertEquals(PersistentWrapper.of(0, 1), PersistentWrapper.of(1, 2).mapIndexed { i, _ -> i })
        Assertions.assertEquals(PersistentWrapper.of(1, 2, 3), PersistentWrapper.of(1, 2, 3).mapIndexed { _, elem -> elem })
        Assertions.assertEquals(PersistentWrapper.of(2, 3, 4), PersistentWrapper.of(1, 2, 3).mapIndexed { _, elem -> elem + 1 })
        Assertions.assertEquals(PersistentWrapper.of(1, 3, 5), PersistentWrapper.of(1, 2, 3).mapIndexed { i, elem -> elem + i })
        Assertions.assertEquals(PersistentWrapper.of(1, 2, 3, 4), PersistentWrapper.of(1, 2, 3, 4).mapIndexed { _, elem -> elem })
        Assertions.assertEquals(PersistentWrapper.of(2, 3, 4, 5), PersistentWrapper.of(1, 2, 3, 4).mapIndexed { _, elem -> elem + 1 })
        Assertions.assertEquals(PersistentWrapper.of(1, 3, 5, 7), PersistentWrapper.of(1, 2, 3, 4).mapIndexed { i, elem -> elem + i })
    }

    @Test
    fun filter() {
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentWrapper.of<Int>().filter { true })
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentWrapper.of(1).filter { true })

        Assertions.assertEquals(PersistentWrapper.of(1, 2), PersistentWrapper.of(1, 2).filter { true })
        Assertions.assertEquals(PersistentWrapper.of<Int>(), PersistentWrapper.of(1, 2).filter { false })
        Assertions.assertEquals(PersistentWrapper.of(2), PersistentWrapper.of(1, 2, 3).filter { it % 2 == 0 })
        Assertions.assertEquals(PersistentWrapper.of(2, 4), PersistentWrapper.of(1, 2, 3, 4).filter { it % 2 == 0 })
    }

    @Test
    fun filterNot() {
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentWrapper.of<Int>().filterNot { true })
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentWrapper.of(1).filterNot { true })

        Assertions.assertEquals(PersistentWrapper.of(1, 2), PersistentWrapper.of(1, 2).filterNot { false })
        Assertions.assertEquals(PersistentWrapper.of<Int>(), PersistentWrapper.of(1, 2).filterNot { true })
        Assertions.assertEquals(PersistentWrapper.of(2), PersistentWrapper.of(1, 2, 3).filterNot { it % 2 != 0 })
        Assertions.assertEquals(PersistentWrapper.of(2, 4), PersistentWrapper.of(1, 2, 3, 4).filterNot { it % 2 != 0 })
    }

    @Test
    fun cadr() {
        Assertions.assertThrows(NoSuchElementException::class.java) { PersistentWrapper.of<Int>().cadr }
        Assertions.assertThrows(NoSuchElementException::class.java) { PersistentWrapper.of(1).cadr }
        Assertions.assertEquals(2, PersistentWrapper.of(1, 2, 3, 4, 5).cadr)
        Assertions.assertEquals(PersistentWrapper.of(1, 2, 3, 4, 5).cdr.car, PersistentWrapper.of(1, 2, 3, 4, 5).cadr)
    }

    @Test
    fun caddr() {
        Assertions.assertThrows(NoSuchElementException::class.java) { PersistentWrapper.of<Int>().caddr }
        Assertions.assertThrows(NoSuchElementException::class.java) { PersistentWrapper.of(1).caddr }
        Assertions.assertEquals(3, PersistentWrapper.of(1, 2, 3, 4, 5).caddr)
        Assertions.assertEquals(PersistentWrapper.of(1, 2, 3, 4, 5).cdr.cdr.car, PersistentWrapper.of(1, 2, 3, 4, 5).caddr)
    }

    @Test
    fun cadddr() {
        Assertions.assertThrows(NoSuchElementException::class.java) { PersistentWrapper.of<Int>().cadddr }
        Assertions.assertThrows(NoSuchElementException::class.java) { PersistentWrapper.of(1).cadddr }
        Assertions.assertEquals(4, PersistentWrapper.of(1, 2, 3, 4, 5).cadddr)
        Assertions.assertEquals(PersistentWrapper.of(1, 2, 3, 4, 5).cdr.cdr.cdr.car, PersistentWrapper.of(1, 2, 3, 4, 5).cadddr)
    }

    @Test
    fun cddr() {
        Assertions.assertEquals(nullCons<Int>(), PersistentWrapper.of<Int>().cddr)
        Assertions.assertEquals(nullCons<Int>(), PersistentWrapper.of(1).cddr)
        Assertions.assertEquals(PersistentWrapper.of(3, 4, 5), PersistentWrapper.of(1, 2, 3, 4, 5).cddr)
        Assertions.assertEquals(PersistentWrapper.of(1, 2, 3, 4, 5).cdr.cdr, PersistentWrapper.of(1, 2, 3, 4, 5).cddr)
    }

    @Test
    fun cdddr() {
        Assertions.assertEquals(nullCons<Int>(), PersistentWrapper.of<Int>().cdddr)
        Assertions.assertEquals(nullCons<Int>(), PersistentWrapper.of(1).cdddr)
        Assertions.assertEquals(PersistentWrapper.of(4, 5), PersistentWrapper.of(1, 2, 3, 4, 5).cdddr)
        Assertions.assertEquals(PersistentWrapper.of(1, 2, 3, 4, 5).cdr.cdr.cdr, PersistentWrapper.of(1, 2, 3, 4, 5).cdddr)
    }

    @Test
    fun cddddr() {
        Assertions.assertEquals(nullCons<Int>(), PersistentWrapper.of<Int>().cddddr)
        Assertions.assertEquals(nullCons<Int>(), PersistentWrapper.of(1).cddddr)
        Assertions.assertEquals(PersistentWrapper.of(5), PersistentWrapper.of(1, 2, 3, 4, 5).cddddr)
        Assertions.assertEquals(PersistentWrapper.of(1, 2, 3, 4, 5).cdr.cdr.cdr.cdr, PersistentWrapper.of(1, 2, 3, 4, 5).cddddr)
    }

    @Test
    fun flatMap() {
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentWrapper.of<Int>().flatMap { listOf(it) })
        Assertions.assertEquals(PersistentWrapper.of<Int>(), PersistentWrapper.of<Int>().flatMap { listOf(it) })
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentWrapper.of(1, 2, 3).flatMap { listOf(it) })
        Assertions.assertEquals(PersistentWrapper.of(1, 2, 3), PersistentWrapper.of(1, 2, 3).flatMap { listOf(it) })
        Assertions.assertEquals(PersistentWrapper.of(1, 2, 3), PersistentWrapper.of(listOf(1, 2), listOf(3)).flatMap { it })
    }

    @Test
    fun take() {
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentWrapper.of<Int>().take(10))
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentWrapper.of(1, 2, 3).take(10))
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentWrapper.of(1, 2, 3).take(2))

        Assertions.assertEquals(PersistentWrapper.of<Int>(), PersistentWrapper.of<Int>().drop(0))
        Assertions.assertEquals(PersistentWrapper.of<Int>(), PersistentWrapper.of<Int>().drop(10))

        Assertions.assertEquals(PersistentWrapper.of<Int>(), PersistentWrapper.of(1, 2, 3, 4, 5, 6).take(0))
        Assertions.assertEquals(PersistentWrapper.of(1), PersistentWrapper.of(1, 2, 3, 4, 5, 6).take(1))
        Assertions.assertEquals(PersistentWrapper.of(1, 2), PersistentWrapper.of(1, 2, 3, 4, 5, 6).take(2))
        Assertions.assertEquals(PersistentWrapper.of(1, 2, 3), PersistentWrapper.of(1, 2, 3, 4, 5, 6).take(3))
        Assertions.assertEquals(PersistentWrapper.of(1, 2, 3, 4), PersistentWrapper.of(1, 2, 3, 4, 5, 6).take(4))
        Assertions.assertEquals(PersistentWrapper.of(1, 2, 3, 4, 5), PersistentWrapper.of(1, 2, 3, 4, 5, 6).take(5))
        Assertions.assertEquals(PersistentWrapper.of(1, 2, 3, 4, 5, 6), PersistentWrapper.of(1, 2, 3, 4, 5, 6).take(6))
    }

    @Test
    fun drop() {
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentWrapper.of<Int>().drop(10))
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentWrapper.of(1, 2, 3).drop(10))
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentWrapper.of(1, 2, 3).drop(2))

        Assertions.assertEquals(PersistentWrapper.of<Int>(), PersistentWrapper.of<Int>().drop(0))
        Assertions.assertEquals(PersistentWrapper.of<Int>(), PersistentWrapper.of<Int>().drop(10))

        Assertions.assertEquals(PersistentWrapper.of(1, 2, 3, 4, 5, 6), PersistentWrapper.of(1, 2, 3, 4, 5, 6).drop(0))
        Assertions.assertEquals(PersistentWrapper.of(2, 3, 4, 5, 6), PersistentWrapper.of(1, 2, 3, 4, 5, 6).drop(1))
        Assertions.assertEquals(PersistentWrapper.of(3, 4, 5, 6), PersistentWrapper.of(1, 2, 3, 4, 5, 6).drop(2))
        Assertions.assertEquals(PersistentWrapper.of(4, 5, 6), PersistentWrapper.of(1, 2, 3, 4, 5, 6).drop(3))
        Assertions.assertEquals(PersistentWrapper.of(5, 6), PersistentWrapper.of(1, 2, 3, 4, 5, 6).drop(4))
        Assertions.assertEquals(PersistentWrapper.of(6), PersistentWrapper.of(1, 2, 3, 4, 5, 6).drop(5))
        Assertions.assertEquals(PersistentWrapper.of<Int>(), PersistentWrapper.of(1, 2, 3, 4, 5, 6).drop(6))
    }

    @Test
    fun takeWhile() {
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentWrapper.of<Int>().takeWhile { true })
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentWrapper.of(1, 2, 3).takeWhile { true })
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentWrapper.of(1, 2, 3).takeWhile { false })
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentWrapper.of(1, 2, 3).takeWhile { it < 3 })

        Assertions.assertEquals(PersistentWrapper.of<Int>(), PersistentWrapper.of<Int>().takeWhile { it < 3 })

        Assertions.assertEquals(PersistentWrapper.of(1, 2), PersistentWrapper.of(1, 2, 3, 2, 1).takeWhile { it < 3 })

        Assertions.assertEquals(PersistentWrapper.of<Int>(), PersistentWrapper.of(1, 2, 3, 4, 5, 6).takeWhile { it < 1 })
        Assertions.assertEquals(PersistentWrapper.of(1), PersistentWrapper.of(1, 2, 3, 4, 5, 6).takeWhile { it < 2 })
        Assertions.assertEquals(PersistentWrapper.of(1, 2), PersistentWrapper.of(1, 2, 3, 4, 5, 6).takeWhile { it < 3 })
        Assertions.assertEquals(PersistentWrapper.of(1, 2, 3), PersistentWrapper.of(1, 2, 3, 4, 5, 6).takeWhile { it < 4 })
        Assertions.assertEquals(PersistentWrapper.of(1, 2, 3, 4), PersistentWrapper.of(1, 2, 3, 4, 5, 6).takeWhile { it < 5 })
        Assertions.assertEquals(PersistentWrapper.of(1, 2, 3, 4, 5), PersistentWrapper.of(1, 2, 3, 4, 5, 6).takeWhile { it < 6 })
        Assertions.assertEquals(PersistentWrapper.of(1, 2, 3, 4, 5, 6), PersistentWrapper.of(1, 2, 3, 4, 5, 6).takeWhile { it < 7 })
    }

    @Test
    fun dropWhile() {
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentWrapper.of<Int>().dropWhile { true })
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentWrapper.of(1, 2, 3).dropWhile { true })
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentWrapper.of(1, 2, 3).dropWhile { false })
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentWrapper.of(1, 2, 3).dropWhile { it < 3 })

        Assertions.assertEquals(PersistentWrapper.of<Int>(), PersistentWrapper.of<Int>().dropWhile { it < 3 })

        Assertions.assertEquals(PersistentWrapper.of(3, 2, 1), PersistentWrapper.of(1, 2, 3, 2, 1).dropWhile { it < 3 })

        Assertions.assertEquals(PersistentWrapper.of(1, 2, 3, 4, 5, 6), PersistentWrapper.of(1, 2, 3, 4, 5, 6).dropWhile { it < 1 })
        Assertions.assertEquals(PersistentWrapper.of(2, 3, 4, 5, 6), PersistentWrapper.of(1, 2, 3, 4, 5, 6).dropWhile { it < 2 })
        Assertions.assertEquals(PersistentWrapper.of(3, 4, 5, 6), PersistentWrapper.of(1, 2, 3, 4, 5, 6).dropWhile { it < 3 })
        Assertions.assertEquals(PersistentWrapper.of(4, 5, 6), PersistentWrapper.of(1, 2, 3, 4, 5, 6).dropWhile { it < 4 })
        Assertions.assertEquals(PersistentWrapper.of(5, 6), PersistentWrapper.of(1, 2, 3, 4, 5, 6).dropWhile { it < 5 })
        Assertions.assertEquals(PersistentWrapper.of(6), PersistentWrapper.of(1, 2, 3, 4, 5, 6).dropWhile { it < 6 })
        Assertions.assertEquals(PersistentWrapper.of<Int>(), PersistentWrapper.of(1, 2, 3, 4, 5, 6).dropWhile { it < 8 })
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
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentWrapper.of<Int>().sortedBy { it })
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentWrapper.of(1, 2, 3).sortedBy { it })

        Assertions.assertEquals(PersistentWrapper.of<Int>(), PersistentWrapper.of<Int>().sortedBy { it })
        Assertions.assertEquals(PersistentWrapper.of(1), PersistentWrapper.of(1).sortedBy { it })

        Assertions.assertEquals(PersistentWrapper.of(1, 2, 3), PersistentWrapper.of(1, 2, 3).sortedBy { it })
        Assertions.assertEquals(PersistentWrapper.of(1, 2, 3), PersistentWrapper.of(3, 1, 2).sortedBy { it })

        Assertions.assertEquals(PersistentWrapper.of(1, 2, 2, 3), PersistentWrapper.of(1, 2, 2, 3).sortedBy { it })
        Assertions.assertEquals(PersistentWrapper.of(1, 2, 2, 3), PersistentWrapper.of(3, 1, 2, 2).sortedBy { it })
    }

    @Test
    fun sortedByDescending() {
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentWrapper.of<Int>().sortedByDescending { it })
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentWrapper.of(1, 2, 3).sortedByDescending { it })

        Assertions.assertEquals(PersistentWrapper.of<Int>(), PersistentWrapper.of<Int>().sortedByDescending { it })
        Assertions.assertEquals(PersistentWrapper.of(1), PersistentWrapper.of(1).sortedByDescending { it })

        Assertions.assertEquals(PersistentWrapper.of(3, 2, 1), PersistentWrapper.of(1, 2, 3).sortedByDescending { it })
        Assertions.assertEquals(PersistentWrapper.of(3, 2, 1), PersistentWrapper.of(3, 1, 2).sortedByDescending { it })

        Assertions.assertEquals(PersistentWrapper.of(3, 2, 2, 1), PersistentWrapper.of(1, 2, 2, 3).sortedByDescending { it })
        Assertions.assertEquals(PersistentWrapper.of(3, 2, 2, 1), PersistentWrapper.of(3, 1, 2, 2).sortedByDescending { it })
    }

    @Test
    fun sortedWith() {
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentWrapper.of<Int>().sortedWith { n, m -> n.compareTo(m) })
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentWrapper.of(1, 2, 3).sortedWith { n, m -> n.compareTo(m) })
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentWrapper.of(1, 2, 3).sortedWith { n, m -> -n.compareTo(m) })

        Assertions.assertEquals(PersistentWrapper.of<Int>(), PersistentWrapper.of<Int>().sortedWith { n, m -> n.compareTo(m) })
        Assertions.assertEquals(PersistentWrapper.of(1), PersistentWrapper.of(1).sortedWith { n, m -> n.compareTo(m) })
        Assertions.assertEquals(PersistentWrapper.of(1), PersistentWrapper.of(1).sortedWith { n, m -> -n.compareTo(m) })

        Assertions.assertEquals(PersistentWrapper.of(3, 1, 2), PersistentWrapper.of(3, 1, 2).sortedWith { _, _ -> 0 })

        Assertions.assertEquals(PersistentWrapper.of(1, 2, 3), PersistentWrapper.of(1, 2, 3).sortedWith { n, m -> n.compareTo(m) })
        Assertions.assertEquals(PersistentWrapper.of(1, 2, 3), PersistentWrapper.of(3, 1, 2).sortedWith { n, m -> n.compareTo(m) })

        Assertions.assertEquals(PersistentWrapper.of(1, 2, 2, 3), PersistentWrapper.of(1, 2, 2, 3).sortedWith { n, m -> n.compareTo(m) })
        Assertions.assertEquals(PersistentWrapper.of(1, 2, 2, 3), PersistentWrapper.of(3, 1, 2, 2).sortedWith { n, m -> n.compareTo(m) })

        Assertions.assertEquals(PersistentWrapper.of(3, 2, 1), PersistentWrapper.of(1, 2, 3).sortedWith { n, m -> -n.compareTo(m) })
        Assertions.assertEquals(PersistentWrapper.of(3, 2, 1), PersistentWrapper.of(3, 1, 2).sortedWith { n, m -> -n.compareTo(m) })

        Assertions.assertEquals(PersistentWrapper.of(3, 2, 2, 1), PersistentWrapper.of(1, 2, 2, 3).sortedWith { n, m -> -n.compareTo(m) })
        Assertions.assertEquals(PersistentWrapper.of(3, 2, 2, 1), PersistentWrapper.of(3, 1, 2, 2).sortedWith { n, m -> -n.compareTo(m) })
    }

    @Test
    fun distinct() {
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentWrapper.of<Int>().distinct())
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentWrapper.of(1, 2, 3).distinct())

        Assertions.assertEquals(PersistentWrapper.of<Int>(), PersistentWrapper.of<Int>().distinct())
        Assertions.assertEquals(PersistentWrapper.of(1), PersistentWrapper.of(1).distinct())
        Assertions.assertEquals(PersistentWrapper.of(1, 2, 3, 4, 5), PersistentWrapper.of(1, 2, 3, 4, 5).distinct())
        Assertions.assertEquals(PersistentWrapper.of(1), PersistentWrapper.of(1, 1, 1, 1, 1, 1).distinct())

        Assertions.assertEquals(PersistentWrapper.of(1, 2, 3), PersistentWrapper.of(1, 1, 2, 2, 3, 3).distinct())
        Assertions.assertEquals(PersistentWrapper.of(2, 1, 3), PersistentWrapper.of(2, 1, 1, 2, 3, 3).distinct())
    }

    @Test
    fun shuffled() {
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentWrapper.of<Int>().shuffled())
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentWrapper.of(1, 2, 3).shuffled())

        val seed = 0xDEADBEEF
        val rand = Random(seed)
        val temp = PersistentWrapper(1..50)
        val tempShuffled = temp.shuffled(rand)
        Assertions.assertNotSame(temp, tempShuffled)

        Assertions.assertEquals(PersistentWrapper.of(1), PersistentWrapper.of(1).shuffled(rand))
        Assertions.assertEquals(PersistentWrapper.of(1, 1, 1), PersistentWrapper.of(1, 1, 1).shuffled(rand))
        Assertions.assertEquals(PersistentWrapper(1..55).shuffled(Random(seed)), PersistentWrapper(1..55).shuffled(Random(seed)))
    }

    @Test
    fun asSequence() {
        Assertions.assertInstanceOf(Sequence::class.java, PersistentWrapper.of<Int>().asSequence())
        Assertions.assertInstanceOf(Sequence::class.java, PersistentWrapper.of(1, 2).asSequence())
        Assertions.assertInstanceOf(Sequence::class.java, PersistentWrapper.of(1, 2, 3).asSequence())
        Assertions.assertInstanceOf(Sequence::class.java, PersistentWrapper.of(1, 2, 3).asSequence().map { it + 1 })

        Assertions.assertEquals(listOf(1), PersistentWrapper.of(1).asSequence().toList())
        Assertions.assertEquals(listOf(1, 2), PersistentWrapper.of(1, 2).asSequence().toList())
        Assertions.assertEquals(listOf(2, 3, 4), PersistentWrapper.of(1, 2, 3).asSequence().map { it + 1 }.toList())
    }

    @Test
    fun plusElement() {
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentWrapper.of<Int>() + 1)
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentWrapper.of(1) + 2)

        Assertions.assertEquals(PersistentList.of(1), PersistentWrapper.of<Int>() + 1)
        Assertions.assertEquals(PersistentList.of(1, 2), PersistentWrapper.of(1) + 2)
    }

    @Test
    fun plusIterable() {
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentWrapper.of<Int>() + listOf(1))
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentWrapper.of(1) + listOf(2, 3))

        Assertions.assertEquals(PersistentList.of<Int>(), PersistentWrapper.of<Int>() + listOf())
        Assertions.assertEquals(PersistentList.of(1, 2, 3), PersistentWrapper.of(1, 2, 3) + listOf())

        Assertions.assertEquals(PersistentList.of(1), PersistentWrapper.of<Int>() + listOf(1))
        Assertions.assertEquals(PersistentList.of(1, 2, 3), PersistentWrapper.of(1) + listOf(2, 3))

        Assertions.assertEquals(PersistentList.of(1, 2, 3, 4, 5), PersistentWrapper.of<Int>() + (1..5))
        Assertions.assertEquals(PersistentList.of(1, 2, 3, 4, 5), PersistentWrapper.of<Int>() + (1..<6))
        Assertions.assertEquals(PersistentList.of(1, 2, 3, 4, 5), PersistentWrapper.of(1) + (2..5))
        Assertions.assertEquals(PersistentList.of(1, 2, 3, 4, 5), PersistentWrapper.of(1) + sequenceOf(2, 3, 4, 5))

        Assertions.assertEquals(PersistentList.of<Int>(), PersistentWrapper.of<Int>() + PersistentList.of())
        Assertions.assertEquals(PersistentList.of(1, 2, 3, 4, 5), PersistentWrapper.of(1) + PersistentList.of(2, 3, 4, 5))
        Assertions.assertEquals(PersistentList.of(1, 2, 3, 4, 5), PersistentWrapper.of(1) + PersistentWrapper(listOf(2, 3, 4, 5)))
    }

    @Test
    fun isSingleton() {
        Assertions.assertFalse(PersistentWrapper.of<Int>().isSingleton())
        Assertions.assertTrue(PersistentWrapper.of(1).isSingleton())
        Assertions.assertFalse(PersistentWrapper.of(1, 2).isSingleton())
    }
}