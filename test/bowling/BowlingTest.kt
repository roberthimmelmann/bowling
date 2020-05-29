package bowling

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class BowlingTest {
    @Test
    fun testSingleRoll() {
        assertEquals(1, Bowling.fromRolls(listOf(1)).getTotalPoints())
        assertEquals(2, Bowling.fromRolls(listOf(2)).getTotalPoints())
    }

    @Test
    fun testMultipleRolls() {
        assertEquals(6, Bowling.fromRolls(listOf(1,2,3)).getTotalPoints())
        assertEquals(16, Bowling.fromRolls(listOf(5,2,9)).getTotalPoints())
    }
}