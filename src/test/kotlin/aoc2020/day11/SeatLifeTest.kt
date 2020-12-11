package aoc2020.day11

import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test

internal class SeatLifeTest {
    @Test
    fun testCheckSeat() {
        val input = "...\n.L.\n...".split("\n").map(String::toCharArray)
        assertThat(SeatLife().checkSeatPart1(input, 'L', 0, 1), equalTo(1))
        assertThat(SeatLife().checkSeatPart1(input, 'L', 1, 1), equalTo(0))
        assertThat(SeatLife().checkSeatPart1(input, 'L', 2, 1), equalTo(1))
        assertThat(SeatLife().checkSeatPart1(input, 'L', 1, 0), equalTo(1))
        assertThat(SeatLife().checkSeatPart1(input, 'L', 1, 1), equalTo(0))
        assertThat(SeatLife().checkSeatPart1(input, 'L', 1, 2), equalTo(1))
    }

    @Test
    fun testCheckSeatPart2() {
        val input = "#.#\n.L.\n#.#".split("\n").map(String::toCharArray)
        assertThat(SeatLife().checkSeatPart2(input, 'L', 0, 1), equalTo(1))
        assertThat(SeatLife().checkSeatPart2(input, 'L', 1, 1), equalTo(0))
        assertThat(SeatLife().checkSeatPart2(input, 'L', 2, 1), equalTo(1))
        assertThat(SeatLife().checkSeatPart2(input, 'L', 1, 0), equalTo(1))
        assertThat(SeatLife().checkSeatPart2(input, 'L', 1, 1), equalTo(0))
        assertThat(SeatLife().checkSeatPart2(input, 'L', 1, 2), equalTo(1))

        val input2 = """
            #.##.
            #####
            .....
            .#.L.
        """.trimIndent().split("\n").map(String::toCharArray)

        assertThat(SeatLife().checkSeatPart2(input2, '#', 0, 2), equalTo(5))
    }

    @Test
    fun testBasicMakeStep() {
        val input = "...\n.L.\n..."
        val expected = "...\n.#.\n..."
        val result = SeatLife().makeStepPart1(input)
        result.forEach{println(it)}
        assertThat(result, equalTo(expected))
    }

    @Test
    fun testFindSeat() {
        val input = """
            .....
            .L..L
            .....
            .#.L.
        """.trimIndent()
        assertThat(SeatLife().findSeat(input.split("\n").map(String::toCharArray), 0 to 0, 1 to 1), equalTo('L'))
        assertThat(SeatLife().findSeat(input.split("\n").map(String::toCharArray), 1 to 1, 1 to 0), equalTo('L'))
        assertThat(SeatLife().findSeat(input.split("\n").map(String::toCharArray), 1 to 1, 0 to 1), equalTo('#'))
        assertThat(SeatLife().findSeat(input.split("\n").map(String::toCharArray), 3 to 3, -1 to 0), equalTo('#'))
        assertThat(SeatLife().findSeat(input.split("\n").map(String::toCharArray), 3 to 3, 1 to 0), equalTo('.'))

        val input2 = """
            #.###
            #####
            .....
        """.trimIndent()
        assertThat(SeatLife().findSeat(input2.split("\n").map(String::toCharArray), 2 to 0, -1 to 0), equalTo('#'))
        assertThat(SeatLife().findSeat(input2.split("\n").map(String::toCharArray), 2 to 0, -1 to 1), equalTo('#'))
        assertThat(SeatLife().findSeat(input2.split("\n").map(String::toCharArray), 2 to 0, 0 to 1), equalTo('#'))
        assertThat(SeatLife().findSeat(input2.split("\n").map(String::toCharArray), 2 to 0, 1 to 1), equalTo('#'))
        assertThat(SeatLife().findSeat(input2.split("\n").map(String::toCharArray), 2 to 0, 1 to 0), equalTo('#'))
        assertThat(SeatLife().findSeat(input2.split("\n").map(String::toCharArray), 2 to 0, -1 to -1), equalTo('.'))
        assertThat(SeatLife().findSeat(input2.split("\n").map(String::toCharArray), 2 to 0, 0 to -1), equalTo('.'))
        assertThat(SeatLife().findSeat(input2.split("\n").map(String::toCharArray), 2 to 0, 1 to -1), equalTo('.'))
    }


    val testInput = """
        L.LL.LL.LL
        LLLLLLL.LL
        L.L.L..L..
        LLLL.LL.LL
        L.LL.LL.LL
        L.LLLLL.LL
        ..L.L.....
        LLLLLLLLLL
        L.LLLLLL.L
        L.LLLLL.LL
    """.trimIndent()

    val step1 = """
        #.##.##.##
        #######.##
        #.#.#..#..
        ####.##.##
        #.##.##.##
        #.#####.##
        ..#.#.....
        ##########
        #.######.#
        #.#####.##
    """.trimIndent()

    val step2 = """
        #.LL.L#.##
        #LLLLLL.L#
        L.L.L..L..
        #LLL.LL.L#
        #.LL.LL.LL
        #.LLLL#.##
        ..L.L.....
        #LLLLLLLL#
        #.LLLLLL.L
        #.#LLLL.##
    """.trimIndent()

    val step3 = """
        #.##.L#.##
        #L###LL.L#
        L.#.#..#..
        #L##.##.L#
        #.##.LL.LL
        #.###L#.##
        ..#.#.....
        #L######L#
        #.LL###L.L
        #.#L###.##
    """.trimIndent()

    @Test
    fun testMakeStepPart1() {
        val step1Result = SeatLife().makeStepPart1(testInput)
        assertThat(step1Result, equalTo(step1))
        val step2Result = SeatLife().makeStepPart1(step1Result)
        assertThat(step2Result, equalTo(step2))
        val step3Result = SeatLife().makeStepPart1(step2Result)
        assertThat(step3Result, equalTo(step3))
    }

    val step1p2 = """
        #.##.##.##
        #######.##
        #.#.#..#..
        ####.##.##
        #.##.##.##
        #.#####.##
        ..#.#.....
        ##########
        #.######.#
        #.#####.##
    """.trimIndent()

    val step2p2 = """
        #.LL.LL.L#
        #LLLLLL.LL
        L.L.L..L..
        LLLL.LL.LL
        L.LL.LL.LL
        L.LLLLL.LL
        ..L.L.....
        LLLLLLLLL#
        #.LLLLLL.L
        #.LLLLL.L#
    """.trimIndent()

    val step3p2 = """
        #.L#.##.L#
        #L#####.LL
        L.#.#..#..
        ##L#.##.##
        #.##.#L.##
        #.#####.#L
        ..#.#.....
        LLL####LL#
        #.L#####.L
        #.L####.L#
    """.trimIndent()

    @Test
    fun testMakeStepPart2() {
        val step1Result = SeatLife().makeStepPart2(testInput)
        assertThat(step1Result, equalTo(step1p2))
        val step2Result = SeatLife().makeStepPart2(step1Result)
        assertThat(step2Result, equalTo(step2p2))
        val step3Result = SeatLife().makeStepPart2(step2Result)
        assertThat(step3Result, equalTo(step3p2))
    }

}


