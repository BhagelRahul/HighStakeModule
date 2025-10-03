package com.playzelo.highstakesdice.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.playzelo.highstakesdice.screens.FireLottie
import com.playzelo.highstakesdice.screens.Token1
import com.playzelo.highstakesdice.screens.Token2
import com.playzelo.highstakesdice.screens.Token3
import com.playzelo.highstakesdice.screens.Win

@Composable
fun CardWithToken(
    index: Int,
    tokenIndex: Int,
    isFinalStop: Boolean,
    riskyCards: List<Int> = listOf(5, 6, 7, 9),
    content: @Composable () -> Unit
) {

    val content= LocalContext.current
    val soundManager = remember { SoundManager(content) }

    val isRiskyStop = isFinalStop && index == tokenIndex && index in riskyCards
    val isSafeStop = isFinalStop && index == tokenIndex && index !in riskyCards
    val isMoving = index == tokenIndex && !isFinalStop

    val bgColor = when {
        isRiskyStop -> Color.Red.copy(alpha = 0.3f)
        isSafeStop -> Color(0xFF00BFFF).copy(alpha = 0.3f)
        isMoving -> Color(0xFF00BFFF).copy(alpha = 0.2f)
        else -> Color.Transparent
    }

    val shadowColor = when {
        isRiskyStop -> Color.Red
        isSafeStop -> Color(0xFF00BFFF)
        isMoving -> Color(0xFF00BFFF)
        else -> Color.Transparent
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .shadow(
                elevation = if (shadowColor != Color.Transparent) 12.dp else 0.dp,
                spotColor = shadowColor,
                shape = RoundedCornerShape(12.dp)
            )
            .background(bgColor, RoundedCornerShape(12.dp))
    ) {
        // ✅ Preserve card layout exactly
        Box {
            content()
        }

        // Token or fire overlay
        if (index == tokenIndex) {
            when {
                isRiskyStop -> {
                    FireLottie(speed = 2f)
                    Token3()
                    LaunchedEffect(tokenIndex) {
                        soundManager.DangerSound(1f)
                    }
                }

                isSafeStop ->
                {
                    Win(speed = 2f)
                    Token2()
                    LaunchedEffect(tokenIndex) {
                        soundManager.WinSound(1f)
                    }
                }
                isMoving -> Token1()
            }
        }
    }
}

