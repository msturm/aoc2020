package aoc2020.day18

import aoc2020.day18.ExprSolver.Ops.*
import java.util.ArrayDeque
import java.util.function.BinaryOperator

fun main() {
    val input = {}.javaClass.getResource("/day18-2020-1.input").readText().trim().lines()
//    val input = {}.javaClass.getResource("/day18-2020-test.input").readText().trim().lines()
    val ans = input.fold(0L) {acc, el -> acc + ExprSolver().parseNext(el.replace(" ", "")).first}
    input.forEach {
        val ans1 = ExprSolver().parseNext(it.replace(" ", "")).first
        val ans2expr = AddPreference2(it).parse()
        val ans2 = ans2expr.calc()
            println(it)
            println(ans2expr)
            println("$ans1 $ans2")
    }
    println("Day 18 part 1: $ans")
    val ans2 = input.fold(0L) {acc, el -> acc + AddPreference2(el).parse().calc()}

//    println(AddPreference2("2 * 3 + 4 * 5").parse())
//    println(AddPreference2("(8 * 6 * 9 + 4) * 4").parse().calc())
//    println(AddPreference2("((2+4)+(5*6))+7*2").parse())
//    println(AddPreference2("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2").parse())
    println("Day 18 part 2: $ans2")
}

class AddPreference2(val input: String) {
    var p = 0

    abstract class Term {
        abstract fun calc(): Long
    }
    class Num(val t: Long) : Term() {
        override fun toString(): String {
            return "$t"
        }

        override fun calc(): Long = t
    }
    class AddOp() : Term() {
        override fun calc(): Long {
            TODO("Not yet implemented")
        }
        override fun toString(): String {
            return "*"
        }
    }
    class MultiplyOp() : Term() {
        override fun calc(): Long {
            TODO("Not yet implemented")
        }

        override fun toString(): String {
            return "*"
        }
    }
    class Add(val t: Term, val u: Term) : Term() {
        override fun toString(): String {
            return "($t + $u)"
        }

        override fun calc(): Long = t.calc() + u.calc()
    }
    class Multiply(val t: Term, val u: Term) : Term() {
        override fun toString(): String {
            return "($t * $u)"
        }

        override fun calc(): Long = t.calc() * u.calc()
    }



    private fun consume(): Char? {
        var r = input.getOrNull(p)
        p += 1
        while (r == ' ') {
            r = input.getOrNull(p)
            p += 1
        }
        return r
    }

    private fun peek(): Char? {
        var a = p
        var r = input.getOrNull(a)
        while (r == ' ') {
            a += 1
            r = input.getOrNull(a)
        }
        return r
    }

    fun parse(): Term {
        return parseExpr()
    }

    fun parseExpr(): Term {
        val stack = ArrayDeque<Term>()
        var t = consume()
        while (t != null && t != ')') {
            when {
                t.isDigit() -> {
                    val peek = stack.peek()
                    when (peek) {
                        is AddOp -> {
                            stack.pop()
                            stack.push(Add(stack.pop(), Num(charToLong(t))))
                        } else -> {
                            stack.push(Num(charToLong(t)))
                        }
                    }
                }
                t == '+' -> {
                    stack.push(AddOp())
                }
                t == '*' -> {
                    stack.push(MultiplyOp())
                }
                t == '(' -> {
                    val peek = stack.peek()
                    when (peek) {
                        is AddOp -> {
                            stack.pop()
                            stack.push(Add(stack.pop(), parseExpr()))
                        } else -> {
                            stack.push(parseExpr())
                        }
                    }
                }

             }
            t = consume()
        }

        var u = stack.pop()
        while (stack.peek() != null) {
            stack.pop()
            var v = stack.pop()
            u = Multiply(v, u)
        }
        return u
    }

    private fun charToLong(c: Char): Long {
        return Character.getNumericValue(c).toLong()
    }
}

class ExprSolver() {

    enum class Ops : BinaryOperator<Long> {
        UNDEFINED {
            override fun apply(t: Long, u: Long): Long {
                return t
            }
        },
        ADD {
            override fun apply(t: Long, u: Long): Long {
                return t + u
            }
        },
        MULTIPLY {
            override fun apply(t: Long, u: Long): Long {
                return t * u
            }
        }
    }

    fun parseNext(input: String): Pair<Long, Int> {
        var p = 0L
        var i = 0
        var prevOp = UNDEFINED
            while (i < input.length) {
                when (input[i]) {
                    '+' -> {
                        prevOp = ADD
                        i += 1
                    }
                    '*' -> {
                        prevOp = MULTIPLY
                        i += 1
                    }
                    '(' -> {
                        val r = parseNext(input.drop(i + 1))
                        p = prevOp.apply(r.first, p)
                        i += r.second + 1
                    }
                    ')' -> {
                        i += 1
                        return p to i
                    }
                    else -> {
                        p = prevOp.apply(Character.getNumericValue(input[i]).toLong(), p)
                        i += 1
                }
            }
        }
        return p to i
    }
}
