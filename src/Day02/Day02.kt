@file:Suppress("NAME_SHADOWING")

package Day02

import println
import readInput

val whiteSpaceRegex = Regex("""\s+""")

data class Set(
    var red: Int = 0,
    var green: Int = 0,
    var blue: Int = 0
)

fun parseGame(game: String): Pair<Int, ArrayList<Set>> {
    var game = game.substring("Game ".length)

    val id = game.substringBefore(':')
    game = game.substring(id.length + 1).trimStart()

    val gameId = id.toInt()
    val sets = arrayListOf<Set>()

    for (set in game.split(';')) {
        val cards = set.split(',').map { it.trim().split(whiteSpaceRegex) }
        val set = Set()

        for (card in cards) {
            val count = card.first().toInt()
            when (card.last()) {
                "red" -> set.red = count
                "green" -> set.green = count
                "blue" -> set.blue = count
            }
        }

        sets.add(set)
    }

    return Pair(gameId, sets)
}

fun main() {
    fun part1(input: List<String>): Int {
        val games = input.map { parseGame(it) }

        return games.sumOf { (id, sets) ->
            val impossibleSetsCount = sets.filter { (red, green, blue) ->
                red > 12 || green > 13 || blue > 14
            }.size

            if (impossibleSetsCount > 0) { 0 }
            else { id }
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf {
            val game = parseGame(it)

            val set = Set()

            game.second.forEach { (red, green, blue) ->
                if (red > set.red) set.red = red
                if (green > set.green) set.green = green
                if (blue > set.blue) set.blue = blue
            }

            set.red * set.green * set.blue
        }
    }

    // test if implementation meets criteria from the description, like:
    check(part1(readInput("Day02/test1")!!) == 8)
    check(part2(readInput("Day02/test2")!!) == 2286)

    val input = readInput("Day02/Day02")
    if (input == null) {
        println("'src/Day02/Day02.txt' is missing, fetch yours at https://adventofcode.com/2023/day/2/input")
        return
    }

    part1(input).println(2, 1)
    part2(input).println(2, 2)
}
