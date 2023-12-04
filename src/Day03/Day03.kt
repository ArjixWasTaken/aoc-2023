@file:Suppress("NAME_SHADOWING")

package Day03

import println
import readInput

fun Boolean.toInt() = if (this) 1 else 0
val numRegex = Regex("""\d+""")

fun main() {
    fun part1(input: List<String>): Int {
        val cols = input.first().length
        val rows = input.size

        val symbols = arrayListOf<Pair<Int, Int>>()
        val numbers = arrayListOf<Int>()

        for (row in 0 until rows) {
            for (col in 0 until cols) {
                val char = input[row][col]
                if (!char.isDigit() && char != '.') {
                    symbols.add(Pair(row, col))
                }
            }
        }

        fun getNum(row: Int, col: Int): Int {
            var idx = col
            while (idx-1 in 0 until cols && input[row][idx].isDigit()) {
                idx--;
            }

            val match = numRegex.find(input[row].substring(idx))
            return match!!.value.toInt()
        }

        for ((row, col) in symbols) {
            fun getLeft(): Char = input[row][col-1]
            fun getRight(): Char = input[row][col+1]
            fun getTop(): Char = input[row-1][col]
            fun getBottom(): Char = input[row+1][col]

            fun getTopLeft(): Char = input[row-1][col-1]
            fun getTopRight(): Char = input[row-1][col+1]
            fun getBottomLeft(): Char = input[row+1][col-1]
            fun getBottomRight(): Char = input[row+1][col+1]

            // Check Sides
            val left = if (col-1 in 0 until cols) { getLeft().isDigit() } else { false }
            val right = if (col+1 < cols) { getRight().isDigit() } else { false }
            val top = if (row-1 in 0 until rows) { getTop().isDigit() } else { false }
            val bottom = if (row+1 < rows) { getBottom().isDigit() } else { false }

            // Check diagonal
            val topLeft = if (row-1 in 0..<rows && col-1 in 0..<cols) { getTopLeft().isDigit() } else { false }
            val topRight = if (row-1 in 0..<rows && col+1 < cols) { getTopRight().isDigit() } else { false }
            val bottomLeft = if (row+1 <rows && col-1 in 0..<cols) { getBottomLeft().isDigit() } else { false }
            val bottomRight = if (row+1 <rows && col+1 <cols) { getBottomRight().isDigit() } else { false }

            if (left) { numbers.add(getNum(row, col-1)) }
            if (right) { numbers.add(getNum(row, col+1)) }
            if (top) { numbers.add(getNum(row-1, col)) }
            else {
                if (topLeft) { numbers.add(getNum(row-1, col-1)) }
                if (topRight) { numbers.add(getNum(row-1, col+1)) }
            }

            if (bottom) { numbers.add(getNum(row+1, col)) }
            else {
                if (bottomLeft) { numbers.add(getNum(row+1, col-1)) }
                if (bottomRight) { numbers.add(getNum(row+1, col+1)) }
            }
        }

        return numbers.sum()
    }

    fun part2(input: List<String>): Int {
        val cols = input.first().length
        val rows = input.size

        val symbols = arrayListOf<Pair<Int, Int>>()
        val numbers = arrayListOf<Int>()

        for (row in 0 until rows) {
            for (col in 0 until cols) {
                val char = input[row][col]
                if (!char.isDigit() && char != '.') {
                    symbols.add(Pair(row, col))
                }
            }
        }

        fun getNum(row: Int, col: Int): Int {
            var idx = col
            while (idx-1 in 0 until cols && input[row][idx].isDigit()) {
                idx--;
            }

            val match = numRegex.find(input[row].substring(idx))
            return match!!.value.toInt()
        }

        for ((row, col) in symbols) {
            fun getLeft(): Char = input[row][col-1]
            fun getRight(): Char = input[row][col+1]
            fun getTop(): Char = input[row-1][col]
            fun getBottom(): Char = input[row+1][col]

            fun getTopLeft(): Char = input[row-1][col-1]
            fun getTopRight(): Char = input[row-1][col+1]
            fun getBottomLeft(): Char = input[row+1][col-1]
            fun getBottomRight(): Char = input[row+1][col+1]

            // Check Sides
            val left = if (col-1 in 0 until cols) { getLeft().isDigit() } else { false }
            val right = if (col+1 < cols) { getRight().isDigit() } else { false }
            val top = if (row-1 in 0 until rows) { getTop().isDigit() } else { false }
            val bottom = if (row+1 < rows) { getBottom().isDigit() } else { false }

            // Check diagonal
            val topLeft = if (row-1 in 0..<rows && col-1 in 0..<cols) { getTopLeft().isDigit() } else { false }
            val topRight = if (row-1 in 0..<rows && col+1 < cols) { getTopRight().isDigit() } else { false }
            val bottomLeft = if (row+1 <rows && col-1 in 0..<cols) { getBottomLeft().isDigit() } else { false }
            val bottomRight = if (row+1 <rows && col+1 <cols) { getBottomRight().isDigit() } else { false }

            var count = 0
            var ratio = 1

            if (left) {
                count++
                ratio *= getNum(row, col-1)
            }
            if (right) {
                count++
                ratio *= getNum(row, col+1)
            }
            if (top) {
                count++
                ratio *= getNum(row-1, col)
            }
            else {
                if (topLeft) {
                    count++
                    ratio *= getNum(row-1, col-1)
                }
                if (topRight) {
                    count++
                    ratio *= getNum(row-1, col+1)
                }
            }

            if (bottom) {
                count++
                ratio *= getNum(row+1, col)
            }
            else {
                if (bottomLeft) {
                    count++
                    ratio *= getNum(row+1, col-1)
                }
                if (bottomRight) {
                    count++
                    ratio *= getNum(row+1, col+1)
                }
            }

            if (count == 2) numbers.add(ratio)
        }

        return numbers.sum()
    }

    // test if implementation meets criteria from the description, like:
    check(part1(readInput("Day03/test1")!!) == 4361)
    check(part2(readInput("Day03/test1")!!) == 467835)

    val input = readInput("Day03/Day03")
    if (input == null) {
        println("'src/Day03/Day03.txt' is missing, fetch yours at https://adventofcode.com/2023/day/3/input")
        return
    }

    part1(input).println(3, 1)
    part2(input).println(3, 2)
}
