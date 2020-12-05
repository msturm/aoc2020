package aoc2020.day5

import java.lang.Integer.max
import java.lang.RuntimeException

fun main() {
    val input = {}.javaClass.getResource("/day5-2020-1.input").readText().lines()
    val maxSeatID = input.fold(0) { ag, el -> max(ag, SeatCalculator(el).calcSeatID()) }
    println("Day 5 part1: $maxSeatID")
    val seatIDs = input.map { el -> SeatCalculator(el).calcSeatID() }
    println("Day 5 part2: ${findMissingSeat(seatIDs)}")
}

fun findMissingSeat(input: List<Int>): Int {
    return input.sorted().zipWithNext().fold(0) { ag, (s1, s2) -> if (s2 - s1 > 1) s1+1 else ag}
}

class SeatCalculator(val input: String) {

    fun calcRow(): Int {
        return input.take(7).fold(0 to 128) { ag, el ->
            when (el) {
                'F' -> ag.first to (ag.first + ag.second)/2
                'B' -> (ag.first + ag.second)/2 to ag.second
                else -> throw RuntimeException("invalid input")
            }
        }.first
    }

    fun calcSeat(): Int {
        return input.takeLast(3).fold(0 to 8) { ag, el ->
            when (el) {
                'L' -> ag.first to (ag.first + ag.second)/2
                'R' -> (ag.first + ag.second)/2 to ag.second
                else -> throw RuntimeException("invalid input")
            }
        }.first
    }

    fun calcSeatID(): Int {
        return (8 * calcRow()) + calcSeat()
    }
}
