package aoc2020.day15

fun main() {
    // part 1 33:52 min
    val input = "0,13,1,16,6,17".split(",").map(String::toInt)
//    val input = "0,3,6".split(",").map(String::toInt)


    println("Day 15 part1: ${playGame(input)}")
    println("Day 15 part2: ${playGame(input, true)}")
}

fun playGame(input: List<Int>, part2: Boolean = false): Int {
    //    var turns = mutableListOf<Int>()
    var lastTurnVal = 0
    var numbers = mutableMapOf<Int, List<Int>>()
    var turn = 0
    val maxTurns = if (part2) 30000000 else 2020

    while(turn < maxTurns) {
        if (turn < input.size) {
            lastTurnVal = input[turn]
//            turns.add(input[turn])
            numbers[input[turn]] = listOf(turn)
        } else {
            val lastNumber = lastTurnVal
            val turnsUsed = numbers[lastNumber]?.takeLast(2)
            val newNumber = (turnsUsed?.getOrNull(1)?.minus(turnsUsed.getOrNull(0)!!) ?: 0)
            lastTurnVal = newNumber
            if (numbers[newNumber] == null) numbers[newNumber] = listOf(turn) else {
                numbers[newNumber] = listOf(numbers[newNumber]!!.last(), turn)
            }
        }
//        if (turn % 100000 == 0)
//            println(turn)
        turn += 1
    }
    return lastTurnVal
}
