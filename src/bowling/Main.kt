package bowling

class Main(val cmd: CmdInteraction, val maxFrames: Int = 10) {
    val rolls = mutableListOf<Int>()

    fun run() {
        while (true) {
            val bowling = Bowling.fromRolls(rolls, maxFrames)
            printScore(bowling)
            if (bowling.isGameFinished()) {
                cmd.println("Game finished!")
                cmd.println("Final score: " + bowling.getTotalPoints())
                break
            }
            cmd.print("Next roll > ")
            rolls.add(cmd.readInt())
        }
    }

    fun printScore(bowling: Bowling) {
        val firstLine = StringBuffer("        +")
        val rollsLine = StringBuffer("Rolls:  |")
        val pointsLine = StringBuffer("Points: |")
        val lastLine = StringBuffer("        +")
        var score = 0

        for (i in bowling.frames.indices) {
            val frame = bowling.frames.get(i)
            rollsLine.append(" %s %s ".format(frame.formatFirstRoll(), frame.formatSecondRoll()))

            if (frame.isSpareOrStrike() && i == maxFrames - 1) {
                val additionalRolls =
                    "%s %s ".format(frame.nextRoll?.toString() ?: "_", frame.secondNextRoll?.toString() ?: "_")
                firstLine.append("-".repeat(additionalRolls.length))
                lastLine.append("-".repeat(additionalRolls.length))
                pointsLine.append(" ".repeat(additionalRolls.length))
                rollsLine.append(additionalRolls)
            }

            if (frame.isFinished()) {
                score += frame.getPoints()
                pointsLine.append(" %3d ".format(score))
            } else {
                pointsLine.append("   _ ")

            }
            rollsLine.append("|")
            pointsLine.append("|")
            firstLine.append("-%2d--+".format(i + 1).replace(" ", "-"))
            lastLine.append("-----+")
        }

        for (i in bowling.frames.size..maxFrames - 1) {
            firstLine.append("-%2d--+".format(i + 1).replace(" ", "-"))
            rollsLine.append("     |")
            pointsLine.append("   _ |")
            lastLine.append("-----+")
        }

        cmd.println(firstLine.toString())
        cmd.println(rollsLine.toString())
        cmd.println(pointsLine.toString())
        cmd.println(lastLine.toString())
    }
}

fun main(args: Array<String>) {
    Main(CmdInteraction(), 10).run()
}