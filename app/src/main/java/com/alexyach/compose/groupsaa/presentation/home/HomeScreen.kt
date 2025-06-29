package com.alexyach.compose.groupsaa.presentation.home

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alexyach.compose.groupsaa.R


@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    onReadClickListener: () -> Unit
) {

    val conntext = LocalContext.current
    val viewModel: HomeViewModel = viewModel()

    val scrollState = rememberScrollState()

    val difference = viewModel.difference.collectAsState(listOf<Int>())
    val selectedDate = viewModel.selectedDate.collectAsState("")

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(paddingValues)
            .verticalScroll(scrollState)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondaryContainer)
    ) {


        PeriodOfSobrietyCard(
            context = conntext,
            viewModel = viewModel,
            difference = difference.value,
            selectedDate = selectedDate.value
        )


    }

}


// period of sobriety

@Composable
private fun PeriodOfSobrietyCard(
    context: Context,
    viewModel: HomeViewModel,
    difference: List<Int>,
    selectedDate: String
) {

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .border(
                width = 2.dp,
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.onSecondary
            ),

        colors = CardDefaults.cardColors(
            MaterialTheme.colorScheme.secondaryContainer
        )
    ) {

        var openDialog by remember { mutableStateOf(false) }
        if (openDialog) {
            DataSelection(
                viewModel = viewModel,
//                    openDialog = openDialog,
                onDismissClickListener = { openDialog = it }
            )
        }


        Column(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)

        ) {
            /* 1 Line */
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = context.getString(R.string.homescreen_sobriety_card_title),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Normal
//                        fontSize = 28.sp
//                        fontStyle = FontStyle.Italic
                )
            }

            /* 2 Line */
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                if (difference.isNotEmpty()) {
                    Text(
                        text = "${difference[0]}  Years ${difference[1]} Months ${difference[2]} Days",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
//                            fontSize = 22.sp,
//                            fontWeight = FontWeight.Bold
                    )
                } else {
                    Text(
                        text = context.getString(R.string.homescreen_sobriety_not_date),
                        style = MaterialTheme.typography.bodyLarge,
                        fontStyle = FontStyle.Italic
//                            fontSize = 18.sp,
//                            fontStyle = FontStyle.Italic
                    )
                }
            }

            /* 3 Line */
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                if (difference.isNotEmpty()) {
                    Text(
                        text = "або ${difference[3]} days",
                        style = MaterialTheme.typography.bodyLarge,
                        fontStyle = FontStyle.Italic
                    )
                }
            }


            /* 4 Line */
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {

                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .weight(0.5f)
                ) {
                    Text(
                        text = selectedDate,
                        style = MaterialTheme.typography.labelMedium
                    )
                }


                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .weight(0.5f)
                        .clickable { openDialog = true }
                ) {
                    Text(
                        text = "Edit",
                        style = MaterialTheme.typography.labelMedium,
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier
                            .padding(end = 8.dp)
                    )
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "setting",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(20.dp)
//                            .clickable { openDialog = true }
                    )
                }
            }


        }


    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DataSelection(
    viewModel: HomeViewModel,
//    openDialog: Boolean,
    onDismissClickListener: (Boolean) -> Unit
) {

//    var openDialog by remember { mutableStateOf(false) }
//    var selectedDate by remember { mutableStateOf<Long?>(null) }
    val datePickerState = rememberDatePickerState()

//    var openDialog1 = openDialog


//    if (openDialog) {
    DatePickerDialog(
        onDismissRequest = { onDismissClickListener(false) },
        confirmButton = {
            TextButton(
                onClick = {
                    viewModel.dataPickerSelected(datePickerState.selectedDateMillis)
//                        openDialog = false
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
//    }
}





