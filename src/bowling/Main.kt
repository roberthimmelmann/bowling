package bowling

class Main(val cmd: CmdInteraction, val maxFrames: Int = 10) {
    val rolls = mutableListOf<Int>()

    fun run() {
        while (true) {
            val bowling = Bowling.fromRolls(rolls, maxFrames)
            if (bowling.isGameFinished()) {
                cmd.println("Game finished!")
                cmd.println("Final score: " + bowling.getTotalPoints())
                break
            }
            cmd.println("Next roll > ")
            rolls.add(cmd.readInt())
        }
    }
}

fun main(args: Array<String>) {

}