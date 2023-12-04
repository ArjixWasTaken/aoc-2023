@file:Suppress("NAME_SHADOWING")

package Day04

import println
import readInput

val cardRegex = Regex("""Card\s+(\d+):\s+(.*)\s+\|\s+(.*)""")
val numberRegex = Regex("""\d+""")

data class Card(
    val id: Int,
    val winning: List<Int>,
    val chosen: List<Int>
)

fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf {
            val (_, _, winning, chosen) = cardRegex.find(it)!!.groups.toList()

            val winningNumbers = numberRegex.findAll(winning!!.value).map { it.value.toInt() }.toList()
            val chosenNumbers = numberRegex.findAll(chosen!!.value).map { it.value.toInt() }.toList()

            var points = 0

            for (num in chosenNumbers) {
                if  (winningNumbers.contains(num)) {
                    if (points == 0) points = 1
                    else points *= 2
                }
            }

            points
        }
    }

    fun part2(input: List<String>): Int {
        val cards = input.associate {
            val (_, cardId, winning, chosen) = cardRegex.find(it)!!.groups.toList()
            val id = cardId!!.value.toInt()

            Pair(id, arrayListOf(Card(
                id,
                numberRegex.findAll(winning!!.value).map { it.value.toInt() }.toList(),
                numberRegex.findAll(chosen!!.value).map { it.value.toInt() }.toList(),
            )))
        }

        for (id in cards.keys) {
            var idx = 0

            while (idx < cards[id]!!.size) {
                val card = cards[id]!![idx]
                val points = card.chosen.filter { card.winning.contains(it) }.size

                for (i in 1 .. points) {
                    cards[id+i]!!.add(cards[id+i]!!.first())
                }

                idx++
            }
        }

        return cards.flatMap { it.value }.size
    }

    // test if implementation meets criteria from the description, like:
    check(part1(readInput("Day04/test1")!!) == 13)
    check(part2(readInput("Day04/test2")!!) == 30)

    val input = readInput("Day04/Day04")
    if (input == null) {
        println("'src/Day04/Day04.txt' is missing, fetch yours at https://adventofcode.com/2023/day/4/input")
        return
    }

    part1(input).println(4, 1)
    part2(input).println(4, 2)
}
