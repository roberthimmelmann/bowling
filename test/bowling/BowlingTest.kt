package bowling

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertNull

internal class BowlingTest {
    fun getScore(rolls: List<Int>) =
        Main.getScore(Main.framesFromRolls(rolls))

    @Test
    fun testSingleRoll() {
        assertEquals(1, getScore(listOf(1)))
        assertEquals(2, getScore(listOf(2)))
    }

    @Test
    fun testMultipleRolls() {
        assertEquals(6, getScore(listOf(1, 2, 3)))
        assertEquals(16, getScore(listOf(5, 2, 9)))
    }

    @Test
    fun testSpare() {
        assertEquals(12, Main.framesFromRolls(listOf(6, 4, 2, 5, 9)).get(0).getPoints())
    }

    @Test
    fun testStrike() {
        assertEquals(17, Main.framesFromRolls(listOf(10, 2, 5, 9)).get(0).getPoints())
    }

    @Test
    fun testUnfinishedSpareOrStrike() {
        assertEquals(10, Main.framesFromRolls(listOf(6, 4)).get(0).getPoints())
        assertEquals(12, Main.framesFromRolls(listOf(10, 2)).get(0).getPoints())
    }

    @Test
    fun testMultipleFrames() {
        val b = Main.framesFromRolls(listOf(1, 3, 10, 5, 5, 3, 1))

        assertEquals(1, b.get(0).firstRoll)
        assertEquals(3, b.get(0).secondRoll)
        assertEquals(4, b.get(0).getPoints())

        assertEquals(10, b.get(1).firstRoll)
        assertNull(b.get(1).secondRoll)
        assertEquals(20, b.get(1).getPoints())

        assertEquals(5, b.get(2).firstRoll)
        assertEquals(5, b.get(2).secondRoll)
        assertEquals(13, b.get(2).getPoints())
    }
}