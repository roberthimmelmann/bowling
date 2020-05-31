package bowling

open class CmdInteraction {
    open fun readInt(): Int {
        val s = readLine()
        val result = if (s == "X") 10 else s!!.toIntOrNull()
        if (result == null) {
            print("Not a valid number, try again > ")
            return readInt()
        }
        if (result !in 0..10) {
            print("Please input a number between 0 and 10 > ")
            return readInt()
        }
        // TODO Catch cases where the second roll or a frame exceeds the number of standing pins.
        // That results in an IllegalArgumentExcception right now.
        return result
    }

    open fun println(arg: String) {
        System.out.println(arg)
    }

    open fun print(arg: String) {
        System.out.print(arg)
    }
}