package kleinert.soap.cons

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import kotlin.random.Random

class VListTest {

    @Test
    fun testConstructor() {
        Assertions.assertTrue(VList.of<Int>() == VList.toVList(listOf<Int>()))
        Assertions.assertTrue(VList.toVList<Int>(arrayOf()) == VList.toVList(listOf<Int>()))
        Assertions.assertTrue(VList.toVList(listOf<Int>().asIterable()) == VList.toVList(listOf<Int>()))
        Assertions.assertTrue(VList.toVList(VList.of<Int>()) == VList.toVList(listOf<Int>()))

        Assertions.assertTrue(VList.toVList(arrayOf(1)) == VList.toVList(listOf(1)))
        Assertions.assertTrue(VList.toVList(listOf(1).asIterable()) == VList.toVList(listOf(1)))

        Assertions.assertTrue(VList.toVList(arrayOf(1, 2, 3, 4, 5)) == VList.toVList(listOf(1, 2, 3, 4, 5)))
        Assertions.assertTrue(VList.toVList(listOf(1, 2, 3, 4, 5).asIterable()) == VList.toVList(listOf(1, 2, 3, 4, 5)))
    }

    @Test
    fun getSize() {
        Assertions.assertEquals(0, VList.of<Int>().size)
        Assertions.assertEquals(0, VList.of<Int>().size)
        Assertions.assertEquals(1, VList.of(99).size)
        Assertions.assertEquals(5, VList.of(1, 2, 3, 4, 5).size)
        Assertions.assertEquals(55, VList.toVList((1..55).toList()).size)
    }

    @Test
    fun cons() {
        val vlist = VList.of<Int>()
        Assertions.assertEquals(0, vlist.size)

        Assertions.assertInstanceOf(VList::class.java, vlist.cons(1))
        Assertions.assertEquals(VList.toVList(arrayOf(1)), vlist.cons(1))
        Assertions.assertEquals(VList.toVList(arrayOf(2, 1)), vlist.cons(1).cons(2))
        Assertions.assertEquals(VList.toVList(arrayOf(3, 2, 1)), vlist.cons(1).cons(2).cons(3))
    }

    @Test
    fun cdr() {
        Assertions.assertInstanceOf(VList::class.java, VList.of<Int>().cdr)
        Assertions.assertInstanceOf(VList::class.java, VList.of(2, 1).cdr)
        Assertions.assertEquals(VList.of<Int>(), VList.of<Int>().cdr)
        Assertions.assertEquals(VList.of(1), VList.of(2, 1).cdr)
        Assertions.assertEquals(VList.toVList(2..5), VList.toVList(1..5).cdr)
    }

    @Test
    fun car() {
        Assertions.assertThrows(NoSuchElementException::class.java) { VList.of<Int>().car }
        Assertions.assertEquals(1, VList.of(1).car)
        Assertions.assertEquals(2, VList.of(2, 1).car)
        Assertions.assertEquals((0..99).first(), VList.toVList(0..99).car)
        Assertions.assertEquals(5, VList.of(1, 2, 3, 4).cons(5).car)

        Assertions.assertThrows(NoSuchElementException::class.java) { VList.of<Int>().first() }
        Assertions.assertEquals(1, VList.of(1).first())
        Assertions.assertEquals(2, VList.of(2, 1).first())
        Assertions.assertEquals((0..99).first(), VList.toVList(0..99).first())
        Assertions.assertEquals(5, VList.of(1, 2, 3, 4).cons(5).first())
    }

    @Test
    fun contains() {
        Assertions.assertFalse(VList.of<Int>().contains(1))
        Assertions.assertFalse(VList.of<String>().contains(""))
        Assertions.assertFalse(VList.toVList<Int>(listOf()).contains(1))
        Assertions.assertFalse(VList.toVList(listOf(2, 1, 3)).contains(5))

        Assertions.assertTrue(VList.toVList(listOf(1)).contains(1))
        Assertions.assertTrue(VList.toVList(listOf(2, 1, 3)).contains(1))
    }

    @Test
    fun containsAll() {
        Assertions.assertTrue(VList.of<Int>().containsAll(listOf()))

        Assertions.assertFalse(VList.of<Int>().containsAll(listOf(1)))
        Assertions.assertFalse(VList.of<String>().containsAll(listOf("")))
        Assertions.assertFalse(VList.toVList<Int>(listOf()).containsAll(listOf(1)))
        Assertions.assertFalse(VList.toVList(listOf(2, 1, 3)).containsAll(listOf(5)))

        Assertions.assertTrue(VList.toVList(listOf(1)).containsAll(listOf(1)))
        Assertions.assertTrue(VList.toVList(listOf(2, 1, 3)).containsAll(listOf(1)))

        Assertions.assertTrue(VList.toVList(0..99).containsAll(listOf(10, 20, 30, 50, 60, 70, 80, 90)))
        Assertions.assertTrue(VList.toVList(0..99).containsAll(setOf(10, 20, 30, 50, 60, 70, 80, 90)))
        Assertions.assertTrue(VList.toVList(0..99).containsAll(VList.of(10, 20, 30, 50, 60, 70, 80, 90)))
    }

