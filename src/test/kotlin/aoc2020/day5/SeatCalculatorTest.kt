package aoc2020.day5

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

internal class SeatCalculatorTest {

    @Test
    fun calcRow() {
        assertThat(SeatCalculator("BFFFBBF").calcRow(), equalTo(70))
        assertThat(SeatCalculator("FFFBBBF").calcRow(), equalTo(14))
        assertThat(SeatCalculator("BBFFBBF").calcRow(), equalTo(102))
    }

    @Test
    fun calcSeat() {
        assertThat(SeatCalculator("RRR").calcSeat(), equalTo(7))
        assertThat(SeatCalculator("RLL").calcSeat(), equalTo(4))
    }

    @Test
    fun calcSeatID() {
        assertThat(SeatCalculator("BFFFBBFRRR").calcSeatID(), equalTo(567))
        assertThat(SeatCalculator("FFFBBBFRRR").calcSeatID(), equalTo(119))
        assertThat(SeatCalculator("BBFFBBFRLL").calcSeatID(), equalTo(820))
    }

    @Test
    fun testFindMissingSeat() {
        assertThat(findMissingSeat(listOf(5,9,6,4,8)), equalTo(7))
    }

}
