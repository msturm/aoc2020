package aoc2020.day20

import java.lang.RuntimeException
import kotlin.math.sqrt

fun main() {
//    val input = {}.javaClass.getResource("/day20-2020-1.input").readText().trim().lines()
    val input = {}.javaClass.getResource("/day20-2020-1.input").readText().trim().lines()
    val tiles = Tile.parseAll(input)
    println(tiles)
    println(tiles.size)
    val ans1 = Tile.findCornerTiles()
    println("Day 20 part 1: $ans1")
    val ans2 = Tile.createGrid()
    println("Day 20 part 2: $ans2")
}


const val TOP = 0
const val RIGHT = 1
const val BOTTOM = 2
const val LEFT = 3

class Tile(val input: List<String>, val id: Int) {

    val borders: List<String> = {
        val topBorder = input.first()
        val rightBorder = input.fold("") {acc, el -> acc + el.last()}
        val bottomBorder = input.last()
        val leftBorder = input.fold("") {acc, el -> acc + el.first()}
        listOf(topBorder, rightBorder, bottomBorder, leftBorder)
    }.invoke()

    var borderMatch = mutableMapOf<Int, Int>()

    fun getNeighbourAtBorder(border: Int): Int? {
        return borderMatch.entries.associateBy({ it.value }) { it.key }.get(border)
    }

    fun flipTileVertical(): Tile {
        println("Flip ${this.id} vertical")
        val newTile = Tile(input.reversed(), id)
        replaceTileWithId(this.id, newTile)
        newTile.matchNeighbours(tiles)
        return newTile
    }

    fun flipTileHorizontal(): Tile {
        println("Flip ${this.id} horizontal")
        val newTile = Tile(input.map { it.reversed() }, id)
        replaceTileWithId(this.id, newTile)
        newTile.matchNeighbours(tiles)
        return newTile
    }

    fun rotateTile(): Tile {
        println("Rotate ${this.id} clockwise")
        var newInput = mutableListOf<String>()
        for (i in input.indices) {
            newInput.add(input.map { it[i]}.reversed().joinToString(""))
        }

        val newTile = Tile(newInput, id)
        replaceTileWithId(this.id, newTile)
        newTile.matchNeighbours(tiles)
        return newTile
    }

    fun matchNeighbours(tiles: List<Tile>): MutableList<Int> {
        var neighbours = mutableListOf<Int>()
        for (t2 in tiles) {
            if (this.id != t2.id) {
                if (matchBorder(t2)) {
                    neighbours.add(t2.id)
                }
            }
        }

        return neighbours
    }

    fun flipIfNeeded(tiles: List<Tile>): Tile {
        matchNeighbours(tiles)
        // check if we need to flip
        var newTile = this
        if (!(borderMatch.values.contains(0) || borderMatch.values.contains(2))) {
            newTile = flipTileHorizontal()
        }
        if (!(borderMatch.values.contains(1) || borderMatch.values.contains(3))) {
            newTile = flipTileVertical()
        }
        return newTile
    }

    fun matchBorder(t: Tile): Boolean {
        val match = t.borders.find {b ->
            if ((this.borders.find {b2 -> b2 == b} != null) ||
                    (this.borders.find {b2 -> b2 == b.reversed()} != null)) {
                println("match ${t.id} and ${this.id}")
                true
            } else {
                false
            }
        }

        if (match != null) {
            val t1Border = t.borders.indexOf(match)
            val thisBorder = if (this.borders.indexOf(match) >= 0) this.borders.indexOf(match) else this.borders.indexOf(match.reversed())
            borderMatch[t.id] = thisBorder
//            println("${t.id} b $t1Border and ${this.id} b $thisBorder")
            return true
        }

        return false
    }

    fun countWaves(): Int {
        return input.fold(0) { acc, l ->
            acc + l.count{ it == '#'}
        }
    }

