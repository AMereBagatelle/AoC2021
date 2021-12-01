package io.github.amerebagatelle.aoc2021

class Day1: Puzzle {
    override fun getDay(): Int = 1

    override fun run1(): Number {
        val input = getInput().map { it.toInt() }
        var result = 0
        for (i in input.indices) {
            if(i > 0) {
                result += if (input[i] > input[i-1]) 1 else 0
            }
        }
        return result
    }

    override fun run2(): Number {
        val input = getInput().map { it.toInt() }
        var result = 0
        for (i in 0..input.indices.last-2) {
            if(i > 0) {
                if(input[i] + input[i+1] + input[i+2] > input[i-1] + input[i] + input[i+1]) result++
            }
        }
        return result
    }
}