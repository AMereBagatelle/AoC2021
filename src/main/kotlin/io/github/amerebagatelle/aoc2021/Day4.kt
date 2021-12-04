package io.github.amerebagatelle.aoc2021

class Day4: Puzzle {
    override fun getDay(): Int = 4

    override fun run1(): Number {
        val input = getInput()
        val calledList = input[0].split(",").map { it.toInt() }

        val boards = mutableListOf<BingoBoard>()
        // create boards
        for (boardPos in 2..input.lastIndex step 6) {
            val board = input.subList(boardPos, boardPos+5).map { it.split(" ").filter { value -> !value.isBlank() } }
            val bingoBoard = mutableMapOf<Pair<Int, Int>, Pair<Int, Boolean>>()
            for (x in board.indices) {
                for (y in board[x].indices) {
                    bingoBoard[Pair(x, y)] = Pair(board[x][y].toInt(), false)
                }
            }
            boards.add(BingoBoard(bingoBoard))
        }

        var chosenBoard: BingoBoard? = null
        var winningCall = 0

        for(call in calledList) {
            for (board in boards) {
                board.markNumber(call)
                if(board.check()) {
                    chosenBoard = board
                    winningCall = call
                }
            }
            if(chosenBoard != null) break
        }

        return chosenBoard!!.getUnmarked().sum() * winningCall
    }

    override fun run2(): Number {
        val input = getInput()
        val calledList = input[0].split(",").map { it.toInt() }

        val boards = mutableListOf<BingoBoard>()
        // create boards
        for (boardPos in 2..input.lastIndex step 6) {
            val board = input.subList(boardPos, boardPos+5).map { it.split(" ").filter { value -> !value.isBlank() } }
            val bingoBoard = mutableMapOf<Pair<Int, Int>, Pair<Int, Boolean>>()
            for (x in board.indices) {
                for (y in board[x].indices) {
                    bingoBoard[Pair(x, y)] = Pair(board[x][y].toInt(), false)
                }
            }
            boards.add(BingoBoard(bingoBoard))
        }

        var winningCall = 0
        var chosenBoard: BingoBoard? = null

        for(call in calledList) {
            val isLast = boards.filter { !it.check() }.size == 1
            for (board in boards.filter { !it.check() }) {
                board.markNumber(call)
                if(isLast && board.check()) {
                    winningCall = call
                    chosenBoard = board
                }
            }
            if(chosenBoard != null) break
        }
        println(chosenBoard!!.getUnmarked())
        return chosenBoard!!.getUnmarked().sum() * winningCall
    }
}

data class BingoBoard(val board: MutableMap<Pair<Int, Int>, Pair<Int, Boolean>>) {
    fun check(): Boolean {
        for (x in 0..4) {
            if(board.filterKeys { it.first == x }.values.all { it.second }) {
                return true
            }
        }

        for (y in 0..4) {
            if(board.filterKeys { it.second == y }.values.all { it.second }) {
                return true
            }
        }

        return false
    }

    fun markNumber(number: Int) {
        for (entry in board) {
            if(entry.value.first == number) {
                board[entry.key] = Pair(entry.value.first, true)
            }
        }
    }

    fun getUnmarked(): List<Int> {
        return board.filterValues { !it.second }.map { it.value.first }
    }
}