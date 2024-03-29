package aoc2020.day6

fun main() {
    val input = {}.javaClass.getResource("/day6-2020-1.input").readText()
    println("Day 6 part 1: ${QuestionAnalyzer(input).countAllQuestionairesAnyoneYes()}")
    println("Day 6 part 2: ${QuestionAnalyzer(input).countAllQuestionairesEveryoneYes()}")

    println("Day 6 part 1 as oneliner: " + input.split("\n\n").fold(0) {acc, el -> acc + el.toSet().filterNot { it == '\n' }.count()})
    println("Day 6 part 2 as oneliner: " + input.split("\n\n").fold(0) {acc, el -> acc + el.trim().split("\n").fold(('a'..'z').asIterable()) { acc2, answers -> answers.toSet().intersect(acc2)}.count()})
}

class QuestionAnalyzer(val input: String = "") {
    fun countAllYes(groupInput: String): Int {
        return groupInput.toSet().filterNot { el -> el == '\n' }.count()
    }

    fun countEveryoneYes(groupInput: String): Int {
        return groupInput.trim().split("\n").fold(('a'..'z').asSequence().toSet()) { acc, answers -> answers.toSet().intersect(acc)}.count()
    }

    fun countAllQuestionairesAnyoneYes(): Int {
        return input.split("\n\n").fold(0) { ag, el -> ag + countAllYes(el)}
    }

    fun countAllQuestionairesEveryoneYes(): Int {
        return input.split("\n\n").fold(0) { ag, el -> ag + countEveryoneYes(el)}
    }
}
