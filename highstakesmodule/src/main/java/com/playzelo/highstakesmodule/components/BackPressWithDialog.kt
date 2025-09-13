package com.playzelo.highstakesmodule.components

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

@Composable
fun BackPressWithDialog(
    title: String = "Exit Game",
    message: String = "Are you sure you want to exit?",
    confirmText: String = "Yes",
    dismissText: String = "No",
    onConfirm: (() -> Unit)? = null
) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    // Intercept back press
    BackHandler { showDialog = true }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(title) },
            text = { Text(message) },

            confirmButton = {
                Button(
                    onClick = {
                        showDialog = false
                        if (onConfirm != null) {
                            onConfirm()
                        } else {
                            // Safely finish activity in a module
                            (context as? Activity)?.finish()
                        }
                    },
                    modifier = Modifier,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA31B03))
                ) {
                    Text(confirmText)
                }
            },

            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text(dismissText)
                }
            }
        )
    }
}
