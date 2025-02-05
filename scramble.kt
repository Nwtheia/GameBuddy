package com.example.gamebuddy

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.text.TextStyle

@Preview
@Composable
fun ScrambleScreen() {
    var input by remember { mutableStateOf("") }
    var results by remember { mutableStateOf(emptyList<String>()) }

    val context = LocalContext.current

    /** Το layout της οθόνης scramble */
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(16.dp))
        Text(
            text = "Scramble",
            color = Color.Blue,
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.padding(30.dp))
        Box(
            modifier = Modifier
                .weight(0.7f)
                .fillMaxWidth()
        ) {
            Text(
                text = "Results:",
                color = Color.Blue,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.TopCenter)
            )
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(results) { word ->
                    Spacer(modifier = Modifier.padding(20.dp))
                    Text(
                        text = word,
                        fontSize = 24.sp,
                        color = Color.Blue,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth())
                }
            }
        }
        OutlinedTextField(
            value = input,
            onValueChange = { input = it },
            singleLine = true,
            textStyle = TextStyle(
                fontSize = 20.sp
            ),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                cursorColor = Color.Blue
            ),
            placeholder = { Text("Type your letters",
                color = Color.Black,
                style = TextStyle(fontSize = 20.sp, textAlign = TextAlign.Center),
                modifier = Modifier.fillMaxWidth()
            )},
            modifier = Modifier
                .border(2.dp, Color.Blue, RoundedCornerShape(16.dp))
                .width(250.dp)
        )
        Button(
            onClick = {
                if (input.trim().isNotEmpty()) {
                    results = solveWordScramble(context, input.trim())
                }},
            modifier = Modifier.padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue,
                contentColor = Color.White
            )
        ) {
            Text("Solve")
        }
        Spacer(modifier = Modifier.padding(160.dp))

    }
}

/** η λογική επίλυσης του scramble μόλις πατηθεί το κουμπί solve */
fun solveWordScramble(context: Context, input: String): List<String> {
    val dictionary = loadDictionary(context) // Load words from a file
    val permutations = generatePermutations(input)

    return permutations
        .filter { it in dictionary }
        .sortedByDescending { it.length }
        .take(2)
}

// Function to load dictionary from resources
fun loadDictionary(context: Context): Set<String> {
    val file1InputStream = context.resources.openRawResource(R.raw.words1)
    val file2InputStream = context.resources.openRawResource(R.raw.words2)

    val words1 = file1InputStream.bufferedReader().readLines()
    val words2 = file2InputStream.bufferedReader().readLines()

    return (words1 + words2).toSet() // Merge and remove duplicates
}

// Function to generate permutations of input letters
fun generatePermutations(input: String, maxResults: Int = 10000): Set<String> {
    if (input.isEmpty()) return emptySet()
    val results = mutableSetOf<String>()

    fun permute(prefix: String, remaining: String) {
        if (prefix.isNotEmpty()) results.add(prefix)
        if (results.size >= maxResults) return

        val usedChars = mutableSetOf<Char>()
        for (i in remaining.indices) {
            val char = remaining[i]
            if (char in usedChars) continue
            usedChars.add(char)

            permute(prefix + remaining[i], remaining.substring(0, i) + remaining.substring(i + 1))
        }
    }

    permute("", input)
    return results
}


