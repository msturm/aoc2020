package aoc2020.day7

fun main() {
    val input = {}.javaClass.getResource("/day7-2020-1.input").readText().trim()
    println("Day 7 part 1: " + BagData().findHoldingBagsCount(input.lines(), "shiny gold"))
    println("Day 7 part 2: " + BagData().countTotalChildBags(input.lines(), "shiny gold"))
}


class BagData {


    fun countTotalChildBags(data: List<String>, bagColor: String): Int {
        val bagData = parseData(data)
        return countChildBags(bagColor, bagData)
    }

    fun countChildBags(bagColor: String, bagData: Map<String, Map<String, Int>>): Int {
        return bagData.getValue(bagColor).map { (k, v) -> v + (v * countChildBags(k, bagData))}.fold(0) {acc, c -> acc + c}
    }

    fun findHoldingBagsCount(data: List<String>, bagColor: String): Int {
        // switch values to keys
        val bagOrder = bagsWhichCanHoldColor(data)

        return possibleBagsForColor(bagOrder, bagColor).count()
    }

    private fun bagsWhichCanHoldColor(data: List<String>) =
            parseData(data).flatMap { (k, v) -> v.keys.map { it to k } }.groupBy { it.first }
                    .map { (k, v) -> k to v.map { it.second } }.toMap()

    fun possibleBagsForColor(bagOrder: Map<String, List<String>>, color: String): Set<String> {
        return recursiveGetBags(color, bagOrder).toSet()
    }

    fun recursiveGetBags(color: String, bagOrder: Map<String, List<String>>): List<String> {
        if (!bagOrder.containsKey(color)) return emptyList()
        return bagOrder.getValue(color) + bagOrder.getValue(color).flatMap { recursiveGetBags(it, bagOrder) }
    }
    fun parseData(data: List<String>): Map<String, Map<String, Int>> {
        return data.map { parseBagLine(it) }.toMap()
    }

    fun parseBagLine(data: String): Pair<String, Map<String, Int>> {
        val color = data.substring(0, data.indexOf("contain")).split(" ").take(2)
        val holds = data.substring(data.indexOf("contain") + "contains".length)
        holds.split(",")
        return color.joinToString(separator = " ") { it } to parseHoldsData(holds.split(","))
    }

    fun parseHoldsData(holdsList: List<String>): Map<String, Int> {
        return if (holdsList[0].contains("no other bags")) {
            emptyMap()
        } else {
            holdsList.map { data ->
                val count = data.trim().split(" ")[0].toInt()
                val color = data.trim().split(" ").subList(1, 3).joinToString(separator = " ") { it }
                color to count
            }.toMap()
        }
    }
}
