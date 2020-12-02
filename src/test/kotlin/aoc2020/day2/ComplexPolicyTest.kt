package aoc2020.day2

import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Test

class ComplexPolicyTest {
    @Test
    fun testPasswordWithPolicy() {
        val line = "1-3 a: abcde"
        val policy = ComplexPolicy.parsePolicy(line.split(":")[0])
        Assert.assertThat(policy.validatePassword("abcde"), CoreMatchers.equalTo(true))
        Assert.assertThat(policy.validatePassword("cbacde"), CoreMatchers.equalTo(true))
        Assert.assertThat(policy.validatePassword("cabcaade"), CoreMatchers.equalTo(false))
        Assert.assertThat(policy.validatePassword("bcde"), CoreMatchers.equalTo(false))
        Assert.assertThat(policy.validatePassword("abade"), CoreMatchers.equalTo(false))
    }

    @Test
    fun test1() {
        Assert.assertThat(ComplexPolicy.parsePolicyAndPass("1-3 a: abcde"), CoreMatchers.equalTo(true))
    }

    @Test
    fun test2() {
        Assert.assertThat(ComplexPolicy.parsePolicyAndPass("1-3 b: cdefg"), CoreMatchers.equalTo(false))
    }

    @Test
    fun test3() {
        Assert.assertThat(ComplexPolicy.parsePolicyAndPass("2-9 c: ccccccccc"), CoreMatchers.equalTo(false))
    }
}
