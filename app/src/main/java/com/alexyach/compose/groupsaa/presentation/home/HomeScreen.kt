package com.alexyach.compose.groupsaa.presentation.home

import HomeScreenSelDateState
import android.app.Application
import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alexyach.compose.groupsaa.R
import com.alexyach.compose.groupsaa.domain.model.DailyReflections
import com.alexyach.compose.groupsaa.domain.model.Prayer
import com.alexyach.compose.groupsaa.domain.model.PrayersEnum
import com.alexyach.compose.groupsaa.domain.model.getPrayersList
import com.alexyach.compose.groupsaa.ui.theme.spice_rice
import com.alexyach.compose.groupsaa.ui.theme.triodionr
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondaryContainer)
    ) {


        val context = LocalContext.current
        val viewModel: HomeViewModel = viewModel(
            factory = HomeViewModelFactory(
                application = LocalContext.current.applicationContext as Application
            )
        )


        val scrollState = rememberScrollState()


        val selDateState = viewModel.selDateScreenState.collectAsState(HomeScreenSelDateState.Initial)

        /* Daily */
        val dailyDate by viewModel.selectDateForDaily.collectAsState(LocalDate.now())
        val dailyItem by viewModel.dailyItem.collectAsState(
            DailyReflections("Title", "", "")
        )


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
                selDateState = selDateState.value
            )

            /* Daily reflection */
            var isHideDailyReflection by remember { mutableStateOf(true) }

            DailyReflectionCard(
                isHide = isHideDailyReflection,
                dailyItem = dailyItem?.let { dailyItem } ?: DailyReflections("Нема", "", ""),
                onClickHideListener = { isHideDailyReflection = !isHideDailyReflection },
                viewModel = viewModel,
                dailyDate = dailyDate
            )


            /* *************************   PRAYER ***************************   */

            val prayerList by viewModel.prayersList.collectAsState( emptyList<Prayer>())//getPrayersList())

            /* Logs */
//            prayerList.forEach {
//                Log.d("Logs", "HomeScreen name: ${it.name}; isVisible: ${it.isVisible}")
//            }
//            Log.i("Logs", "----------------------------------------------------------------")



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
                        style = MaterialTheme.typography.titleLarge,
                        fontFamily = triodionr
                    )
                }



                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            isShowPrayerSetting = !isShowPrayerSetting
                            /* load preferences visibility Prayers  */
                            viewModel.loadPrefPrayerList()
                        }
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


           if(prayerList.isNotEmpty()) {

               prayerList.forEach { prayer ->

                   var isHidePrayer by remember { mutableStateOf(true) }
                   var isVisiblePrayer by remember { mutableStateOf(true) }
                   isVisiblePrayer = prayer.isVisible

                   if (isVisiblePrayer || isShowPrayerSetting) {
                       PrayerCard(
                           isHide = isHidePrayer,
                           onClickHideListener = { isHidePrayer = !isHidePrayer },
                           resTextTitle = stringResource(prayer.title),
                           resTextStart = stringResource(prayer.textShort),
                           resTextFull = stringResource(prayer.textFull),
                           isShowPrayerSetting = isShowPrayerSetting,
                           isShowPrayer = isVisiblePrayer,
                           onClickShowMorningPrayer = {
                               isVisiblePrayer = !isVisiblePrayer
                               prayer.isVisible = !prayer.isVisible

                               viewModel.savePrefPrayerList(
                                   prayersEnum = prayer.name,
                                   value = prayer.isVisible
                               )
                           }
                       )
                   }

               }
           }


        }

    }
}

