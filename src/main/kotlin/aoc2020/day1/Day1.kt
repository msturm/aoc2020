package aoc2020.day1

fun main() {
    day1part1()
    day1part2()
}

fun day1part1() {
    val input = {}.javaClass.getResource("/day1-2020.input").readText().lines().filter{el -> el.isNotEmpty() }.map { line -> line.toInt() }.toList()
    val res = input.flatMap { lhsElem -> input.map { rhsElem -> rhsElem to lhsElem } }.filter { (a, b) -> a + b == 2020 }.map { (a, b) -> (a * b) }.first()

    println("solution part 1: $res")
}

fun day1part2() {
    val input = {}.javaClass.getResource("/day1-2020.input").readText().lines().filter{el -> el.isNotEmpty() }.map { line -> line.toInt() }.toList()
    val res = input.flatMap {
        lhsElem -> input.flatMap {
            rhsElem -> input.filter { mElem -> (rhsElem + mElem + lhsElem == 2020) }
                .map { mElem -> rhsElem * mElem * lhsElem }
        }
    }.first()
    println("solution part 2: $res")
}
