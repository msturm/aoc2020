package aoc2020.day25

fun main() {
    val cardPK = 5290733L
    val doorPK = 15231938L
//    val cardPK = 5764801L
//    val doorPK = 17807724L


    val cardLC = calcForward(cardPK)
    println(cardLC)
    val doorLC = calcForward(doorPK)
    println(doorLC)
    val doorEK = applyEncryption(cardLC, doorPK)
    val cardEK = applyEncryption(doorLC, cardPK)
    println("doorEK: $doorEK cardEK: $cardEK")

}

fun calculateLoopCount() {
    var subject = 7
}

fun calcForward(target: Long): Int {
    var value = 1L
    var loopcount = 0
    while (value != target) {
        value = value * 7
        value = value % 20201227L
//        println(subject)
        loopcount += 1
    }
    return loopcount
}

fun applyEncryption(loopCount: Int, initialSubject: Long): Long {
    var value = 1L
    for (i in 0 until loopCount) {
        value = value * initialSubject
        value = value % 20201227L
//        println(subject)
    }
    return value
}
