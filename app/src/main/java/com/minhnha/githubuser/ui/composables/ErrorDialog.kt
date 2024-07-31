package com.minhnha.githubuser.ui.composables

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

@Composable
fun ErrorDialog(error: MutableState<String>, showErrorDialog: MutableState<Boolean>) {
    AlertDialog(
        onDismissRequest = { showErrorDialog.value = false },
        confirmButton = { /*TODO*/ },
        containerColor = Color.White,
        title = {
            Text(
                text = "Error",
                color = Color.Black
            )
        },
        text = {
            Text(
                text = error.value,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }
    )
}