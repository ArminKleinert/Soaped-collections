package kleinert.soap.cons

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import kotlin.random.Random

class EmptyListTest {

    private val instance = nullCons<Boolean>()

    @Test
    fun testConstructor() {
        Assertions.assertEquals(instance, nullCons<Boolean>())
    }

    @Test
    fun getSize() {
        Assertions.assertEquals(0, instance.size)
    }

    @Test
    fun cons() {
        Assertions.assertInstanceOf(PersistentList::class.java, instance.cons(true))
        Assertions.assertEquals(PersistentList.of(true), instance.cons(true))
        Assertions.assertEquals(PersistentList.of(false, true), instance.cons(true).cons(false))
        Assertions.assertEquals(PersistentList.of(true, false, true), instance.cons(true).cons(false).cons(true))
    }

    @Test
    fun cdr() {
        Assertions.assertInstanceOf(EmptyList::class.java, instance.cdr)
        Assertions.assertTrue(instance.isEmpty())
        Assertions.assertTrue(instance.cdr.isEmpty())
        Assertions.assertSame(instance, instance.cdr)
    }

    @Test
    fun car() {
        Assertions.assertThrows(NoSuchElementException::class.java) { instance.car }
    }

    @Test
    fun contains() {
        Assertions.assertFalse(instance.contains(true))
        Assertions.assertFalse(nullCons<String>().contains(""))
    }

    @Test
    fun containsAll() {
        Assertions.assertFalse(instance.containsAll(listOf(true)))
        Assertions.assertFalse(nullCons<String>().containsAll(listOf("")))
    }

    @Test
    fun get() {
        Assertions.assertThrows(IndexOutOfBoundsException::class.java) { instance[0] }
        Assertions.assertThrows(IndexOutOfBoundsException::class.java) { instance[1] }
        Assertions.assertThrows(IndexOutOfBoundsException::class.java) { instance[2] }
    }

    @Test
    fun isEmpty() {
        Assertions.assertTrue(instance.isEmpty())
        Assertions.assertTrue(nullCons<String>().isEmpty())
    }

    @Test
    fun indexOf() {
        Assertions.assertEquals(-1, instance.indexOf(true))
        Assertions.assertEquals(-1, instance.indexOf(false))
    }

    @Test
    fun lastIndexOf() {
        Assertions.assertEquals(-1, instance.lastIndexOf(true))
        Assertions.assertEquals(-1, instance.lastIndexOf(false))
    }

    @Test
    fun iterator() {
        run {
            var counter = 0
            for (e in instance) {
                counter++
            }
            Assertions.assertEquals(0, counter)
        }
        Assertions.assertEquals(false, instance.fold(false, Boolean::or))
        Assertions.assertEquals(true, instance.fold(true, Boolean::or))
        Assertions.assertEquals(false, instance.foldRight(false, Boolean::or))
        Assertions.assertEquals(true, instance.foldRight(true, Boolean::or))
    }

    @Test
    fun listIterator() {
        run {
            var counter = 0
            for (e in instance.listIterator()) {
                counter++
            }
            Assertions.assertEquals(0, counter)
        }
    }

    @Test
    fun subList() {
        Assertions.assertThrows(IndexOutOfBoundsException::class.java) { instance.subList(0, 1).size }
        Assertions.assertThrows(IndexOutOfBoundsException::class.java) { instance.subList(1, 2).size }
        Assertions.assertEquals(instance, instance.subList(0, 0))
    }

    @Test
    fun toMutableList() {
        Assertions.assertTrue(instance.toMutableList().isEmpty())
        Assertions.assertEquals(0, instance.toMutableList().size)
    }

    @Test
    fun reversed() {
        Assertions.assertInstanceOf(EmptyList::class.java, instance.reversed())
        Assertions.assertEquals(instance, instance.reversed())
    }

    @Test
    fun map() {
        Assertions.assertInstanceOf(PersistentList::class.java, instance.map { it })

        Assertions.assertEquals(instance, instance.map { it })
        Assertions.assertEquals(instance, instance.map { false })
    }

    @Test
    fun mapIndexed() {
        Assertions.assertInstanceOf(PersistentList::class.java, instance.mapIndexed { _, elem -> elem })
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentWrapper.of(true).mapIndexed { _, elem -> elem })

