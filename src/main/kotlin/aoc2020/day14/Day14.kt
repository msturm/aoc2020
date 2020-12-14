package aoc2020.day14

import java.math.BigDecimal
import java.math.BigInteger
import kotlin.math.pow


fun main() {
    val input ={}.javaClass.getResource("/day14-2020-1.input").readText().trim().lines()



    val commands = input.map{it.split("=")}
    var mask = "000000000000000000000000000000000000"
    var mem = mutableMapOf<Int, Long>()
    for (i in commands) {
        var cmdv = i[1].trim()
        var cmd = i[0].trim()
        var addr = 0
        when (i[0].trim()) {
            "mask" -> mask = cmdv
            else -> {
                addr = cmd.substring(4, cmd.length - 1).toInt()
                var binValue = cmdv.toInt().toString(2).reversed().toCharArray()
                var valueString = (0 until (mask.length - binValue.size)).map{ it -> '0'}.toCharArray() + binValue.reversed()
                var newValueString = valueString
                for (n in valueString.indices) {
                    when (mask[n]) {
                        '0' -> newValueString[n] = '0'
                        '1' -> newValueString[n] = '1'
                        else -> newValueString[n] = valueString[n]
                    }
                }
                mem[addr] = newValueString.joinToString("").toLong(2)
            }
        }

    }
    val ans = mem.values.fold(0L) { acc, el -> acc + el}
    // 56:42 minuten
    println("Day 14 part 1: $ans")
    println("Day 14 part 2: ${part2(input)}")

}


fun part2(input: List<String>): Long {
    val commands = input.map{it.split("=")}
    var mask = "000000000000000000000000000000000000"
    var mem = mutableMapOf<Long, Long>()
    for (i in commands) {
        var cmdv = i[1].trim()
        var cmd = i[0].trim()

        when (i[0].trim()) {
            "mask" -> mask = cmdv.reversed()
            else -> {
                val valueToWrite = cmdv.toLong()
                val addr = cmd.substring(4, cmd.length - 1)
                var addrValue = addr.toLong()
                val addresses = getAllAddresses(mask, addrValue)
                addresses.forEach { mem[it] = valueToWrite }
//                println(mem.size.toString() + " " + mem)
                println("val $valueToWrite " + addresses.size + " " + mem.size.toString())
            }
        }
    }

    val ans = mem.values.fold(0L) { acc, el -> acc + el}
    return ans
}

fun getAllAddresses(mask: String, addrValue: Long): LongArray {
    var addressString = addrValue.toString(2).reversed()
    var address = 0L
    var floating = longArrayOf()
    for (i in mask.indices) {
        when (mask[i]) {
            '1' -> address += BigInteger.valueOf(2).pow(i).toLong()
            '0' -> if (i < addressString.length) address += Character.getNumericValue(addressString[i]) * 2.0.pow(i).toInt()
            else -> floating += longArrayOf(i.toLong())
        }
    }
    return getAddresses(floating, address)
}

fun getAddresses(floating: LongArray, newIndx: Long): LongArray {
    if (floating.isEmpty()) {
        return longArrayOf(newIndx)
    } else {
        val b0 = floating.first()
        val remainFloating = floating.drop(1).toLongArray()
        return getAddresses(remainFloating, (newIndx + BigInteger.valueOf(2).pow(b0.toInt()).toLong())) + getAddresses(remainFloating, newIndx)
    }
}
