package bowling

class Frame(val firstThrow: Int, val secondThrow: Int?, val nextRoll: Int, val secondNextRoll: Int) {
    fun getPoints(): Int {
        var points = firstThrow + (secondThrow ?: 0)
        if (points == 10) {
            points += nextRoll
            if (secondThrow == null) {
                points += secondNextRoll
            }
        }
        return points
    }
}