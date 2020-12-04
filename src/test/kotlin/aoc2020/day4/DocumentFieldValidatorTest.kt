package aoc2020.day4

import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test

internal class DocumentFieldValidatorTest {
    @Test
    fun testBirthDate() {
        assertThat(DocumentFieldValidator.create("byr", "1919").valid(), equalTo(false))
        assertThat(DocumentFieldValidator.create("byr", "1920").valid(), equalTo(true))
        assertThat(DocumentFieldValidator.create("byr", "2002").valid(), equalTo(true))
        assertThat(DocumentFieldValidator.create("byr", "2003").valid(), equalTo(false))
    }

    @Test
    fun testHeight() {
        assertThat(DocumentFieldValidator.create("hgt", "60in").valid(), equalTo(true))
        assertThat(DocumentFieldValidator.create("hgt", "190cm").valid(), equalTo(true))
        assertThat(DocumentFieldValidator.create("hgt", "190in").valid(), equalTo(false))
        assertThat(DocumentFieldValidator.create("hgt", "190").valid(), equalTo(false))
    }

    @Test
    fun testHairColor() {
        assertThat(DocumentFieldValidator.create("hcl", "#123abc").valid(), equalTo(true))
        assertThat(DocumentFieldValidator.create("hcl", "#123abz").valid(), equalTo(false))
        assertThat(DocumentFieldValidator.create("hcl", "123abc").valid(), equalTo(false))
    }

    @Test
    fun testEyeColor() {
        assertThat(DocumentFieldValidator.create("ecl", "brn").valid(), equalTo(true))
        assertThat(DocumentFieldValidator.create("ecl", "wat").valid(), equalTo(false))
    }

    @Test
    fun testPasspordId() {
        assertThat(DocumentFieldValidator.create("pid", "000000001").valid(), equalTo(true))
        assertThat(DocumentFieldValidator.create("pid", "0123456789").valid(), equalTo(false))
    }

}