    @Test
    fun get() {
        Assertions.assertThrows(IndexOutOfBoundsException::class.java) { VList.of<Int>()[0] }
        Assertions.assertThrows(IndexOutOfBoundsException::class.java) { VList.of(1)[1] }
        Assertions.assertThrows(IndexOutOfBoundsException::class.java) { VList.of(1, 2, 3, 4, 5)[10] }

        Assertions.assertEquals(1, VList.of(1)[0])
        Assertions.assertEquals(2, VList.of(2, 1)[0])
        Assertions.assertEquals((0..99).first(), VList.toVList(0..99)[0])
        Assertions.assertEquals(5, VList.of(1, 2, 3, 4).cons(5)[0])

        Assertions.assertEquals(2, VList.of(3, 2, 1)[1])
        Assertions.assertEquals(1, VList.of(3, 2, 1)[2])
        Assertions.assertEquals(5, VList.of(1, 2, 3, 4).cons(5)[0])
        Assertions.assertEquals(50, VList.toVList(0..99)[50])
    }

    @Test
    fun isEmpty() {
        Assertions.assertTrue(VList.of<Int>().isEmpty())
        Assertions.assertTrue(VList.of<String>().isEmpty())
        Assertions.assertTrue(VList.toVList<Int>(listOf()).isEmpty())
        Assertions.assertTrue(VList.toVList(listOf(1)).cdr.isEmpty())

        Assertions.assertFalse(VList.of<Int>().cons(1).isEmpty())
        Assertions.assertFalse(VList.of<Int>().cons(1).cons(2).isEmpty())
        Assertions.assertFalse(VList.of<Int>().cons(1).cons(2).cons(3).isEmpty())
        Assertions.assertFalse(VList.toVList(listOf(2, 1, 3)).isEmpty())

        Assertions.assertFalse(VList.of<Int>().isNotEmpty())
        Assertions.assertFalse(VList.of<String>().isNotEmpty())
        Assertions.assertFalse(VList.toVList<Int>(listOf()).isNotEmpty())
        Assertions.assertFalse(VList.toVList(listOf(1)).cdr.isNotEmpty())

        Assertions.assertTrue(VList.of<Int>().cons(1).isNotEmpty())
        Assertions.assertTrue(VList.of<Int>().cons(1).cons(2).isNotEmpty())
        Assertions.assertTrue(VList.of<Int>().cons(1).cons(2).cons(3).isNotEmpty())
        Assertions.assertTrue(VList.toVList(listOf(2, 1, 3)).isNotEmpty())
    }

    @Test
    fun indexOf() {
        Assertions.assertEquals(-1, VList.of(1).indexOf(0))
        Assertions.assertEquals(-1, VList.toVList(listOf(2, 1, 3, 2, 1, 3)).indexOf(4))
        Assertions.assertEquals(0, VList.of(1).indexOf(1))
        Assertions.assertEquals(0, VList.of<Int>().cons(1).indexOf(1))
        Assertions.assertEquals(2, VList.toVList(listOf(2, 1, 3)).indexOf(3))
        Assertions.assertEquals(2, VList.toVList(listOf(2, 1, 3, 2, 1, 3)).indexOf(3))
        Assertions.assertEquals(2, VList.toVList(listOf(2, 1, 3, 2, 1, 3, 15)).indexOf(3))
        Assertions.assertEquals(0, VList.toVList(listOf(1, 1, 1, 1, 1, 1)).indexOf(1))
    }

    @Test
    fun lastIndexOf() {
        Assertions.assertEquals(-1, VList.of(1).lastIndexOf(0))
        Assertions.assertEquals(-1, VList.toVList(listOf(2, 1, 3, 2, 1, 3)).lastIndexOf(4))
        Assertions.assertEquals(0, VList.of(1).lastIndexOf(1))
        Assertions.assertEquals(0, VList.of<Int>().cons(1).lastIndexOf(1))
        Assertions.assertEquals(2, VList.toVList(listOf(2, 1, 3)).lastIndexOf(3))
        Assertions.assertEquals(5, VList.toVList(listOf(2, 1, 3, 2, 1, 3)).lastIndexOf(3))
        Assertions.assertEquals(5, VList.toVList(listOf(2, 1, 3, 2, 1, 3, 15)).lastIndexOf(3))
        Assertions.assertEquals(5, VList.toVList(listOf(1, 1, 1, 1, 1, 1)).lastIndexOf(1))
    }

