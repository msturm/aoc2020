package aoc2020.day10

fun main() {
    val input = {}.javaClass.getResource("/day10-2020-1.input").readText().trim().lines()
    val answer1 = Adapters().findAdapterCombination(input.map(String::toInt).toIntArray() + 0)
    // 18:36 min
    println("Day 10 part 1: $answer1")
    // println very long
    println("\nDay 10 part 2: " +  Adapters().countAllOptionsUntilEnd(input.map(String::toInt).toIntArray()))
}

class Adapters {
    fun findAdapterCombination(input: IntArray): Int {
        val diff1cnt = input.sorted().zipWithNext().fold(0) {acc, (adpt1, adpt2) -> if (adpt2 - adpt1 == 1) acc + 1 else acc}
        val diff3cnt = input.sorted().zipWithNext().fold(0) {acc, (adpt1, adpt2) -> if (adpt2 - adpt1 == 3) acc + 1 else acc}
        return diff1cnt * (diff3cnt + 1)
    }

    fun countAllOptionsUntilEnd(inp: IntArray): Long {
        val sortedInp = intArrayOf(0) + inp.sorted().toIntArray() + (inp.maxOrNull()!! + 3)
        val countTracker = LongArray(sortedInp.size)
        for (i in sortedInp.size-1 downTo 0) {
            when (countOptions(sortedInp, i)) {
                1 -> countTracker[i] = countTracker[i + 1]
                2 -> countTracker[i] = countTracker[i + 1] + countTracker[i+2]
                3 -> countTracker[i] = countTracker[i + 1] + countTracker[i+2] + countTracker[i+3]
                else -> countTracker[i] = 1
            }
        }
        for (i in sortedInp.indices) print("${sortedInp[i]}, ")
        println("\n")
        for (i in countTracker.indices) print("${countTracker[i]}, ")
        return countTracker[0];
    }

    fun countOptions(inp: IntArray, i: Int = 0): Int {
        var count = 0
        var curPos = i + 1
        while (curPos < inp.size && inp[curPos] - inp[i] <= 3) {
            curPos += 1
            count += 1
        }
        return count
    }


}
