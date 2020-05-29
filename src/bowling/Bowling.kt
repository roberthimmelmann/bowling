package bowling

import java.security.cert.TrustAnchor

class Bowling(val frames:List<Frame>) {
    fun getTotalPoints() = frames.map(Frame::points).sum()

    companion object {
        fun fromRolls(rolls:List<Int>): Bowling {
            val frames = mutableListOf<Frame>()
            var i = 0
            while (i < rolls.size) {
                val firstRoll = rolls[i]
                var secondRoll:Int? = null
                if (firstRoll < 10) {
                    secondRoll = rolls.getOrNull(i + 1)
                    i++
                }
                val points = firstRoll + (secondRoll ?: 0)
                frames.add(Frame(firstRoll, secondRoll, points))
                i++
            }
            return Bowling(frames)
        }
    }
}