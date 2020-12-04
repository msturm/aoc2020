package aoc2020.day4

import aoc2020.day4.Requirement.OPTIONAL
import aoc2020.day4.Requirement.REQUIRED
import java.lang.NumberFormatException
import java.lang.RuntimeException

fun main() {
    val input = {}.javaClass.getResource("/day4-2020-1.input").readText()
    println("Day 4 part1: " + DataParser(input).countValidPart1())
    println("Day 4 part2: " + DataParser(input).countValidPart2())
}

enum class Requirement {
    REQUIRED, OPTIONAL
}

typealias DocumentFields = Map<String, Requirement>

interface DocumentFieldValidator {
    val required: Requirement
    val input: String
    fun valid(): Boolean
    companion object {
        fun create(fieldName: String, input: String): DocumentFieldValidator {
            return when (fieldName) {
                "byr" -> BirthYear(input)
                "iyr" -> IssueYear(input)
                "eyr" -> ExpirationYear(input)
                "hgt" -> Height(input)
                "hcl" -> HairColor(input)
                "ecl" -> EyeColor(input)
                "pid" -> PassportID(input)
                "cid" -> CountryID(input)
                else -> throw RuntimeException("Invalid field code")
            }
        }
    }

    class BirthYear(override val input: String) : DocumentFieldValidator {
        override val required = REQUIRED

        override fun valid(): Boolean {
            return try {
                input.toInt() in 1920..2002
            } catch (e: NumberFormatException) {
                false;
            }
        }
    }

    class IssueYear(override val input: String): DocumentFieldValidator {
        override val required = REQUIRED
        override fun valid(): Boolean {
            return try {
                input.toInt() in 2010..2020
            } catch (e: NumberFormatException) {
                false;
            }
        }
    }

    class ExpirationYear(override val input: String): DocumentFieldValidator {
        override val required = REQUIRED
        override fun valid(): Boolean {
            return try {
                input.toInt() in 2020..2030
            } catch (e: NumberFormatException) {
                false;
            }
        }
    }

    class Height(override val input: String): DocumentFieldValidator {
        override val required = REQUIRED
        override fun valid(): Boolean {
            return when (input.takeLast(2)) {
                "in" -> input.dropLast(2).toInt() in 59..76
                "cm" -> input.dropLast(2).toInt() in 150..193
                else -> false
            }

        }
    }

    class HairColor(override val input: String): DocumentFieldValidator {
        override val required = REQUIRED
        override fun valid(): Boolean {
            return input.matches("#[0-9a-f]{6}".toRegex())
        }
    }

    class EyeColor(override val input: String): DocumentFieldValidator {
        override val required = REQUIRED
        override fun valid(): Boolean {
            return input.matches("amb|blu|brn|gry|grn|hzl|oth".toRegex())
        }
    }

    class PassportID(override val input: String): DocumentFieldValidator {
        override val required = REQUIRED
        override fun valid(): Boolean {
            return try {
                input.toLong()
                input.length == 9
            } catch (e: NumberFormatException) {
                false;
            }
        }
    }

    class CountryID(override val input: String): DocumentFieldValidator {
        override val required = OPTIONAL
        override fun valid(): Boolean {
            return true
        }
    }
}


class DataParser(input: String) {
    private val passportFields: DocumentFields = mapOf(
            "byr" to REQUIRED,
            "iyr" to REQUIRED,
            "eyr" to REQUIRED,
            "hgt" to REQUIRED,
            "hcl" to REQUIRED,
            "ecl" to REQUIRED,
            "pid" to REQUIRED,
            "cid" to OPTIONAL
    )
    private val requiredFields = passportFields.filter { (name, req) -> req == REQUIRED }.keys

    val records = input.split("\n\n")

    fun countValidPart1(): Int {
        return records.map { record ->
                    // split to fields
                    record.split("\\s".toRegex())
                    // get fieldname
                    .map {field -> field.split(":")[0]}
                    // check if all require fields are there
                    .containsAll(requiredFields)
                }
                // fold to get count of valid records
                .fold(0) { ag, el -> if (el) ag + 1 else ag}

    }

    fun countValidPart2(): Int {
        return records.map { record ->
            // split to fields
            record.split("\\s".toRegex())
                    // get fieldname
                    .flatMap { field -> field.split(":").zipWithNext() }
                    // filter out invalid values
                    .filter { (fieldName, inputValue) -> DocumentFieldValidator.create(fieldName, inputValue).valid() }
                    .map{ (fieldName, _) -> fieldName}
                    .containsAll(requiredFields)
            }
                // fold to get count of valid records
                .fold(0) { ag, el -> if (el) ag + 1 else ag}
    }
}
