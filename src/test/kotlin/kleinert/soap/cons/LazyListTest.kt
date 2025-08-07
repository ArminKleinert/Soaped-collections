package kleinert.soap.cons

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.collections.LinkedHashMap
import kotlin.random.Random
import kotlin.random.nextInt

class LazyListTest {

    @Test
    fun testConstructor() {
        run {
            val ll = LazyList.of<Int>()
            Assertions.assertTrue(ll.isEmpty())
            Assertions.assertTrue(ll.isLazyType())
        }
        run {
            val ll = LazyList.of(1)
            Assertions.assertTrue(ll.isSingleton())
        }
        run {
            val ll = LazyList.of(1, 2, 3, 2, 1, 4)
            Assertions.assertEquals(listOf(1, 2, 3, 2, 1, 4), ll)
            Assertions.assertEquals(LazyList.of(1, 2, 3, 2, 1, 4), ll)
        }
        run {
            val ll = LazyList.of(1, 2, 3, 2, 1, 4)
            Assertions.assertEquals(listOf(1, 2, 3, 2, 1, 4), ll)
            val ll2 = ll
            Assertions.assertEquals(LazyList.of(1, 2, 3, 2, 1, 4), ll2)
        }
    }

    @Test
    fun take() {
        run {
            val ll = LazyList.of<Int>().take(15)
            Assertions.assertTrue(ll.isEmpty())
        }
        run {
            val ll = LazyList.of(1, 2, 3, 2, 1, 4)
            Assertions.assertEquals(listOf<Int>(), ll.take(0))
            Assertions.assertEquals(listOf<Int>(1, 2, 3), ll.take(3))
            Assertions.assertEquals(listOf<Int>(1, 2, 3, 2, 1, 4), ll.take(6))
            Assertions.assertEquals(listOf<Int>(1, 2, 3, 2, 1, 4), ll.take(128))
        }
    }

    @Test
    fun takeWhile() {
        run {
            val ll = LazyList.of<Int>().takeWhile { false }
            Assertions.assertTrue(ll.isEmpty())
        }
        run {
            val ll = LazyList.of(1, 2, 3, 2, 1, 4, 4)
            Assertions.assertEquals(listOf<Int>(), ll.takeWhile { false })
            Assertions.assertEquals(listOf(1, 2), ll.takeWhile { it < 3 })
            Assertions.assertEquals(listOf(1, 2, 3, 2, 1, 4, 4), ll.takeWhile { it < 5 })
            Assertions.assertEquals(listOf(1, 2, 3, 2, 1, 4, 4), ll.takeWhile { true })
        }
    }

    @Test
    fun drop() {
        run {
            val ll = LazyList.of<Int>().drop(15)
            Assertions.assertTrue(ll.isEmpty())
        }
        run {
            val ll = LazyList.of(1, 2, 3, 2, 1, 4)
            Assertions.assertEquals(listOf<Int>(1, 2, 3, 2, 1, 4), ll.drop(0))
            Assertions.assertEquals(listOf<Int>(2, 1, 4), ll.drop(3))
            Assertions.assertEquals(listOf<Int>(), ll.drop(6))
            Assertions.assertEquals(listOf<Int>(), ll.drop(128))
        }
    }

    @Test
    fun dropWhile() {
        run {
            val ll = LazyList.of<Int>().dropWhile { false }
            Assertions.assertTrue(ll.isEmpty())
        }
        run {
            val ll = LazyList.of(1, 2, 3, 2, 1, 4)
            Assertions.assertEquals(listOf<Int>(1, 2, 3, 2, 1, 4), ll.dropWhile { false })
            Assertions.assertEquals(listOf<Int>(4), ll.dropWhile { it <= 3 })
            Assertions.assertEquals(listOf<Int>(), ll.dropWhile { true })
        }
    }

    @Test
    fun map() {
        run {
            val ll = LazyList.of<Int>()
            val ll2 = ll.map { it + 1 }
            Assertions.assertNotSame(ll, ll2)
            Assertions.assertTrue(ll.isEmpty())
            Assertions.assertTrue(ll.isLazyType())
        }
        run {
            val ll = LazyList.of(1, 2, 3, 2, 1, 4)
            Assertions.assertEquals(listOf(2, 3, 4, 3, 2, 5), ll.map { it + 1 })
        }
    }

