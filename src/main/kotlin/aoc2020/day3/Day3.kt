package aoc2020.day3

fun main() {
    val input = {}.javaClass.getResource("/day3-2020-1.input").readText().lines().filter { el -> el.isNotEmpty() }
    val slopes = arrayListOf<Pair<Int, Int>>(Pair(1, 1), Pair(3, 1), Pair(5, 1), Pair(7, 1), Pair(1, 2))
    println("Day 3 part1: ${TobogganMap(input).slopeTreeCount(3, 1)}")
    println("Day 3 part2: ${TobogganMap(input).multiplySlopes(slopes)}")

}

enum class MapElement {
    TREE, EMPTY, OUT_OF_BOUNDARY, UNDEFINED
}

class TobogganMap(private val mapData: List<String>) {
    private val mapWidth = mapData[0].length
    fun getCoordinate(x: Int, y: Int): MapElement {
        if (mapData.size <= y) {
            return MapElement.OUT_OF_BOUNDARY
        }
        return when (mapData[y][x % mapWidth]) {
            '.' -> MapElement.EMPTY
            '#' -> MapElement.TREE
            else -> MapElement.UNDEFINED
        }
    }

    private tailrec fun takeStep(treeCount: Int, curX: Int, curY: Int, stepX: Int, stepY: Int): Int {
        return when (getCoordinate(curX, curY)) {
            MapElement.EMPTY -> takeStep(treeCount, curX + stepX, curY + stepY, stepX, stepY)
            MapElement.TREE -> takeStep(treeCount + 1, curX + stepX, curY + stepY, stepX, stepY)
            MapElement.OUT_OF_BOUNDARY -> treeCount
            MapElement.UNDEFINED -> treeCount
        }
    }

    fun slopeTreeCount(stepX: Int, stepY: Int): Int {
        return takeStep(0, 0, 0, stepX, stepY)
    }

    fun multiplySlopes(slopesList: ArrayList<Pair<Int, Int>>): Long {
        return slopesList.fold(1) { ag, (x, y) -> ag * slopeTreeCount(x, y) }
    }
 }
