package bowling

open class CmdInteraction {
    open fun readInt(): Int {
        val s = readLine()
        val result = if (s == "X") 10 else s!!.toIntOrNull()
        if (result == null) {
            print("Not a valid number, try again > ")
            return readInt()
        }
        return result
    }

    open fun println(arg: String) {
        System.out.println(arg)
    }

    open fun print(arg: String) {
        System.out.print(arg)
    }
}