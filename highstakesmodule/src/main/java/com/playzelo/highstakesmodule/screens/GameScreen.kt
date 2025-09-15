package com.playzelo.highstakesmodule.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.playzelo.highstakesmodule.GameDiceModel
import com.playzelo.highstakesmodule.R
import com.playzelo.highstakesmodule.components.BackPressWithDialog
import com.playzelo.highstakesmodule.components.BetButton
import com.playzelo.highstakesmodule.components.CardWithToken
import com.playzelo.highstakesmodule.components.DiceCard
import com.playzelo.highstakesmodule.components.MultiplierCard
import com.playzelo.highstakesmodule.components.RiskyCard
import com.playzelo.highstakesmodule.components.RollButton
import com.playzelo.highstakesmodule.components.SoundManager
import com.playzelo.highstakesmodule.components.StartCard
import java.util.Locale


@Composable
fun GameScreen(
    navController: NavController,
    viewModel: GameDiceModel = viewModel()
) {

    BackPressWithDialog(
        title = "Exit Game?",
        message = "Do you really want to quit the game?",
        confirmText = "Exit",
        dismissText = "Cancel"
    )

    // sound
    val context = LocalContext.current
    val soundManager = remember { SoundManager(context) }

    // -------- ViewModel states observe karo --------
    val diceV1 by viewModel.diceV1.collectAsState()
    val diceV2 by viewModel.diceV2.collectAsState()
    val tokenIndex by viewModel.tokenIndex.collectAsState()
    val isFinalStop by viewModel.isFinalStop.collectAsState()
    val betAmount by viewModel.betAmount.collectAsState()
    val inputText by viewModel.inputText.collectAsState()

    val isRollEnabled by viewModel.isRollEnabled.collectAsState()

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(Color(0xFF0F212E))
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // ===== Header =====

        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
//            Icon(
//                imageVector = Icons.Filled.Search,
//                contentDescription = null,
//                tint = Color.White,
//                modifier = Modifier.size(18.dp)
//            )
            Image(
                painter = painterResource(id = R.mipmap.stakeslogo),
                contentDescription = "",
                modifier = Modifier.size(50.dp)
            )
            Text(
                text = "HighStakes Dice",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = FontFamily.Cursive,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color.White
                )
            )
//            Icon(
//                imageVector = Icons.Filled.Notifications,
//                contentDescription = null,
//                tint = Color.White,
//                modifier = Modifier.size(18.dp)
//            )
        }

        // ===== TOP ROW =====

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            CardWithToken(0, tokenIndex, isFinalStop) { StartCard(value = "") }
            CardWithToken(1, tokenIndex, isFinalStop) { MultiplierCard("0.34") }
            CardWithToken(2, tokenIndex, isFinalStop) { MultiplierCard("0.55") }
            CardWithToken(3, tokenIndex, isFinalStop) { MultiplierCard("1.66") }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // ===== MIDDLE ROW =====

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            // Left Column
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                CardWithToken(4, tokenIndex, isFinalStop) { MultiplierCard("1.50x") }
                CardWithToken(5, tokenIndex, isFinalStop) { RiskyCard(" ") }
            }

            // Center Dice
            DiceCard(
                diceV1,
                diceV2,
                value = "₹${String.format("%.2f", betAmount)}"
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Right Column
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                CardWithToken(7, tokenIndex, isFinalStop) { RiskyCard(value = "2.09") }
                CardWithToken(11, tokenIndex, isFinalStop) { MultiplierCard("2.40x") }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // ===== BOTTOM ROW =====

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            CardWithToken(8, tokenIndex, isFinalStop) { MultiplierCard("1.44") }
            CardWithToken(9, tokenIndex, isFinalStop) { RiskyCard(" ") }
            CardWithToken(10, tokenIndex, isFinalStop) { MultiplierCard("4.00x") }
            CardWithToken(6, tokenIndex, isFinalStop) { RiskyCard(" ") }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // ===== Betting Section =====
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF2F4553), RoundedCornerShape(8.dp))
        ) {
            // Bet Amount Display
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Bet Amount",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "₹ ${String.format(Locale.getDefault(), "%.2f", betAmount)}",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Bet Input
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = inputText,
                    onValueChange = { viewModel.onInputChange(it) },
                    label = { Text("Bet Amount") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFF1E3A44),
                        unfocusedContainerColor = Color(0xFF182C31),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        cursorColor = Color(0xFF234F48)
                    )
                )

                Text(text = "1/2", color = Color.White, modifier = Modifier.padding(end = 4.dp))
                Text(text = "|", color = Color.Gray, modifier = Modifier.padding(end = 4.dp))
                Text(text = "1/5", color = Color.White)
            }

            // Buttons
            // Buttons
            BetButton(onClick = { viewModel.placeBet() })

            RollButton(
                onClick = { viewModel.rollDiceFromApi("RahulApp",soundManager) },
                enabled = isRollEnabled,   //  Bet lagne ke baad hi enable

            )

            // BetButton(onClick = { viewModel.placeBet() })
            // RollButton(onClick = { viewModel.rollDice() })

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

///composables

@Composable
fun Token1() {
    Box(
        modifier = Modifier
            .padding(5.dp)
            .size(60.dp)
            .clip(RoundedCornerShape(10.dp))
            .border(4.dp, Color(0xFF00BFFF), RoundedCornerShape(10.dp)), // ✅ sirf border
        contentAlignment = Alignment.Center
    ) {}
}

@Composable
fun Token2() {
    Box(
        modifier = Modifier
            .padding(5.dp)
            .size(60.dp)
            .clip(RoundedCornerShape(10.dp))
            .border(4.dp, Color(0xFF16F122), RoundedCornerShape(10.dp)), // ✅ sirf border
        contentAlignment = Alignment.Center
    ) {}
}

@Composable
fun Token3() {
    Box(
        modifier = Modifier
            .padding(5.dp)
            .size(62.dp)
            .clip(RoundedCornerShape(10.dp))
            .border(4.dp, Color(0xFF9A0014), RoundedCornerShape(10.dp)), // ✅ sirf border
        contentAlignment = Alignment.Center
    ) {}
}


@Composable
fun FireLottie(speed: Float = 2f) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.fire))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = 1,
        speed = speed
    )
    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = Modifier
            .size(65.dp)
            .padding(bottom = 3.dp),

        )
}

@Composable
fun Win(speed: Float = 2f) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.celebrations))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = 1,
        speed = speed
    )
    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = Modifier
            .size(70.dp)
        // .padding(bottom = 3.dp),

    )
}


