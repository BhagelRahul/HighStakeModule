package com.playzelo.highstakesdice.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun StartCard(value: String, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(70.dp)
            //.clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF2F4553), shape = RoundedCornerShape(10.dp))

    ) {
        Box(
            modifier = Modifier
                .background(
                    Color(0xFF2E3D48),
                    RoundedCornerShape(8.dp)
                ) // Text ke peeche background
                .padding(horizontal = 8.dp, vertical = 8.dp)
        ) {
            Text(
                text = " ▶ ",
                color = Color(0xFF4BD3FA),
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp
            )
        }
    }
}

