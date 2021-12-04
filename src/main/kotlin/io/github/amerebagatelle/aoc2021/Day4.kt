package io.github.amerebagatelle.aoc2021

class Day4: Puzzle {
    override fun getDay(): Int = 4

    override fun run1(): Number {
        val input = getInput()
        val calledList = input[0].split(",").map { it.toInt() }
        val boards = createBoards(input)

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
        val boards = createBoards(input)

        var winningCall = 0
        var chosenBoard: BingoBoard? = null

        for(call in calledList) {
            val remainingBoards = boards.filter { !it.check() }
            val isLast = remainingBoards.size == 1
            for (board in remainingBoards) {
                board.markNumber(call)
                if(isLast && board.check()) {
                    winningCall = call
                    chosenBoard = board
                }
            }
            if(chosenBoard != null) break
        }

        return chosenBoard!!.getUnmarked().sum() * winningCall
    }

    private fun createBoards(input: List<String>): List<BingoBoard> {
        val boards = mutableListOf<BingoBoard>()
        for (boardPos in 2..input.lastIndex step 6) {
            val board = input.subList(boardPos, boardPos+5).map { it.split(" ").filter { value -> value.isNotBlank() }.map { value -> value.toInt() } }
            boards.add(BingoBoard.fromList(board))
        }
        return boards
    }
}

class BingoBoard(private val board: MutableMap<Pair<Int, Int>, Pair<Int, Boolean>>) {
    companion object {
        fun fromList(list: List<List<Int>>): BingoBoard {
            val bingoBoardMap = mutableMapOf<Pair<Int, Int>, Pair<Int, Boolean>>()
            for (x in list.indices) {
                for (y in list[x].indices) {
                    bingoBoardMap[Pair(x, y)] = Pair(list[x][y], false)
                }
            }
            return BingoBoard(bingoBoardMap)
        }
    }

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

    fun getUnmarked(): List<Int> = board.filterValues { !it.second }.map { it.value.first }
}