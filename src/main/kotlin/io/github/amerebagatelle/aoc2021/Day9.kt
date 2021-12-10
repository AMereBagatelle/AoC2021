package io.github.amerebagatelle.aoc2021

class Day9 : Puzzle {
    override fun getDay(): Int = 9

    override fun run1(): Number {
        val input = getInput()

        val heightmapBuilder = mutableMapOf<Pair<Int, Int>, Int>()
        for (x in input[0].indices) {
            for (y in input.indices) {
                heightmapBuilder[Pair(x, y)] = input[y][x].digitToInt()
            }
        }

        println(heightmapBuilder)
        val heightmap = Heightmap(heightmapBuilder)

        val lowPoints = heightmap.getLowPoints()

        return lowPoints.sumOf { heightmap.riskLevel(it) }
    }

    override fun run2(): Number {
        val input = getInput()

        val heightmapBuilder = mutableMapOf<Pair<Int, Int>, Int>()
        for (x in input[0].indices) {
            for (y in input.indices) {
                heightmapBuilder[Pair(x, y)] = input[y][x].digitToInt()
            }
        }
        val heightmap = Heightmap(heightmapBuilder)
        val basins = heightmap.getBasins()

        return basins.sortedBy { it.size }.takeLast(3).map { it.size }.reduce { acc, i -> acc * i }
    }
}

data class Heightmap(val map: Map<Pair<Int, Int>, Int>) {
    fun riskLevel(pos: Pair<Int, Int>) = map[pos]?.plus(1) ?: 0

    fun getLowPoints(): List<Pair<Int, Int>> {
        val points = mutableListOf<Pair<Int, Int>>()
        for (entry in map) {
            if (
                entry.value < (map[entry.key.copy(first = entry.key.first + 1)] ?: 10) &&
                entry.value < (map[entry.key.copy(first = entry.key.first - 1)] ?: 10) &&
                entry.value < (map[entry.key.copy(second = entry.key.second + 1)] ?: 10) &&
                entry.value < (map[entry.key.copy(second = entry.key.second - 1)] ?: 10)
            ) points.add(entry.key)
        }
        return points
    }

    fun getBasins(): List<List<Pair<Int, Int>>> {
        val lowPoints = getLowPoints()
        val basins = mutableListOf<List<Pair<Int, Int>>>()
        for (point in lowPoints) {
            val basin = Basin(point)
            basins.add(basin.findBasin(this))
        }
        return basins
    }
}

class Basin(private val startingPoint: Pair<Int, Int>) {
    private val allPoints = mutableListOf<Pair<Int, Int>>()

    fun findBasin(heightmap: Heightmap): List<Pair<Int, Int>> {
        return lookAroundPoint(heightmap, startingPoint)
    }

    private fun lookAroundPoint(heightmap: Heightmap, point: Pair<Int, Int>): List<Pair<Int, Int>> {
        val right = point.copy(first = point.first + 1)
        if ((heightmap.map[right] ?: 10) < 9 && !allPoints.contains(right)) {
            allPoints.add(right)
            lookAroundPoint(heightmap, right)
        }
        val left = point.copy(first = point.first - 1)
        if ((heightmap.map[left] ?: 10) < 9 && !allPoints.contains(left)) {
            allPoints.add(left)
            lookAroundPoint(heightmap, left)
        }
        val up = point.copy(second = point.second + 1)
        if ((heightmap.map[up] ?: 10) < 9 && !allPoints.contains(up)) {
            allPoints.add(up)
            lookAroundPoint(heightmap, up)
        }
        val down = point.copy(second = point.second - 1)
        if ((heightmap.map[down] ?: 10) < 9 && !allPoints.contains(down)) {
            allPoints.add(down)
            lookAroundPoint(heightmap, down)
        }
        return allPoints
    }
}