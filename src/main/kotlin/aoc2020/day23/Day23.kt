package aoc2020.day23

import java.util.*
import kotlin.system.measureTimeMillis
val inputString = "871369452"
//    val inputString = "389125467"

fun main() {

    val input = inputString.toCharArray().toList().map(Character::getNumericValue).toList()
    var result = Pair(input, input.first())
    for (i in 1 .. 100) {
//        result = playRound(result.first, result.second, i)
    }
    val lastOrder = result.first
    val ans1 = (lastOrder.drop(lastOrder.indexOf(1) + 1) + lastOrder.take(lastOrder.indexOf(1))).joinToString("")
    println("Day 23 part 1: $ans1")

    var result2 = Character.getNumericValue(inputString.first())
    // make circle:

    setupPart1()
    for (i in 1 .. 100) {
        result2 = playFastRound(result2, i)
        result = playRound(result.first, result.second, i)
    }
    println("Day 23 part 1: " + printAnswer(1))

    setupPart2()
    result2 = Character.getNumericValue(inputString.first())
    for (i in 1 .. 10000000) {
        result2 = playFastRound(result2, i)
    }

    // order after cup 1:
    println(m1circle.size)
//    printCircle(1, 20)
    printPart2()
}

fun playRound(circle: List<Int>, current: Int, mvcount: Int): Pair<List<Int>, Int> {
    val currentIdx = circle.indexOf(current)
//    println("Move $mvcount\n-----")
//    println(currentIdx)
    val pickup = {
        var newPickup = circle.drop(currentIdx + 1).take(3)
        if (newPickup.size < 3) {
            newPickup = newPickup + circle.take(3- newPickup.size)
        }
        newPickup
    }.invoke()
    val remaining = circle.subtract(pickup).toList()
//    println("Numbers to pick up: $pickup")
//    println("Remaining: $remaining")
    val insertionPoint = remaining.filter { it < (current)}.maxOrNull() ?: (remaining.maxOrNull()!!)
//    println("Current point $current")
    println("$circle current $current")
    println("Destination1 $insertionPoint")
    val indexOfInsertion = remaining.indexOf(insertionPoint) + 1
//    println("Index of insertionpoint: $indexOfInsertion")
    var newCircle = remaining.subList(0, indexOfInsertion) + pickup + remaining.drop(indexOfInsertion)
//    println("New circle: $newCircle")
    val newIdx = if ((newCircle.indexOf(current) + 1) < newCircle.size) newCircle.indexOf(current) + 1 else 0

    // rebalance circle
    val mvIdx = mvcount
    val shift = if ((mvcount - newIdx) < 0) circle.size + (mvcount - newIdx) else mvcount - newIdx
    Collections.rotate(newCircle, shift.toInt())
//    if (shift > 0) {
//        newCircle = newCircle.drop(shift) + newCircle.take(shift)
//    } else if (shift < 0) {
//        newCircle = newCircle.takeLast(shift) + newCircle.dropLast(shift)
//    }
//    println("New circle (after rebalancing): ${newCircle.map {if (it == newCircle[mvIdx % newCircle.size]) "($it)" else "$it"}}")
    return Pair(newCircle, newCircle[mvIdx.toInt() % newCircle.size])
}

//var m1circle = "389125467".asSequence().map(Character::getNumericValue)
//var m1circle = "389125467".toCharArray().toList().map(Character::getNumericValue).toMutableList()
//var m1circle = ("389125467".toCharArray().toList().map(Character::getNumericValue) + (10 .. 1000000).toList()).zipWithNext().toMap().toMutableMap()
var m1circle = mutableMapOf<Int, Int>()


fun setupPart1() {
    highestNr = 9
    val items = (inputString.toCharArray().toList().map(Character::getNumericValue)).zipWithNext()
    m1circle = (items + (items.last().second to items.first().first)).toMap().toMutableMap()
}

fun setupPart2() {
    highestNr = 1000000
    val items = (inputString.toCharArray().toList().map(Character::getNumericValue) + (10 .. highestNr).toList()).zipWithNext()
    m1circle = (items + (items.last().second to items.first().first)).toMap().toMutableMap()
}

fun printPart2() {
    val valueAfter1 = m1circle.getValue(1).toLong()
    val secondValueAfter1 = m1circle.getValue(m1circle.getValue(1)).toLong()
    val ans = valueAfter1 * secondValueAfter1
    println("Day 23 part 2: $valueAfter1  ${secondValueAfter1} result $ans")
}


var highestNr = 1000000

fun printCircle(first: Int, limit: Int = -1) {
    print("cups: $first ")
    var nextVar = m1circle.getValue(first)
    var counter = 0
    do {
        print("$nextVar ")
        nextVar = m1circle.getValue(nextVar)
        counter += 1
    } while (nextVar != first && (limit > 0 && counter < limit) )
}

fun printAnswer(first: Int): String {
    var ans = ""
    var nextVar = m1circle.getValue(first)
    ans += nextVar
    while (nextVar != first) {
        nextVar = m1circle.getValue(nextVar)
        if (nextVar != first) ans += nextVar
    }
    return ans
}

fun playFastRound(current: Int, mvcount: Int): Int {
      if ((mvcount % 1000000) == 0) println("Move $mvcount")
//    printCircle(3, 20)
//    println()
//    println(m1circle)
//    printCircle(current)
//    println("Current $current")
    val pickup = Triple(m1circle.getValue(current), m1circle.getValue(m1circle.getValue(current)), m1circle.getValue(m1circle.getValue(m1circle.getValue(current))))
//    println("pickup: $pickup")

    var insertPoint = current - 1
    while (pickup.toList().contains(insertPoint) || insertPoint < 1) {
        insertPoint -= 1
        if (insertPoint < 1) insertPoint = highestNr
    }

//    println("Destination: $insertPoint")

    m1circle[current] = m1circle.getValue(pickup.third)
    m1circle[pickup.third] = m1circle.getValue(insertPoint)
    m1circle[insertPoint] = pickup.first

//    println("")
    return m1circle.getValue(current)
}

//val findInsertionPointIdx(goal: Int): Int {
//    m1circle
//}
