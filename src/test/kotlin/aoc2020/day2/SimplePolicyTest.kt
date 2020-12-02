package aoc2020.day2

import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Test

class SimplePolicyTest {
    @Test
    fun testPolicyCreation() {
        val line = "1-3 a: abcde"
        val policy = SimplePolicy.parsePolicy(line.split(":")[0])
        Assert.assertThat(policy.min, CoreMatchers.equalTo(1))
        Assert.assertThat(policy.max, CoreMatchers.equalTo(3))
        Assert.assertThat(policy.char, CoreMatchers.equalTo("a"))
    }

    @Test
    fun testPasswordWithPolicy() {
        val line = "1-3 a: abcde"
        val policy = SimplePolicy.parsePolicy(line.split(":")[0])
        Assert.assertThat(policy.validatePassword("abcde"), CoreMatchers.equalTo(true))
        Assert.assertThat(policy.validatePassword("abacde"), CoreMatchers.equalTo(true))
        Assert.assertThat(policy.validatePassword("abacaade"), CoreMatchers.equalTo(false))
        Assert.assertThat(policy.validatePassword("bcde"), CoreMatchers.equalTo(false))
    }

    @Test
    fun testPolicyAndPass() {
        val line1 = "1-3 a: abcde"
        val line2 = "1-3 a: aaaabcde"
        Assert.assertThat(SimplePolicy.parsePolicyAndPass(line1), CoreMatchers.equalTo(true))
        Assert.assertThat(SimplePolicy.parsePolicyAndPass(line2), CoreMatchers.equalTo(false))

    }
}
