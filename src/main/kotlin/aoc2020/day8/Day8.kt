package aoc2020.day8

import java.lang.RuntimeException

fun main() {

    val input = {}.javaClass.getResource("/day8-2020-1.input").readText().trim().lines()
    // 16:06 min inclusief afleiding
    try {
        Executor().parseInput(input)
    } catch (e: RuntimeException) {
        println("Day 8 part 1: " + e.message)
    }
    // 30:47 min inclusief afleiding
    println("Day 8 part 2: " + Executor().tryProgramModifications(input))
}

class Executor {

    fun tryProgramModifications(input: List<String>): Int {
        var lastChangedLine = 0
        var currentProgram = input
        var catchedException = true
        while(catchedException) {
            try {
                println(lastChangedLine)
                println(currentProgram)
                return parseInput(currentProgram)
            } catch (e: RuntimeException) {
                catchedException = true
                val changedData = changeLine(input, lastChangedLine+1)
                currentProgram = changedData.first
                lastChangedLine = changedData.second
            }
        }
        return 0
    }

    fun changeLine(program: List<String>, lastChangedLine: Int): Pair<List<String>, Int> {
        val newProgram = program.toMutableList()
        for (i in lastChangedLine .. program.size) {
            if (program[i].contains("jmp")) {
                newProgram[i] = newProgram[i].replace("jmp", "nop")
                return newProgram to i
            } else if (program[i].contains("nop")) {
                newProgram[i] = newProgram[i].replace("nop", "jmp")
                return newProgram to i
            }
        }
        throw RuntimeException("No line to change")
    }



    fun parseInput(input: List<String>): Int {
        var lineCounter = IntArray(input.size)
        var acc = 0
        var pc = 0

        while (pc < lineCounter.size) {
            if (lineCounter[pc] > 0) throw RuntimeException("Double execution (Day 8 part 1: cur acc $acc)")
            lineCounter[pc] += 1
            val instrSet = input[pc].split(" ")
            val instr = instrSet[0]
            val param = instrSet[1].toInt()
            when (instr) {
                "nop" -> pc += 1
                "acc" -> {acc += param; pc += 1}
                "jmp" -> {pc += param}
            }
        }
        return acc;
    }
}
