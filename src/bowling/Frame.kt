package bowling

class Frame(val firstRoll: Int, val secondRoll: Int?, val nextRoll: Int, val secondNextRoll: Int) {
    fun getPoints(): Int {
        var points = firstRoll + (secondRoll ?: 0)
        if (points == 10) {
            points += nextRoll
            if (secondRoll == null) {
                points += secondNextRoll
            }
        }
        return points
    }
}