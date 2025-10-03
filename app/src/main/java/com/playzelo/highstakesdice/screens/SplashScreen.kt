package com.playzelo.highstakesdice.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.playzelo.highstakesdice.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF001743))
    )
    {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Logo(modifier = Modifier)
            Spacer(modifier = Modifier.height(24.dp))
            LottieAnimationTwo()
            Spacer(modifier = Modifier.height(40.dp))
            LottieAnimationThree()
        }
    }

    // Optional: navigate to login after delay
    LaunchedEffect(true) {
        delay(5000)
        navController.navigate("entry")
    }
}

@Composable
fun Logo(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.mipmap.stakeslogo),
        contentDescription = "Teen Patti Logo",
        modifier = Modifier
            .size(200.dp)
            .clip(RoundedCornerShape(16.dp))

    )
 }

@Composable
fun LottieAnimationTwo(speed: Float = 2f, modifier: Modifier = Modifier) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.dice))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        speed = speed
    )
    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = Modifier.size(250.dp),

        )
}

@Composable
fun LottieAnimationThree(modifier: Modifier = Modifier) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.stacksloading))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever
    )
    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = Modifier.size(190.dp)
    )
}