// period of sobriety

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PeriodOfSobrietyCard(
    context: Context,
    viewModel: HomeViewModel,
    selDateState: HomeScreenSelDateState
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
                    {
                        viewModel.dataPickerSelected(datePickerState.selectedDateMillis)
//                        viewModel.loadDateFromDataStore()
                    }
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

                when(selDateState) {
                    is HomeScreenSelDateState.SelectedDate -> {

                        /* 2 Line */
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {

                                Text(
                                    text = selDateState.selDate.difference,
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 26.sp
                                )

                            }


                        /* 3 Line */
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                                Text(
                                    text = "або ${selDateState.selDate.totalDays}",
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontStyle = FontStyle.Italic,
                                    fontSize = 22.sp
                                )
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
                                    text = "від ${ selDateState.selDate.selectedDateSobriety}",
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
                                    modifier = Modifier
                                        .size(20.dp)
                                )
                            }
                        }

                    }

                    HomeScreenSelDateState.Error -> {
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 8.dp, vertical = 16.dp)
                                .fillMaxWidth()

                        ) {
                            Text(
                                text = context.getString(R.string.homescreen_sobriety_not_date),
                                style = MaterialTheme.typography.bodyLarge,
                                fontStyle = FontStyle.Italic
                            )
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .clickable { openDialog = true }
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = "Edit",
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontStyle = FontStyle.Italic,
                                    modifier = Modifier
                                        .padding(end = 8.dp)
                                )
                                Icon(
                                    imageVector = Icons.Default.Settings,
                                    contentDescription = "setting",
                                    tint = MaterialTheme.colorScheme.tertiary,
                                    modifier = Modifier
                                        .size(25.dp)
                                )
                            }
                        }
                    }

                    HomeScreenSelDateState.Loading -> {
                        CircularProgressIndicator()
                    }

                    HomeScreenSelDateState.Initial -> {}
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

//    /* Speech */
//    var isSpeaking by remember { mutableStateOf(false) }
//    val tts = rememberTextToSpeech()

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
                    PlayAudioUkrText("${dailyItem.title}.${dailyItem.quote}.${dailyItem.discussion}")
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
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {


            /* Text Size */
            if (!isHide) {
                /* *** Speech *** */
                Row(
                    modifier = Modifier
                        .weight(0.3f)
                        .padding(8.dp)
                ) {
                    PlayAudioUkrText(resTextFull)
                }

                Row(
                    modifier = Modifier.weight(0.7f)
                ) {}

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .weight(0.3f)
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "plus",
                        tint = MaterialTheme.colorScheme.onTertiaryContainer,
                        modifier = Modifier
                            .padding(end = 8.dp, start = 8.dp)
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
                    fontWeight = FontWeight.Normal,
                    fontFamily = spice_rice,
                )
                Text(
                    text = resTextStart,
                    style = MaterialTheme.typography.bodySmall,
                    fontFamily = spice_rice,
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
//                    fontFamily = triodionr,
                    fontFamily = spice_rice,
//                    color = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                )
            }

        }

    }
}


/* Speech */
@Composable
private fun PlayAudioUkrText(text: String) {

    var isVoiceUkr by remember { mutableStateOf(false) }

//    var isSpeaking by remember { mutableStateOf(false) }
    val tts = rememberTextToSpeech({ isVoiceUkr = it })

    Log.d("Logs", "HomeScreen PlayAudioUkrText isVoiceUkr= $isVoiceUkr")


//    if (true) {
    if (isVoiceUkr) {
        Icon(
            painterResource(R.drawable.text_to_speech),
            contentDescription = "Play",
            tint = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier
                .size(30.dp)
                .clickable {
                    if (tts.value?.isSpeaking == true) {
                        tts.value?.stop()
                    } else {
                        tts.value?.speak(
                            text,
                            TextToSpeech.QUEUE_FLUSH,
                            null,
                            null
                        )
                    }

                }
        )
    }
}

@Composable
private fun rememberTextToSpeech(isVoiceUkrListener: (Boolean) -> Unit): MutableState<TextToSpeech?> {
    val context = LocalContext.current
    val tts = remember { mutableStateOf<TextToSpeech?>(null) }

    var textToSpeech: TextToSpeech? = null
    val locale = java.util.Locale("uk", "UA")

    DisposableEffect(context) {
        textToSpeech = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech?.language = locale

                /* Is Ukrainian Voice */
                tts.let {
                    val voices = tts.value?.voices
//                    Log.d("Logs", "rememberTextToSpeech voices= $voices")
                    val ukrainianVoices =
                        voices?.filter { it.locale.language == "uk" } ?: emptyList()

                    isVoiceUkrListener(ukrainianVoices.isNotEmpty())
                }

            }
        }
        tts.value = textToSpeech

        onDispose {
            tts.value?.stop()
            tts.value?.shutdown()
        }
    }

    return tts
}



//@Composable
//@Preview
//private fun PreviewHomeScreen() {
//    PrayerCard(
//       isHide = false,
//        onClickHideListener = {},
//        resTextTitle = "Title",
//        resTextStart = "Start start start",
//        resTextFull = "Tite fulllllllll llllllllllll sulllll lll llll ",
//        isShowPrayerSetting = false,
//        isShowPrayer = true,
//        onClickShowMorningPrayer = { }
//    )
//}