package bowling

open class CmdInteraction {
    open fun readInt(): Int {
        return 0
    }

    open fun println(arg: String) {
        System.out.println(arg)
    }
}