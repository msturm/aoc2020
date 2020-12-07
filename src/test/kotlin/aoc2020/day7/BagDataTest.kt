package aoc2020.day7

import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test

internal class BagDataTest {

    val testInput = "light red bags contain 1 bright white bag, 2 muted yellow bags.\n" +
            "dark orange bags contain 3 bright white bags, 4 muted yellow bags.\n" +
            "bright white bags contain 1 shiny gold bag.\n" +
            "muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.\n" +
            "shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.\n" +
            "dark olive bags contain 3 faded blue bags, 4 dotted black bags.\n" +
            "vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.\n" +
            "faded blue bags contain no other bags.\n" +
            "dotted black bags contain no other bags."

    @Test
    fun findHoldingBags() {
        assertThat(BagData().findHoldingBagsCount(testInput.split("\n"), "shiny gold"), equalTo(4))
    }

    @Test
    fun testParseHoldsData() {
        assertThat(BagData().parseHoldsData(listOf("1 bright white bag", "2 muted yellow bags")),
                equalTo(mapOf("bright white" to 1, "muted yellow" to 2)))
    }

    @Test
    fun testPartBagDataLine() {
        assertThat(BagData().parseBagLine("light red bags contain 1 bright white bag, 2 muted yellow bags"),
                equalTo("light red" to mapOf("bright white" to 1, "muted yellow" to 2)))
        assertThat(BagData().parseBagLine("bright white bags contain 1 shiny gold bag"),
                equalTo("bright white" to mapOf("shiny gold" to 1)))
        assertThat(BagData().parseBagLine("faded blue bags contain no other bags"),
                equalTo("faded blue" to mapOf()))

    }

    @Test
    fun testCountTotalChildBags() {
        assertThat(BagData().countTotalChildBags(testInput.split("\n"), "shiny gold"), equalTo(32))
    }
}

