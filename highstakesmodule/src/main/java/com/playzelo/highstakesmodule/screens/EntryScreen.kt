package com.playzelo.highstakesmodule.screens

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun EntryScreen(navController: NavHostController) {
    val context= LocalContext.current

    BackHandler(enabled = true) { }

    Box(
        modifier =
            Modifier
               // .background(Color(0xFF001743))
                .background(Color.White)
                .fillMaxSize()

    ) {

        // Scrollable Rules
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 25.dp, bottom = 40.dp, start = 20.dp)
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f) // take all available height except buttons
            ) {
                item {
                    Text(
                        text = " How to Play  HighStakes Dice", modifier =
                            Modifier.padding(10.dp), color = Color.Black, fontSize = 22.sp
                    )
                }
                item {
                    Text(
                        text = """
            ✅ Game Rules
            • Set your bet (wager amount).
            • Roll 2 dice.
            • The total of the dice decides how far you move on the 12-space board.
            • Multipliers increase your bet winnings.
            • Snakes make you lose.

            🎮 Game Modes
            Easy → 1 snake, 1.01x – 2.00x
            Medium → 3 snakes, 1.11x – 4.00x
            Hard → 5 snakes, 1.38x – 7.50x
            Expert → 7 snakes, 3.82x – 10.00x
            Master → 9 snakes, 17.64x – 18.00x
            👉 More snakes = higher risk but higher rewards.

            💰 Betting Options
            • Auto Bet → game plays automatically.
            • Instant Bet → skips animations for faster play.

            ✅ Terms & Conditions
            • Players must be 18+ years of age to play.  
            • This game is for entertainment purposes only.  
            • Wagering involves real money — play responsibly.  
            • The outcome of every round is determined by RNG (Random Number Generator).  
            • Once a bet is placed, it cannot be canceled or refunded.  
            • Auto Bet and Instant Bet follow the same fairness rules.  
            • Maximum winnings are capped as per platform limits.  
            • By clicking Agree, you accept these rules and confirm you understand the risks.  
            • Stakeholders are not responsible for losses due to player decisions or disconnections.  
            • Please gamble responsibly — set limits and stop when needed.  
            • If you or someone you know has a gambling problem, seek help immediately.
        """.trimIndent(),
                        color = Color.White,
                        fontSize = 16.sp,
                        lineHeight = 22.sp
                    )
                }
            }
            // Buttons Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                Button(
                    onClick = { (navController.context as? Activity)?.finish()  }, // cancel -> go back
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF44336)), // red
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 6.dp)
                ) {
                    Text("Cancel", color = Color.White, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.width(5.dp))

                Button(
                    onClick = { navController.navigate("game") }, // go to game
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)), // green
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 6.dp)
                ) {
                    Text("Agree", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

