package aoc2020.day6

import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test

internal class QuestionAnalyzerTest {

    @Test
    fun testCountYesPerGroup() {
        assertThat(QuestionAnalyzer().countAllYes("abc"), equalTo(3))
        assertThat(QuestionAnalyzer().countAllYes("a\nb\nc"), equalTo(3))
        assertThat(QuestionAnalyzer().countAllYes("ab\nac"), equalTo(3))
        assertThat(QuestionAnalyzer().countAllYes("a\na\na\na"), equalTo(1))
        assertThat(QuestionAnalyzer().countAllYes("b"), equalTo(1))
    }

    @Test
    fun testCountAllQuestionaires() {
        val input = "abc\n" +
                "\n" +
                "a\n" +
                "b\n" +
                "c\n" +
                "\n" +
                "ab\n" +
                "ac\n" +
                "\n" +
                "a\n" +
                "a\n" +
                "a\n" +
                "a\n" +
                "\n" +
                "b\n"
        assertThat(QuestionAnalyzer(input).countAllQuestionairesAnyoneYes(), equalTo(11))
    }

    @Test
    fun testCountAllYes() {
        assertThat(QuestionAnalyzer().countEveryoneYes("abc"), equalTo(3))
        assertThat(QuestionAnalyzer().countEveryoneYes("a\nb\nc"), equalTo(0))
        assertThat(QuestionAnalyzer().countEveryoneYes("ab\nac"), equalTo(1))
        assertThat(QuestionAnalyzer().countEveryoneYes("a\na\na\na"), equalTo(1))
        assertThat(QuestionAnalyzer().countEveryoneYes("b\n"), equalTo(1))
    }

    @Test
    fun testCountAllQuestionaires2() {
        val input = "abc\n" +
                "\n" +
                "a\n" +
                "b\n" +
                "c\n" +
                "\n" +
                "ab\n" +
                "ac\n" +
                "\n" +
                "a\n" +
                "a\n" +
                "a\n" +
                "a\n" +
                "\n" +
                "b\n"
        assertThat(QuestionAnalyzer(input).countAllQuestionairesEveryoneYes(), equalTo(6))
    }
}