    companion object TileParser {
        var tiles = mutableListOf<Tile>()
        var neighbours = mutableMapOf<Int, MutableList<Int>>()
        var matchCount = mutableMapOf<Int, Int>()

        val seaMonster = """
                  # 
#    ##    ##    ###
 #  #  #  #  #  #   
            """.trimIndent().lines()
        var seaMonsterPoints = mutableListOf<Pair<Int, Int>>()

        fun replaceTileWithId(id: Int, newTile: Tile) {
            val index = tiles.indexOfFirst { it.id == id }
            if (index > -1) tiles[index] = newTile
        }

        fun parseAll(input: List<String>): List<Tile> {
            val tiles = mutableListOf<Tile>()
            var i = 0
            while (i < input.size) {
                if (input[i].startsWith("Tile")) {
                    val id = input[i].substring(5, input[i].indexOf(":")).toInt()
                    tiles += Tile(input.subList(i + 1, i + 11), id)
                    i += 11
                }
                i += 1
            }
            this.tiles = tiles
            return tiles
        }

        fun findCornerTiles(): Long {
            // first just match without rotating or anything

            for (i in tiles.indices) {
//                tiles[i] = tiles[i].flipIfNeeded(tiles)
                val t1 = tiles[i]
                println(t1.id)
                neighbours[t1.id] = t1.matchNeighbours(tiles)

                var count = neighbours[t1.id]!!.size
                matchCount[t1.id] = count
            }
            val ans = matchCount.filter { (k, v) -> v == 2 }.keys.fold(1L) { acc, el -> acc * el }
            println(matchCount)

            for (t1 in tiles) {
                println("${t1.id}:" + " " + t1.borderMatch)
            }
            return ans
        }

        fun createGrid(): Int {
            val grid = mutableMapOf<Pair<Int,Int>, Tile>()
            var corners = matchCount.filter { (k, v) -> v == 2 }
            var sides = matchCount.filter { (k, v) -> v == 3 }
            val gridsize = sqrt(matchCount.count().toDouble()).toInt()
            var tilesMap = tiles.map { it.id to it}.toMap()
            println("corners: " + corners)
            println("sides: " + sides)
            println("gridsize: " + gridsize)

            grid[0 to 0] = tilesMap.getValue(corners.keys.first())
            // rotate it correctly
            while (grid[0 to 0]!!.getNeighbourAtBorder(0) != null || grid[0 to 0]!!.getNeighbourAtBorder(3) != null) {
                grid[0 to 0] = grid[0 to 0]!!.rotateTile()
            }

            // fill first row
            for (c in 1 until gridsize) {
                val nextId = grid[0 to c-1]!!.getNeighbourAtBorder(RIGHT)!!
                grid[0 to c] = tilesMap.getValue(nextId)
                while (grid[0 to c]!!.getNeighbourAtBorder(LEFT) != grid[0 to (c-1)]!!.id) {
                    grid[0 to c] = grid[0 to c]!!.rotateTile()
                }
                if (grid[0 to c]!!.getNeighbourAtBorder(TOP) != null) {
                    grid[0 to c] = grid[0 to c]!!.flipTileVertical()
                }
                println(grid[0 to c])
            }
            // fill all other rows
            for (r in 1 until gridsize) {
                for (c in 0 until gridsize) {
                    println("filling $r, $c")
                    val nextId = grid[r-1 to c]!!.getNeighbourAtBorder(BOTTOM)!!
                    grid[r to c] = tilesMap.getValue(nextId)

                    var attempts = 0
                    // rotate until top aligns with bottom of the row above
                    while (grid[r to c]!!.getNeighbourAtBorder(TOP) != grid[r-1 to c]!!.id) {
                        grid[r to c] = grid[r to c]!!.rotateTile()
                        attempts += 1
                        if (attempts > 6)
//                            printGrid(grid)
                            throw RuntimeException("does not complete")
                    }


                    if (c == 0 && grid[r to 0]!!.getNeighbourAtBorder(LEFT) != null) {
                        grid[r to 0] = grid[r to 0]!!.flipTileHorizontal()
                    } else if (c > 0 && grid[r to c]!!.getNeighbourAtBorder(LEFT) != grid[r to c - 1]!!.id) {
                        grid[r to c] = grid[r to c]!!.flipTileHorizontal()
                    }

                    println(grid[0 to c])
                }
            }

            println(grid)
            printGrid(grid, false, true)
            val totalGrid = printGrid(grid, true)

            // tile as in example
            var totalTile = Tile(totalGrid, 9999).flipTileVertical().rotateTile()
            totalTile.prettyPrint(-1, false, true)

            // manipulate tile to find Seamonsters

            var seaMonstersLocations = findSeaMonsters(totalTile)
            var modificationCount = 0
            while (seaMonstersLocations.count() == 0) {
                println(seaMonstersLocations.count())
                totalTile = totalTile.rotateTile()
                if (modificationCount == 3) {
                    println("After four rotations")
                    totalTile.prettyPrint(-1, false, true)
                    totalTile = totalTile.flipTileVertical()
                }
                if (modificationCount == 7) {
                    println("After 8 rotations")
                    totalTile.prettyPrint(-1, false, true)
                    totalTile = totalTile.flipTileHorizontal()
                }
                if (modificationCount == 11) {
                    totalTile = totalTile.flipTileVertical()
                }
                seaMonstersLocations = findSeaMonsters(totalTile)
                modificationCount += 1
            }

            // mark seamonsters
            val tileWithSeamonsters = markSeaMonsters(totalTile, seaMonstersLocations.toList())
            return tileWithSeamonsters.countWaves()
        }

        fun printGrid(grid: Map<Pair<Int, Int>, Tile>, cutBorders: Boolean = true, print: Boolean = false): List<String> {
            var output = mutableListOf<String>()
            val gridsize = grid.keys.maxOf { it.second}
            for (r in 0 .. gridsize) {
                for (l in 0 until 10) {
                    var newLine = ""
                    for (c in 0..gridsize) {
                        newLine += grid[r to c]!!.prettyPrint(l, cutBorders, print)
                        if (!cutBorders) print(" ")
                    }
                    output.add(newLine)
                    if (print) print("\n")
                }
                if (!cutBorders) print("\n")
            }
            return output.filterNot(String::isBlank)
        }

        fun markSeaMonsters(tile: Tile, seaMonsterLocations: List<Pair<Int, Int>>): Tile {
            val locationsToChange = seaMonsterLocations.flatMap { (r, c) ->
                seaMonsterPoints.map { (smr, smc) ->
                    Pair(r + smr, c + smc)
                }
            }

            val curTileGrid = tile.input.toMutableList().map {StringBuilder(it)}
            locationsToChange.forEach { (r, c) ->
                curTileGrid[r][c] = 'O'
            }

            val tileWithSeamonsters = Tile(curTileGrid.map(StringBuilder::toString), 9998)
            tileWithSeamonsters.prettyPrint(-1, false, true)
            return tileWithSeamonsters
        }

        fun findSeaMonsters(tile: Tile): MutableList<Pair<Int, Int>> {

            for (r in seaMonster.indices) {
                for (c in seaMonster[r].indices) {
                    if (seaMonster[r][c] == '#') {
                        seaMonsterPoints.add(Pair(r, c))
                    }
                }
            }

            val img = tile.input
            val smLocation = mutableListOf<Pair<Int, Int>>()
            for (r in 0 until (img.size - 3)) {
                for (c in 0 until (img[r].length - 20)) {
                    val foundSeamonster = seaMonsterPoints.fold(true) {
                        acc, p ->
                        if (img[r + p.first][c + p.second] != '#') {
                            false
                        } else {
                            acc
                        }
                    }
                    if (foundSeamonster) {
                        smLocation.add(Pair(r, c))
                    }
                }
            }

            println(smLocation)
            return smLocation
        }

    }