    @Test
    fun filter() {
        run {
            val ll = LazyList.of<Int>()
            val ll2 = ll.filter { it > 1 }
            Assertions.assertEquals(ll, ll2)
            Assertions.assertTrue(ll.isEmpty())
            Assertions.assertTrue(ll.isLazyType())
        }
        run {
            val ll = LazyList.of(1, 2, 3, 2, 1, 4)
            val ll2 = ll.filter { it > 1 }
            Assertions.assertNotSame(ll, ll2)
            Assertions.assertEquals(listOf(2, 3, 2, 4), ll2)
        }
        run {
            val ll = LazyList.of(1, 2, 3, 2, 1, 4)
            val ll2 = ll.filter { false }
            Assertions.assertNotSame(ll, ll2)
            Assertions.assertTrue(ll2.isEmpty())
            Assertions.assertEquals(listOf<Int>(), ll2)
        }
    }

    @Test
    fun filterNot() {
        run {
            val ll = LazyList.of<Int>()
            val ll2 = ll.filterNot { it > 1 }
            Assertions.assertEquals(ll, ll2)
            Assertions.assertTrue(ll.isEmpty())
            Assertions.assertTrue(ll.isLazyType())
        }
        run {
            val ll = LazyList.of(1, 2, 3, 2, 1, 4)
            Assertions.assertInstanceOf(LazyList::class.java, ll)
            val ll2 = ll.filterNot { it <= 1 }
            Assertions.assertNotSame(ll, ll2)
            Assertions.assertEquals(listOf(2, 3, 2, 4), ll2)
        }
        run {
            val ll = LazyList.of(1, 2, 3, 2, 1, 4)
            val ll2 = ll.filterNot { true }
            Assertions.assertNotSame(ll, ll2)
            Assertions.assertTrue(ll2.isEmpty())
            Assertions.assertEquals(listOf<Int>(), ll2)
        }
    }

    @Test
    fun filterNotNull() {
        run {
            val ll = LazyList.of<Int?>()
            Assertions.assertEquals(ll, ll.filterNotNull())
        }
        run {
            val ll = LazyList.of<Int?>(1, 2, 3)
            Assertions.assertEquals(ll, ll.filterNotNull())
        }
        run {
            val ll = LazyList.of<Int?>(null)
            Assertions.assertEquals(listOf<Any?>(), ll.filterNotNull())
        }
        run {
            val ll = LazyList.of<Int?>(1, null, 2)
            Assertions.assertEquals(listOf<Int>(1, 2), ll.filterNotNull())
        }
    }

