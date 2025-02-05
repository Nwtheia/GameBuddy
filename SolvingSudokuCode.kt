package com.example.gamebuddy

fun solveSudoku(grid: Array<Array<Int?>>) {

    /** Όλη η λογική επίλυσης του sudoku μόλις πατηθεί το κουμπί solve */
    val n = 9

    fun isSafe(nonNullableGrid: Array<IntArray>, row: Int, col: Int, num: Int): Boolean {
        for (x in 0 until 9) {
            if (nonNullableGrid[row][x] == num) {
                return false
            }
        }

        for (x in 0 until 9) {
            if (nonNullableGrid[x][col] == num) {
                return false
            }
        }

        val startRow = row - row % 3
        val startCol = col - col % 3
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                if (nonNullableGrid[i + startRow][j + startCol] == num) {
                    return false
                }
            }
        }
        return true
    }

    fun solveSudoku(nonNullableGrid: Array<IntArray>, row: Int, col: Int): Boolean {
        if (row == n - 1 && col == n) {
            return true
        }

        if (col == n) {
            return solveSudoku(nonNullableGrid, row + 1, 0)
        }

        if (nonNullableGrid[row][col] > 0) {
            return solveSudoku(nonNullableGrid, row, col + 1)
        }

        for (num in 1..n) {
            if (isSafe(nonNullableGrid, row, col, num)) {
                nonNullableGrid[row][col] = num
                if (solveSudoku(nonNullableGrid, row, col + 1)) {
                    return true
                }
            }
            nonNullableGrid[row][col] = 0
        }
        return false
    }

    val nonNullableGrid = Array(9) { row ->
        IntArray(9) { col -> grid[row][col] ?: 0 } // Convert null to 0 for solving
    }

    if (solveSudoku(nonNullableGrid, 0, 0)) {
        for (row in 0 until n) {
            for (col in 0 until n) {
                grid[row][col] = if (nonNullableGrid[row][col] == 0) null else nonNullableGrid[row][col]
            }
        }
    } else {
        println("no solution exists")
    }
}