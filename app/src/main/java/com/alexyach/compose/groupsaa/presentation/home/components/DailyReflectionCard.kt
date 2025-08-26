package com.alexyach.compose.groupsaa.presentation.home.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexyach.compose.groupsaa.R
import com.alexyach.compose.groupsaa.domain.model.DailyReflections
import com.alexyach.compose.groupsaa.presentation.home.HomeViewModel
import com.alexyach.compose.groupsaa.ui.theme.spice_rice
import com.alexyach.compose.groupsaa.ui.theme.triodionr
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyReflectionCard(
    viewModel: HomeViewModel,
    isUkrVoice: Boolean
) {

    var isHide by remember { mutableStateOf(true) }

    val dailyDate by viewModel.selectDateForDaily.collectAsState(LocalDate.now())
    val dailyItemFlow by viewModel.dailyItem.collectAsState(
        DailyReflections("Title", "", "")
    )
    val dailyItem = dailyItemFlow?.let { dailyItemFlow } ?: DailyReflections("Нема", "", "")

    var fontSizeText by remember { mutableIntStateOf(16) }
    val datePickerState = rememberDatePickerState()
    var openDialog by remember { mutableStateOf(false) }

    /* Title */

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
    ) {
        Text(
            text = stringResource(R.string.homescreen_daily_title),
            style = MaterialTheme.typography.titleLarge,
            fontFamily = triodionr,
//            modifier = Modifier
//                .padding(bottom = 32.dp)
        )
    }



    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .border(
                width = 2.dp,
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.onSecondary
            )
            .clickable { isHide = !isHide },

        colors = CardDefaults.cardColors(
            MaterialTheme.colorScheme.secondaryContainer
        )
    ) {

        if (openDialog) {
            DataSelection(
                datePickerState = datePickerState,
                onDismissClickListener = { openDialog = it },
                onConfirmClickListener = {
                    datePickerState.selectedDateMillis?.let { millis ->

                        val selectedDate = Instant.ofEpochMilli(millis)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                        viewModel.loadDailyForDate(selectedDate)
                    }
                }
            )
        }

        /* Text Size */
        if (!isHide) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()

            ) {

                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .weight(0.3f)

                ) {
                    Icon(
                        painterResource(id = R.drawable.calendar),
                        contentDescription = "calendar",
                        tint = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable { openDialog = true }
                    )

                    /* *** Speech *** */
                    PlayAudioUkrText(
                        "${dailyItem.title}. ${dailyItem.quote}.${dailyItem.discussion}",
                        viewModel = viewModel,
                        isUkrVoice = isUkrVoice
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .weight(0.7f)
                        .padding(8.dp)

                ) {
                    Text(
                        text = dailyDate.format(
                            DateTimeFormatter.ofPattern(
                                "dd MMMM",
                                Locale.forLanguageTag("uk-UA")
//                                Locale.UK
//                                Locale("uk", "UA")
                            )
                        )
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(0.3f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "plus",
                        tint = MaterialTheme.colorScheme.onTertiaryContainer,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clickable { fontSizeText++ }
                    )

                    Icon(
                        painter = painterResource(R.drawable.minus),
                        contentDescription = "minus",
                        tint = MaterialTheme.colorScheme.onTertiaryContainer,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clickable { fontSizeText-- }
                    )
                }

            }
        } else { if (viewModel.isSpeaking()) viewModel.stopUkrainianText() }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {

            if (isHide) {
                /* Title */
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = dailyItem.title,
                        fontSize = 18.sp,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        fontFamily = spice_rice,
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                    )
                }

                /* Quote */
                Text(
                    text = dailyItem.quote,
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.bodyLarge,
                    fontStyle = FontStyle.Italic,
                    fontFamily = spice_rice
                )
            } else {

                /* Title */
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = dailyItem.title,
                        fontSize = fontSizeText.sp,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        fontFamily = spice_rice,
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                    )
                }


                /* Quote */
                Text(
                    text = dailyItem.quote,
                    fontSize = fontSizeText.sp,
                    style = MaterialTheme.typography.bodyLarge,
                    fontStyle = FontStyle.Italic,
                    fontFamily = spice_rice,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                )

                /* Discussion */
                Text(
                    text = dailyItem.discussion,
                    fontSize = fontSizeText.sp,
                    style = MaterialTheme.typography.bodyLarge,
                    fontFamily = spice_rice
                )
            }
        }

    }
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//private fun DataSelection(
//    datePickerState: DatePickerState,
//    onDismissClickListener: (Boolean) -> Unit,
//    onConfirmClickListener: () -> Unit
//) {
//
////    val datePickerState = rememberDatePickerState()
//
//    DatePickerDialog(
//        onDismissRequest = { onDismissClickListener(false) },
//        confirmButton = {
//            TextButton(
//                onClick = {
//                    onConfirmClickListener()
//                    onDismissClickListener(false)
//                }
//            ) { Text(text = "Ok") }
//        },
//        dismissButton = {
//            TextButton(onClick = { onDismissClickListener(false) }) { Text("Cancel") }
//        }
//
//    ) {
//        DatePicker(state = datePickerState)
//    }
//}