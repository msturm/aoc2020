package aoc2020.day2

fun main() {
    val input = {}.javaClass.getResource("/day2-2020-1.input").readText().lines().filter{el -> el.isNotEmpty() }
    println(input.fold(0) { ac, el -> if (ComplexPolicy.parsePolicyAndPass(el)) ac + 1 else ac })
}

class ComplexPolicy(val min: Int, val max: Int, val char: String) {
    fun validatePassword(input: String): Boolean {
        return (input[min-1].toString() == char) xor (input[max-1].toString() == char)
    }

    companion object PolicyFactory {
        fun parsePolicy(policy: String): ComplexPolicy {
            val minMax = policy.split(" ")[0].split("-")
            val char = policy.split(" ")[1]
            return ComplexPolicy(minMax[0].toInt(), minMax[1].toInt(), char)
        }

        fun parsePolicyAndPass(input: String): Boolean {
            return parsePolicy(input.split(":")[0]).validatePassword(input.split(":")[1].trim())
        }
    }
}
