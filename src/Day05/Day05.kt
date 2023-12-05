@file:Suppress("NAME_SHADOWING")

package Day05

import println
import readInput

val whitespaceRegex = Regex("""\s+""")

data class Almanac(
    val seeds: List<Long>,
    val seedToSoil: Map<LongRange, LongRange>,
    val soilToFertilizer: Map<LongRange, LongRange>,
    val fertilizerToWater: Map<LongRange, LongRange>,
    val waterToLight: Map<LongRange, LongRange>,
    val lightToTemp: Map<LongRange, LongRange>,
    val tempToHumidity: Map<LongRange, LongRange>,
    val humidityToLocation: Map<LongRange, LongRange>,
) {
    companion object {
        fun fromLines(input: List<String>): Almanac {
            val input = ArrayList(input)

            val seeds = arrayListOf<Long>()
            val maps = mutableMapOf<String, Map<LongRange, LongRange>>()

            while (input.isNotEmpty()) {
                val line = input.removeAt(0)
                if (line.trim().isBlank()) continue
                if (!line.contains(':')) throw Error("Malformed input.")

                val header = line.substringBefore(':')

                if (header == "seeds") {
                    seeds.addAll(line.substringAfter("$header:").trim()
                        .split(whitespaceRegex)
                        .map { it.toLong() })
                } else if (header.endsWith("map")) {
                    val mappings = arrayListOf<Triple<Long, Long, Long>>()

                    while (input.isNotEmpty() && input.first().trim().isNotBlank()) {
                        val line = input.removeAt(0)
                        val (first, second, third) = line.trim()
                            .split(whitespaceRegex)
                            .map { it.toLong() }

                        mappings.add(Triple(first, second, third))
                    }

                    maps[header.trim().split(whitespaceRegex).first()] = mappings.associate {
                        Pair(
                            it.second .. it.second+it.third,
                            it.first .. it.first+it.third
                        )
                    }
                } else {
                    throw Error("Malformed input.")
                }
            }

            return Almanac(
                seeds,
                seedToSoil = maps["seed-to-soil"]!!,
                soilToFertilizer = maps["soil-to-fertilizer"]!!,
                fertilizerToWater = maps["fertilizer-to-water"]!!,
                waterToLight = maps["water-to-light"]!!,
                lightToTemp = maps["light-to-temperature"]!!,
                tempToHumidity = maps["temperature-to-humidity"]!!,
                humidityToLocation = maps["humidity-to-location"]!!,
            )
        }
    }
}

fun main() {
    fun part1(input: List<String>): Long {
        val almanac = Almanac.fromLines(input)
        val seedToLocation = mutableMapOf<Long, Long>()

        almanac.seeds.forEach { seed ->
            val soil = almanac.seedToSoil.keys.mapNotNull {
                if (seed in it) { almanac.seedToSoil[it]!!.first + (seed - it.first) }
                else { null }
            }.firstOrNull() ?: seed

            val fertilizer = almanac.soilToFertilizer.keys.mapNotNull {
                if (soil in it) { almanac.soilToFertilizer[it]!!.first + (soil - it.first) }
                else { null }
            }.firstOrNull() ?: soil

            val water = almanac.fertilizerToWater.keys.mapNotNull {
                if (fertilizer in it) { almanac.fertilizerToWater[it]!!.first + (fertilizer - it.first) }
                else { null }
            }.firstOrNull() ?: fertilizer

            val light = almanac.waterToLight.keys.mapNotNull {
                if (water in it) { almanac.waterToLight[it]!!.first + (water - it.first) }
                else { null }
            }.firstOrNull() ?: water

            val temp = almanac.lightToTemp.keys.mapNotNull {
                if (light in it) { almanac.lightToTemp[it]!!.first + (light - it.first) }
                else { null }
            }.firstOrNull() ?: light

            val humidity = almanac.tempToHumidity.keys.mapNotNull {
                if (temp in it) { almanac.tempToHumidity[it]!!.first + (temp - it.first) }
                else { null }
            }.firstOrNull() ?: temp

            val location = almanac.humidityToLocation.keys.mapNotNull {
                if (humidity in it) { almanac.humidityToLocation[it]!!.first + (humidity - it.first) }
                else { null }
            }.firstOrNull() ?: humidity

            seedToLocation[seed] = location
        }

        return seedToLocation.values.min()
    }

    fun part2(input: List<String>): Long {
        val almanac = Almanac.fromLines(input)
        val seedToLocation = mutableMapOf<Long, Long>()

        val seedRanges = almanac.seeds.chunked(2)
            .map { (first, count) ->
                (first until first+count)
            }

        seedRanges.forEach { seedRange ->
            for (seed in seedRange) {
                val soil = almanac.seedToSoil.keys.firstNotNullOfOrNull {
                    if (seed in it) {
                        almanac.seedToSoil[it]!!.first + (seed - it.first)
                    } else {
                        null
                    }
                } ?: seed

                val fertilizer = almanac.soilToFertilizer.keys.firstNotNullOfOrNull {
                    if (soil in it) {
                        almanac.soilToFertilizer[it]!!.first + (soil - it.first)
                    } else {
                        null
                    }
                } ?: soil

                val water = almanac.fertilizerToWater.keys.firstNotNullOfOrNull {
                    if (fertilizer in it) {
                        almanac.fertilizerToWater[it]!!.first + (fertilizer - it.first)
                    } else {
                        null
                    }
                } ?: fertilizer

                val light = almanac.waterToLight.keys.firstNotNullOfOrNull {
                    if (water in it) {
                        almanac.waterToLight[it]!!.first + (water - it.first)
                    } else {
                        null
                    }
                } ?: water

                val temp = almanac.lightToTemp.keys.firstNotNullOfOrNull {
                    if (light in it) {
                        almanac.lightToTemp[it]!!.first + (light - it.first)
                    } else {
                        null
                    }
                } ?: light

                val humidity = almanac.tempToHumidity.keys.firstNotNullOfOrNull {
                    if (temp in it) {
                        almanac.tempToHumidity[it]!!.first + (temp - it.first)
                    } else {
                        null
                    }
                } ?: temp

                val location = almanac.humidityToLocation.keys.firstNotNullOfOrNull {
                    if (humidity in it) {
                        almanac.humidityToLocation[it]!!.first + (humidity - it.first)
                    } else {
                        null
                    }
                } ?: humidity

                seedToLocation[seed] = location
            }
        }

        return seedToLocation.values.min()
    }

    // test if implementation meets criteria from the description, like:
    check(part1(readInput("Day05/test")!!) == 35.toLong())
    check(part2(readInput("Day05/test")!!) == 46.toLong())

    val input = readInput("Day05/Day05")
    if (input == null) {
        println("'src/Day05/Day05.txt' is missing, fetch yours at https://adventofcode.com/2023/day/5/input")
        return
    }

    part1(input).println(5, 1)
    part2(input).println(5, 2)
}
