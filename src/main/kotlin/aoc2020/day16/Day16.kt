package aoc2020.day16


fun main() {
    val input = {}.javaClass.getResource("/day16-2020-1.input").readText().trim().lines()
    // 37:32 min
    Tickets(input).findErrors()
}

class Tickets(val input: List<String>) {
    val fieldDefs = mutableListOf<Field>()
    var validTickets: List<List<Int>> = listOf<List<Int>>()
    fun findErrors() {
        parseFields()
        validTickets = parseNearbyTickets()
        println(validTickets)
        val correctFieldMatchers = findMatchingFields()
        val departureFields = correctFieldMatchers.filter { (k, v) -> v.name.startsWith("departure") }
        val departureFieldsIndex = departureFields.map { it.key }
        println(departureFieldsIndex)
        val myTicket = parseMyTicket()
        println(myTicket)
        println("ticket values: " + departureFieldsIndex.map { el -> myTicket[el]})
        val ans = departureFieldsIndex.fold(1L) {acc, el -> acc * myTicket[el]}
        println("Day 16 part 2: $ans")
    }

    fun parseFields() {
        for (fieldString in input) {
            if (fieldString == "your ticket:") {
                return
            } else if (fieldString != ""){
                fieldDefs.add(Field(fieldString))
            }
        }

    }

    fun parseMyTicket(): List<Int> {
        val ticket = input.drop(input.indexOf("your ticket:") + 1).first()
        val myticketnr = ticket.split(",").map(String::toInt)
        return myticketnr
    }

    fun parseNearbyTickets(): MutableList<List<Int>> {
        val tickets = input.drop(input.indexOf("nearby tickets:") + 1)
        val allRanges = fieldDefs.flatMap { listOf(it.range1, it.range2)}
        var countInvalid = 0
        var validTickets = mutableListOf<List<Int>>()
        for (i in tickets) {
            val ticketNrs = i.split(",").map(String::toInt)
            val invalidTickets = ticketNrs.filter { v -> allRanges.filter { it.contains(v) }.count() == 0 }
//            println(ticketNrs + " " + invalidTickets + " " + allRanges)
            if (invalidTickets.isEmpty()) {
                validTickets.add(ticketNrs)
            }
            countInvalid += invalidTickets.fold(0) { agg, el -> agg + el}

        }
        println("Day 16 part 1: $countInvalid")
        return validTickets
    }

    fun findMatchingFields(): Map<Int, Field> {
        val numOfFields = validTickets.first().size
        val validFieldMatchers = (0 until numOfFields).map { it to fieldDefs.toMutableSet()}.toMap().toMutableMap()

        for (v in validTickets) {
            for (f in v.indices) {
                validFieldMatchers[f] = validFieldMatchers[f]!!.filterNot { it.isNotInRange(v[f]) }.toMutableSet()
            }
        }

        var f = 0
        var usedFields = mutableListOf<Field>()
        while (usedFields.size < fieldDefs.size) {
            if (validFieldMatchers[f]!!.size == 1 && !usedFields.contains(validFieldMatchers[f]!!.first())) {
                val correctFieldMatcher = validFieldMatchers[f]!!.first()
                usedFields.add(correctFieldMatcher)
                validFieldMatchers.values.map { it.remove(correctFieldMatcher) }
                validFieldMatchers[f]!!.add(correctFieldMatcher)
            }
            if (f < validFieldMatchers.size - 1) f += 1 else f = 0
        }
        return validFieldMatchers.map { (k, v) -> k to v.first() }.toMap()
    }
}

class Field(input: String) {
    val name = input.split(":")[0].trim()
    val range1 = input.split(":")[1].trim().split("or")[0].trim().split("-").zipWithNext().map { (a, b) -> IntRange(a.toInt(), b.toInt())}.first()
    val range2 = input.split(":")[1].trim().split("or")[1].trim().split("-").zipWithNext().map { (a, b) -> IntRange(a.toInt(), b.toInt())}.first()

    override fun toString(): String {
        return "Field(name='$name', range1='$range1', range2='$range2')"
    }

    fun isNotInRange(num: Int): Boolean {
        return !range1.contains(num) && !range2.contains(num)
    }


}