    @Test
    fun iterator() {
        run {
            var counter = 0
            for (e in VList.of<Int>()) {
                counter++
            }
            Assertions.assertEquals(0, counter)
        }
        run {
            var counter = 0
            for (e in VList.toVList(listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))) {
                counter++
            }
            Assertions.assertEquals(10, counter)
        }
        run {
            var counter = 0
            VList.toVList(listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)).forEach { _ -> counter++ }
            Assertions.assertEquals(10, counter)
        }

        run {
            val iterator = VList.of(1, 2, 3, 4, 5).iterator()
            var sum = 0
            while (iterator.hasNext()) {
                sum += iterator.next()
            }
            Assertions.assertEquals(15, sum)
        }

        Assertions.assertEquals(15, VList.of(1, 2, 3, 4, 5).sum())
        Assertions.assertEquals(15, VList.of(1, 2, 3, 4, 5).fold(0, Int::plus))
        Assertions.assertEquals(15, VList.of(1, 2, 3, 4, 5).foldRight(0, Int::plus))
        Assertions.assertEquals(15, VList.of(1, 2, 3, 4, 5).reduce(Int::plus))
    }

    @Test
    fun listIterator() {
        run {
            var counter = 0
            for (e in VList.of<Int>().listIterator()) {
                counter++
            }
            Assertions.assertEquals(0, counter)
        }
        run {
            var counter = 0
            for (e in VList.toVList(listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)).listIterator()) {
                counter++
            }
            Assertions.assertEquals(10, counter)
        }
        run {
            var counter = 0
            VList.toVList(listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)).listIterator().forEach { _ -> counter++ }
            Assertions.assertEquals(10, counter)
        }

        run {
            val iterator = VList.of(1, 2, 3, 4, 5).listIterator()
            var sum = 0
            while (iterator.hasNext()) {
                sum += iterator.next()
            }
            Assertions.assertEquals(15, sum)
        }
    }

    @Test
    fun subList() {
        Assertions.assertThrows(IndexOutOfBoundsException::class.java) { VList.of<Int>().subList(3, 6).size }
        Assertions.assertInstanceOf(PersistentList::class.java, VList.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).subList(3, 6))
        Assertions.assertEquals(listOf<Int>(), VList.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).subList(0, 0))
        Assertions.assertEquals(listOf(4, 5, 6), VList.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).subList(3, 6))
        Assertions.assertEquals(3, VList.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).subList(3, 6).size)
    }

    @Test
    fun toMutableList() {
        Assertions.assertTrue(VList.of<Int>().toMutableList().isEmpty())
        Assertions.assertEquals(5, VList.of(1, 2, 3, 4, 5).toMutableList().size)
    }

    @Test
    fun reversed() {
        Assertions.assertInstanceOf(VList::class.java, VList.of<Int>().reversed())
        Assertions.assertInstanceOf(VList::class.java, VList.of(3, 2, 1).reversed())

        Assertions.assertEquals(VList.of<Int>(), VList.of<Int>().reversed())
        Assertions.assertEquals(VList.of(1, 2, 3), VList.of(3, 2, 1).reversed())
        Assertions.assertEquals(VList.of(1, 2, 3), VList.of<Int>().cons(1).cons(2).cons(3).reversed())
    }

    @Test
    fun map() {

        Assertions.assertEquals(VList.of(1), VList.of(1).map { it })
        Assertions.assertEquals(VList.of(1, 2), VList.of(1, 2).map { it })
        Assertions.assertEquals(VList.of(2, 3), VList.of(1, 2).map { it + 1 })
        Assertions.assertEquals(VList.of(1, 2, 3), VList.of(1, 2, 3).map { it })
        Assertions.assertEquals(VList.of(2, 3, 4), VList.of(1, 2, 3).map { it + 1 })
    }

    @Test
    fun mapIndexed() {
        Assertions.assertInstanceOf(VList::class.java, VList.of<Int>().mapIndexed { _, elem -> elem })
        Assertions.assertInstanceOf(VList::class.java, VList.of(1).mapIndexed { _, elem -> elem })

        Assertions.assertEquals(VList.of(1), VList.of(1).mapIndexed { _, elem -> elem })
        Assertions.assertEquals(VList.of(1, 2), VList.of(1, 2).mapIndexed { _, elem -> elem })
        Assertions.assertEquals(VList.of(0, 1), VList.of(1, 2).mapIndexed { i, _ -> i })
        Assertions.assertEquals(VList.of(1, 2, 3), VList.of(1, 2, 3).mapIndexed { _, elem -> elem })
        Assertions.assertEquals(VList.of(2, 3, 4), VList.of(1, 2, 3).mapIndexed { _, elem -> elem + 1 })
        Assertions.assertEquals(VList.of(1, 3, 5), VList.of(1, 2, 3).mapIndexed { i, elem -> elem + i })
        Assertions.assertEquals(VList.of(1, 2, 3, 4), VList.of(1, 2, 3, 4).mapIndexed { _, elem -> elem })
        Assertions.assertEquals(VList.of(2, 3, 4, 5), VList.of(1, 2, 3, 4).mapIndexed { _, elem -> elem + 1 })
        Assertions.assertEquals(VList.of(1, 3, 5, 7), VList.of(1, 2, 3, 4).mapIndexed { i, elem -> elem + i })
    }

    @Test
    fun filter() {
        Assertions.assertTrue(VList.of<Int>().filter { true }.isEmpty())

        Assertions.assertEquals(VList.of(1, 2), VList.of(1, 2).filter { true })
        Assertions.assertEquals(VList.of<Int>(), VList.of(1, 2).filter { false })
        Assertions.assertEquals(VList.of(2), VList.of(1, 2, 3).filter { it % 2 == 0 })
        Assertions.assertEquals(VList.of(2, 4), VList.of(1, 2, 3, 4).filter { it % 2 == 0 })
    }

    @Test
    fun filterNot() {
        Assertions.assertTrue(VList.of<Int>().filterNot { true }.isEmpty())

        Assertions.assertEquals(VList.of(1, 2), VList.of(1, 2).filterNot { false })
        Assertions.assertEquals(VList.of<Int>(), VList.of(1, 2).filterNot { true })
        Assertions.assertEquals(VList.of(2), VList.of(1, 2, 3).filterNot { it % 2 != 0 })
        Assertions.assertEquals(VList.of(2, 4), VList.of(1, 2, 3, 4).filterNot { it % 2 != 0 })
    }

    @Test
    fun split() {
        Assertions.assertThrows(NoSuchElementException::class.java) { VList.of<Int>().split() }
        Assertions.assertEquals(1 to VList.of<Int>(), VList.of(1).split())
        Assertions.assertEquals(1 to VList.of(2), VList.of(1, 2).split())
    }

    @Test
    fun cadr() {
        Assertions.assertThrows(NoSuchElementException::class.java) { VList.of<Int>().cadr }
        Assertions.assertThrows(NoSuchElementException::class.java) { VList.of(1).cadr }
        Assertions.assertEquals(2, VList.of(1, 2, 3, 4, 5).cadr)
        Assertions.assertEquals(VList.of(1, 2, 3, 4, 5).cdr.car, VList.of(1, 2, 3, 4, 5).cadr)
    }

    @Test
    fun caddr() {
        Assertions.assertThrows(NoSuchElementException::class.java) { VList.of<Int>().caddr }
        Assertions.assertThrows(NoSuchElementException::class.java) { VList.of(1).caddr }
        Assertions.assertEquals(3, VList.of(1, 2, 3, 4, 5).caddr)
        Assertions.assertEquals(VList.of(1, 2, 3, 4, 5).cdr.cdr.car, VList.of(1, 2, 3, 4, 5).caddr)
    }

    @Test
    fun cadddr() {
        Assertions.assertThrows(NoSuchElementException::class.java) { VList.of<Int>().cadddr }
        Assertions.assertThrows(NoSuchElementException::class.java) { VList.of(1).cadddr }
        Assertions.assertEquals(4, VList.of(1, 2, 3, 4, 5).cadddr)
        Assertions.assertEquals(VList.of(1, 2, 3, 4, 5).cdr.cdr.cdr.car, VList.of(1, 2, 3, 4, 5).cadddr)
    }

    @Test
    fun cddr() {
        Assertions.assertEquals(nullCons<Int>(), VList.of<Int>().cddr)
        Assertions.assertEquals(nullCons<Int>(), VList.of(1).cddr)
        Assertions.assertEquals(VList.of(3, 4, 5), VList.of(1, 2, 3, 4, 5).cddr)
        Assertions.assertEquals(VList.of(1, 2, 3, 4, 5).cdr.cdr, VList.of(1, 2, 3, 4, 5).cddr)
    }

    @Test
    fun cdddr() {
        Assertions.assertEquals(nullCons<Int>(), VList.of<Int>().cdddr)
        Assertions.assertEquals(nullCons<Int>(), VList.of(1).cdddr)
        Assertions.assertEquals(VList.of(4, 5), VList.of(1, 2, 3, 4, 5).cdddr)
        Assertions.assertEquals(VList.of(1, 2, 3, 4, 5).cdr.cdr.cdr, VList.of(1, 2, 3, 4, 5).cdddr)
    }

    @Test
    fun cddddr() {
        Assertions.assertEquals(nullCons<Int>(), VList.of<Int>().cddddr)
        Assertions.assertEquals(nullCons<Int>(), VList.of(1).cddddr)
        Assertions.assertEquals(VList.of(5), VList.of(1, 2, 3, 4, 5).cddddr)
        Assertions.assertEquals(VList.of(1, 2, 3, 4, 5).cdr.cdr.cdr.cdr, VList.of(1, 2, 3, 4, 5).cddddr)
    }

    @Test
    fun flatMap() {
        Assertions.assertEquals(VList.of<Int>(), VList.of<Int>().flatMap { listOf(it) })
        Assertions.assertEquals(VList.of(1, 2, 3), VList.of(1, 2, 3).flatMap { listOf(it) })
        Assertions.assertEquals(VList.of(1, 2, 3), VList.of(listOf(1, 2), listOf(3)).flatMap { it })
    }

    @Test
    fun take() {
        Assertions.assertInstanceOf(VList::class.java, VList.of<Int>().take(10))
        Assertions.assertInstanceOf(VList::class.java, VList.of(1, 2, 3).take(10))
        Assertions.assertInstanceOf(VList::class.java, VList.of(1, 2, 3).take(2))

        Assertions.assertEquals(VList.of<Int>(), VList.of<Int>().drop(0))
        Assertions.assertEquals(VList.of<Int>(), VList.of<Int>().drop(10))

        Assertions.assertEquals(VList.of<Int>(), VList.of(1, 2, 3, 4, 5, 6).take(0))
        Assertions.assertEquals(VList.of(1), VList.of(1, 2, 3, 4, 5, 6).take(1))
        Assertions.assertEquals(VList.of(1, 2), VList.of(1, 2, 3, 4, 5, 6).take(2))
        Assertions.assertEquals(VList.of(1, 2, 3), VList.of(1, 2, 3, 4, 5, 6).take(3))
        Assertions.assertEquals(VList.of(1, 2, 3, 4), VList.of(1, 2, 3, 4, 5, 6).take(4))
        Assertions.assertEquals(VList.of(1, 2, 3, 4, 5), VList.of(1, 2, 3, 4, 5, 6).take(5))
        Assertions.assertEquals(VList.of(1, 2, 3, 4, 5, 6), VList.of(1, 2, 3, 4, 5, 6).take(6))
    }

    @Test
    fun drop() {
        Assertions.assertInstanceOf(VList::class.java, VList.of<Int>().drop(10))
        Assertions.assertInstanceOf(VList::class.java, VList.of(1, 2, 3).drop(10))
        Assertions.assertInstanceOf(VList::class.java, VList.of(1, 2, 3).drop(2))

        Assertions.assertEquals(VList.of<Int>(), VList.of<Int>().drop(0))
        Assertions.assertEquals(VList.of<Int>(), VList.of<Int>().drop(10))

        Assertions.assertEquals(VList.of(1, 2, 3, 4, 5, 6), VList.of(1, 2, 3, 4, 5, 6).drop(0))
        Assertions.assertEquals(VList.of(2, 3, 4, 5, 6), VList.of(1, 2, 3, 4, 5, 6).drop(1))
        Assertions.assertEquals(VList.of(3, 4, 5, 6), VList.of(1, 2, 3, 4, 5, 6).drop(2))
        Assertions.assertEquals(VList.of(4, 5, 6), VList.of(1, 2, 3, 4, 5, 6).drop(3))
        Assertions.assertEquals(VList.of(5, 6), VList.of(1, 2, 3, 4, 5, 6).drop(4))
        Assertions.assertEquals(VList.of(6), VList.of(1, 2, 3, 4, 5, 6).drop(5))
        Assertions.assertEquals(VList.of<Int>(), VList.of(1, 2, 3, 4, 5, 6).drop(6))
    }

    @Test
    fun takeWhile() {
        Assertions.assertEquals(VList.of<Int>(), VList.of<Int>().takeWhile { it < 3 })

        Assertions.assertEquals(VList.of(1, 2), VList.of(1, 2, 3, 2, 1).takeWhile { it < 3 })

        Assertions.assertEquals(VList.of<Int>(), VList.of(1, 2, 3, 4, 5, 6).takeWhile { it < 1 })
        Assertions.assertEquals(VList.of(1), VList.of(1, 2, 3, 4, 5, 6).takeWhile { it < 2 })
        Assertions.assertEquals(VList.of(1, 2), VList.of(1, 2, 3, 4, 5, 6).takeWhile { it < 3 })
        Assertions.assertEquals(VList.of(1, 2, 3), VList.of(1, 2, 3, 4, 5, 6).takeWhile { it < 4 })
        Assertions.assertEquals(VList.of(1, 2, 3, 4), VList.of(1, 2, 3, 4, 5, 6).takeWhile { it < 5 })
        Assertions.assertEquals(VList.of(1, 2, 3, 4, 5), VList.of(1, 2, 3, 4, 5, 6).takeWhile { it < 6 })
        Assertions.assertEquals(VList.of(1, 2, 3, 4, 5, 6), VList.of(1, 2, 3, 4, 5, 6).takeWhile { it < 7 })
    }

    @Test
    fun dropWhile() {
        Assertions.assertInstanceOf(VList::class.java, VList.of<Int>().dropWhile { true })
        Assertions.assertInstanceOf(VList::class.java, VList.of(1, 2, 3).dropWhile { true })
        Assertions.assertInstanceOf(VList::class.java, VList.of(1, 2, 3).dropWhile { false })
        Assertions.assertInstanceOf(VList::class.java, VList.of(1, 2, 3).dropWhile { it < 3 })

        Assertions.assertEquals(VList.of<Int>(), VList.of<Int>().dropWhile { it < 3 })

        Assertions.assertEquals(VList.of(3, 2, 1), VList.of(1, 2, 3, 2, 1).dropWhile { it < 3 })

        Assertions.assertEquals(VList.of(1, 2, 3, 4, 5, 6), VList.of(1, 2, 3, 4, 5, 6).dropWhile { it < 1 })
        Assertions.assertEquals(VList.of(2, 3, 4, 5, 6), VList.of(1, 2, 3, 4, 5, 6).dropWhile { it < 2 })
        Assertions.assertEquals(VList.of(3, 4, 5, 6), VList.of(1, 2, 3, 4, 5, 6).dropWhile { it < 3 })
        Assertions.assertEquals(VList.of(4, 5, 6), VList.of(1, 2, 3, 4, 5, 6).dropWhile { it < 4 })
        Assertions.assertEquals(VList.of(5, 6), VList.of(1, 2, 3, 4, 5, 6).dropWhile { it < 5 })
        Assertions.assertEquals(VList.of(6), VList.of(1, 2, 3, 4, 5, 6).dropWhile { it < 6 })
        Assertions.assertEquals(VList.of<Int>(), VList.of(1, 2, 3, 4, 5, 6).dropWhile { it < 8 })
    }