    fun prettyPrint(row: Int = -1, cutBorders: Boolean = true, print: Boolean = false): String {
        if (row == -1) {
            println("Tile $id")
            input.forEach { println(it) }
        } else {
            if (cutBorders) {
//                if (row == 9 && getNeighbourAtBorder(BOTTOM) != null) {
                if (row == 9) {
                    // no printing
//                } else if (row == 0 && getNeighbourAtBorder(TOP) != null) {
                } else if (row == 0) {
//                     no printing
                } else {
                    var line = input[row]
//                    line = if (getNeighbourAtBorder(LEFT) != null) line.drop(1) else line
//                    line = if (getNeighbourAtBorder(RIGHT) != null) line.dropLast(1) else line
                    line = line.drop(1).dropLast(1)
                    if (print) print(line)
                    return line
                }

            } else {
                print(input[row])
                return input[row]
            }
        }
        return ""
    }


    override fun toString(): String {
//        return "Tile(input=$input, id=$id, borders=$borders)"
        return "Tile(id=$id, borderMatch=$borderMatch)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Tile

        if (input != other.input) return false
        if (id != other.id) return false
        if (borders != other.borders) return false

        return true
    }

    override fun hashCode(): Int {
        var result = input.hashCode()
        result = 31 * result + id
        result = 31 * result + borders.hashCode()
        return result
    }
}
