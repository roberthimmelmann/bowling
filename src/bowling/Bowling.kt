package bowling

class Bowling(val frames: List<Frame>, val maxFrames: Int) {
    companion object {
        fun fromRolls(rolls: List<Int>, maxFrames: Int = 10): List<Frame> {
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
    }
}