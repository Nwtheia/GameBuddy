package com.example.gamebuddy

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight

/** Μεταβλητή που κρατάει τις τιμές του sudoku οι αρχικές τιμές είναι null */
val grid = Array(9) { arrayOfNulls<Int>(9) }

@Preview
@Composable
fun SudokuScreen() {

    /** Μεταβλητές που κρατάνε τα κελιά που έχω επιλέξει */
    var selectedCell by remember { mutableStateOf<Pair<Int, Int>?>(null) }

    val sudokuBoard = remember {
        List(9) { row ->
            List(9) { col ->
                mutableStateOf(grid[row][col]?.toString() ?: "")
            }
        }
    }

    /**
     * Το layout της οθόνης sudoku
     * Αποτελείται απο το Text, ένα Box το οποίο έχει τον canva αλλα και την λογική του κάθε κελιού
     * και 2 σειρές απο κουμπιά η μία με τα νούμερα και η άλλη με τις εντολές
     */
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sudoku",
            color = Color.Blue,
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(64.dp))
        Box() {
            SudokuBorder()
            Column {
                for (col in 0..8) {
                    Row {
                        for (row in 0..8) {
                            val cellValue = sudokuBoard[row][col]
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .border(
                                        1.dp,
                                        if (selectedCell == Pair(
                                                row,
                                                col
                                            )
                                        ) Color.Blue else Color.Transparent
                                    )
                                    .clickable {
                                        selectedCell = Pair(row, col)
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                BasicTextField(
                                    value = cellValue.value,
                                    onValueChange = { newValue ->
                                        if (newValue.length <= 1 && newValue.all { it.isDigit() }) {
                                            sudokuBoard[row][col].value = newValue
                                            grid[row][col] = newValue.toIntOrNull()
                                        }
                                    },
                                    textStyle = TextStyle(
                                        color = Color.Black,
                                        textAlign = TextAlign.Center,
                                        fontSize = (20.sp)
                                    ),
                                    maxLines = 1,
                                    enabled = false
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        // Buttons
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            for (number in 1..9) {
                Button(
                    onClick = {
                        selectedCell?.let { (row, col) ->
                            sudokuBoard[row][col].value = number.toString()
                            grid[row][col] = number
                        }
                    },
                    modifier = Modifier.size(40.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Blue,
                        contentColor = Color.White
                    ),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(text = number.toString(), fontSize = 18.sp)
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Solve Button
            Button(
                onClick = {
                    solveSudoku(grid)
                    for (row in 0..8) {
                        for (col in 0..8) {
                            sudokuBoard[row][col].value = grid[row][col]?.toString() ?: ""
                        }
                    }
                },
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Solve", fontSize = 16.sp)
            }
            Button(
                onClick = {
                    selectedCell?.let { (row, col) ->
                        sudokuBoard[row][col].value = ""
                        grid[row][col] = null
                    }
                },
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Delete", fontSize = 16.sp)
            }
            Button(
                onClick = {
                    for (row in 0..8) {
                        for (col in 0..8) {
                            sudokuBoard[row][col].value = ""
                            grid[row][col] = null
                        }
                    }
                },
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White
                ),
            ) {
                Text(text = "Clear All", fontSize = 16.sp)
            }
        }
    }
}