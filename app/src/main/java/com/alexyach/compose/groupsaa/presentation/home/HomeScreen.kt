package com.alexyach.compose.groupsaa.presentation.home

import android.app.Application
import android.content.Context
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alexyach.compose.groupsaa.R
import com.alexyach.compose.groupsaa.domain.model.DailyReflections
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    onReadClickListener: () -> Unit
) {

    val context = LocalContext.current
    val viewModel: HomeViewModel = viewModel(
        factory = HomeViewModelFactory(
            application = LocalContext.current.applicationContext as Application
        )
    )

    val scrollState = rememberScrollState()

    val difference by viewModel.difference.collectAsState("")
    val selectedDate by viewModel.selectedDateSobriety.collectAsState("")
    val totalDays by viewModel.totalDays.collectAsState("")

    val dataStoreYear by viewModel.dataStoreYear.collectAsState(-1)
    val dataStoreMonth by viewModel.dataStoreMonth.collectAsState(-1)
    val dataStoreDay by viewModel.dataStoreDay.collectAsState(-1)

    /* Daily */
    val dailyDate by viewModel.selectDateForDaily.collectAsState(LocalDate.now())
    val dailyItem by viewModel.dailyItem.collectAsState(
        DailyReflections("Title", "", "")
    )



    if (dataStoreYear > 0 && dataStoreMonth > 0 && dataStoreDay > 0) {
        viewModel.formatingDate(listOf(dataStoreYear, dataStoreMonth, dataStoreDay))
    }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(paddingValues)
            .verticalScroll(scrollState)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondaryContainer)
    ) {


        PeriodOfSobrietyCard(
            context = context,
            viewModel = viewModel,
            difference = difference,
            selectedDate = selectedDate,
            totalDays = totalDays
        )

        /* Daily reflection */
        var isHideDailyReflection by remember { mutableStateOf(true) }

        DailyReflectionCard(
            isHide = isHideDailyReflection,
            dailyItem = dailyItem?.let { dailyItem } ?: DailyReflections("Нема","",""),
            onClickHideListener = { isHideDailyReflection = !isHideDailyReflection },
            viewModel = viewModel,
            dailyDate = dailyDate
        )


        /* *************************   PRAYER ***************************   */

        /* From Data Store Preference Show Prayer */
        val prefMorningPrayer by viewModel.prefMorningPrayer.collectAsState(true)
        val prefEveningPrayer by viewModel.prefEveningPrayer.collectAsState(true)
        val prefDelegationPrayer by viewModel.prefDelegationPrayer.collectAsState(true)
        val prefPeaceOfMindPrayer by viewModel.prefPeaceOfMindPrayer.collectAsState(true)
        val prefResentmentPrayer by viewModel.prefResentmentPrayer.collectAsState(true)
        val prefFearPrayer by viewModel.prefFearPrayer.collectAsState(true)
        val prefStepTenPrayer by viewModel.prefStepTenPrayer.collectAsState(true)

        var isShowPrayerSetting by remember { mutableStateOf(false) }

        /* *** TITLE *** */
        Row(
            modifier = Modifier
                .fillMaxWidth()
//                .background(Color.Green)
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center
        ) {

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .weight(4f)
                    .padding(start = 40.dp)
            ) {
                Text(
                    text = stringResource(R.string.homescreen_prayer_title),
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .weight(1f)
                    .clickable { isShowPrayerSetting = !isShowPrayerSetting }
            ) {
                Text(
                    text = "show",
                    style = MaterialTheme.typography.labelMedium,
                    fontStyle = FontStyle.Italic,
                    color = if (isShowPrayerSetting) {
                        MaterialTheme.colorScheme.onPrimary
                    } else {
                        MaterialTheme.colorScheme.primary
                    },
                    modifier = Modifier
                        .padding(end = 8.dp)
                )
                Icon(
                    painterResource(R.drawable.hand_eye),
                    contentDescription = "setting",
                    tint = if (isShowPrayerSetting) {
                        MaterialTheme.colorScheme.onPrimary
                    } else {
                        MaterialTheme.colorScheme.primary
                    },
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .size(20.dp)
                )
            }
        }
        /* *** END TITLE *** */


        var isHideMorningPrayer by remember { mutableStateOf(true) }
        var isShowMorningPrayer = prefMorningPrayer

        if (isShowMorningPrayer || isShowPrayerSetting) {
            PrayerCard(
                isHide = isHideMorningPrayer,
                onClickHideListener = { isHideMorningPrayer = !isHideMorningPrayer },
                resTextTitle = stringResource(R.string.number_step_eleven_morning_title),
                resTextStart = stringResource(R.string.number_step_eleven_morning_start),
                resTextFull = stringResource(R.string.number_step_eleven_morning),
                isShowPrayerSetting = isShowPrayerSetting,
                isShowPrayer = isShowMorningPrayer,
                onClickShowMorningPrayer = {
                    isShowMorningPrayer = !isShowMorningPrayer
                    viewModel.savePrefMorningPrayer(isShowMorningPrayer)
                }

            )
        }


        var isHideEveningPrayer by remember { mutableStateOf(true) }
        var isShowEveningPrayer = prefEveningPrayer

        if (isShowEveningPrayer || isShowPrayerSetting) {
            PrayerCard(
                isHide = isHideEveningPrayer,
                onClickHideListener = { isHideEveningPrayer = !isHideEveningPrayer },
                resTextTitle = stringResource(R.string.number_step_eleven_evening_title),
                resTextStart = stringResource(R.string.number_step_eleven_evening_start),
                resTextFull = stringResource(R.string.number_step_eleven_evening),
                isShowPrayerSetting = isShowPrayerSetting,
                isShowPrayer = isShowEveningPrayer,
                onClickShowMorningPrayer = {
                    isShowEveningPrayer = !isShowEveningPrayer
                    viewModel.savePrefEveningPrayer(isShowEveningPrayer)
                }
            )
        }

        var isHidePrayerDelegation by remember { mutableStateOf(true) }
        var isShowPrayerDelegation = prefDelegationPrayer

        if (isShowPrayerDelegation || isShowPrayerSetting) {
            PrayerCard(
                isHide = isHidePrayerDelegation,
                onClickHideListener = { isHidePrayerDelegation = !isHidePrayerDelegation },
                resTextTitle = stringResource(R.string.prayer_delegation_title),
                resTextStart = stringResource(R.string.prayer_delegation_start),
                resTextFull = stringResource(R.string.prayer_delegation),
                isShowPrayerSetting = isShowPrayerSetting,
                isShowPrayer = isShowPrayerDelegation,
                onClickShowMorningPrayer = {
                    isShowPrayerDelegation = !isShowPrayerDelegation
                    viewModel.savePrefDelegationPrayer(isShowPrayerDelegation)
                }
            )
        }

        var isHidePrayerPeaceOfMind by remember { mutableStateOf(true) }
        var isShowPrayerPeaceOfMind = prefPeaceOfMindPrayer

        if (isShowPrayerPeaceOfMind || isShowPrayerSetting) {
            PrayerCard(
                isHide = isHidePrayerPeaceOfMind,
                onClickHideListener = { isHidePrayerPeaceOfMind = !isHidePrayerPeaceOfMind },
                resTextTitle = stringResource(R.string.peace_of_mind_title),
                resTextStart = stringResource(R.string.peace_of_mind_start),
                resTextFull = stringResource(R.string.peace_of_mind_full),
                isShowPrayerSetting = isShowPrayerSetting,
                isShowPrayer = isShowPrayerPeaceOfMind,
                onClickShowMorningPrayer = {
                    isShowPrayerPeaceOfMind = !isShowPrayerPeaceOfMind
                    viewModel.savePrefPeaceOfMindPrayer(isShowPrayerPeaceOfMind)
                }
            )
        }

        var isHidePrayerResentment by remember { mutableStateOf(true) }
        var isShowPrayerResentment = prefResentmentPrayer

        if (isShowPrayerResentment || isShowPrayerSetting) {
            PrayerCard(
                isHide = isHidePrayerResentment,
                onClickHideListener = { isHidePrayerResentment = !isHidePrayerResentment },
                resTextTitle = stringResource(R.string.resentment_title),
                resTextStart = stringResource(R.string.resentment_start),
                resTextFull = stringResource(R.string.resentment_full),
                isShowPrayerSetting = isShowPrayerSetting,
                isShowPrayer = isShowPrayerResentment,
                onClickShowMorningPrayer = {
                    isShowPrayerResentment = !isShowPrayerResentment
                    viewModel.saveResentmentPrayer(isShowPrayerResentment)
                }
            )
        }

        var isHidePrayerFear by remember { mutableStateOf(true) }
        var isShowPrayerFear = prefFearPrayer

        if (isShowPrayerFear || isShowPrayerSetting) {
            PrayerCard(
                isHide = isHidePrayerFear,
                onClickHideListener = { isHidePrayerFear = !isHidePrayerFear },
                resTextTitle = stringResource(R.string.prayer_fear_title),
                resTextStart = stringResource(R.string.prayer_fear_start),
                resTextFull = stringResource(R.string.prayer_fear_full),
                isShowPrayerSetting = isShowPrayerSetting,
                isShowPrayer = isShowPrayerFear,
                onClickShowMorningPrayer = {
                    isShowPrayerFear = !isShowPrayerFear
                    viewModel.saveFearPrayer(isShowPrayerFear)
                }
            )
        }

        var isHideStepTen by remember { mutableStateOf(true) }
        var isShowStepTen = prefStepTenPrayer

        if (isShowStepTen || isShowPrayerSetting) {
            PrayerCard(
                isHide = isHideStepTen,
                onClickHideListener = { isHideStepTen = !isHideStepTen },
                resTextTitle = stringResource(R.string.step_number_ten_title),
                resTextStart = stringResource(R.string.step_number_ten_start),
                resTextFull = stringResource(R.string.step_number_ten_full),
                isShowPrayerSetting = isShowPrayerSetting,
                isShowPrayer = isShowStepTen,
                onClickShowMorningPrayer = {
                    isShowStepTen = !isShowStepTen
                    viewModel.saveStepTenPrayer(isShowStepTen)
                }
            )
        }


        /* TEST */
//        Text(
//            text = "$dataTest day $dataYear year",
//            fontSize = 25.sp
//        )


    }

}


