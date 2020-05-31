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

    fun printScore(bowling: Bowling) {
        val firstLine = StringBuffer("        +")
        val rollsLine = StringBuffer("Rolls:  |")
        val pointsLine = StringBuffer("Points: |")
        val lastLine = StringBuffer("        +")

        for (i in bowling.frames.indices) {
            val frame = bowling.frames.get(i)
            firstLine.append("--%d--+".format(i + 1))
            rollsLine.append(" %d %d |".format(frame.firstRoll, frame.secondRoll))
            pointsLine.append(" %3d |".format(frame.getPoints()))
            lastLine.append("-----+")
        }

        cmd.println(firstLine.toString())
        cmd.println(rollsLine.toString())
        cmd.println(pointsLine.toString())
        cmd.println(lastLine.toString())
    }
}

fun main(args: Array<String>) {

}