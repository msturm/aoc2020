package aoc2020.day17


fun main() {
    val input = {}.javaClass.getResource("/day17-2020-1.input").readText().trim().lines()
    with(Tdg(input)) {
        for (i in 1..6) {
            this.step()
            println("Step $i: ${this.countActive()}")
        }
    }
    with(Fdg(input)) {
        for (i in 1..6) {
            this.step()
            println("Step $i: ${this.countActive()}")
        }
    }
}

class Tdg(val gridString: List<String>) {

    var grid: MutableList<MutableList<StringBuilder>> = mutableListOf(gridString.map { StringBuilder(it) }.toMutableList())
    var step = 0

    fun step(): MutableList<MutableList<StringBuilder>> {
        val newGrid: MutableList<MutableList<StringBuilder>> = mutableListOf(mutableListOf(java.lang.StringBuilder()))
        step += 1
        for (l in -1 .. grid.size) {
            for (r in -1 .. grid[0].size) {
                for (c in -1 .. grid[0][0].length) {
                    if (l >= 0 && l < grid.size &&
                            r >= 0 && r < grid[l].size &&
                            c >= 0 && c < grid[l][r].length
                            && grid[l][r][c] == '#') { // curr element active
                        val activeElem = countElem(l, r, c, '#')
                        if (activeElem == 3 || activeElem == 2) {
                            setPoint(newGrid, '#', step, l, r, c)
                        } else {
                            setPoint(newGrid, '.', step, l, r, c)
                        }
                    } else { // curr element inactive
                        if (countElem(l, r, c, '#') == 3) {
                            setPoint(newGrid, '#', step, l, r, c)
                        } else {
                            setPoint(newGrid, '.', step, l, r, c)
                        }
                    }

                }
            }
        }
        grid = newGrid
        step = 0
        return newGrid
    }

    fun countActive(): Int {
        return grid.fold(0) { acc, el -> acc + el.fold(0) { acc2, el2 -> acc2 + el2.count { it == '#'}} }
    }

    fun setPoint(newGrid: MutableList<MutableList<StringBuilder>>, point: Char, step: Int, l: Int, r: Int, c: Int): MutableList<MutableList<StringBuilder>> {
        while (l+step >= newGrid.size) {
            newGrid.add(mutableListOf(StringBuilder()))
        }
        while (r+step >= newGrid[l+step].size) {
            newGrid[l+step].add(StringBuilder())
        }
        while (c+step >= newGrid[l+step][r+step].length) {
            newGrid[l+step][r+step].append(point)
        }
        newGrid[l+step][r+step][c+step] = point
        return newGrid
    }


    fun countElem(l: Int, r: Int, c: Int, s: Char): Int {
        var count = 0
        for (i in -1 .. 1) {
            for (j in -1 .. 1) {
                for (k in -1 .. 1) {
                    val ls = l + i
                    val rs = r + j
                    val cs = c + k
                    if (ls >= 0 && ls < grid.size &&
                            rs >= 0 && rs < grid[ls].size &&
                            cs >= 0 && cs < grid[ls][rs].length) {
                        if (grid[ls][rs][cs] == s && !(ls == l && rs == r && cs == c)) {
                            count += 1
                        }
                    }
                }
            }
        }
        return count
    }

}

class Fdg(val gridString: List<String>) {

    var grid: MutableList<MutableList<MutableList<StringBuilder>>> = mutableListOf(mutableListOf(gridString.map { StringBuilder(it) }.toMutableList()))
    var step = 0

    fun step(): MutableList<MutableList<MutableList<StringBuilder>>> {
        val newGrid: MutableList<MutableList<MutableList<StringBuilder>>> = mutableListOf(mutableListOf(mutableListOf(java.lang.StringBuilder())))
        step += 1
        for (w in -1 .. grid.size) {
            for (l in -1 .. grid[0].size) {
                for (r in -1 .. grid[0][0].size) {
                    for (c in -1 .. grid[0][0][0].length) {
                        if (w >=0 && w < grid.size &&
                                l >= 0 && l < grid[w].size &&
                                r >= 0 && r < grid[w][l].size &&
                                c >= 0 && c < grid[w][l][r].length
                                && grid[w][l][r][c] == '#') { // curr element active
                            val activeElem = countElem(w, l, r, c, '#')
                            if (activeElem == 3 || activeElem == 2) {
                                setPoint(newGrid, '#', step, w, l, r, c)
                            } else {
                                setPoint(newGrid, '.', step, w, l, r, c)
                            }
                        } else { // curr element inactive
                            if (countElem(w, l, r, c, '#') == 3) {
                                setPoint(newGrid, '#', step, w, l, r, c)
                            } else {
                                setPoint(newGrid, '.', step, w, l, r, c)
                            }
                        }

                    }
                }
            }
        }
        grid = newGrid
        step = 0
        return newGrid
    }

    fun countActive(): Int {
        return grid.fold(0) { acc, el -> acc + el.fold(0) { acc1, el1 -> acc1 + el1.fold(0) { acc2, el2 -> acc2 + el2.count { it == '#'}} }}
    }

    fun setPoint(newGrid: MutableList<MutableList<MutableList<StringBuilder>>>, point: Char, step: Int, w:Int, l: Int, r: Int, c: Int): MutableList<MutableList<MutableList<StringBuilder>>> {
        while (w+step >= newGrid.size) {
            newGrid.add(mutableListOf(mutableListOf(StringBuilder())))
        }
        while (l+step >= newGrid[w+step].size) {
            newGrid[w+step].add(mutableListOf(StringBuilder()))
        }
        while (r+step >= newGrid[w+step][l+step].size) {
            newGrid[w+step][l+step].add(StringBuilder())
        }
        while (c+step >= newGrid[w+step][l+step][r+step].length) {
            newGrid[w+step][l+step][r+step].append(point)
        }
        newGrid[w+step][l+step][r+step][c+step] = point
        return newGrid
    }


    fun countElem(w: Int, l: Int, r: Int, c: Int, s: Char): Int {
        var count = 0
        for (h in -1 .. 1) {
            for (i in -1..1) {
                for (j in -1..1) {
                    for (k in -1..1) {
                        val ws = w + h
                        val ls = l + i
                        val rs = r + j
                        val cs = c + k
                        if (ws >= 0 && ws < grid.size &&
                                ls >= 0 && ls < grid[ws].size &&
                                rs >= 0 && rs < grid[ws][ls].size &&
                                cs >= 0 && cs < grid[ws][ls][rs].length) {
                            if (grid[ws][ls][rs][cs] == s && !(ws == w && ls == l && rs == r && cs == c)) {
                                count += 1
                            }
                        }
                    }
                }
            }
        }
        return count
    }

}
