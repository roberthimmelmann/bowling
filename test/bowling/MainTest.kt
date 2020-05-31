package bowling

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertFalse
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

internal class MainTest {
    val cmd = MockCmdInteraction()
    var main = Main(cmd)

    @Test
    fun testMain() {
        main = Main(cmd, 1)
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
        main = Main(cmd, 2)
        //Finished games
        assertTrue { main.isGameFinished(Main.framesFromRolls(listOf(1, 1, 1, 1), 2)) }
        assertTrue { main.isGameFinished(Main.framesFromRolls(listOf(10, 1, 1), 2)) }
        //unfinished game
        assertFalse { main.isGameFinished(Main.framesFromRolls(listOf(1, 1, 1), 2)) }
        //after a strike the game doesn't immediately end
        assertFalse { main.isGameFinished(Main.framesFromRolls(listOf(1, 1, 10), 2)) }
        assertFalse { main.isGameFinished(Main.framesFromRolls(listOf(1, 1, 10, 1), 2)) }
        assertTrue { main.isGameFinished(Main.framesFromRolls(listOf(1, 1, 10, 1, 1), 2)) }
        //after a spare the game doesn't immediately end
        assertFalse { main.isGameFinished(Main.framesFromRolls(listOf(1, 1, 5, 5), 2)) }
        assertTrue { main.isGameFinished(Main.framesFromRolls(listOf(1, 1, 5, 5, 1), 2)) }
    }

    @Test
    fun testPrintSingleFrame() {
        main = Main(cmd, 1)
        main.printScore(Main.framesFromRolls(listOf(5, 3), 1))
        assertEquals("        +--1--+", cmd.output.removeFirst())
        assertEquals("Rolls:  | 5 3 |", cmd.output.removeFirst())
        assertEquals("Points: |   8 |", cmd.output.removeFirst())
        assertEquals("        +-----+", cmd.output.removeFirst())
    }

    @Test
    fun testPrintMultipleFrames() {
        main = Main(cmd, 2)
        main.printScore(Main.framesFromRolls(listOf(5, 3, 2, 1), 2))
        assertEquals("        +--1--+--2--+", cmd.output.removeFirst())
        assertEquals("Rolls:  | 5 3 | 2 1 |", cmd.output.removeFirst())
        assertEquals("Points: |   8 |  11 |", cmd.output.removeFirst())
        assertEquals("        +-----+-----+", cmd.output.removeFirst())
        main = Main(cmd, 10)
        main.printScore(Main.framesFromRolls(List(20) { 1 }, 10))
        assertEquals("        +--1--+--2--+--3--+--4--+--5--+--6--+--7--+--8--+--9--+-10--+", cmd.output.removeFirst())
    }

    @Test
    fun testPrintSpare() {
        main = Main(cmd, 2)
        main.printScore(Main.framesFromRolls(listOf(5, 5, 2), 2))
        assertEquals("        +--1--+--2--+", cmd.output.removeFirst())
        assertEquals("Rolls:  | 5 / | 2 _ |", cmd.output.removeFirst())
        assertEquals("Points: |  12 |   _ |", cmd.output.removeFirst())
        assertEquals("        +-----+-----+", cmd.output.removeFirst())
        main = Main(cmd, 2)
        main.printScore(Main.framesFromRolls(listOf(5, 5), 2))
        assertEquals("        +--1--+--2--+", cmd.output.removeFirst())
        assertEquals("Rolls:  | 5 / |     |", cmd.output.removeFirst())
        assertEquals("Points: |   _ |   _ |", cmd.output.removeFirst())
        assertEquals("        +-----+-----+", cmd.output.removeFirst())
        main = Main(cmd, 1)
        main.printScore(Main.framesFromRolls(listOf(5, 5, 2), 1))
        assertEquals("        +------1--+", cmd.output.removeFirst())
        assertEquals("Rolls:  | 5 / 2 _ |", cmd.output.removeFirst())
        assertEquals("Points: |      12 |", cmd.output.removeFirst())
        assertEquals("        +---------+", cmd.output.removeFirst())
    }

    @Test
    fun testPrintStrike() {
        main = Main(cmd, 2)
        main.printScore(Main.framesFromRolls(listOf(10, 2, 2), 2))
        assertEquals("        +--1--+--2--+", cmd.output.removeFirst())
        assertEquals("Rolls:  | X _ | 2 2 |", cmd.output.removeFirst())
        assertEquals("Points: |  14 |  18 |", cmd.output.removeFirst())
        assertEquals("        +-----+-----+", cmd.output.removeFirst())
        main = Main(cmd, 1)
        main.printScore(Main.framesFromRolls(listOf(10, 2, 2), 1))
        assertEquals("        +------1--+", cmd.output.removeFirst())
        assertEquals("Rolls:  | X _ 2 2 |", cmd.output.removeFirst())
        assertEquals("Points: |      14 |", cmd.output.removeFirst())
        assertEquals("        +---------+", cmd.output.removeFirst())
    }

    @Test
    fun testPrintEmptyFrames() {
        main = Main(cmd, 2)
        main.printScore(Main.framesFromRolls(listOf(1), 2))
        assertEquals("        +--1--+--2--+", cmd.output.removeFirst())
        assertEquals("Rolls:  | 1 _ |     |", cmd.output.removeFirst())
        assertEquals("Points: |   _ |   _ |", cmd.output.removeFirst())
        assertEquals("        +-----+-----+", cmd.output.removeFirst())
    }
}