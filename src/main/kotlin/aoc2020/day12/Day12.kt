package aoc2020.day12

import kotlin.math.abs

fun main() {
    val input = {}.javaClass.getResource("/day12-2020-1.input").readText().trim()
    // 15:37 plus 5 minutes plus another 10 minutes to fix the bugs
    println("Day 12 part 1: ${Navigate().calcEnd(input)}")
    // 20:46 minuten
    println("Day 12 part 2: ${NavigateWaypoint().calcEnd(input)}")
}

class NavigateWaypoint() {
    var xpos = 0
    var ypos = 0
    // waypoint pos
    var wpxpos = 10
    var wpypos = 1

    fun calcEnd(input: String): Int {
        for (i in input.lines()) {
            when (i[0]) {
                'N' -> performStepWp('N', i.drop(1).toInt())
                'S' -> performStepWp('S', i.drop(1).toInt())
                'E' -> performStepWp('E', i.drop(1).toInt())
                'W' -> performStepWp('W', i.drop(1).toInt())
                'L' -> for (i in 0 until (i.drop(1).toInt() / 90)) {
                            val newxpos = -wpypos
                            val newypos = wpxpos
                            wpxpos = newxpos
                            wpypos = newypos
                        }

                'R' -> for (i in 0 until (i.drop(1).toInt() / 90)) {
                            val newxpos = wpypos
                            val newypos = -wpxpos
                            wpxpos = newxpos
                            wpypos = newypos
                        }
                'F' -> {
                    xpos += (i.drop(1).toInt() * wpxpos)
                    ypos += (i.drop(1).toInt() * wpypos)
                }
            }
            println(i)
            println("wp: $wpxpos,$wpypos ship: $xpos, $ypos")
        }
        println("${abs(xpos)}, ${abs(ypos)}")
        return abs(xpos) + abs(ypos)
    }

    fun performStepWp(dir: Char, amount: Int) {
        when (dir) {
            'N' -> wpypos += amount
            'S' -> wpypos -= amount
            'E' -> wpxpos += amount
            'W' -> wpxpos -= amount
        }
    }
}


class Navigate() {
    var dir = 'E'
    var xpos = 0
    var ypos = 0

    fun calcEnd(input: String): Int {
        val order = listOf('E', 'S', 'W', 'N')
        for (i in input.lines()) {
            when (i[0]) {
                'N' -> performStep('N', i.drop(1).toInt())
                'S' -> performStep('S', i.drop(1).toInt())
                'E' -> performStep('E', i.drop(1).toInt())
                'W' -> performStep('W', i.drop(1).toInt())
                'L' -> dir = order[abs((order.indexOf(dir) + (order.size - (i.drop(1).toInt() / 90))) % 4)]
                'R' -> dir = order[abs((order.indexOf(dir) + (i.drop(1).toInt() / 90)) % 4)]
                'F' -> performStep(dir, i.drop(1).toInt())
            }
            println(i)
            println("$dir $xpos,$ypos")
        }
        return abs(xpos) + abs(ypos)
    }

    fun performStep(dir: Char, amount: Int) {
        when (dir) {
            'N' -> ypos += amount
            'S' -> ypos -= amount
            'E' -> xpos += amount
            'W' -> xpos -= amount
        }
    }
}
