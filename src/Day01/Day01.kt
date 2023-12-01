package Day01

import println
import readInput

val digits = mapOf<String, Int>(
    "nine" to 9,
    "eight" to 8,
    "seven" to 7,
    "six" to 6,
    "five" to 5,
    "four" to 4,
    "three" to 3,
    "two" to 2,
    "one" to 1
)
    .entries
    .sortedBy { it.key.length }
    .reversed()

fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf {str ->
            val chars = str.toList()

            val first = chars.first { it.isDigit() }
            val last = chars.reversed().first { it.isDigit() }
            "$first$last".toInt()
        }
    }

    fun part2(input: List<String>): Int {
        return input.mapNotNull {
            var line = it;
            val nums = ArrayList<Int>();

            while (line.isNotEmpty()) {
                val word = digits.firstOrNull {
                    line.startsWith(it.key)
                }
                val digit = line.first().digitToIntOrNull();

                if (word != null) {
                    nums.add(word.value)
                    line = line.substringAfter(word.key.substringBeforeLast(word.key.last()))

                    continue
                }

                if (digit != null) {
                    nums.add(digit)
                }

                line = line.substring(1)
            }

            "${nums.first()}${nums.last()}".toInt()
        }.sum()
    }

    // test if implementation meets criteria from the description, like:
    check(part1(readInput("Day01/Day01_test1")!!) == 142)
    check(part2(readInput("Day01/Day01_test2")!!) == 281)

    val input = readInput("Day01/Day01")
    if (input == null) {
        println("'src/Day01.txt' is missing, fetch yours at https://adventofcode.com/2023/day/1/input")
        return
    }

    part1(input).println(1, 1)
    part2(input).println(1, 2)
}
