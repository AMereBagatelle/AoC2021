package io.github.amerebagatelle.aoc2021

class Day2: Puzzle {
    override fun getDay(): Int = 2

    override fun run1(): Number {
        val input = getInput()
        var depth = 0
        var horizontal = 0

        for (line in input) {
            with(line) {
                when {
                    startsWith("down") -> depth += line.last().digitToInt()
                    startsWith("up") -> depth -= line.last().digitToInt()
                    startsWith("forward") -> horizontal += line.last().digitToInt()
                }
            }
        }

        return depth * horizontal
    }

    override fun run2(): Number {
        val input = getInput()
        var depth = 0
        var aim = 0
        var horizontal = 0

        for (line in input) {
            with(line) {
                when {
                    startsWith("down") -> aim += line.last().digitToInt()
                    startsWith("up") -> aim -= line.last().digitToInt()
                    startsWith("forward") -> {
                        val magnitude = line.last().digitToInt()
                        horizontal += magnitude
                        depth += magnitude * aim
                    }
                }
            }
        }

        return depth * horizontal
    }
}