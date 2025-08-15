package com.alexyach.compose.groupsaa.presentation.grouplist

import android.Manifest
import android.content.Context
import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alexyach.compose.groupsaa.App
import com.alexyach.compose.groupsaa.R
import com.alexyach.compose.groupsaa.domain.model.Group
import com.alexyach.compose.groupsaa.presentation.grouplist.LoadingFrom.LoadInet
import com.alexyach.compose.groupsaa.presentation.grouplist.LoadingFrom.LoadRoom
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState


@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun GroupListScreen(
    paddingValues: PaddingValues,
    onGroupClickListener: (Group) -> Unit
) {
    val viewModel: GroupListViewModel = hiltViewModel()


    /* Permission */
    val context = LocalContext.current
    val permissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    /* END Permission */

    val isLoadFromInternet = viewModel.isLoadFromInternet.collectAsState(true)
    val screenState = viewModel.screenState.observeAsState(GroupListScreenState.Initial)
    val filterForGroups = viewModel.filterForGroups.collectAsState(FilterGroupsState.All)

    /* Buttons */
    Scaffold(
        topBar = {
//            TopAppBar(
//                title = {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
//                    .background(Color.DarkGray)
                    .fillMaxWidth()
                    .padding(top = 48.dp)
            ) {
                Button(
                    onClick = { viewModel.setFilterForGroups(FilterGroupsState.All) },

                    colors = ButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary,
                        containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.6f),
//                        containerColor = Color.Transparent,
                        disabledContainerColor = Color.Gray,
                        disabledContentColor = Color.DarkGray
                    ),
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 0.dp)
//                        .size(height = 38.dp, width = 110.dp)
                        .border(
                            width = 1.dp,
                            color = if (filterForGroups.value == FilterGroupsState.All) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.onSecondary
                            },

                            shape = RoundedCornerShape(16.dp)
                        )

                ) {
                    Text(
                        text = stringResource(R.string.grouplistscreen_button_all),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                    )
                }

                Button(
                    onClick = { viewModel.setFilterForGroups(FilterGroupsState.Today) },
                    colors = ButtonColors(

                        contentColor = MaterialTheme.colorScheme.primary,
                        containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.6f),
                        disabledContainerColor = Color.Gray,
                        disabledContentColor = Color.DarkGray
                    ),
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 0.dp)
//                                .size(height = 38.dp, width = 110.dp)
                        .border(
                            width = 1.dp,
                            color = if (filterForGroups.value == FilterGroupsState.Today) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.onSecondary
                            },
                            shape = RoundedCornerShape(16.dp)
                        )
                ) {
                    Text(
                        text = stringResource(R.string.grouplistscreen_button_today),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                    )
                }

                Button(
                    onClick = { viewModel.setFilterForGroups(FilterGroupsState.Now) },
                    colors = ButtonColors(

                        contentColor = MaterialTheme.colorScheme.primary,
                        containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.6f),
                        disabledContainerColor = Color.Gray,
                        disabledContentColor = Color.DarkGray
                    ),
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 0.dp)
//                                .size(height = 38.dp, width = 110.dp)
                        .border(
                            width = 1.dp,
                            color = if (filterForGroups.value == FilterGroupsState.Now) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.onSecondary
                            },
                            shape = RoundedCornerShape(16.dp)
                        )

                ) {
                    Text(
                        text = stringResource(R.string.grouplistscreen_button_now),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                    )
                }
            }
//                },
//                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.secondaryContainer),
//            )


        }

    ) { paddingValue ->
        val pV = paddingValue
        when (val currentState = screenState.value) {
            is GroupListScreenState.Groups -> {

                Groups(
                    context = context,
                    permissionState = permissionState,
                    groups = currentState.group,
                    viewModel = viewModel,
                    paddingValues = paddingValues,
                    onGroupClickListener = onGroupClickListener,
                    filterForGroups = filterForGroups.value,
                    isLoadFromInternet = isLoadFromInternet.value
                )


            }

            is GroupListScreenState.Loading -> {
                when (currentState.loadFrom) {
                    LoadInet -> {
                        LoadingListGroup("load from Internet")
                    }

                    LoadRoom -> {
                        LoadingListGroup("load from local BD")
                    }
                }
            }

            GroupListScreenState.Error -> {
                ErrorListGroup()
            }

            GroupListScreenState.Initial -> {}
        }
    }


}

@Composable
fun ErrorListGroup() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.grouplistscreen_error_load)
        )
    }
}

@Composable
fun LoadingListGroup(
    loadFrom: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
        Text(
            text = loadFrom
        )
    }
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun Groups(
    context: Context,
    permissionState: PermissionState,
    groups: List<Group>,
    viewModel: GroupListViewModel,
    onGroupClickListener: (Group) -> Unit,
    isLoadFromInternet: Boolean,
    filterForGroups: FilterGroupsState,
    paddingValues: PaddingValues
) {
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
                isLoadFromInternet = isLoadFromInternet,
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
                isLoadFromInternet = isLoadFromInternet,
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

@Composable
private fun HeaderGroupList(
    isLoadFromInternet: Boolean,
    resStringTitle: Int,
    isOffline: Boolean
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(resStringTitle),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            if (isOffline) {
                Icon(
                    painterResource(
                        if (isLoadFromInternet) R.drawable.web_icon
                        else R.drawable.database_icon
                    ),
                    tint = Color.DarkGray,
                    contentDescription = "icon",
                    modifier = Modifier
                        .size(25.dp)
                        .padding(start = 8.dp)
                )
            }
        }

    }
}




//    /* ************ */
////    currentLocation?.let { location ->
////        testText = location.latitude.toString()
//
//
////        val uri = Uri.parse("geo:${location.latitude},${location.longitude}?q=${location.latitude},${location.longitude}(Your Location)")
////        val mapIntent = Intent(Intent.ACTION_VIEW, uri)
////        context.startActivity(mapIntent)
//
////    }
//
//
//}


//@Preview
//@Composable
//private fun ViewGroupListScreen() {
//    GroupListScreen(
//        paddingValues = PaddingValues(0.dp),
//        onGroupClickListener = {}
//    )
//}