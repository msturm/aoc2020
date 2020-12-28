package aoc2020.day22

fun main() {
//    val input = {}.javaClass.getResource("/day22-2020-1.input").readText().trim().lines()
    val input = {}.javaClass.getResource("/day22-2020-1.input").readText().trim().lines()

    val deck1 = Deck.DeckParser.parse(input, 1)
    val deck2 = Deck.DeckParser.parse(input, 2)
    val ans1 = Game(deck1, deck2).playGame(deck1, deck2).second
    println("Day 22 part 1: $ans1")
}

class Game(var deck1: Deck, var deck2: Deck) {
//    fun play(): Long {
//        var round = 1
//        println("Round $round:")
//        while(deck1.canStillPlay() && deck2.canStillPlay()) {
//            val card1 = deck1.play()
//            val card2 = deck2.play()
//            if (card1 > card2) {
//                deck1 = deck1.win(card1, card2)
//                deck2 = deck2.lose()
//            } else {
//                deck2 = deck2.win(card2, card1)
//                deck1 = deck1.lose()
//            }
//            round += 1
//            println("Round $round:")
//        }
//        val player1Score = deck1.calculateScore()
//        val player2Score = deck2.calculateScore()
//        return if (player1Score > 0) player1Score else player2Score
//    }

    fun playr(deck1: Deck, deck2: Deck, deck1played: List<List<Int>>, deck2played: List<List<Int>>): Pair<Int, Long> {
        return if (!deck1.canStillPlay()) {
            2 to deck2.calculateScore()
        } else if (!deck2.canStillPlay()) {
            1 to deck1.calculateScore()
        } else if (deck1played.drop(1).contains(deck1.deck) || deck2played.drop(1).contains(deck2.deck)) {
            1 to deck1.calculateScore()
        } else if (deck1.deckSize() >= deck1.play() && deck2.deckSize() >= deck2.play()) {
            println("Recurse")
            if (playGame(deck1.duplicate(), deck2.duplicate()).first == 1) {
                playr(deck1.win(deck1.play(), deck2.play()), deck2.lose(), deck1played + listOf(deck1.deck), deck2played + listOf(deck2.deck))
            } else {
                playr(deck1.lose(), deck2.win(deck2.play(), deck1.play()), deck1played + listOf(deck1.deck), deck2played + listOf(deck2.deck))
            }
        } else if (deck1.play() > deck2.play()) {
            playr(deck1.win(deck1.play(), deck2.play()), deck2.lose(), deck1played + listOf(deck1.deck), deck2played + listOf(deck2.deck))
        } else {
            playr(deck1.lose(), deck2.win(deck2.play(), deck1.play()), deck1played + listOf(deck1.deck), deck2played + listOf(deck2.deck))
        }
    }

    fun playGame(deck1: Deck, deck2: Deck): Pair<Int, Long> {
        return playr(deck1, deck2, listOf(deck1.deck), listOf(deck2.deck))
    }

}

class Deck(val deck: List<Int>, val player: Int) {

    fun duplicate(): Deck {
        val count = deck.first()
        return Deck(deck.drop(1).take(count).toMutableList(), player)
    }

    fun deckSize(): Int {
        return deck.size - 1
    }

    fun calculateScore(): Long {
        return deck.reversed().foldIndexed(0L) {idx, acc, el -> acc + ((idx+1) * el)}
    }

    fun canStillPlay(): Boolean {
        println("Players $player deck: $deck" )
        return deck.size > 0
    }

    fun play(): Int {
        println("Player $player plays ${deck.first()}")
        return deck.first()
    }

    fun win(card1: Int, card2: Int): Deck {
        println("Player $player wins the round!")
        return Deck(deck.drop(1) + card1 + card2, player)
    }

    fun lose(): Deck {
//        println("Player $player lost!")
        return Deck(deck.drop(1), player)
    }

    companion object DeckParser {
        fun parse(input: List<String>, player: Int): Deck {
            var tempDeck = mutableListOf<Int>()
            var startFrom = -1
            for (i in input.indices) {
                if (input[i].contains("Player $player:")) {
                    startFrom = i.toInt()
                }

                if (input[i].isBlank() || i == input.size) {
                    startFrom = -1
                }
                if (startFrom > -1 && i > startFrom) {
                    tempDeck.add(input[i].toInt())
                }
            }
//            println("Player $player deck " + tempDeck)
            return Deck(tempDeck.toList(), player)
        }
    }
}
