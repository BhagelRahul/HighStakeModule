package com.playzelo.highstakesdice.components


import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.LineHeightStyle
import com.playzelo.highstakesmodule.StakesMainActivity

@Composable
fun Module() {
    val context = LocalContext.current

    Box(modifier= Modifier, contentAlignment = Alignment.Center) {
        Button(onClick = {
            // Launch the module activity
            val intent = Intent(context, StakesMainActivity::class.java)
            context.startActivity(intent)
        }) {
            Text("Open High Stakes Module")
        }
    }
}

