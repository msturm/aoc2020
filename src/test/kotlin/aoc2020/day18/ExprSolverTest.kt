package aoc2020.day18

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

internal class ExprSolverTest {
    @Test
    fun testExpressionSolver() {
        assertThat(ExprSolver().parseNext("5 + 6 * 3".replace(" ","")).first, equalTo(33))
        assertThat(ExprSolver().parseNext("1 + 2 * 3 + 4 * 5 + 6".replace(" ","")).first, equalTo(71))
        assertThat(ExprSolver().parseNext("5 + (6 * 3)".replace(" ","")).first, equalTo(23))
        assertThat(ExprSolver().parseNext("2 * 3 + (4 * 5)".replace(" ","")).first, equalTo(26))
        assertThat(ExprSolver().parseNext("5 + (8 * 3 + 9 + 3 * 4 * 3)".replace(" ","")).first, equalTo(437))
        assertThat(ExprSolver().parseNext("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))".replace(" ","")).first, equalTo(12240))
        assertThat(ExprSolver().parseNext("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2".replace(" ","")).first, equalTo(13632))
    }


    @Test
    fun testExprSolver2() {
        assertThat(AddPreference2("5 + 6 * 3".replace(" ","")).parse().calc(), equalTo(33))
        assertThat(AddPreference2("1 + 2 * 3 + 4 * 5 + 6".replace(" ","")).parse().calc(), equalTo(231))
        assertThat(AddPreference2("1 + (2 * 3)".replace(" ","")).parse().calc(), equalTo(7))
        assertThat(AddPreference2("1 + (2 * 3) + (4 * (5 + 6))".replace(" ","")).parse().calc(), equalTo(51))
        assertThat(AddPreference2("2 * 3 + (4 * 5)".replace(" ","")).parse().calc(), equalTo(46))
        assertThat(AddPreference2("5 + (8 * 3 + 9 + 3 * 4 * 3)".replace(" ","")).parse().calc(), equalTo(1445))
        assertThat(AddPreference2("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))".replace(" ","")).parse().calc(), equalTo(669060))
        println(AddPreference2("(1 * 2) * 3".replace(" ","")).parse())
        assertThat(AddPreference2("((1 * 2)) * 3".replace(" ","")).parse().calc(), equalTo(6))
        println(AddPreference2("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2".replace(" ","")).parse())
        assertThat(AddPreference2("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2".replace(" ","")).parse().calc(), equalTo(23340))
//        println(AddPreference().parseNext("6 + 7 + (6 * (3 + 8 * 6 * 8) * (2 + 6 * 5 + 6 + 5) * (6 + 3 + 3 + 3 + 8 * 6)) * (9 * 2 * (8 + 6 * 3 + 8 * 9)) + 5".replace(" ","")).first)
//        assertThat(AddPreference().parseNext("6 + 7 + (6 * (3 + 8 * 6 * 8) * (2 + 6 * 5 + 6 + 5) * (6 + 3 + 3 + 3 + 8 * 6)) * (9 * 2 * (8 + 6 * 3 + 8 * 9)) + 5".replace(" ","")).first.calc(), equalTo(23340))
    }
}
