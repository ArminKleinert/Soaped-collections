package kleinert.soap.cons

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class PersistentListTest {
    @Test
    fun of() {
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentList.of<Boolean>())
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentList.of(true))
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentList.of(true, false))

        Assertions.assertEquals(PersistentList.of<Boolean>(), PersistentList.of<Boolean>())
        Assertions.assertEquals(VList.of<Boolean>(), PersistentList.of<Boolean>())
        Assertions.assertEquals(PersistentWrapper.of<Boolean>(), PersistentList.of<Boolean>())
        Assertions.assertEquals(nullCons<Boolean>(), PersistentList.of<Boolean>())
        Assertions.assertEquals(listOf<Boolean>(), PersistentList.of<Boolean>())

        Assertions.assertEquals(PersistentList.of(true), PersistentList.of(true))
        Assertions.assertEquals(VList.of(true), PersistentList.of(true))
        Assertions.assertEquals(PersistentWrapper.of(true), PersistentList.of(true))
        Assertions.assertEquals(PersistentListHead(true, nullCons()), PersistentList.of(true))
        Assertions.assertEquals(listOf(true), PersistentList.of(true))

        Assertions.assertEquals(PersistentList.of(true, false), PersistentList.of(true, false))
        Assertions.assertEquals(VList.of(true, false), PersistentList.of(true, false))
        Assertions.assertEquals(PersistentWrapper.of(true, false), PersistentList.of(true, false))
        Assertions.assertEquals(PersistentListHead(true, PersistentListHead(false, nullCons())), PersistentList.of(true, false))
        Assertions.assertEquals(listOf(true, false), PersistentList.of(true, false))
    }

    @Test
    fun fromIterable() {
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentList.from<Boolean>(arrayOf()))
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentList.from<Boolean>(listOf()))
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentList.from(sequenceOf<Boolean>().asIterable()))

        Assertions.assertEquals(nullCons<Boolean>(), PersistentList.from<Boolean>(arrayOf()))
        Assertions.assertEquals(nullCons<Boolean>(), PersistentList.from<Boolean>(listOf()))
        Assertions.assertEquals(nullCons<Boolean>(), PersistentList.from(sequenceOf<Boolean>().asIterable()))

        Assertions.assertInstanceOf(PersistentList::class.java, PersistentList.from(arrayOf(true)))
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentList.from(listOf(true)))
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentList.from(sequenceOf(true).asIterable()))

        Assertions.assertEquals(PersistentList.of(true), PersistentList.from(arrayOf(true)))
        Assertions.assertEquals(PersistentList.of(true), PersistentList.from(listOf(true)))
        Assertions.assertEquals(PersistentList.of(true), PersistentList.from(sequenceOf(true).asIterable()))

        Assertions.assertInstanceOf(PersistentList::class.java, PersistentList.from(arrayOf(true, false)))
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentList.from(listOf(true, false)))
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentList.from(sequenceOf(true, false).asIterable()))

        Assertions.assertEquals(PersistentList.of(true, false), PersistentList.from(arrayOf(true, false)))
        Assertions.assertEquals(PersistentList.of(true, false), PersistentList.from(listOf(true, false)))
        Assertions.assertEquals(PersistentList.of(true, false), PersistentList.from(sequenceOf(true, false).asIterable()))
    }

    @Test
    fun from() {
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentList.from<Boolean>(listOf()))
        Assertions.assertEquals(PersistentWrapper.of<Boolean>(), PersistentList.from<Boolean>(listOf()))

        Assertions.assertInstanceOf(PersistentWrapper::class.java, PersistentList.from(listOf(true)))
        Assertions.assertEquals(PersistentWrapper.of(true), PersistentList.from(listOf(true)))

        Assertions.assertInstanceOf(PersistentWrapper::class.java, PersistentList.from(listOf(true, false)))
        Assertions.assertEquals(PersistentWrapper.of(true, false), PersistentList.from(listOf(true, false)))
    }

    @Test
    fun randomAccess() {
        Assertions.assertInstanceOf(RandomAccess::class.java, PersistentList.randomAccess<Boolean>(listOf()))
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentList.randomAccess<Boolean>(listOf()))
        Assertions.assertEquals(PersistentList.of<Boolean>(), PersistentList.randomAccess<Boolean>(listOf()))

        Assertions.assertInstanceOf(RandomAccess::class.java, PersistentList.randomAccess(listOf(true)))
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentList.randomAccess<Boolean>(listOf()))
        Assertions.assertEquals(PersistentList.of(true), PersistentList.randomAccess(listOf(true)))

        Assertions.assertInstanceOf(RandomAccess::class.java, PersistentList.randomAccess(listOf(true, false)))
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentList.randomAccess<Boolean>(listOf()))
        Assertions.assertEquals(PersistentList.of(true, false), PersistentList.randomAccess(listOf(true, false)))
    }

    @Test
    fun log2Access() {
        Assertions.assertInstanceOf(PersistentList::class.java, PersistentList.log2Access<Boolean>(listOf()))
        Assertions.assertEquals(PersistentList.of<Boolean>(), PersistentList.log2Access<Boolean>(listOf()))

        Assertions.assertInstanceOf(PersistentList::class.java, PersistentList.log2Access<Boolean>(listOf()))
        Assertions.assertEquals(PersistentList.of(true), PersistentList.log2Access(listOf(true)))

        Assertions.assertInstanceOf(PersistentList::class.java, PersistentList.log2Access<Boolean>(listOf()))
        Assertions.assertEquals(PersistentList.of(true, false), PersistentList.log2Access(listOf(true, false)))
    }

    @Test
    fun equalsConsOf() {
        Assertions.assertEquals(PersistentList.of<Boolean>(), PersistentWrapper.of<Boolean>())
        Assertions.assertEquals(PersistentList.of(true, false), PersistentWrapper.of(true, false))

        Assertions.assertEquals(PersistentList.of<Boolean>(), nullCons<Boolean>())
        Assertions.assertEquals(PersistentList.of(true, false), nullCons<Boolean>().cons(false).cons(true))

        Assertions.assertEquals(PersistentList.of<Boolean>(), VList.of<Boolean>())
        Assertions.assertEquals(PersistentList.of(true, false), VList.of(true, false))

        Assertions.assertEquals(
            PersistentList.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10),
            ListPair.concat(PersistentList.from(1..5), PersistentList.from(6..10))
        )
    }

    @Test
    fun equalsCdrCodedListOf() {
        Assertions.assertEquals(PersistentWrapper.of<Boolean>(), PersistentWrapper.of<Boolean>())
        Assertions.assertEquals(PersistentWrapper.of(true, false), PersistentWrapper.of(true, false))

        Assertions.assertEquals(PersistentWrapper.of<Boolean>(), nullCons<Boolean>())
        Assertions.assertEquals(PersistentWrapper.of(true, false), nullCons<Boolean>().cons(false).cons(true))

        Assertions.assertEquals(PersistentWrapper.of<Boolean>(), VList.of<Boolean>())
        Assertions.assertEquals(PersistentWrapper.of(true, false), VList.of(true, false))

        Assertions.assertEquals(
            PersistentWrapper.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10),
            ListPair.concat(PersistentList.from(1..5), PersistentList.from(6..10))
        )
    }

    @Test
    fun equalsEmptyCons() {
        Assertions.assertEquals(nullCons<Boolean>(), PersistentWrapper.of<Boolean>())
        Assertions.assertEquals(nullCons<Boolean>(), nullCons<Boolean>())
        Assertions.assertEquals(nullCons<Boolean>(), VList.of<Boolean>())
    }

    @Test
    fun equalsCell() {
        val trueFalse = PersistentListHead(true, PersistentListHead(false, nullCons()))
        Assertions.assertEquals(trueFalse, PersistentWrapper.of(true, false))
        Assertions.assertEquals(trueFalse, nullCons<Boolean>().cons(false).cons(true))
        Assertions.assertEquals(trueFalse, VList.of(true, false))

        val oneToTen = PersistentListHead(
            1,
            PersistentListHead(
                2,
                PersistentListHead(
                    3,
                    PersistentListHead(
                        4,
                        PersistentListHead(5, PersistentListHead(6, PersistentListHead(7, PersistentListHead(8, PersistentListHead(9, PersistentListHead(10, nullCons()))))))
                    )
                )
            )
        )
        Assertions.assertEquals(oneToTen, ListPair.concat(PersistentList.from(1..5), PersistentList.from(6..10)))
    }

    @Test
    fun equalsVListOf() {
        Assertions.assertEquals(VList.of<Boolean>(), PersistentWrapper.of<Boolean>())
        Assertions.assertEquals(VList.of(true, false), PersistentWrapper.of(true, false))

        Assertions.assertEquals(VList.of<Boolean>(), nullCons<Boolean>())
        Assertions.assertEquals(VList.of(true, false), nullCons<Boolean>().cons(false).cons(true))

        Assertions.assertEquals(VList.of<Boolean>(), VList.of<Boolean>())
        Assertions.assertEquals(VList.of(true, false), VList.of(true, false))

        Assertions.assertEquals(
            VList.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10),
            ListPair.concat(PersistentList.from(1..5), PersistentList.from(6..10))
        )
    }

    @Test
    fun equalsListOf() {
        Assertions.assertEquals(listOf<Boolean>(), PersistentWrapper.of<Boolean>())
        Assertions.assertEquals(listOf(true, false), PersistentWrapper.of(true, false))

        Assertions.assertEquals(listOf<Boolean>(), nullCons<Boolean>())
        Assertions.assertEquals(listOf(true, false), nullCons<Boolean>().cons(false).cons(true))

        Assertions.assertEquals(listOf<Boolean>(), VList.of<Boolean>())
        Assertions.assertEquals(listOf(true, false), VList.of(true, false))

        Assertions.assertEquals(
            listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10),
            ListPair.concat(PersistentList.from(1..5), PersistentList.from(6..10))
        )
    }
}
