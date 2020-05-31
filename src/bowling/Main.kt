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
            rollsLine.append(" %s %s ".format(frame.formatFirstRoll(), frame.formatSecondRoll()))

            if (frame.isSpareOrStrike() && i == bowling.frames.size - 1) {
                val additionalRolls =
                    "%s %s ".format(frame.nextRoll?.toString() ?: "_", frame.secondNextRoll?.toString() ?: "_")
                firstLine.append("-".repeat(additionalRolls.length))
                lastLine.append("-".repeat(additionalRolls.length))
                pointsLine.append(" ".repeat(additionalRolls.length))
                rollsLine.append(additionalRolls)
            }

            pointsLine.append(" %3s ".format(if (frame.isFinished()) "%d".format(frame.getPoints()) else "_"))
            rollsLine.append("|")
            pointsLine.append("|")
            firstLine.append("-%2d--+".format(i + 1).replace(" ", "-"))
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