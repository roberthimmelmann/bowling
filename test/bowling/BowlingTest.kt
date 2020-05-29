package bowling

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

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
        assertEquals(12, Bowling.fromRolls(listOf(6, 4, 2, 5, 9)).frames.get(0).points)
    }

    @Test
    fun testStrike() {
        assertEquals(17, Bowling.fromRolls(listOf(10, 2, 5, 9)).frames.get(0).points)
    }

    @Test
    fun testUnfinishedSpareOrStrike() {
        assertEquals(10, Bowling.fromRolls(listOf(6, 4)).frames.get(0).points)
        assertEquals(12, Bowling.fromRolls(listOf(10, 2)).frames.get(0).points)
    }
}