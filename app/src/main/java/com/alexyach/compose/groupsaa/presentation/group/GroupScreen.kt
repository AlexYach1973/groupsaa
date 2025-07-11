package com.alexyach.compose.groupsaa.presentation.group

import android.content.Context
import android.icu.util.LocaleData
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withLink
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alexyach.compose.groupsaa.R
import com.alexyach.compose.groupsaa.domain.model.Group
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupScreen(
    group: Group,
    onBackPress: () -> Unit
) {

    val context = LocalContext.current
    val viewModel: GroupViewModel = viewModel(
        factory = GroupViewModelFactory(group = group)
    )

    /* Errors observable */
    val errorToast = viewModel.errorToast.collectAsState(0)
    if (errorToast.value == 1) {
        toastShow(context, "Launch Skype error")
        viewModel.setErrorToast(0)
    }
    if (errorToast.value == 2) {
        toastShow(context, "Launch Telegram error")
        viewModel.setErrorToast(0)
    }
    if (errorToast.value == 3) {
        toastShow(context, "Launch Zoom error")
        viewModel.setErrorToast(0)
    }
    /* END Errors observable */


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 50.dp)

                    ) {
                        Text(
                            text = group.name,
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }

                },
                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.secondaryContainer),
                navigationIcon = {
                    IconButton(onClick = { onBackPress() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
//                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Arrow Back"
                        )
                    }
                }
            )
        },

        ) { paddingValue ->
        val pv = paddingValue

        if (
        /* group.addressForMap.contains("t.me") ||
         group.addressForMap.contains("skype") ||
         group.addressForMap.contains("zoom")*/
            group.addressForMap.contains("http")
        ) {
            GroupScreenOnline(
                context = context,
                group = group,
                viewModel = viewModel
            )

        } else {
            GroupScreenOffline(
                context = context,
                group = group,
                viewModel = viewModel
            )
        }


    }

}


@Composable
private fun GroupScreenOffline(
    context: Context,
    group: Group,
    viewModel: GroupViewModel
) {
    val scrollState = rememberScrollState()
    val brush = Brush.verticalGradient(listOf(Color.LightGray, Color.DarkGray))



    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.secondaryContainer)
//                .background(MaterialTheme.colorScheme.background)
            .padding(
//                    paddingValues = paddingValue)
                top = 124.dp,
                end = 8.dp,
                start = 8.dp,
                bottom = 132.dp
            )
            .fillMaxSize()
            .verticalScroll(scrollState),

        ) {

        AddressGroup(
            group = group,
            onClickLaunchMapListener = {
                viewModel.showGroupMap(
                    context = context,
                    addressForMap = group.addressForMap
                )
            }
        )
        Spacer(modifier = Modifier.padding(8.dp))

//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Center,
//            modifier = Modifier
//                .fillMaxWidth()
//        ) {

        TelephoneGroup(
            context = context,
            viewModel = viewModel,
            group = group
        )
        Spacer(Modifier.padding(8.dp))

        InternetLink(group = group)

//        }


        Spacer(modifier = Modifier.padding(8.dp))

        GroupSchedule(group = group)

        Spacer(modifier = Modifier.padding(8.dp))

        if (group.note.isNotEmpty()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onSecondary
//                        brush = brush,
//                        shape = RoundedCornerShape(12.dp),
//                shape = RectangleShape
                    )
            ) {
                Text(
                    text = group.note,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(8.dp)
//                fontSize = 22.sp,
                )
            }
        }
    }
}


