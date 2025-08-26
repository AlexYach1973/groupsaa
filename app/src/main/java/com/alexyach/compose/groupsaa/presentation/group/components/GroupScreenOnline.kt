package com.alexyach.compose.groupsaa.presentation.group.components

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alexyach.compose.groupsaa.R
import com.alexyach.compose.groupsaa.domain.model.Group
import com.alexyach.compose.groupsaa.presentation.group.GroupScreenAction
import com.alexyach.compose.groupsaa.presentation.group.GroupViewModel



@Composable
fun GroupScreenOnline(
    context: Context,
    group: Group,
    viewModel: GroupViewModel
) {

    val scrollState = rememberScrollState()
//    val brush = Brush.verticalGradient(listOf(Color.LightGray, Color.DarkGray))

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
