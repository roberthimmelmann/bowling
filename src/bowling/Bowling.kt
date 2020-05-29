package bowling

class Bowling(val frames: List<Frame>) {
    fun getTotalPoints() = frames.map(Frame::getPoints).sum()

    companion object {
        fun fromRolls(rolls: List<Int>): Bowling {
            val frames = mutableListOf<Frame>()
            var i = 0
            while (i < rolls.size) {
                val firstRoll = rolls[i]
                var secondRoll: Int? = null
                if (firstRoll < 10) {
                    secondRoll = rolls.getOrNull(i + 1)
                    i++
                }
                i++
                frames.add(Frame(firstRoll, secondRoll, rolls.getOrNull(i), rolls.getOrNull(i + 1)))
            }
            return Bowling(frames)
        }
    }

    fun isGameFinished(maxFrames: Int): Boolean {
        if (frames.size < maxFrames)
            return false
        val frame = frames.last()
        if (frame.firstRoll < 10 && frame.secondRoll == null)
            return false
        return true
    }
}