    @Test
    fun repeat() {
        run {
            val ll = LazyList.repeat(1)
            Assertions.assertTrue(ll.isLazyType()) // Must not hang
            Assertions.assertTrue(ll.isNotEmpty()) // Must not hang
            Assertions.assertEquals(listOf(1, 1, 1, 1, 1, 1), ll.take(6))
            Assertions.assertEquals(listOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1), ll.take(12))
        }
    }

    @Test
    fun repeatedly() {
        run {
            val ll = LazyList.repeatedly { 1 }
            Assertions.assertTrue(ll.isLazyType()) // Must not hang
            Assertions.assertTrue(ll.isNotEmpty()) // Must not hang
            Assertions.assertEquals(listOf(1, 1, 1, 1, 1, 1), ll.take(6))
        }
        run {
            val rand = Random(0xDEADBEEF)
            val ll = LazyList.repeatedly { rand.nextInt() }
            Assertions.assertTrue(ll.isLazyType()) // Must not hang
            Assertions.assertTrue(ll.isNotEmpty()) // Must not hang
            Assertions.assertEquals(6, ll.take(6).size)
        }
    }

    @Test
    fun splitAt() {
        run {
            val ll = LazyList.of<Int>()
            require(ll is LazyList<Int>)
            val (head, tail) = ll.splitAt(5)
            Assertions.assertEquals(listOf<Int>(), head)
            Assertions.assertEquals(listOf<Int>(), head.take(8))
        }
        run {
            val ll = LazyList.repeat(1)
            require(ll is LazyList<Int>)
            val (head, tail) = ll.splitAt(5)
            Assertions.assertEquals(listOf(1, 1, 1, 1, 1), head)
            Assertions.assertEquals(listOf(1, 1, 1, 1, 1, 1, 1, 1), tail.take(8))
        }
        run {
            val ll = LazyList.of<Int>()
            require(ll is LazyList<Int>)
            val (head, tail) = ll.splitAt(5)
            Assertions.assertTrue(head.isEmpty())
            Assertions.assertEquals(listOf<Int>(), head)
            Assertions.assertTrue(tail.isEmpty())
            Assertions.assertEquals(listOf<Int>(), tail.take(8))
        }
        run {
            val ll = LazyList.of(1, 2, 3)
            require(ll is LazyList<Int>)
            val (head, tail) = ll.splitAt(0)
            Assertions.assertEquals(listOf<Int>(), head)
            Assertions.assertEquals(listOf(1, 2, 3), tail)
        }
    }

    @Test
    fun cycle() {
        run {
            val ll = LazyList.of<Int>()
            Assertions.assertTrue(ll.cycle().isEmpty()) // Tests constraint: list.cycle() == [] <=> list == []
        }
        run {
            val ll = LazyList.of(1, 2, 3)
            val cycled = ll.cycle()
            Assertions.assertEquals(listOf(1, 2, 3, 1, 2, 3), cycled.take(6))
        }
        run {
            val ll = LazyList.iterate({ it + 1 }, 1)
            val cycled = ll.cycle() // If the list is infinite and never repeats, then list.cycle() still never repeats.
            Assertions.assertEquals(listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), cycled.take(10))
        }
    }

    @Test
    fun distinct() {
        TreeMap<Int, Int>().clone()
        LinkedHashMap<Int, Int>().clone()
        run {
            val ll = LazyList.of<Int>().distinct()
            Assertions.assertTrue(ll.isEmpty())
        }
        run {
            val ll = LazyList.of(1).distinct()
            Assertions.assertEquals(listOf(1), ll)
        }
        run {
            val ll = LazyList.of(1, 2, 3)
            Assertions.assertEquals(ll, ll.distinct())
        }
        run {
            val ll = LazyList.of(1, 2, 3, 2, 1, 4)
            Assertions.assertEquals(listOf(1, 2, 3, 4), ll.distinct())
        }
    }

    @Test
    fun flatMap() {
        Assertions.assertEquals(LazyList.of<Int>(), LazyList.of<Int>().flatMap { listOf(it) })
        Assertions.assertEquals(LazyList.of(1, 2, 3), LazyList.of(1, 2, 3).flatMap { listOf(it) })
        Assertions.assertEquals(LazyList.of(1, 2, 3), LazyList.of(listOf(1, 2), listOf(3)).flatMap { it })
    }

    @Test
    fun flatMapIndexed() {
        Assertions.assertEquals(LazyList.of<Int>(), LazyList.of<Int>().flatMapIndexed { _, it -> listOf(it) })
        Assertions.assertEquals(LazyList.of(1, 2, 3), LazyList.of(1, 2, 3).flatMapIndexed { _, it -> listOf(it) })
        Assertions.assertEquals(LazyList.of(1, 3, 5), LazyList.of(1, 2, 3).flatMapIndexed { i, it -> listOf(i + it) })
        Assertions.assertEquals(LazyList.of(1, 2, 3), LazyList.of(listOf(1, 2), listOf(3)).flatMapIndexed { _, it -> it })
    }

    @Test
    fun mapNotNull() {
        Assertions.assertEquals(LazyList.of<Int>(), LazyList.of<Int>().mapNotNull { it })
        Assertions.assertEquals(LazyList.of(2), LazyList.of(1, 2, 5).mapNotNull { if (it % 2 == 0) it else null })
        Assertions.assertEquals(LazyList.of(1,5), LazyList.of(1, 2, 5).mapNotNull { if (it % 2 != 0) it else null })

        val seq = LazyList.iterate({it+1}, 0).mapNotNull { if (it % 2 != 0) it else null }.take(5)
        Assertions.assertEquals(LazyList.of(1,3, 5, 7, 9), seq)
    }

    @Test
    fun minus() {
        // Minus with xs empty
        Assertions.assertEquals(LazyList.of<Int>(), LazyList.of<Int>().minus(1))
        Assertions.assertEquals(LazyList.of<Int>(), LazyList.of<Int>().minus(listOf(1, 2, 3)))
        Assertions.assertEquals(LazyList.of<Int>(), LazyList.of<Int>().minus(setOf(1, 2, 3)))

        // Minus nothing
        Assertions.assertEquals(LazyList.of(1, 2, 3), LazyList.of(1, 2, 3).minus(listOf()))
        Assertions.assertEquals(LazyList.of(1, 2, 3), LazyList.of(1, 2, 3).minus(setOf()))

        // Minus but none of the elements match
        Assertions.assertEquals(LazyList.of(1, 2, 3), LazyList.of(1, 2, 3).minus(4))
        Assertions.assertEquals(LazyList.of(1, 2, 3), LazyList.of(1, 2, 3).minus(listOf(4, 5, 6)))
        Assertions.assertEquals(LazyList.of(1, 2, 3), LazyList.of(1, 2, 3).minus(setOf(4, 5, 6)))

        // Minus all
        Assertions.assertEquals(LazyList.of<Int>(), LazyList.of(1, 2, 3).minus(1).minus(2).minus(3))
        Assertions.assertEquals(LazyList.of<Int>(), LazyList.of(1, 2, 3).minus(listOf(1, 2, 3)))
        Assertions.assertEquals(LazyList.of<Int>(), LazyList.of(1, 2, 3).minus(setOf(1, 2, 3)))

        // Minus partial match
        Assertions.assertEquals(LazyList.of(1, 3), LazyList.of(1, 2, 3).minus(2))
        Assertions.assertEquals(LazyList.of(3), LazyList.of(1, 2, 3).minus(listOf(1, 2, 4)))
        Assertions.assertEquals(LazyList.of(3), LazyList.of(1, 2, 3).minus(setOf(1, 2, 4)))

        // Works on infinite lists
        Assertions.assertEquals(LazyList.of(1, 2, 1, 2), LazyList.cycle(LazyList.of(1, 2, 3)).minus(3).take(4))
        Assertions.assertEquals(LazyList.of(1, 2, 1, 2), LazyList.cycle(LazyList.of(1, 2, 3)).minus(listOf(3)).take(4))
        Assertions.assertEquals(LazyList.of(1, 2, 1, 2), LazyList.cycle(LazyList.of(1, 2, 3)).minus(setOf(3)).take(4))
    }

    @Test
    fun windowed() {
        run { //Tests on empty inputs
            Assertions.assertEquals(PersistentList.of<PersistentList<Int>>(), PersistentList.of<Int>().windowed(2))

            Assertions.assertEquals(
                PersistentList.of<PersistentList<Int>>(),
                PersistentList.of<Int>().windowed(2, partialWindows = true)
            )
        }

        run { // Tests on basic, finite inputs
            val seq = PersistentList.of(1, 2, 3, 4)
            Assertions.assertEquals(listOf(listOf(1, 2), listOf(2, 3), listOf(3, 4)), seq.windowed(2))
            Assertions.assertEquals(listOf(listOf(1, 2), listOf(3, 4)), seq.windowed(2, step = 2))
            Assertions.assertEquals(listOf(listOf(1, 2, 3)), seq.windowed(3, step = 3))
            Assertions.assertEquals(listOf(listOf(1, 2, 3), listOf(4)), seq.windowed(3, step = 3, partialWindows = true))

            Assertions.assertEquals(listOf(3), seq.windowed(3, step = 3, transform = {it.size}))
            Assertions.assertEquals(listOf(3, 1), seq.windowed(3, step = 3, partialWindows = true, transform = {it.size}))
        }
        run { // Test on infinite input
            val seq = PersistentList.of(1, 2, 3).cycle()
            Assertions.assertEquals(listOf(listOf(1, 2, 3, 1), listOf(2, 3, 1, 2), listOf(3, 1, 2, 3)), seq.windowed(4).take(3))

            val windowsXP = seq.windowed(4, transform={ (it as Iterable<Int>).sum()})
            Assertions.assertEquals(listOf(7, 8, 9), windowsXP.take(3))
        }
    }
}
