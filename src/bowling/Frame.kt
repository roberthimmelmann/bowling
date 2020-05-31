package bowling

/**
 * Represents the score of one frame. The frame can be in progress, i.e., not all rolls of the frame have been made. In this case the relevant values are null.
 */
class Frame(val firstRoll: Int, val secondRoll: Int?, val nextRoll: Int?, val secondNextRoll: Int?) {
    init {
        require(firstRoll >= 0 && firstRoll <= 10)
    }

    fun getPoints(): Int {
        var points = firstRoll + (secondRoll ?: 0)
        if (points == 10) {
            points += nextRoll ?: 0
            if (secondRoll == null) {
                points += secondNextRoll ?: 0
            }
        }
        return points
    }

    fun isFinished(): Boolean {
        if (firstRoll < 10 && secondRoll == null)
            return false
        if (firstRoll == 10 && secondNextRoll == null)
            return false
        if (firstRoll + (secondRoll ?: 0) == 10 && nextRoll == null)
            return false
        return true
    }
}