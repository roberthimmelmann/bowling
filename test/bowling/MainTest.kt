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
    fun test1() {
        main = Main(cmd, 1)
        cmd.rolls.add(5)
        cmd.rolls.add(3)
        main.run()
        assertEquals(cmd.output.removeFirst(), "Next roll > ")
        assertEquals(cmd.output.removeFirst(), "Next roll > ")
        assertEquals(cmd.output.removeFirst(), "Game finished!")
        assertEquals(cmd.output.removeFirst(), "Final score: 8")
    }
}