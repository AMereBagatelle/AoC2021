package io.github.amerebagatelle.aoc2021

import java.util.*

class Day6 : Puzzle {
    override fun getDay(): Int = 6

    override fun run1(): Number {
        val input = getInput()
        val fishies = mutableListOf<LanternFish>()

        for (age in input[0].split(",").map { it.toInt() }) {
            fishies.add(LanternFish(age))
        }

        for (day in 1..80) {
            for (fishIndex in fishies.indices) {
                val fish = fishies[fishIndex]
                if (fish.timer <= 0) {
                    fish.timer = 6
                    fishies.add(LanternFish(8))
                } else fish.timer--
            }
        }

        return fishies.size
    }

    override fun run2(): Number {
        val input = getInput()
        val fishies = mutableListOf<Long>()
        for (i in 0..8) fishies.add(0)

        for (age in input[0].split(",").map { it.toInt() }) {
            fishies[age] += 1L
        }

        for (day in 1..256) {
            val birthing = fishies[0]
            fishies[0] = 0
            Collections.rotate(fishies, -1)
            fishies[6] += birthing
            fishies[8] += birthing
        }

        return fishies.sum()
    }
}

data class LanternFish(var timer: Int)