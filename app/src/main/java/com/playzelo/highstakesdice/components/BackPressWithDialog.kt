package com.playzelo.highstakesdice.components


import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun BackPressWithDialog(
    title: String = "Exit Game",
    message: String = "Are you sure you want to exit?",
    confirmText: String = "Yes",
    dismissText: String = "No",
    onConfirm: (() -> Unit)? = null
) {
    val activity = LocalActivity.current
    var showDialog by remember { mutableStateOf(false) }

    //intercept back press

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
                        onConfirm?.invoke() ?: activity?.finish()
                    },
                    modifier = Modifier,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA31B03))
                ) {
                    Text(confirmText)
                }

            },
            dismissButton = {
                Button(onClick = { showDialog = false })
                {
                    Text(dismissText)
                }
            }
        )
    }
}





