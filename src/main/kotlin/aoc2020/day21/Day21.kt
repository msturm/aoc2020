package aoc2020.day21

fun main() {
    val input = {}.javaClass.getResource("/day21-2020-1.input").readText().trim().lines()

    val ingredients = input.map { it.split("(").first().trim().split(" ")}
    val alergens = input.map {it.split("(")[1].replace("contains", "").replace(")", "").trim().split(", ").map(String::trim)}

    println(ingredients)
    println(alergens)

    // find alergens and foods which always occur together

    val alergensSet = alergens.flatten().toSet()
    val ingredientsSet = ingredients.flatten().toSet()
    println(alergensSet)
    println(ingredientsSet)
    var alergensMap = mutableMapOf<Int, List<String>>()
    for (i in alergens.indices) {
        alergensMap[i] = alergens[i]
    }

    var ingredientsMap = mutableMapOf<Int, List<String>>()
    for (i in ingredients.indices) {
        ingredientsMap[i] = ingredients[i]
    }

    var alergensToLines = alergensSet.map { alergen ->
        alergen to alergensMap.filter { (k, v) -> v.contains(alergen) }.keys
    }.toMap()

    var ingredientsToLines = ingredientsSet.map { ingredient ->
        ingredient to ingredientsMap.filter { (k, v) -> v.contains(ingredient) }.keys
    }.toMap()

    println(alergensToLines)
    println(ingredientsToLines)

    val ingredientsWithNoMatchingAlergen = ingredientsToLines.filter { (ingredient, lines1) ->
        println(lines1)
        alergensToLines.count { (alergen, lines2) ->
            lines1.containsAll(lines2)
        } == 0
    }
    println(ingredientsWithNoMatchingAlergen)
    println("Day 21 part 1: " + ingredientsWithNoMatchingAlergen.keys.fold(0) {acc, el -> acc + ingredientsToLines[el]!!.count()})

    val ingredientsWithMatchingAlergen = ingredientsToLines.map { (ingredient, lines1) ->
        val matchingAlergen = alergensToLines.filter { (alergen, lines2) ->
            lines1.containsAll(lines2)
        }.keys
        if (matchingAlergen.isNotEmpty()) {
            println(matchingAlergen)
            ingredient to matchingAlergen
        } else {
            null
        }
    }.filterNot { it == null}.map { it!!}.toMap()

    var am = ingredientsWithMatchingAlergen.map { (k, v) -> k to v.toMutableSet() }.toMap().toMutableMap()
    while (am.values.maxOf { it.size } > 1) {
        for (i in am.keys) {
            if (am[i]!!.size == 1) {
                val alergenToRemove = am[i]!!.first()
                am = am.map { (k, v) ->
                    if (v.size > 1) {
                        v.remove(alergenToRemove)
                    }
                    k to v
                }.toMap().toMutableMap()
            }
        }
        println(am)
    }
    val canonicalDangerousIngredientList = am.map { (k, v) -> v.first() to k}.toMap().toSortedMap().values.joinToString(",")


//    val canonicalDangerousIngredientList = ingredientsWithMatchingAlergen.values.joinToString(",")
    println(ingredientsWithMatchingAlergen)
    println("Day 21 part 2: " + canonicalDangerousIngredientList)
}

class Ingredient {

}
