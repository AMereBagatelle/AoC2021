package io.github.amerebagatelle.aoc2021

import kotlin.math.abs

class Day7 : Puzzle {
    override fun getDay(): Int = 7

    override fun run1(): Number {
        val input = getInput()

        val crabPositions = input[0].split(",").map { it.toInt() }
        val fuelAmounts = mutableListOf<Int>()

        for (pos in crabPositions) {
            val fuel = mutableListOf<Int>()
            for (crab in crabPositions) {
                fuel.add(abs(pos - crab))
            }
            fuelAmounts.add(fuel.sum())
        }

        return fuelAmounts.minOrNull() ?: error("uhhh help")
    }

    override fun run2(): Number {
        val input = getInput()

        val crabPositions = input[0].split(",").map { it.toInt() }
        val fuelAmounts = mutableListOf<Int>()
        for (pos in crabPositions.minOrNull()!!..crabPositions.maxOrNull()!!) {
            var fuel = 0
            for (crab in crabPositions) {
                for (it in 1..abs(pos - crab)) {
                    fuel += it
                }
            }
            fuelAmounts.add(fuel)
        }

        return fuelAmounts.minOrNull() ?: error("uhhh help")
    }
}