package bowling

class Bowling(val frames:List<Frame>) {
    fun getPoints(): Int {
        return 1
    }

    companion object {
        fun fromRolls(rolls:List<Int>): Bowling {
            return Bowling(listOf())
        }
    }
}