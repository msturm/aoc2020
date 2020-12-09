package aoc2020.day9

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main() {

    val input = {}.javaClass.getResource("/day9-2020-1.input").readText().trim().lines()
    // took 25:19 min :(
    val findInvalidNumber = Day9().findInvalidNumber(input, 25)
    println("Day 9 part 1: $findInvalidNumber")
    // took 19:42
    println("Day 9 part 2: ${Day9().findContiguousList(findInvalidNumber.toLong(), input)}")

}

class Day9 {
    fun findInvalidNumber(input: List<String>, preamble: Int): Int {
        for (a in preamble until input.size) {
            if (!findSum(a, input, preamble)) return input[a].toInt()
        }
        return -1;
    }

    fun findSum(num: Int, input: List<String>, preamble: Int): Boolean {
        for (i in abs(num-preamble) until num) {
            for (j in abs(num-preamble) until num) {
                if (input[i].toInt()+input[j].toInt() == input[num].toInt()) {
                    return true
                }
            }
        }
        return false
    }

    fun findContiguousList(num: Long, input: List<String>): Long {
        val inputInts = input.map(String::toLong)
        for (i in inputInts.indices) {
            var c = 0L
            var j = i
            while (c < num) {
                j +=1
                c += inputInts[j]
                if (c == num)
                    return inputInts.slice(i until j).minOrNull()!! + inputInts.slice(i until j).maxOrNull()!!
            }
        }
        return -1
    }

}
