package aoc2020.day3

import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test

internal class TobogganMapTest {
    @Test
    fun testMapWrapper() {
        val input = arrayListOf<String>("..#.",".#.#","#..#")
        assertThat(TobogganMap(input).getCoordinate(0,0), equalTo(MapElement.EMPTY))
        assertThat(TobogganMap(input).getCoordinate(1,1), equalTo(MapElement.TREE))
        assertThat(TobogganMap(input).getCoordinate(9,1), equalTo(MapElement.TREE))
        assertThat(TobogganMap(input).getCoordinate(9,3), equalTo(MapElement.OUT_OF_BOUNDARY))
    }

    @Test
    fun testSlopesTreeCount() {
        val input = arrayListOf<String>(
                "..##.......",
                "#...#...#..",
                ".#....#..#.",
                "..#.#...#.#",
                ".#...##..#.",
                "..#.##.....",
                ".#.#.#....#",
                ".#........#",
                "#.##...#...",
                "#...##....#",
                ".#..#...#.#")
        assertThat(TobogganMap(input).slopeTreeCount(3, 1), equalTo(7))
    }

    @Test
    fun testSumSlopes() {
        val input = arrayListOf<String>(
                "..##.......",
                "#...#...#..",
                ".#....#..#.",
                "..#.#...#.#",
                ".#...##..#.",
                "..#.##.....",
                ".#.#.#....#",
                ".#........#",
                "#.##...#...",
                "#...##....#",
                ".#..#...#.#")
        val slopes = arrayListOf<Pair<Int, Int>>(Pair(1, 1), Pair(3, 1), Pair(5, 1), Pair(7, 1), Pair(1, 2))
        assertThat(TobogganMap(input).multiplySlopes(slopes), equalTo(336))
    }
}
