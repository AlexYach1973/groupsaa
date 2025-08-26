package com.alexyach.compose.groupsaa.presentation.group.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.alexyach.compose.groupsaa.R
import com.alexyach.compose.groupsaa.domain.model.Group
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

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