@Composable
private fun GroupScreenOnline(
    context: Context,
    group: Group,
    viewModel: GroupViewModel
) {

    val scrollState = rememberScrollState()
    val brush = Brush.verticalGradient(listOf(Color.LightGray, Color.DarkGray))

    /* Лаунчери для запуску додатків */
    val telegramLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { }

    val skypeLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { }

    val zoomLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { }

    /* Лаунчер для Pay Store */
    val playStoreLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(
                top = 124.dp,
                end = 8.dp,
                start = 8.dp,
                bottom = 132.dp
            )
            .fillMaxSize()
            .verticalScroll(scrollState),
    ) {

//        if (group.addresses.isNotEmpty()) {
//            AddressGroup(
//                group = group,
//                onClickLaunchMapListener = {}
//            )
//        }

//        Spacer(modifier = Modifier.padding(8.dp))

        /* *** Launch Conference *** */
        if (group.addressForMap.contains("t.me")) {
            LaunchOnlineConference(
                viewModel = viewModel,
                resIcon = R.drawable.telegram_logo,
                onClickLaunchListener = {
                    viewModel.launchTelegram(
                        context = context,
                        telegramLauncher = telegramLauncher,
                        uri = group.addressForMap,
                        playStoreLauncher = playStoreLauncher
                    )
                }
            )
        } else if (group.addressForMap.contains("skype")) {
            LaunchOnlineConference(
                viewModel = viewModel,
                resIcon = R.drawable.skype_logo,
                onClickLaunchListener = {
                    viewModel.launchSkype(
                        context = context,
                        skypeLauncher = skypeLauncher,
                        uri = group.addressForMap,
                        playStoreLauncher = playStoreLauncher
                    )
                }
            )
        } else if (group.addressForMap.contains("zoom")) {
            LaunchOnlineConference(
                viewModel = viewModel,
                resIcon = R.drawable.zoom_logo,
                onClickLaunchListener = {
                    viewModel.launchZoom(
                        context = context,
                        zoomLauncher = zoomLauncher,
                        uri = group.addressForMap,
                        playStoreLauncher = playStoreLauncher
                    )
                }
            )
        }
        /* END Launch Conference *** */

        Spacer(modifier = Modifier.padding(8.dp))

        if (group.schedule.isNotEmpty()) {
            GroupSchedule(group = group)

            Spacer(modifier = Modifier.padding(8.dp))
        }



        if (group.telephone.isDigitsOnly()) {
            TelephoneGroup(
                group = group,
                viewModel = viewModel,
                context = context
            )

            Spacer(modifier = Modifier.padding(8.dp))
        }


        if (group.email.isNotEmpty()) {
            InternetLink(group = group)

            Spacer(modifier = Modifier.padding(8.dp))
        }

        if (group.note.isNotEmpty()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onSecondary
//                        brush = brush,
//                        shape = RoundedCornerShape(12.dp),
                    )
            ) {
                Text(
                    text = group.note,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(8.dp)
                )
            }
        }


    }

}

@Composable
private fun LaunchOnlineConference(
    viewModel: GroupViewModel,
    resIcon: Int,
    onClickLaunchListener: (GroupViewModel) -> Unit
) {
    val brush = Brush.verticalGradient(listOf(Color.LightGray, Color.DarkGray))

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClickLaunchListener(viewModel)
            }

            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.onSecondary
//                brush = brush,
//                shape = RoundedCornerShape(12.dp),
            )
    ) {

        Image(
            painter = painterResource(id = resIcon),
            contentDescription = "icon",
            modifier = Modifier
                .padding(8.dp)
                .size(50.dp)
                .weight(0.3f)
        )

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(1f)
        ) {

            Text(
                text = stringResource(R.string.groupscreen_online_chat),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}


@Composable
private fun TelephoneGroup(
    group: Group,
    viewModel: GroupViewModel,
    context: Context
) {

    val brush = Brush.verticalGradient(listOf(Color.LightGray, Color.DarkGray))

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                viewModel.makeCallGroup(
                    context = context,
                    phoneNumber = group.telephone
                )
            }
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.onSecondary
//                brush = brush,
//                shape = RoundedCornerShape(12.dp),
            )
    ) {

        Icon(
            imageVector = Icons.Default.Call,
            contentDescription = null,
            tint = Color.Green,
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .size(60.dp)
        )

        Text(
            text = group.telephone,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp)
        )

    }
}

@Composable
fun InternetLink(
    group: Group
) {
    val brush = Brush.verticalGradient(listOf(Color.LightGray, Color.DarkGray))

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.onSecondary
//                brush = brush,
//                shape = RoundedCornerShape(12.dp),
//                shape = RectangleShape
            )
    ) {

        Image(
            painterResource(R.drawable.website),
            contentDescription = "website",
            modifier = Modifier
                .padding(start = 8.dp)
                .size(60.dp)
        )

        /*Icon(
            painter = painterResource(id = R.drawable.web),
            contentDescription = null,
            tint = Color.Blue,
            modifier = Modifier
                .padding(start = 4.dp)
                .size(60.dp)
        )*/

        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = buildAnnotatedString {
                    withLink(
                        LinkAnnotation.Url(
                            group.email,
                            TextLinkStyles(style = SpanStyle(color = MaterialTheme.colorScheme.primary))
                        )
                    ) {
                        append(
                            text = group.email,
                        )
                    }
                },
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(8.dp)
            )
        }

    }

}

