package aoc2020.day19

import java.util.*

fun main() {
    val input = {}.javaClass.getResource("/day19-2020-1.input").readText().trim().lines()
    val ans1 = generatePossibilities(input)
    println("Day 19 part1: " + ans1)
    val input2 = {}.javaClass.getResource("/day19-2020-2.input").readText().trim().lines()
    val ans2 = generatePossibilities(input2)
    println("Day 19 part2: " + ans2)
}

fun generatePossibilities(input: List<String>): Int {
    var rules = input.filter { it.contains(":")}.map{
        val p = it.split(":").map(String::trim)
        p[0] to p[1]
    }.toMap().toSortedMap().toMutableMap()

    val finalRules = rules.filter { it.value.contains("\"")}.map { (k, v) -> k to v.replace("\"", "")}.toMap().toMutableMap()

    finalRules.forEach { (k, v) -> rules[k] = v}

    println(rules)
    println(finalRules)

    while (rules.size > 1) {
        rules = rules.map { (k, v) ->
            var newV = v
            finalRules.forEach { (k1, v1) ->
                newV = newV.replace("( $k1 |^$k1 |^$k1\$| $k1\$)".toRegex(), " ($v1) ")
            }
            k to newV
        }.toMap().toMutableMap()

        finalRules.putAll(rules.filterNot { (k, v) -> v.contains("\\d+".toRegex())}.toMap())
        val keysToRemove = rules.keys.filter { k -> k.toInt() > 0 && rules.values.filter { it.contains(k) }.count() == 0}
        keysToRemove.forEach {rules.remove(it)}
        println(rules.size)
    }


    val cleanedRules = finalRules.map {(k, v) ->
        k to v.replace("\\((\\w)\\)".toRegex(), "$1").replace(" ", "")
    }.toMap().toMutableMap()

    println("f" + finalRules)
    cleanedRules.forEach{ (k, v) -> println("$k -> ${v.toRegex()}")}

    var valuesToVerify = input.filterNot { it.contains(":")}.filterNot(String::isBlank).map(String::trim)
    println(valuesToVerify)
    println(cleanedRules.get("0"))
    valuesToVerify.filter { it.matches(cleanedRules.get("0")!!.toRegex())}.forEach{println(it)}
    println("aabbbbbaabbbaaaaaabbbbbababaaaaabbaaabba".matches(cleanedRules.get("0")!!.toRegex()))
    return valuesToVerify.filter { el -> el.matches(cleanedRules.get("0")!!.toRegex())}.count()
}

