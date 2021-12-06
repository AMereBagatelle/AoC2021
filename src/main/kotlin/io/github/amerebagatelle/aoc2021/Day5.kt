package io.github.amerebagatelle.aoc2021

import kotlin.math.abs

class Day5: Puzzle {
    override fun getDay(): Int = 5

    override fun run1(): Number {
        val input = getInput()

        val lines = mutableListOf<Line>()
        for(line in input) {
            val points = line.split(" -> ").map { it.split(",").map { value -> value.toInt() } }.map { Pair(it[0], it[1]) }
            if(points[0].first != points[1].first && points[0].second != points[1].second) continue
            lines.add(Line(points[0], points[1]))
        }

        val allPoints = mutableMapOf<Pair<Int, Int>, Int>()

        for (line in lines) {
            val points = line.getPoints()
            for (point in points) {
                if(allPoints.contains(point)) {
                    allPoints[point] = allPoints[point]!! + 1
                } else {
                    allPoints[point] = 1
                }
            }
        }

        return allPoints.filterValues { it >= 2 }.size
    }

    override fun run2(): Number {
        val input = getInput()

        val lines = mutableListOf<Line>()
        for(line in input) {
            val points = line.split(" -> ").map { it.split(",").map { value -> value.toInt() } }.map { Pair(it[0], it[1]) }
            lines.add(Line(points[0], points[1]))
        }

        val allPoints = mutableMapOf<Pair<Int, Int>, Int>()

        for (line in lines) {
            val points = line.getPoints()
            for (point in points) {
                if(allPoints.contains(point)) {
                    allPoints[point] = allPoints[point]!! + 1
                } else {
                    allPoints[point] = 1
                }
            }
        }

        return allPoints.filterValues { it >= 2 }.size
    }
}

class Line(private val start: Pair<Int, Int>, private val end: Pair<Int, Int>) {
    fun isVertical() = start.first == end.first
    fun isDiagonal() = start.first != end.first && start.second != end.second
    fun isHorizontal() = start.second == end.second

    fun getPoints(): List<Pair<Int, Int>> {
        val points = mutableListOf<Pair<Int, Int>>()
        if(isVertical()) {
            if(start.second < end.second) {
                for (y in start.second..end.second) {
                    points.add(Pair(start.first, y))
                }
            } else {
                for (y in end.second..start.second) {
                    points.add(Pair(start.first, y))
                }
            }
        } else if(isHorizontal()) {
            if(start.first < end.first) {
                for (x in start.first..end.first) {
                    points.add(Pair(x, start.second))
                }
            } else {
                for (x in end.first..start.first) {
                    points.add(Pair(x, start.second))
                }
            }
        } else if(isDiagonal()) {
            val xRange = if(start.first < end.first) start.first..end.first else end.first..start.first
            val yRange = if(start.second < end.second) start.second..end.second else end.second..start.second
            for (x in xRange) {
                for(y in yRange) {
                    if(abs(start.first-x) == abs(start.second-y) && distance(start, Pair(x, y)) + distance(Pair(x, y), end) == distance(start, end)) points.add(Pair(x, y))
                }
            }
        }
        return points
    }

    private fun distance(first: Pair<Int, Int>, second: Pair<Int, Int>): Int {
        return (first.first - second.first) + (first.second - second.second)
    }
}