        Assertions.assertEquals(instance, instance.mapIndexed { _, elem -> elem })
        Assertions.assertEquals(nullCons<Int>(), instance.mapIndexed { i, _ -> i })
    }

    @Test
    fun filter() {
        Assertions.assertTrue(instance.filter { true }.isEmpty())
        Assertions.assertTrue(instance.filter { false }.isEmpty())

        Assertions.assertEquals(instance, instance.filter { true })
        Assertions.assertEquals(instance, instance.filter { it })
    }

    @Test
    fun filterNot() {
        Assertions.assertTrue(instance.filterNot { true }.isEmpty())
        Assertions.assertTrue(instance.filterNot { false }.isEmpty())

        Assertions.assertEquals(instance, instance.filterNot { true })
        Assertions.assertEquals(instance, instance.filterNot { it })
    }

    @Test
    fun filterv() {
        Assertions.assertInstanceOf(EmptyList::class.java, instance.filterv { true })
        Assertions.assertInstanceOf(EmptyList::class.java, instance.filterv { false })

        Assertions.assertEquals(instance, instance.filterv { true })
        Assertions.assertEquals(instance, instance.filterv { it })
    }

    @Test
    fun filterVNot() {
        Assertions.assertTrue(instance.filterVNot { true }.isEmpty())
        Assertions.assertEquals(instance, instance.filterVNot { true })
        Assertions.assertEquals(instance, instance.filterVNot { it })
    }

    @Test
    fun cadr() {
        Assertions.assertThrows(NoSuchElementException::class.java) { instance.cadr }
    }

    @Test
    fun caddr() {
        Assertions.assertThrows(NoSuchElementException::class.java) { instance.caddr }
    }

    @Test
    fun cadddr() {
        Assertions.assertThrows(NoSuchElementException::class.java) { instance.cadddr }
    }

    @Test
    fun cddr() {
        Assertions.assertSame(instance, instance.cddr)
    }

    @Test
    fun cdddr() {
        Assertions.assertSame(instance, instance.cdddr)
    }

    @Test
    fun cddddr() {
        Assertions.assertSame(instance, instance.cddddr)
    }

    @Test
    fun flatMap() {
        Assertions.assertInstanceOf(PersistentList::class.java, instance.flatMap { listOf(it) })
        Assertions.assertEquals(instance, instance.flatMap { listOf(it) })
        Assertions.assertEquals(instance, instance.flatMap { listOf(false, true) })
    }

    @Test
    fun take() {
        Assertions.assertInstanceOf(PersistentList::class.java, instance.take(0))
        Assertions.assertInstanceOf(PersistentList::class.java, instance.take(10))

        Assertions.assertEquals(instance, instance.take(0))
        Assertions.assertEquals(instance, instance.take(10))
    }

    @Test
    fun drop() {
        Assertions.assertInstanceOf(PersistentList::class.java, instance.drop(0))
        Assertions.assertInstanceOf(PersistentList::class.java, instance.drop(10))

        Assertions.assertEquals(instance, instance.drop(0))
        Assertions.assertEquals(instance, instance.drop(10))
    }

    @Test
    fun takeWhile() {
        Assertions.assertInstanceOf(PersistentList::class.java, instance.takeWhile { true })
        Assertions.assertInstanceOf(PersistentList::class.java, instance.takeWhile { false })
        Assertions.assertInstanceOf(PersistentList::class.java, instance.takeWhile { it })

        Assertions.assertEquals(instance, instance.takeWhile { true })
        Assertions.assertEquals(instance, instance.takeWhile { false })
    }

    @Test
    fun dropWhile() {
        Assertions.assertInstanceOf(PersistentList::class.java, instance.dropWhile { true })
        Assertions.assertInstanceOf(PersistentList::class.java, instance.dropWhile { false })
        Assertions.assertInstanceOf(PersistentList::class.java, instance.dropWhile { it })

        Assertions.assertEquals(instance, instance.dropWhile { true })
        Assertions.assertEquals(instance, instance.dropWhile { false })
    }

//    @Test
//    fun sorted() {
//        Assertions.assertInstanceOf(Cons::class.java, (CdrCodedList.of<Boolean>() as Cons<Boolean>).sorted())
//        Assertions.assertInstanceOf(Cons::class.java, CdrCodedList.of<Boolean>().sorted())
//        Assertions.assertEquals(CdrCodedList.of<Boolean>(), CdrCodedList.of<Boolean>().sorted())
//        Assertions.assertInstanceOf(Cons::class.java, CdrCodedList.of(true, false, true).sorted())
//        Assertions.assertEquals(CdrCodedList.of(true, false, true), CdrCodedList.of(true, false, true).sorted())
//        Assertions.assertEquals(CdrCodedList.of(true, false, true), CdrCodedList.of(true, false, true).sorted())
//        Assertions.assertEquals(CdrCodedList.of(true, false, true, true), CdrCodedList.of(true, false, true, true).sorted())
//    }

