package bowling

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class BowlingTest {
    @Test
    fun testScore() {
        assertEquals(1, Bowling.fromRolls(listOf(1)).getPoints())
        assertEquals(2, Bowling.fromRolls(listOf(2)).getPoints())
    }
}