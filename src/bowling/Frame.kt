package bowling

/**
 * Represents the score of one frame. The frame can be in progress, i.e., not all rolls of the frame have been made.
 * In this case the relevant values are null.
 *
 * nextRoll and secondNextRoll refer to bonus rolls from the next frame
 */
class Frame(val firstRoll: Int, val secondRoll: Int?, val nextRoll: Int?, val secondNextRoll: Int?) {
    init {
        require(firstRoll in 0..10)
        require(secondRoll ?: 0 in 0..10)
        require(nextRoll ?: 0 in 0..10)
        require(secondNextRoll ?: 0 in 0..10)
        require((firstRoll + (secondRoll ?: 0)) in 0..10)
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

    /**
     * A frame is finished when all rolls needed to calculate the score have been made. In the case of a spare or
     * strike this included rolls from the next frame.
     */
    fun isFinished(): Boolean {
        if (firstRoll < 10 && secondRoll == null)
            return false
        if (firstRoll == 10 && secondNextRoll == null)
            return false
        if (firstRoll + (secondRoll ?: 0) == 10 && nextRoll == null)
            return false
        return true
    }

    fun formatFirstRoll(): String = if (firstRoll == 10) "X" else firstRoll.toString()
    fun formatSecondRoll(): String = if (firstRoll != 10 && isSpareOrStrike()) "/" else (secondRoll?.toString() ?: "_")
    fun isSpareOrStrike() = firstRoll + (secondRoll ?: 0) == 10
}