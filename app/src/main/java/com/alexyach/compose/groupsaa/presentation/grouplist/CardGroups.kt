package com.alexyach.compose.groupsaa.presentation.grouplist

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexyach.compose.groupsaa.R
import com.alexyach.compose.groupsaa.domain.model.Group
import com.alexyach.compose.groupsaa.ui.theme.GroupsaaTheme
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun CardGroups(
    group: Group,
    onGroupClickListener : (Group) -> Unit,
    modifier: Modifier = Modifier
) {

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .border(
                width = 2.dp,
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.onSecondary
            ),

            colors = CardDefaults.cardColors(
                MaterialTheme.colorScheme.secondaryContainer
            ),
        onClick = { onGroupClickListener(group) }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            DistanceToGroup(
                group = group,
            )
//            Spacer(modifier = Modifier.padding(4.dp))
            InfoAboutGroup(
                group = group
            )
        }
    }

}


@Composable
private fun DistanceToGroup(group: Group){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 8.dp, start = 4.dp, end = 4.dp, bottom = 4.dp)
            .width(100.dp)

    ) {
        if (group.latitude == 0.0) {
            Icon(
                painterResource(id = R.drawable.seminar),
                contentDescription = "groups",
                tint = if (isActiveGroup(group = group)) {
                    Color.Green
                } else {
                    MaterialTheme.colorScheme.primary
                },
                modifier = Modifier
                    .size(45.dp)
            )

            Text(
                text = "online",
                fontSize = 20.sp,
                color = if (isActiveGroup(group = group)) {
                    Color.Green
                } else {
                    MaterialTheme.colorScheme.primary
                }
            )

        } else {
            Icon(
                painterResource(id = R.drawable.group),
                contentDescription = "groups",
                tint = if (isActiveGroup(group = group)) {
                    Color.Green
                } else {
                    MaterialTheme.colorScheme.primary
                },
                modifier = Modifier
                    .size(45.dp)
            )

            Text(
                text = String.format("%.1f", group.distance) + "км",
                fontSize = 28.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }

    }

}

@Composable
private fun InfoAboutGroup(
    group: Group
) {
    Column(
        modifier = Modifier
            .padding(4.dp)
    ) {
        Text(
            text = group.name,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.padding(4.dp))

        Text(
            text = group.addresses,
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.bodyLarge
        )

    }
}


private fun isActiveGroup(group: Group): Boolean {

    if (group.schedule.isEmpty()) return false

    val dayOfWeek = listOf(
        "monday", "tuesday", "wednesday", "thursday",
        "friday", "saturday", "sunday"
    )

    /* Date */
    val currentDay = LocalDateTime.now().dayOfWeek.name.lowercase()
    /* Hour */
    val currentTime = LocalTime.now()
    val formatter = DateTimeFormatter.ofPattern("HH:mm")

    val indexListDay = dayOfWeek.indexOf(currentDay)
    val timeTodayStr = group.schedule[indexListDay]

//    Log.d("Logs", "isActiveGroup ${group.name} indexListDay= $indexListDay; timeTodayStr= $timeTodayStr")

    if (timeTodayStr.isNotBlank()) {
        var isTimeMatched = false

        /*  If Schedule list (12:00, 18:00) */
            val listScheduleItem: List<String> = timeTodayStr.split(",")
            val timeScheduleList = mutableListOf<LocalTime>()
            val timeSchedulePlusHourList = mutableListOf<LocalTime>()

            listScheduleItem.forEach {timeScheduleList.add(LocalTime.parse(it,formatter))}
            timeScheduleList.forEach {timeSchedulePlusHourList.add(it.plusHours(1))}


            for (i in 0..(listScheduleItem.size-1)) {
                if (
                    currentTime.isAfter(timeScheduleList[i]) &&
                    currentTime.isBefore(timeSchedulePlusHourList[i])
                ) {
                    isTimeMatched = true
                }
            }

        return isTimeMatched

    } else {
        return false
    }

}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GroupsaaTheme {
        CardGroups(
            Group("Name",
                "qweqwe",
                listOf("","","","","18:00","",""),
                "3434",
                "",
                "",
                0.0,
                0.0),
            {}
        )
    }
}