// period of sobriety

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PeriodOfSobrietyCard(
    context: Context,
    viewModel: HomeViewModel,
    difference: String,
    selectedDate: String,
    totalDays: String
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
        val datePickerState = rememberDatePickerState()

        if (openDialog) {
            DataSelection(
                datePickerState = datePickerState,
                onDismissClickListener = { openDialog = it },
                onConfirmClickListener =
                    { viewModel.dataPickerSelected(datePickerState.selectedDateMillis) }
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()

        ) {
            Image(
                painter = painterResource(id = R.drawable.shar),
                contentDescription = null,
                contentScale = ContentScale.FillWidth, // Масштабування, щоб заповнити екран
                modifier = Modifier.fillMaxSize()
            )

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
                            text = difference,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            fontSize = 26.sp
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
                            text = "або $totalDays",
                            style = MaterialTheme.typography.bodyLarge,
                            fontStyle = FontStyle.Italic,
                            fontSize = 22.sp
                        )
                    }
                }


                /* 4 Line */
                Row(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .weight(4f)
                    ) {
                        Text(
                            text = "від $selectedDate",
                            style = MaterialTheme.typography.labelMedium
                        )
                    }


                    Row(
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .weight(1f)
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


        } // Box
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DataSelection(
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DailyReflectionCard(
    isHide: Boolean,
    dailyItem: DailyReflections,
    onClickHideListener: () -> Unit,
    viewModel: HomeViewModel,
    dailyDate: LocalDate
) {

    var fontSizeText by remember { mutableIntStateOf(16) }
    val datePickerState = rememberDatePickerState()
    var openDialog by remember { mutableStateOf(false) }

    /* Tetle */

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = stringResource(R.string.homescreen_daily_title),
            style = MaterialTheme.typography.titleLarge
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
            .clickable { onClickHideListener() },

        colors = CardDefaults.cardColors(
            MaterialTheme.colorScheme.secondaryContainer
        )
    ) {

        if (openDialog) {
            DataSelection(
                datePickerState = datePickerState,
                onDismissClickListener = { openDialog = it },
                onConfirmClickListener = {
                    datePickerState.selectedDateMillis?.let {millis ->

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
                }

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .weight(0.7f)
                        .padding(8.dp)
//                        .background(Color.Green)

                ) {
                    Text(
                        text = dailyDate.format(DateTimeFormatter.ofPattern("dd MMMM"))
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
        }

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
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                    )
                }

                /* Quote */
                Text(
                    text = dailyItem.quote,
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.bodyLarge,
                    fontStyle = FontStyle.Italic
                )
            } else {

                /* Title */
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    Text(
                        text = dailyItem.title,
                        fontSize = fontSizeText.sp,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
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
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                )

                /* Discussion */
                Text(
                    text = dailyItem.discussion,
                    fontSize = fontSizeText.sp,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }

    }
}


@Composable
private fun PrayerCard(
    isHide: Boolean,
    onClickHideListener: () -> Unit,
    resTextTitle: String,
    resTextStart: String,
    resTextFull: String,
    isShowPrayerSetting: Boolean,
    isShowPrayer: Boolean,
    onClickShowMorningPrayer: () -> Unit
) {
    var fontSizeText by remember { mutableIntStateOf(16) }

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
            .clickable { onClickHideListener() },

        colors = CardDefaults.cardColors(
            MaterialTheme.colorScheme.secondaryContainer
        )
    ) {

        if (isShowPrayerSetting) {
            Checkbox(
                checked = isShowPrayer,
                onCheckedChange = {
                    onClickShowMorningPrayer()
                },
                modifier = Modifier
//                    .background(Color.Green)
                    .padding(start = 8.dp, top = 8.dp)
                    .size(15.dp)
            )
        }

        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
        ) {

            /* Text Size */
            if (!isHide) {
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

        if (isHide) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = resTextTitle,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    text = resTextStart,
                    style = MaterialTheme.typography.bodySmall,
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "arrow"
                )

            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
//                    .background(color = MaterialTheme.colorScheme.primary)
            ) {

                Text(
                    text = resTextFull,
                    fontSize = fontSizeText.sp,
//                    color = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                )
            }

        }

    }
}







