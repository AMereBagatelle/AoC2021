package io.github.amerebagatelle.aoc2021

class Day8 : Puzzle {
    override fun getDay(): Int = 8

    override fun run1(): Number {
        val input = getInput()

        var result = 0
        for (line in input) {
            val outputValues = line.split(" | ")[1].split(" ")
            for (value in outputValues) {
                when (value.length) {
                    2, 3, 4, 7 -> result++
                }
            }
        }

        return result
    }

    override fun run2(): Number {
        val input = getInput()

        var result = 0
        for (line in input) {
            val lineParts = line.split(" | ")
            val first = lineParts[0].split(" ")
            val second = lineParts[1].split(" ")
            val display = Display()
            display.findSignals(first as MutableList<String>)

            val lineResult = StringBuilder()
            for (piece in second) {
                lineResult.append(display.getNumberFor(piece))
            }
            result += lineResult.toString().toInt()
        }

        return result
    }
}

val displaySegmentsToInt = mapOf(
    intArrayOf(0, 1, 2, 4, 5, 6) to 0,
    intArrayOf(2, 4) to 1,
    intArrayOf(0, 2, 3, 4, 6) to 2,
    intArrayOf(0, 2, 3, 5, 6) to 3,
    intArrayOf(1, 2, 3, 5) to 4,
    intArrayOf(0, 1, 3, 5, 6) to 5,
    intArrayOf(0, 1, 3, 4, 5, 6) to 6,
    intArrayOf(0, 2, 5) to 7,
    intArrayOf(0, 1, 2, 3, 4, 5, 6) to 8
)

class Display {
    private val signalMap = mutableMapOf<Int, Char>()

    fun findSignals(input: MutableList<String>) {
        signalMap[0] = input.filter { it.length == 3 }[0].replace(input.filter { it.length == 2 }[0], "").toCharArray()[0]
        for (letter in 'a'..'f') {
            if (signalMap[0] == letter) continue
            println(input.filter { !it.contains(letter) })
            if (input.filter { !it.contains(letter) }.size == 3) signalMap[1] = letter
            if (input.filter { !it.contains(letter) && (it.length == 6 || it.length == 5) }.size == 2) signalMap[2] = letter
            if (input.filter { !it.contains(letter) && it.length != 2 && it.length != 3 }.size == 1) signalMap[3] = letter
            if (input.filter { !it.contains(letter) && (it.length != 8) }.size == 3) signalMap[4] = letter
            if (input.filter { !it.contains(letter) && (it.length == 5) }.size == 1) signalMap[5] = letter
            if (input.filter { !it.contains(letter) && (it.length == 2 || it.length == 3 || it.length == 4) }.size == 3) signalMap[6] = letter
        }
    }

    fun getNumberFor(input: String): Int {
        val ints = mutableListOf<Int>()
        for (i in input) {
            signalMap.entries.filter { it.value == i }[0].key.let { ints.add(it) }
        }
        return displaySegmentsToInt[ints.toIntArray()]!!
    }
}