@Composable
fun GroupSchedule(
    group: Group
) {

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
//                .weight(1f)
//            .padding(4.dp)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.onSecondary
            )
    ) {
        Spacer(Modifier.padding(top = 4.dp))

//            val listSchedule = listOf("11:00","11:00","11:00","11:00","11:00","11:00","11:00")
        val listSchedule = group.schedule

        val dayOfWeek = listOf(
            "Monday", "Tuesday", "Wednesday", "Thursday",
            "Friday", "Saturday", "Sunday"
        )
        val dayOfWeekUkr = listOf(
            "Понеілок", "Вівторок", "Середа", "Четвер",
            "П'ятниця", "Субота", "Неділя"
        )

        /* Date */
        val currentDay = LocalDateTime.now().dayOfWeek.name
        /* Hour */
        val currentTime = LocalTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH:mm")

        for ((index, listScheduleItem) in listSchedule.withIndex()) {
            if (listScheduleItem.isNotBlank()) {

                val timeSchedule = LocalTime.parse(listScheduleItem, formatter)
                val timeSchedulePlusHour = timeSchedule.plusHours(1)

                val isDayMatched = currentDay.lowercase() == dayOfWeek[index].lowercase()
                val isTimeMatched = currentTime.isAfter(timeSchedule) &&
                        currentTime.isBefore(timeSchedulePlusHour)


//                    Log.d("Logs", "currentTime= $currentTime; timeSchedule= $timeSchedule")

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)

                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .weight(1f)

                    ) {
                        Icon(
                            painterResource(R.drawable.handwatch),
                            contentDescription = null,
                            tint = if (isDayMatched && isTimeMatched) {
                                Color.Green
                            } else {
                                MaterialTheme.colorScheme.primary
                            },
                            modifier = Modifier
                                .size(30.dp)
                                .padding(4.dp)
                        )

                        Text(
                            text = "${dayOfWeekUkr[index]}:",
                            style = MaterialTheme.typography.bodyLarge,
                            color = if (isDayMatched) {
                                colorResource(R.color.purple_200)
                            } else {
                                MaterialTheme.colorScheme.primary
                            },
                            fontWeight = if (isDayMatched) { FontWeight.Bold} else {FontWeight.Normal},
                            modifier = Modifier
                                .padding(start = 16.dp)
                        )
                    }

                    /* Hour */
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier
                            .padding(end = 50.dp)
                            .weight(1f)
                    ) {
                        Text(
                            text = listScheduleItem,
                            style = MaterialTheme.typography.bodyLarge,
                            color = if (isTimeMatched && isDayMatched) {
                                colorResource(R.color.purple_200)
                            } else {
                                MaterialTheme.colorScheme.primary
                            },
                            modifier = Modifier
                                .padding(4.dp)
                        )
                    }

                }
            }
        }

        Spacer(Modifier.padding(bottom = 4.dp))
    }

}

@Composable
fun AddressGroup(
    group: Group,
    onClickLaunchMapListener: () -> Unit
) {
    val brush = Brush.verticalGradient(listOf(Color.LightGray, Color.DarkGray))

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClickLaunchMapListener()
            }
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.onSecondary
//                brush = brush,
//                shape = RoundedCornerShape(12.dp),
            )
    ) {

        Image(
            painterResource(R.drawable.google_maps_logo),
            contentDescription = "google map logo",
            modifier = Modifier
                .padding(8.dp)
                .size(60.dp)
        )

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = group.addresses,
                style = MaterialTheme.typography.bodyLarge,
//                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(4.dp)
            )
        }


    }
}

/* Toast */
private fun toastShow(
    context: Context,
    message: String
) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}


@Preview
@Composable
private fun ViewGroupScreen() {
    GroupScreen(
        Group(
            "Obolon",
            "qwefwe werwerwer werqwerqe qwer 12",
            listOf("19:00", "19:00", "19:00", "19:00", "19:00", "19:00", "19:00"),
            "qw@wqBlaBlaBlaBlaBlaBla",
            "123-3221",
            "BlaBlaBlaBlaBlaBla BlaBlaBla BlaBlaBlaBlaBlaBlaBlaBlaBla BlaBlaBla",
            1.1, 2.2
        ),
        { }
    )
}