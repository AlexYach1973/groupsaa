package com.alexyach.compose.groupsaa.presentation.home.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataSelection(
    datePickerState: DatePickerState,
    onDismissClickListener: (Boolean) -> Unit,
    onConfirmClickListener: () -> Unit
) {

//    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = { onDismissClickListener(false) },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmClickListener()
                    onDismissClickListener(false)
                }
            ) { Text(text = "Ok") }
        },
        dismissButton = {
            TextButton(onClick = { onDismissClickListener(false) }) { Text("Cancel") }
        }

    ) {
        DatePicker(state = datePickerState)
    }
}