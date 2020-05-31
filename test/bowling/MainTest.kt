package bowling

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

internal class MockCmdInteraction : CmdInteraction() {
    val rolls = LinkedList<Int>()
    val output = LinkedList<String>()

    override fun readInt(): Int {
        return rolls.removeFirst()
    }

    override fun println(arg: String) {
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
        assertEquals("Next roll > ", cmd.output.removeFirst())
        assertEquals("Next roll > ", cmd.output.removeFirst())
        assertEquals("Game finished!", cmd.output.removeFirst())
        assertEquals("Final score: 8", cmd.output.removeFirst())
    }

    @Test
    fun testPrintScore() {
        main = Main(cmd, 1)
        main.printScore(Bowling.fromRolls(listOf(5, 3), 1))
        assertEquals("        +--1--+", cmd.output.removeFirst())
        assertEquals("Rolls:  | 5 3 |", cmd.output.removeFirst())
        assertEquals("Points: |   8 |", cmd.output.removeFirst())
        assertEquals("        +-----+", cmd.output.removeFirst())
    }
}