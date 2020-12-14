package aoc2020.day13

fun main() {
//    val input = {}.javaClass.getResource("/day13-2020-1.input").readText().trim().lines()
    val input = {}.javaClass.getResource("/day13-2020-1.input").readText().trim().lines()
//    val start = input[0].toInt()
    val busses = input[1].split(",").filterNot {it == "x"}.map(String::toInt)
//    val ans = busses.map { m -> m to ((((start/m)+1)*m) - start) }.sortedBy { it.second }
//     12:33 min
//    println(ans.first().first * ans.first().second)
    println(part2(input))
//    println(gcd(3000, 197))
//    println(modInverse(197, 3000))
}



fun gcd(a: Int, b: Int): Int {
    val r = a%b
    println("$b, $r, $a")
    return if (r > 0)
        gcd(b, r)
    else {
        b
    }
}

fun modInverse(a: Long, m: Long): Long {
    var a = a
    var m = m
    val m0 = m
    var y = 0L
    var x = 1L
    if (m == 1L) return 0
    while (a > 1) {
        // q is quotient
        val q = a / m
        var t = m

        // m is remainder now, process
        // same as Euclid's algo
        m = a % m
        a = t
        t = y

        // Update x and y
        y = x - q * y
        x = t
    }

    // Make x positive
    if (x < 0) x += m0
    return x
}

fun part2(input: List<String>) {
    val B = input[1].split(",").zip(input[1].indices).filterNot{it.first == "x"}.map{ it.first.toLong() to (it.first.toInt() - it.second)%it.first.toInt()}
//    val B = listOf(67L to 0, 7L to (7-2), 59L to 59-3, 61L to 61-4)
    /* The idea is that you use the chinese remainder theorem to solve this problem.
    Basically, you have to solve that
    X = 1 mod b1
    X = 2 mod b2
    X = 3 mod b3
    ...

    Where 1,2,3 can be different (depending of the number of x's in between.

    In order to implement this theorem, we need to be able to apply inverse modulo, because you need to solve the
    individual equation and sum everything.
     */

    var prod = 1L
    for (i in B.indices) {
        prod *= B[i].first
    }

    var ans = 0L
    for (i in B.indices) {
        val pp: Long = prod/B[i].first
        ans += B[i].second * modInverse(pp, B[i].first) * pp
    }

    println(B)
    println("ans ${ans % prod}")

}

fun calcBussesRow(input: List<String>) {
    val busses2 = input[1].split(",")
    var a = 1L

//    for (n in busses.indices) {
    var k = 1
    var t = 0L
    while (k < busses2.size) {
        t = a * busses2[0].toLong()
//        println("$a $k $t")
        if (busses2[k] != "x") {
            if ((t+k) % busses2[k].toLong() == 0L) {
                k+=1
            } else {
                k = 1
                a+=1
            }
        } else {
            k+=1
        }
    }
    val ans2 = t
    println("Day 13 part 2: $ans2")
}
