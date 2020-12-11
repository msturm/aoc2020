package aoc2020.day10

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

internal class AdaptersTest {

    @Test
    fun testCountOptions() {
        assertThat(Adapters().countOptions(intArrayOf(0, 1, 2, 3), 0), equalTo(3))
        assertThat(Adapters().countOptions(intArrayOf(0, 2, 4, 5, 6), 1), equalTo(2))
        assertThat(Adapters().countOptions(intArrayOf(0, 2, 5, 6), 1), equalTo(1))
    }
}
