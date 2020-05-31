package bowling

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

internal class MockCmdInteraction : CmdInteraction() {
    val rolls = LinkedList<Int>()
    val output = LinkedList<String>()

    override fun readInt(): Int {
        return rolls.removeFirst()
    }

    override fun println(arg: String) {
        output.add(arg)
    }

    override fun print(arg: String) {
        output.add(arg)
    }
}

internal class BowlingTest {
    fun getScore(rolls: List<Int>) =
        Bowling.getScore(Bowling.framesFromRolls(rolls))

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
        assertEquals(12, Bowling.framesFromRolls(listOf(6, 4, 2, 5, 9)).get(0).getPoints())
    }

    @Test
    fun testStrike() {
        assertEquals(17, Bowling.framesFromRolls(listOf(10, 2, 5, 9)).get(0).getPoints())
    }

    @Test
    fun testUnfinishedSpareOrStrike() {
        assertEquals(10, Bowling.framesFromRolls(listOf(6, 4)).get(0).getPoints())
        assertEquals(12, Bowling.framesFromRolls(listOf(10, 2)).get(0).getPoints())
    }

    @Test
    fun testMultipleFrames() {
        val b = Bowling.framesFromRolls(listOf(1, 3, 10, 5, 5, 3, 1))

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
    fun testMain() {
        val cmd = MockCmdInteraction()
        val main = Bowling(cmd, 1)
        cmd.rolls.add(5)
        cmd.rolls.add(3)
        main.run()
        repeat(4) { cmd.output.removeFirst() }
        assertEquals("Next roll > ", cmd.output.removeFirst())
        repeat(4) { cmd.output.removeFirst() }
        assertEquals("Next roll > ", cmd.output.removeFirst())
        repeat(4) { cmd.output.removeFirst() }
        assertEquals("Game finished!", cmd.output.removeFirst())
        assertEquals("Final score: 8", cmd.output.removeFirst())
    }

    @Test
    fun testGameFinished() {
        val cmd = MockCmdInteraction()
        val main = Bowling(cmd, 2)
        //Finished games
        assertTrue { main.isGameFinished(Bowling.framesFromRolls(listOf(1, 1, 1, 1), 2)) }
        assertTrue { main.isGameFinished(Bowling.framesFromRolls(listOf(10, 1, 1), 2)) }
        //unfinished game
        assertFalse { main.isGameFinished(Bowling.framesFromRolls(listOf(1, 1, 1), 2)) }
        //after a strike the game doesn't immediately end
        assertFalse { main.isGameFinished(Bowling.framesFromRolls(listOf(1, 1, 10), 2)) }
        assertFalse { main.isGameFinished(Bowling.framesFromRolls(listOf(1, 1, 10, 1), 2)) }
        assertTrue { main.isGameFinished(Bowling.framesFromRolls(listOf(1, 1, 10, 1, 1), 2)) }
        //after a spare the game doesn't immediately end
        assertFalse { main.isGameFinished(Bowling.framesFromRolls(listOf(1, 1, 5, 5), 2)) }
        assertTrue { main.isGameFinished(Bowling.framesFromRolls(listOf(1, 1, 5, 5, 1), 2)) }
    }

    @Test
    fun testPrintSingleFrame() {
        val cmd = MockCmdInteraction()
        val main = Bowling(cmd, 1)
        main.printScore(Bowling.framesFromRolls(listOf(5, 3), 1))
        assertEquals("        +--1--+", cmd.output.removeFirst())
        assertEquals("Rolls:  | 5 3 |", cmd.output.removeFirst())
        assertEquals("Points: |   8 |", cmd.output.removeFirst())
        assertEquals("        +-----+", cmd.output.removeFirst())
    }

    @Test
    fun testPrintMultipleFrames() {
        val cmd = MockCmdInteraction()
        var main = Bowling(cmd, 2)
        main.printScore(Bowling.framesFromRolls(listOf(5, 3, 2, 1), 2))
        assertEquals("        +--1--+--2--+", cmd.output.removeFirst())
        assertEquals("Rolls:  | 5 3 | 2 1 |", cmd.output.removeFirst())
        assertEquals("Points: |   8 |  11 |", cmd.output.removeFirst())
        assertEquals("        +-----+-----+", cmd.output.removeFirst())
        main = Bowling(cmd, 10)
        main.printScore(Bowling.framesFromRolls(List(20) { 1 }, 10))
        assertEquals("        +--1--+--2--+--3--+--4--+--5--+--6--+--7--+--8--+--9--+-10--+", cmd.output.removeFirst())
    }

    @Test
    fun testPrintSpare() {
        val cmd = MockCmdInteraction()
        var main = Bowling(cmd, 2)
        main.printScore(Bowling.framesFromRolls(listOf(5, 5, 2), 2))
        assertEquals("        +--1--+--2--+", cmd.output.removeFirst())
        assertEquals("Rolls:  | 5 / | 2 _ |", cmd.output.removeFirst())
        assertEquals("Points: |  12 |   _ |", cmd.output.removeFirst())
        assertEquals("        +-----+-----+", cmd.output.removeFirst())
        main = Bowling(cmd, 2)
        main.printScore(Bowling.framesFromRolls(listOf(5, 5), 2))
        assertEquals("        +--1--+--2--+", cmd.output.removeFirst())
        assertEquals("Rolls:  | 5 / |     |", cmd.output.removeFirst())
        assertEquals("Points: |   _ |   _ |", cmd.output.removeFirst())
        assertEquals("        +-----+-----+", cmd.output.removeFirst())
        main = Bowling(cmd, 1)
        main.printScore(Bowling.framesFromRolls(listOf(5, 5, 2), 1))
        assertEquals("        +------1--+", cmd.output.removeFirst())
        assertEquals("Rolls:  | 5 / 2 _ |", cmd.output.removeFirst())
        assertEquals("Points: |      12 |", cmd.output.removeFirst())
        assertEquals("        +---------+", cmd.output.removeFirst())
    }

    @Test
    fun testPrintStrike() {
        val cmd = MockCmdInteraction()
        var main = Bowling(cmd, 2)
        main.printScore(Bowling.framesFromRolls(listOf(10, 2, 2), 2))
        assertEquals("        +--1--+--2--+", cmd.output.removeFirst())
        assertEquals("Rolls:  | X _ | 2 2 |", cmd.output.removeFirst())
        assertEquals("Points: |  14 |  18 |", cmd.output.removeFirst())
        assertEquals("        +-----+-----+", cmd.output.removeFirst())
        main = Bowling(cmd, 1)
        main.printScore(Bowling.framesFromRolls(listOf(10, 2, 2), 1))
        assertEquals("        +------1--+", cmd.output.removeFirst())
        assertEquals("Rolls:  | X _ 2 2 |", cmd.output.removeFirst())
        assertEquals("Points: |      14 |", cmd.output.removeFirst())
        assertEquals("        +---------+", cmd.output.removeFirst())
    }

    @Test
    fun testPrintEmptyFrames() {
        val cmd = MockCmdInteraction()
        val main = Bowling(cmd, 2)
        main.printScore(Bowling.framesFromRolls(listOf(1), 2))
        assertEquals("        +--1--+--2--+", cmd.output.removeFirst())
        assertEquals("Rolls:  | 1 _ |     |", cmd.output.removeFirst())
        assertEquals("Points: |   _ |   _ |", cmd.output.removeFirst())
        assertEquals("        +-----+-----+", cmd.output.removeFirst())
    }
}