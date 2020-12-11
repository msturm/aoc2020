package aoc2020.day11

fun main() {
    val input = {}.javaClass.getResource("/day11-2020-1.input").readText().trim()

    println("Day 11 part 1: ${SeatLife().findStableSituationSeatCount(input, SeatLife()::makeStepPart1)}")
    println("Day 11 part 2: ${SeatLife().findStableSituationSeatCount(input, SeatLife()::makeStepPart2)}")
}

class SeatLife() {
    fun findStableSituationSeatCount(stringInput: String, stepFunction: (String) -> String): Int {
        var oS: String
        var nS = stringInput
        do {
            oS = nS
            nS = stepFunction(oS)
        } while(nS != oS)
        return nS.count { it == '#' }
    }

    fun makeStepPart2(stringInput: String): String {
        val input = stringInput.split("\n").map(String::toCharArray)
        val result = stringInput.split("\n").map(String::toCharArray)
        for (i in input.indices) {
            for (j in input[i].indices) {
                when (input[i][j]) {
                    '.' -> result[i][j] = input[i][j]
                    'L' -> if (checkSeatPart2(input, '#', i, j) == 0) result[i][j] = '#'
                    '#' -> if (checkSeatPart2(input, '#', i, j) > 4) result[i][j] = 'L'
                }
            }
        }
        return result.map {it.joinToString("")}.joinToString("\n")
    }

    fun checkSeatPart2(input: List<CharArray>, seatType: Char, row: Int, column: Int): Int {

        val directions = listOf(-1 to -1, -1 to 0, -1 to 1, 0 to -1, 0 to 1, 1 to -1, 1 to 0, 1 to 1)
        val seats = directions.map{ findSeat(input, column to row, it) }

        return directions.fold(0){ acc, dir -> if (findSeat(input, column to row, dir) == seatType) acc + 1 else acc}
    }

    fun findSeat(input: List<CharArray>, start: Pair<Int, Int>, step: Pair<Int, Int>): Char {
        var curPos = start
        while (curPos.first + step.first < input[0].size &&
                curPos.first + step.first >= 0 &&
                curPos.second + step.second < input.size &&
                curPos.second + step.second >= 0) {
            curPos = curPos.first + step.first to curPos.second + step.second;
            if (input[curPos.second][curPos.first] != '.') {
                return input[curPos.second][curPos.first]
            }
        }
        return if (curPos == start) {
            '.'
        } else {
            input[curPos.second][curPos.first]
        }
    }

    fun makeStepPart1(stringInput: String): String {
        val input = stringInput.split("\n").map(String::toCharArray)
        val result = stringInput.split("\n").map(String::toCharArray)
        for (i in input.indices) {
            for (j in input[i].indices) {
                when (input[i][j]) {
                    '.' -> result[i][j] = '.'
                    'L' -> if (checkSeatPart1(input, '#', i, j) == 0) result[i][j] = '#'
                    '#' -> if (checkSeatPart1(input, '#', i, j) > 3) result[i][j] = 'L'
                }
            }
        }
        return result.joinToString("\n") { it.joinToString("") }
    }

    fun checkSeatPart1(input: List<CharArray>, seatType: Char, row: Int, column: Int): Int {
        var count = 0
        val rowsToCheck = when (row) {
            0 -> 0..1
            input.size - 1 -> row-1 .. row
            else -> row-1 .. row+1
        }

        val columnsToCheck = when (column) {
            0 -> 0 .. 1
            input[row].size - 1 -> column-1 .. column
            else -> column-1 .. column+1
        }

        for (i in rowsToCheck) {
            for (j in columnsToCheck) {
                if (!(i == row && j == column)) { // ignore current coordinate
                    if (input[i][j] == seatType) count += 1
                }
            }
        }
        return count
    }
}
