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
        cmd.rolls.add(5)
        main.run()
        assertEquals(cmd.output.removeFirst(), "First roll:")
    }
}