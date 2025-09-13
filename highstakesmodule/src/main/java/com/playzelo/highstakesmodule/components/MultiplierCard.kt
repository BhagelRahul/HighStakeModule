package com.playzelo.highstakesmodule.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun MultiplierCard(value: String, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(70.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF2F4553), shape = RoundedCornerShape(10.dp))
            .shadow(6.dp, RoundedCornerShape(16.dp))
    ) {
        Box(
            modifier = Modifier
                .background(
                    Color(0xFF2F4553),
                    RoundedCornerShape(8.dp)
                ) // Text ke peeche background
                .padding(horizontal = 8.dp, vertical = 10.dp)
        ) {
            Text(
                text = value,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
    }
}