//    @Test
//    fun sorted() {
//        Assertions.assertInstanceOf(Cons::class.java, (VList.of<Int>() as Cons<Int>).sorted())
//        Assertions.assertInstanceOf(Cons::class.java, VList.of<Int>().sorted())
//        Assertions.assertEquals(VList.of<Int>(), VList.of<Int>().sorted())
//        Assertions.assertInstanceOf(Cons::class.java, VList.of(1, 2, 3).sorted())
//        Assertions.assertEquals(VList.of(1, 2, 3), VList.of(1, 2, 3).sorted())
//        Assertions.assertEquals(VList.of(1, 2, 3), VList.of(3, 2, 1).sorted())
//        Assertions.assertEquals(VList.of(1, 2, 3, 3), VList.of(3, 2, 3, 1).sorted())
//    }

//    @Test
//    fun sortedDescending() {
//        Assertions.assertInstanceOf(Cons::class.java, VList.of<Int>().sortedDescending())
//        Assertions.assertEquals(VList.of<Int>(), VList.of<Int>().sortedDescending())
//        Assertions.assertInstanceOf(Cons::class.java, VList.of(1, 2, 3).sortedDescending())
//        Assertions.assertEquals(VList.of(3, 2, 1), VList.of(1, 2, 3).sortedDescending())
//        Assertions.assertEquals(VList.of(3, 2, 1), VList.of(3, 2, 1).sortedDescending())
//        Assertions.assertEquals(VList.of(3, 3, 2, 1), VList.of(3, 2, 3, 1).sortedDescending())
//    }

    @Test
    fun sortedBy() {
        Assertions.assertInstanceOf(VList::class.java, VList.of<Int>().sortedBy { it })
        Assertions.assertInstanceOf(VList::class.java, VList.of(1, 2, 3).sortedBy { it })

        Assertions.assertEquals(VList.of<Int>(), VList.of<Int>().sortedBy { it })
        Assertions.assertEquals(VList.of(1), VList.of(1).sortedBy { it })

        Assertions.assertEquals(VList.of(1, 2, 3), VList.of(1, 2, 3).sortedBy { it })
        Assertions.assertEquals(VList.of(1, 2, 3), VList.of(3, 1, 2).sortedBy { it })

        Assertions.assertEquals(VList.of(1, 2, 2, 3), VList.of(1, 2, 2, 3).sortedBy { it })
        Assertions.assertEquals(VList.of(1, 2, 2, 3), VList.of(3, 1, 2, 2).sortedBy { it })
    }

    @Test
    fun sortedByDescending() {
        Assertions.assertInstanceOf(VList::class.java, VList.of<Int>().sortedByDescending { it })
        Assertions.assertInstanceOf(VList::class.java, VList.of(1, 2, 3).sortedByDescending { it })

        Assertions.assertEquals(VList.of<Int>(), VList.of<Int>().sortedByDescending { it })
        Assertions.assertEquals(VList.of(1), VList.of(1).sortedByDescending { it })

        Assertions.assertEquals(VList.of(3, 2, 1), VList.of(1, 2, 3).sortedByDescending { it })
        Assertions.assertEquals(VList.of(3, 2, 1), VList.of(3, 1, 2).sortedByDescending { it })

        Assertions.assertEquals(VList.of(3, 2, 2, 1), VList.of(1, 2, 2, 3).sortedByDescending { it })
        Assertions.assertEquals(VList.of(3, 2, 2, 1), VList.of(3, 1, 2, 2).sortedByDescending { it })
    }

    @Test
    fun sortedWith() {
        Assertions.assertInstanceOf(VList::class.java, VList.of<Int>().sortedWith { n, m -> n.compareTo(m) })
        Assertions.assertInstanceOf(VList::class.java, VList.of(1, 2, 3).sortedWith { n, m -> n.compareTo(m) })
        Assertions.assertInstanceOf(VList::class.java, VList.of(1, 2, 3).sortedWith { n, m -> -n.compareTo(m) })

        Assertions.assertEquals(VList.of<Int>(), VList.of<Int>().sortedWith { n, m -> n.compareTo(m) })
        Assertions.assertEquals(VList.of(1), VList.of(1).sortedWith { n, m -> n.compareTo(m) })
        Assertions.assertEquals(VList.of(1), VList.of(1).sortedWith { n, m -> -n.compareTo(m) })

        Assertions.assertEquals(VList.of(3, 1, 2), VList.of(3, 1, 2).sortedWith { _, _ -> 0 })

        Assertions.assertEquals(VList.of(1, 2, 3), VList.of(1, 2, 3).sortedWith { n, m -> n.compareTo(m) })
        Assertions.assertEquals(VList.of(1, 2, 3), VList.of(3, 1, 2).sortedWith { n, m -> n.compareTo(m) })

        Assertions.assertEquals(VList.of(1, 2, 2, 3), VList.of(1, 2, 2, 3).sortedWith { n, m -> n.compareTo(m) })
        Assertions.assertEquals(VList.of(1, 2, 2, 3), VList.of(3, 1, 2, 2).sortedWith { n, m -> n.compareTo(m) })

        Assertions.assertEquals(VList.of(3, 2, 1), VList.of(1, 2, 3).sortedWith { n, m -> -n.compareTo(m) })
        Assertions.assertEquals(VList.of(3, 2, 1), VList.of(3, 1, 2).sortedWith { n, m -> -n.compareTo(m) })

        Assertions.assertEquals(VList.of(3, 2, 2, 1), VList.of(1, 2, 2, 3).sortedWith { n, m -> -n.compareTo(m) })
        Assertions.assertEquals(VList.of(3, 2, 2, 1), VList.of(3, 1, 2, 2).sortedWith { n, m -> -n.compareTo(m) })
    }

    @Test
    fun distinct() {
        Assertions.assertEquals(VList.of<Int>(), VList.of<Int>().distinct())
        Assertions.assertEquals(VList.of(1), VList.of(1).distinct())
        Assertions.assertEquals(VList.of(1, 2, 3, 4, 5), VList.of(1, 2, 3, 4, 5).distinct())
        Assertions.assertEquals(VList.of(1), VList.of(1, 1, 1, 1, 1, 1).distinct())

        Assertions.assertEquals(VList.of(1, 2, 3), VList.of(1, 1, 2, 2, 3, 3).distinct())
        Assertions.assertEquals(VList.of(2, 1, 3), VList.of(2, 1, 1, 2, 3, 3).distinct())
    }

    @Test
    fun shuffled() {
        Assertions.assertInstanceOf(VList::class.java, VList.of<Int>().shuffled())
        Assertions.assertInstanceOf(VList::class.java, VList.of(1, 2, 3).shuffled())

        val seed = 0xDEADBEEF
        val rand = Random(seed)
        val temp = VList.toVList(1..50)
        val tempShuffled = temp.shuffled(rand)
        Assertions.assertNotSame(temp, tempShuffled)

        Assertions.assertEquals(VList.of(1), VList.of(1).shuffled(rand))
        Assertions.assertEquals(VList.of(1, 1, 1), VList.of(1, 1, 1).shuffled(rand))
        Assertions.assertEquals(VList.toVList(1..55).shuffled(Random(seed)), VList.toVList(1..55).shuffled(Random(seed)))
    }

    @Test
    fun asSequence() {
        Assertions.assertInstanceOf(Sequence::class.java, VList.of<Int>().asSequence())
        Assertions.assertInstanceOf(Sequence::class.java, VList.of(1, 2).asSequence())
        Assertions.assertInstanceOf(Sequence::class.java, VList.of(1, 2, 3).asSequence())
        Assertions.assertInstanceOf(Sequence::class.java, VList.of(1, 2, 3).asSequence().map { it + 1 })

        Assertions.assertEquals(listOf(1), VList.of(1).asSequence().toList())
        Assertions.assertEquals(listOf(1, 2), VList.of(1, 2).asSequence().toList())
        Assertions.assertEquals(listOf(2, 3, 4), VList.of(1, 2, 3).asSequence().map { it + 1 }.toList())
    }

    @Test
    fun plusElement() {
        Assertions.assertInstanceOf(PersistentList::class.java, VList.of<Int>() + 1)
        Assertions.assertInstanceOf(PersistentList::class.java, VList.of(1) + 2)

        Assertions.assertEquals(PersistentList.of(1), VList.of<Int>() + 1)
        Assertions.assertEquals(PersistentList.of(1, 2), VList.of(1) + 2)
    }

    @Test
    fun plusIterable() {
        Assertions.assertInstanceOf(PersistentList::class.java, VList.of<Int>() + listOf(1))
        Assertions.assertInstanceOf(PersistentList::class.java, VList.of(1) + listOf(2, 3))

        Assertions.assertEquals(PersistentList.of<Int>(), VList.of<Int>() + listOf())
        Assertions.assertEquals(PersistentList.of(1, 2, 3), VList.of(1, 2, 3) + listOf())

        Assertions.assertEquals(PersistentList.of(1), VList.of<Int>() + listOf(1))
        Assertions.assertEquals(PersistentList.of(1, 2, 3), VList.of(1) + listOf(2, 3))

        Assertions.assertEquals(PersistentList.of(1, 2, 3, 4, 5), VList.of<Int>() + (1..5))
        Assertions.assertEquals(PersistentList.of(1, 2, 3, 4, 5), VList.of<Int>() + (1..<6))
        Assertions.assertEquals(PersistentList.of(1, 2, 3, 4, 5), VList.of(1) + (2..5))
        Assertions.assertEquals(PersistentList.of(1, 2, 3, 4, 5), VList.of(1) + sequenceOf(2, 3, 4, 5))

        Assertions.assertEquals(PersistentList.of<Int>(), VList.of<Int>() + PersistentList.of())
        Assertions.assertEquals(PersistentList.of(1, 2, 3, 4, 5), VList.of(1) + PersistentList.of(2, 3, 4, 5))
        Assertions.assertEquals(PersistentList.of(1, 2, 3, 4, 5), VList.of(1) + PersistentWrapper(listOf(2, 3, 4, 5)))
    }

    @Test
    fun getSegments() {
        Assertions.assertEquals(listOf<List<Int>>(), VList.of<Int>().getSegments())
        Assertions.assertEquals(listOf(listOf(1)), VList.of(1).getSegments())
        Assertions.assertEquals(listOf(listOf(1, 2), listOf(3)), VList.of(1, 2, 3).getSegments())
        Assertions.assertEquals(listOf(listOf(null, 1), listOf(2)), VList.of(1, 2).getSegments())
        Assertions.assertEquals(listOf(listOf(1)), VList.of<Int>().cons(1).getSegments())
        Assertions.assertEquals(listOf(listOf(null, 1), listOf(2)), VList.of<Int>().cons(2).cons(1).getSegments())
        Assertions.assertEquals(listOf(listOf(1, 2), listOf(3)), VList.of<Int>().cons(3).cons(2).cons(1).getSegments())
    }

    @Test
    fun mapSegments() {
        Assertions.assertEquals(listOf<List<Int>>(), VList.of<Int>().mapSegments { it })

        Assertions.assertEquals(
            listOf(listOf(2), listOf(3)),
            VList.of(1, 2).mapSegments { it + 1 }
        )
        Assertions.assertEquals(
            listOf(listOf(2, 3), listOf(4)),
            VList.of(1, 2, 3).mapSegments { it + 1 }
        )
        Assertions.assertEquals(
            listOf(listOf(2, 3, 4, 5), listOf(6, 7), listOf(8)),
            VList.of(1, 2, 3, 4, 5, 6, 7).mapSegments { it + 1 }
        )
        Assertions.assertEquals(
            listOf(listOf(1), listOf(2, 3, 4, 5), listOf(6, 7), listOf(8)),
            VList.of(0, 1, 2, 3, 4, 5, 6, 7).mapSegments { it + 1 }
        )
    }

    @Test
    fun isSingleton() {
        Assertions.assertFalse(VList.of<Int>().isSingleton())
        Assertions.assertTrue(VList.of(1).isSingleton())
        Assertions.assertFalse(VList.of(1, 2).isSingleton())
    }
}
