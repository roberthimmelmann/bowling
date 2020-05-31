package bowling

class Bowling(val cmd: CmdInteraction, val maxFrames: Int = 10) {
    val rolls = mutableListOf<Int>()

    companion object {
        fun framesFromRolls(rolls: List<Int>, maxFrames: Int = 10): List<Frame> {
            val frames = mutableListOf<Frame>()
            var i = 0
            while (i < rolls.size && frames.size < maxFrames) {
                val firstRoll = rolls[i]
                var secondRoll: Int? = null
                if (firstRoll < 10) {
                    secondRoll = rolls.getOrNull(i + 1)
                    i++
                }
                i++
                frames.add(Frame(firstRoll, secondRoll, rolls.getOrNull(i), rolls.getOrNull(i + 1)))
            }
            return frames
        }

        fun getScore(frames: List<Frame>) = frames.map(Frame::getPoints).sum()
    }

    fun isGameFinished(frames: List<Frame>) =
        if (frames.size < maxFrames) false else frames.last().isFinished()

    fun run() {
        while (true) {
            val bowling = framesFromRolls(rolls, maxFrames)
            printScore(bowling)
            if (isGameFinished(bowling)) {
                cmd.println("Game finished!")
                cmd.println("Final score: " + getScore(bowling))
                break
            }
            cmd.print("Next roll > ")
            rolls.add(cmd.readInt())
        }
    }

    fun printScore(bowling: List<Frame>) {
        val firstLine = StringBuffer("        +")
        val rollsLine = StringBuffer("Rolls:  |")
        val pointsLine = StringBuffer("Points: |")
        val lastLine = StringBuffer("        +")
        var score = 0

        for (i in bowling.indices) {
            val frame = bowling.get(i)
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

        for (i in bowling.size..maxFrames - 1) {
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
    Bowling(CmdInteraction(), 10).run()
}