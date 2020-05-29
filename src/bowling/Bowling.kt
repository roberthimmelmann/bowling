package bowling

class Bowling(val frames: List<Frame>) {
    fun getTotalPoints() = frames.map(Frame::points).sum()

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
                var points = firstRoll + (secondRoll ?: 0)
                if (points == 10) {
                    points += rolls.getOrElse(i) { 0 }
                    if (firstRoll == 10)
                        points += rolls.getOrElse(i + 1) { 0 }
                }
                frames.add(Frame(firstRoll, secondRoll, points))
            }
            return Bowling(frames)
        }
    }
}