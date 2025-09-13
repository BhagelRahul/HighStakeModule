package com.playzelo.highstakesmodule.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//
//@Composable
//fun RollButton(onClick: () -> Unit) {
//    val interactionSource = remember { MutableInteractionSource() }
//    val isPressed by interactionSource.collectIsPressedAsState()
//
//    // Animate background color
//    val backgroundColor by animateColorAsState(
//        targetValue = if (isPressed) Color(0xFF136013) else Color(0xFF00FF00),
//        label = "buttonColor"
//    )
//
//    Button(
//        onClick = onClick,
//         modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 16.dp, vertical = 8.dp)
//            .height(50.dp),
//        colors = ButtonDefaults.buttonColors(
//            containerColor = backgroundColor
//        ),
//        shape = RoundedCornerShape(12.dp),
//        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
//        interactionSource = interactionSource   // needed for pressed state
//    ) {
//        Text(
//            text = "Roll",
//            color = Color.Black,
//            fontWeight = FontWeight.Bold,
//            fontSize = 16.sp
//        )
//    }
//}
//

@Composable
fun RollButton(
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    // ✅ Background color depend karega enabled aur pressed pe
    val backgroundColor by animateColorAsState(
        targetValue = when {
            !enabled -> Color(0xFF2F4F2F) // disabled = dull dark green
            isPressed -> Color(0xFF136013) // pressed = dark green
            else -> Color(0xFF00FF00)      // normal = bright green
        },
        label = "buttonColor"
    )

    Button(
        onClick = onClick,
        enabled = enabled, // 👈 ye control karega blur/disable
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            disabledContainerColor = Color(0xFF2F4F2F), // extra safety
            disabledContentColor = Color.Gray
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
        interactionSource = interactionSource
    ) {
        Text(
            text = "Roll",
            color = if (enabled) Color.Black else Color.Gray, // text bhi dull jab disable
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}
