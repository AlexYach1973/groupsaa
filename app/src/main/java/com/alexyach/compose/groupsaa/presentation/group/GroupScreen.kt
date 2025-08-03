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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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

    /* Errors State observable */
    val errorState = viewModel.errorState.collectAsState(GroupScreenErrorState.NoError)

    LaunchedEffect(errorState.value) {
        when(errorState.value) {
            GroupScreenErrorState.NoError -> {}
            is GroupScreenErrorState.TelegramError -> { toastShow(context, "Launch Telegram error") }
            GroupScreenErrorState.SkypeError -> { toastShow(context, "Launch Skype error") }
            GroupScreenErrorState.ZoomError -> { toastShow(context, "Launch Zoom error") }
        }
        viewModel.setGroupScreenErrorState(GroupScreenErrorState.NoError)
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
                            painterResource(R.drawable.left_arrow_button),
//                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
//                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Arrow Back",
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier
                                .size(30.dp)
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
                viewModel.handlerGroupScreenAction(
                    GroupScreenAction.GroupMap(
                        context = context,
                        addressForMap = group.addressForMap
                    )
                )
            }
        )
        Spacer(modifier = Modifier.padding(8.dp))

        TelephoneGroup(
            context = context,
            viewModel = viewModel,
            group = group
        )
        Spacer(Modifier.padding(8.dp))

        InternetLink(group = group)

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

        /* *** Launch Conference *** */
        if (group.addressForMap.contains("t.me")) {
            LaunchOnlineConference(
                viewModel = viewModel,
                resIcon = R.drawable.telegram_logo,
                onClickLaunchListener = {
                    viewModel.handlerGroupScreenAction(
                        GroupScreenAction.LaunchTelegram(
                            context = context,
                            telegramLauncher = telegramLauncher,
                            uri = group.addressForMap,
                            playStoreLauncher = playStoreLauncher
                        )
                    )
                }
            )
        } else if (group.addressForMap.contains("skype")) {
            LaunchOnlineConference(
                viewModel = viewModel,
                resIcon = R.drawable.skype_logo,
                onClickLaunchListener = {
                    viewModel.handlerGroupScreenAction(
                        GroupScreenAction.LaunchSkype(
                            context = context,
                            skypeLauncher = skypeLauncher,
                            uri = group.addressForMap,
                            playStoreLauncher = playStoreLauncher
                        )
                    )
                }
            )
        } else if (group.addressForMap.contains("zoom")) {
            LaunchOnlineConference(
                viewModel = viewModel,
                resIcon = R.drawable.zoom_logo,
                onClickLaunchListener = {
                    viewModel.handlerGroupScreenAction(
                        GroupScreenAction.LaunchZoom(
                            context = context,
                            zoomLauncher = zoomLauncher,
                            uri = group.addressForMap,
                            playStoreLauncher = playStoreLauncher
                        )
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



        if (group.telephone.isNotEmpty()) {
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

    /**
     * КиевСтар:  067, 068, 077, 096, 097, 098;
     * Lifecell 063, 073, 093;
     * Vodafone (050, 066, 095, 099);
     * 3Mob (091),
     * PEOPLEnet (092),
     * Интертелеком (094)
     */


    val listTelephone = group.telephone.split(";")

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {

        listTelephone.forEach {telephone ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        viewModel.handlerGroupScreenAction(
                            GroupScreenAction.CallGroup(
                                context = context,
                                phoneNumber = group.telephone
                            )
                        )
                    }
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
            ) {

                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = "Call",
                    tint = Color.Green,
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .size(60.dp)
                )

                Text(
                    text = telephone,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
                Image(
                    painter = if(telephone.contains("067") || telephone.contains("068") ||
                        telephone.contains("096") || telephone.contains("097") ||
                        telephone.contains("098")) {

                        painterResource(R.drawable.kyivstar)

                    } else if(telephone.contains("063") || telephone.contains("073") ||
                        telephone.contains("093")){

                        painterResource(R.drawable.lifecell_logo)

                    } else if(telephone.contains("050") || telephone.contains("066") ||
                        telephone.contains("095") || telephone.contains("099")) {

                        painterResource(R.drawable.vodafon)

                    } else {
                        painterResource(R.drawable.home_phone)
                    },
                    contentDescription = "Kyivstar",
                    modifier = Modifier
                        .padding(vertical = 4.dp, horizontal = 8.dp)
                        .size(50.dp)
                )

            }
            Spacer(Modifier.padding(4.dp))

        }

    }


}

@Composable
fun InternetLink(
    group: Group
) {
    val brush = Brush.verticalGradient(listOf(Color.LightGray, Color.DarkGray))



    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.onSecondary
            )
    ) {

        Image(
            painterResource(R.drawable.website),
            contentDescription = "website",
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .size(60.dp)
        )

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

        for ((index, scheduleItem) in listSchedule.withIndex()) {
            if (scheduleItem.isNotBlank()) {

                var isTimeMatched = false
                /*  If Schedule list (12:00, 18:00) */
                val listScheduleItem: List<String> = scheduleItem.split(",")
                val timeScheduleList = mutableListOf<LocalTime>()
                val timeSchedulePlusHourList = mutableListOf<LocalTime>()

                listScheduleItem.forEach { timeScheduleList.add(LocalTime.parse(it, formatter)) }
                timeScheduleList.forEach { timeSchedulePlusHourList.add(it.plusHours(1)) }

                for (i in 0..(listScheduleItem.size - 1)) {
                    if (
                        currentTime.isAfter(timeScheduleList[i]) &&
                        currentTime.isBefore(timeSchedulePlusHourList[i])
                    ) {
                        isTimeMatched = true
                    }
                }


                val isDayMatched = currentDay.lowercase() == dayOfWeek[index].lowercase()

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
                            text = scheduleItem,
//                            text = group.schedule.joinToString(separator = ", "),
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
        horizontalArrangement = Arrangement.SpaceBetween,
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
            listOf("19:00", "19:00", "19:00", "19:00", "19:00,10:00", "19:00", "19:00"),
            "qw@wqBlaBlaBlaBlaBlaBla",
            "123-3221",
            "BlaBlaBlaBlaBlaBla BlaBlaBla BlaBlaBlaBlaBlaBlaBlaBlaBla BlaBlaBla",
            1.1, 2.2
        ),
        { }
    )
}