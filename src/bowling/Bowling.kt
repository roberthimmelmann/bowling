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
                frames.add(Frame(firstRoll, secondRoll, rolls.getOrElse(i) { 0 }, rolls.getOrElse(i + 1) { 0 }))
            }
            return Bowling(frames)
        }
    }
}