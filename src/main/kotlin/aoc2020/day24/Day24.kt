package aoc2020.day24

import kotlin.math.absoluteValue

fun main() {
//    val input = {}.javaClass.getResource("/day24-2020-test.input").readText().trim().lines()
    val input = {}.javaClass.getResource("/day24-2020-1.input").readText().trim().lines()

//    println(parseLine("esew", Pair(0, 0)))
//    println(parseLine("nwwswee", Pair(0, 0)))
    val points = input.map { parseLine(it, Pair(0, 0))}
    println(points)
    val grouped = points.groupBy { it }.map{ (k, v) -> k to v.size}.filter { (point, count) -> (count % 2 == 1)}
    println("Day 24 part 1: ${grouped.size}")
    val curBlack = grouped.toMap().keys.toList()
    println(curBlack)

    var blackTilesPattern = curBlack.toMutableList()
    for (c in 1 .. 100) {
        blackTilesPattern = calculateNewTiles(blackTilesPattern).toMutableList()
//        println(blackTilesPattern)
        println("Day $c: ${blackTilesPattern.count()}")
    }

}


// while hexognal, we just treat it as coordinates
var grid = mutableMapOf<Int, Int>()

fun parseLine(direction: String, coordinate: Pair<Int, Int>): Pair<Int, Int> {
//    println(direction + " " + coordinate)
    return when {
        // pair is r, c (row, column)
        direction.startsWith("e") -> parseLine(direction.drop(1), Pair( coordinate.first, coordinate.second + 1))
        direction.startsWith("se") ->
            if (coordinate.first.absoluteValue % 2 == 0) {
                parseLine(direction.drop(2), Pair(coordinate.first + 1, coordinate.second))
            } else {
                parseLine(direction.drop(2), Pair(coordinate.first + 1, coordinate.second + 1))
            }
        direction.startsWith("sw") ->
            if (coordinate.first.absoluteValue % 2 == 1) {
                parseLine(direction.drop(2), Pair( coordinate.first + 1, coordinate.second))
            } else {
                parseLine(direction.drop(2), Pair( coordinate.first + 1, coordinate.second - 1))
            }
        direction.startsWith("w") -> parseLine(direction.drop(1), Pair( coordinate.first, coordinate.second - 1))
        direction.startsWith("nw") ->
            if (coordinate.first.absoluteValue % 2 == 1) {
                parseLine(direction.drop(2), Pair( coordinate.first - 1, coordinate.second))
            } else {
                parseLine(direction.drop(2), Pair( coordinate.first - 1, coordinate.second - 1))
            }
        direction.startsWith("ne") ->
            if (coordinate.first.absoluteValue % 2 == 0) {
                parseLine(direction.drop(2), Pair(coordinate.first - 1, coordinate.second))
            } else {
                parseLine(direction.drop(2), Pair(coordinate.first - 1, coordinate.second + 1))
            }
        else -> coordinate
    }

}

fun calculateNewTiles(points: List<Pair<Int, Int>>): List<Pair<Int, Int>> {
    val maxR = points.maxOf { (r, c) -> r} + 1
    val minR = points.minOf { (r, c) -> r} - 1
    val maxC = points.maxOf { (r, c) -> c} + 1
    val minC = points.minOf { (r, c) -> c} - 1
//    println("$minR $minC $maxR $maxC")
    var newBlackTiles = points.toMutableList()
    for (r in minR .. maxR) {
        for (c in minC .. maxC) {
            val blackNeighbours = countBlackNeighbours(points, Pair(r, c))
            if (points.contains(Pair(r, c))) { // current black
                if (blackNeighbours == 0 || blackNeighbours > 2) { // becomes white
                    newBlackTiles.remove(Pair(r, c))
                }
            } else { // current white
                if (blackNeighbours == 2) {
                    newBlackTiles.add(Pair(r, c))
                }
            }
        }
    }
    return newBlackTiles
}

fun countBlackNeighbours(points: List<Pair<Int, Int>>, curPoint: Pair<Int, Int>): Int {
    val evenRow = listOf(Pair(-1, -1), Pair(-1, 0), Pair(0, -1), Pair(0, 1), Pair(1, -1), Pair(1, 0))
    val oddRow = listOf(Pair(-1, 0), Pair(-1, 1), Pair(0, -1), Pair(0, 1), Pair(1, 0), Pair(1, 1))

    return if (curPoint.first.absoluteValue % 2 == 0) { // even row
        evenRow.count { (r, c) -> points.contains(Pair(curPoint.first + r, curPoint.second + c))}
    } else { // odd row
        oddRow.count { (r, c) -> points.contains(Pair(curPoint.first + r, curPoint.second + c))}
    }

}
