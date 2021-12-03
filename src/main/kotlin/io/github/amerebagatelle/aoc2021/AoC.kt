package io.github.amerebagatelle.aoc2021

import java.io.File
import java.time.LocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

interface Puzzle {
    fun getDay(): Int
    fun getInput() = getInputForDay(getDay())
    fun run1(): Number
    fun run2(): Number
}

fun getInputForDay(day: Int): List<String> {
    return File("day$day").readLines()
}

val puzzleMap = mutableMapOf<Int, Puzzle>()

fun register(puzzle: Puzzle) {
    puzzleMap[puzzle.getDay()] = puzzle
}

fun registerAll() {
    register(Day1())
    register(Day2())
}

@Suppress("EXPERIMENTAL_IS_NOT_ENABLED")
@OptIn(ExperimentalTime::class)
fun main() {
    registerAll()

    val day = LocalDateTime.now().dayOfMonth
    val puzzle = puzzleMap[day] ?: error("No puzzle for today.  You forgot to register it you dummy")
    println("Running both parts of puzzle $day")

    val time1 = measureTime {
        println("Part one answer: ${puzzle.run1()}")
    }

    println("Part one finished in ${time1.inWholeMilliseconds}ms")

    val time2 = measureTime {
        println("Part two answer: ${puzzle.run2()}")
    }

    println("Part two finished in ${time2.inWholeMilliseconds}ms")
}