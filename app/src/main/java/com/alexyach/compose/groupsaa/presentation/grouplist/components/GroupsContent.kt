package com.alexyach.compose.groupsaa.presentation.grouplist.components

import android.Manifest
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.alexyach.compose.groupsaa.R
import com.alexyach.compose.groupsaa.domain.model.Group
import com.alexyach.compose.groupsaa.presentation.grouplist.CardGroups
import com.alexyach.compose.groupsaa.presentation.grouplist.FilterGroupsState
import com.alexyach.compose.groupsaa.presentation.grouplist.GroupListViewModel
import com.alexyach.compose.groupsaa.presentation.grouplist.isActiveGroup
import com.alexyach.compose.groupsaa.presentation.grouplist.isTodayGroup
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun GroupsContent(
    context: Context,
    groups: List<Group>,
    viewModel: GroupListViewModel,
    onGroupClickListener: (Group) -> Unit,
    filterForGroups: FilterGroupsState,
) {

    val permissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)

    val isLoadFromInternet = viewModel.isLoadFromInternet.collectAsState(true)

    val scrollState = rememberScrollState()

    /* Get Current Location */
    viewModel.getLocation(
        context = context,
        permissionState = permissionState,
        groups = groups
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .padding(top = 96.dp, bottom = 132.dp)
                /* Индикатор прокуртки */
                .drawWithContent{
                    drawContent()

                    val thumbHeight = size.height * 0.2f // Пропорциональная высота
                    val thumbOffset = scrollState.value.toFloat() / scrollState.maxValue * (size.height - thumbHeight)

                    drawRect(
                        color = Color.Gray,
                        topLeft = Offset(x = size.width - 8.dp.toPx(), y = thumbOffset),
                        size = Size(width = 4.dp.toPx(), height = thumbHeight)
                    )
                }
//            .padding(paddingValues)
        )
        {

            /* *** *** Offline *** *** */
            HeaderGroupList(
                isLoadFromInternet = isLoadFromInternet.value,
                resStringTitle = R.string.grouplistscreen_offline_title,
                isOffline = true
            )


            Spacer(modifier = Modifier.padding(4.dp))
            groups
                .filter { it.latitude != 0.0 }
                .filter {
                    when (filterForGroups) {
                        is FilterGroupsState.All -> {
                            true
                        }

                        FilterGroupsState.Today -> {
                            isTodayGroup(group = it)
                        }

                        FilterGroupsState.Now -> {
                            isActiveGroup(group = it)
                        }
                    }
                }
                .sortedBy { it.distance }
                .forEach { group ->
                    CardGroups(
                        group = group,
                        onGroupClickListener
                    )
                }


            /* Online */
            HeaderGroupList(
                isLoadFromInternet = isLoadFromInternet.value,
                resStringTitle = R.string.grouplistscreen_online_title,
                isOffline = false
            )
            Spacer(modifier = Modifier.padding(4.dp))

            groups
                .filter { it.latitude == 0.0 }
                .filter {
                    when (filterForGroups) {
                        is FilterGroupsState.All -> {
                            true
                        }

                        FilterGroupsState.Today -> {
                            isTodayGroup(group = it)
                        }

                        FilterGroupsState.Now -> {
                            isActiveGroup(group = it)
                        }
                    }
                }
//            .filter { isActiveGroup(it) }
//            .filter { isTodayGroup(it) }
                .forEach { group ->
                    CardGroups(
                        group = group,
                        onGroupClickListener
                    )
                }

        }


    }

}
