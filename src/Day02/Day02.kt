@file:Suppress("NAME_SHADOWING")

package Day02

import println
import readInput

val whiteSpaceRegex = Regex("""\s+""")

fun parseGame(game: String): Pair<Int, ArrayList<Map<String, Int>>> {
    var game = game.substring("Game ".length)

    val id = game.substringBefore(':')
    game = game.substring(id.length + 1).trimStart()

    val gameId = id.toInt()
    val sets = arrayListOf<Map<String, Int>>()

    for (set in game.split(';')) {
        val cards = set.split(',').map { it.trim().split(whiteSpaceRegex) }

        sets.add(cards.associate {
            Pair(it.last(), it.first().toInt())
        })
    }

    return Pair(gameId, sets)
}

fun main() {
    fun part1(input: List<String>): Int {
        val games = input.associate { parseGame(it) }

        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    check(part1(readInput("Day02/test1")!!) == 142)
//    check(part2(readInput("Day02/test2")!!) == 281)

    val input = readInput("Day02/Day02")
    if (input == null) {
        println("'src/Day02/Day02.txt' is missing, fetch yours at https://adventofcode.com/2023/day/2/input")
        return
    }

    part1(input).println(1, 1)
//    part2(input).println(1, 2)
}
