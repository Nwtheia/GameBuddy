package com.example.gamebuddy

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Preview
@Composable
fun GameBuddyApp() {

    /** Μεταβλητή η οποία θυμάται ποια οθόνη θα εμφανιστεί */
    val navController = rememberNavController()

    /**
     * startDestination = η αρχική οθόνη που θα εμφανιστεί με το άνοιγμα της εφρμογής
     * και οι 3 διαθέσιμες οθόνες της εφαρμογής
     */
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            GameBuddyUI(navController)
        }
        composable("sudoku") {
            SudokuScreen()
        }
        composable("scramble") {
            ScrambleScreen()
        }
    }
}

/**
 * Το layout της αρχικής οθόνης με τον τίτλο
 * και 2 κουμπιά που σε οδηγούν στα παιχνίδια
 */
@Composable
fun GameBuddyUI(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(16.dp))
        Text(
            text = "Game Buddy",
            color = Color.Blue,
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.padding(64.dp))
        GameButtonSection(gameName = "Sudoku", painterResource(id = R.drawable.sudoku)) {
            navController.navigate("sudoku")
        }
        Spacer(modifier = Modifier.padding(64.dp))
        GameButtonSection(gameName = "Scramble", painterResource(id = R.drawable.scramble)) {
            navController.navigate("scramble")
        }
    }
}

/**
 * Το layout των κουμπιών σε βάθος
 * Και η λειτουργία πατήματος
 */
@Composable
fun GameButtonSection(gameName: String, image: Painter, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(150.dp)
                .background(Color(0xFFECECEC), shape = RoundedCornerShape(24.dp))
                .wrapContentSize(Alignment.Center)
                .clip(RoundedCornerShape(24.dp))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(
                        bounded = true,
                        radius = 150.dp,
                        color = Color.DarkGray
                    ),
                    onClick = { onClick() }
                )
        ) {
            Image(
                painter = image,
                contentDescription = "fill"
            )
        }
        Spacer(modifier = Modifier.size(12.dp))
        Text(
            text = gameName,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Blue
        )
    }
}


