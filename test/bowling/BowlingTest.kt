package bowling

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertNull
import kotlin.test.assertTrue

internal class BowlingTest {
    @Test
    fun testSingleRoll() {
        assertEquals(1, Bowling.fromRolls(listOf(1)).getTotalPoints())
        assertEquals(2, Bowling.fromRolls(listOf(2)).getTotalPoints())
    }

    @Test
    fun testMultipleRolls() {
        assertEquals(6, Bowling.fromRolls(listOf(1, 2, 3)).getTotalPoints())
        assertEquals(16, Bowling.fromRolls(listOf(5, 2, 9)).getTotalPoints())
    }

    @Test
    fun testSpare() {
        assertEquals(12, Bowling.fromRolls(listOf(6, 4, 2, 5, 9)).frames.get(0).getPoints())
    }

    @Test
    fun testStrike() {
        assertEquals(17, Bowling.fromRolls(listOf(10, 2, 5, 9)).frames.get(0).getPoints())
    }

    @Test
    fun testUnfinishedSpareOrStrike() {
        assertEquals(10, Bowling.fromRolls(listOf(6, 4)).frames.get(0).getPoints())
        assertEquals(12, Bowling.fromRolls(listOf(10, 2)).frames.get(0).getPoints())
    }

    @Test
    fun testMultipleFrames() {
        val b = Bowling.fromRolls(listOf(1, 3, 10, 5, 5, 3, 1)).frames

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

    @Test
    fun testGameFinished() {
        assertTrue { Bowling.fromRolls(listOf(1, 1, 1, 1)).isGameFinished(2) }
        assertTrue { Bowling.fromRolls(listOf(10, 1, 1)).isGameFinished(2) }
    }
}