//    @Test
//    fun sortedDescending() {
//        Assertions.assertInstanceOf(Cons::class.java, CdrCodedList.of<Boolean>().sortedDescending())
//        Assertions.assertEquals(CdrCodedList.of<Boolean>(), CdrCodedList.of<Boolean>().sortedDescending())
//        Assertions.assertInstanceOf(Cons::class.java, CdrCodedList.of(true, false, true).sortedDescending())
//        Assertions.assertEquals(CdrCodedList.of(true, false, true), CdrCodedList.of(true, false, true).sortedDescending())
//        Assertions.assertEquals(CdrCodedList.of(true, false, true), CdrCodedList.of(true, false, true).sortedDescending())
//        Assertions.assertEquals(CdrCodedList.of(true, true, false, true), CdrCodedList.of(true, false, true, true).sortedDescending())
//    }

    @Test
    fun sortedBy() {
        Assertions.assertInstanceOf(PersistentList::class.java, nullCons<Int>().sortedBy { it })
        Assertions.assertEquals(nullCons<Int>(), nullCons<Int>().sortedBy { it })
    }

    @Test
    fun sortedByDescending() {
        Assertions.assertInstanceOf(PersistentList::class.java, nullCons<Int>().sortedByDescending { it })
        Assertions.assertEquals(nullCons<Int>(), nullCons<Int>().sortedByDescending { it })
    }

    @Test
    fun sortedWith() {
        Assertions.assertInstanceOf(PersistentList::class.java, nullCons<Int>().sortedWith { n, m -> n.compareTo(m) })
        Assertions.assertEquals(nullCons<Int>(), nullCons<Int>().sortedWith { n, m -> n.compareTo(m) })
    }

    @Test
    fun distinct() {
        Assertions.assertInstanceOf(PersistentList::class.java, instance.distinct())
        Assertions.assertEquals(instance, instance.distinct())
    }

    @Test
    fun shuffled() {
        Assertions.assertInstanceOf(PersistentList::class.java, instance.shuffled())

        val seed = 0xDEADBEEF
        val rand = Random(seed)
        Assertions.assertEquals(instance, instance.shuffled(rand))
        Assertions.assertEquals(instance.shuffled(rand), instance.shuffled(rand))
    }

    @Test
    fun asSequence() {
        Assertions.assertInstanceOf(Sequence::class.java, instance.asSequence())
        Assertions.assertEquals(listOf<Boolean>(), instance.asSequence().toList())
        Assertions.assertEquals(listOf<Boolean>(), instance.asSequence().map { !it }.toList())
    }

    @Test
    fun plusElement() {
        Assertions.assertInstanceOf(PersistentList::class.java, instance + true)
        Assertions.assertEquals(PersistentList.of(true), instance + true)
    }

    @Test
    fun plusIterable() {
        Assertions.assertInstanceOf(PersistentList::class.java, instance + listOf(true))

        Assertions.assertEquals(instance, instance + listOf())

        Assertions.assertEquals(PersistentList.of(true), instance + listOf(true))
        Assertions.assertEquals(PersistentList.of(1, 2, 3, 4, 5), nullCons<Int>() + (1..5))

        Assertions.assertEquals(instance, instance + PersistentList.of())
        Assertions.assertEquals(
            PersistentList.of(false, true, false, true),
            instance + PersistentList.of(false, true, false, true)
        )
        Assertions.assertEquals(
            PersistentList.of(false, true, false, true),
            instance + PersistentWrapper(listOf(false, true, false, true))
        )
    }

    @Test
    fun plusVList() {
        Assertions.assertInstanceOf(VList::class.java, instance + VList.of())
        Assertions.assertEquals(VList.of<Boolean>(), instance + VList.of())

        Assertions.assertInstanceOf(VList::class.java, instance + VList.of(true))
        Assertions.assertEquals(VList.of(true), instance + VList.of(true))

        Assertions.assertInstanceOf(VList::class.java, instance + VList.of(true, false, true, false))
        Assertions.assertEquals(VList.of(true, false, true, false), instance + VList.of(true, false, true, false))
    }

    @Test
    fun isSingleton() {
        Assertions.assertFalse(instance.isSingleton())
    }
}