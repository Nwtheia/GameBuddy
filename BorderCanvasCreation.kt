package com.example.gamebuddy

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun SudokuBorder() {

        /** Ο σχεδιασμός του sudoku με canva */
        Canvas(
            modifier = Modifier
                .size(360.dp)
        ) {
            val step = size.width / 9
            val boldStroke = 7f
            val thinStroke = 2f

            for (i in 0..9) {
                val strokeWidth = if (i % 3 == 0) boldStroke else thinStroke
                drawLine(
                    color = Color.Black,
                    start = Offset(x = i * step, y = 0f),
                    end = Offset(x = i * step, y = size.height),
                    strokeWidth = strokeWidth
                )
                drawLine(
                    color = Color.Black,
                    start = Offset(x = 0f, y = i * step),
                    end = Offset(x = size.width, y = i * step),
                    strokeWidth = strokeWidth
                )
            }
        }
}