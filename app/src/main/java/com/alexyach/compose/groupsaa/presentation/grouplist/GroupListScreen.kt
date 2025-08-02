package com.alexyach.compose.groupsaa.presentation.grouplist

import android.Manifest
import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alexyach.compose.groupsaa.App
import com.alexyach.compose.groupsaa.R
import com.alexyach.compose.groupsaa.domain.model.Group
import com.alexyach.compose.groupsaa.presentation.grouplist.FilterGroupsState
import com.alexyach.compose.groupsaa.presentation.grouplist.LoadingFrom.LoadInet
import com.alexyach.compose.groupsaa.presentation.grouplist.LoadingFrom.LoadRoom
import com.alexyach.compose.groupsaa.ui.theme.amatic
import com.alexyach.compose.groupsaa.ui.theme.backgroundDark
import com.alexyach.compose.groupsaa.ui.theme.spice_rice
import com.alexyach.compose.groupsaa.ui.theme.triodionr
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.text.lowercase

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun GroupListScreen(
    paddingValues: PaddingValues,
    onGroupClickListener: (Group) -> Unit
) {
    val viewModel: GroupListViewModel = viewModel(
        factory = GroupListViewModelFactory(
            application = LocalContext.current.applicationContext as App
        )
    )

    /* Permission */
    val context = LocalContext.current
    val permissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    /* END Permission */

    val isInternet = viewModel.isInternet.collectAsState(true)

    val screenState = viewModel.screenState.observeAsState(GroupListScreenState.Initial)

    val filterForGroups = viewModel.filterForGroups.collectAsState(FilterGroupsState.All)

    /* Buttons */
    Scaffold(
        topBar = {

            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
//                    .background(Color.Green)
                    .fillMaxWidth()
                    .padding(top = 48.dp)
            ) {
                Button(
                    onClick = { viewModel.setFilterForGroups(FilterGroupsState.All) },

                    colors = ButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary,
                        containerColor = Color.Transparent,
                        disabledContainerColor = Color.Gray,
                        disabledContentColor = Color.DarkGray
                    ),
                    modifier = Modifier
                        .size(height = 38.dp, width = 110.dp)
                        .border(
                            width = 1.dp,
                            color = if (filterForGroups.value == FilterGroupsState.All) {
                                MaterialTheme.colorScheme.primary
                            } else { MaterialTheme.colorScheme.onSecondary},

                            shape = RoundedCornerShape(16.dp)
                        )

                ) {
                    Text(
                        text = stringResource(R.string.grouplistscreen_button_all),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                    )
                }

                Button(
                    onClick = { viewModel.setFilterForGroups(FilterGroupsState.Today) },
                    colors = ButtonColors(

                        contentColor = MaterialTheme.colorScheme.primary,
                        containerColor = Color.Transparent,
                        disabledContainerColor = Color.Gray,
                        disabledContentColor = Color.DarkGray
                    ),
                    modifier = Modifier
                        .size(height = 38.dp, width = 110.dp)
                        .border(
                            width = 1.dp,
                            color = if (filterForGroups.value == FilterGroupsState.Today) {
                                MaterialTheme.colorScheme.primary
                            } else { MaterialTheme.colorScheme.onSecondary},
                            shape = RoundedCornerShape(16.dp)
                        )
                ) { Text(
                    text = stringResource(R.string.grouplistscreen_button_today),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                ) }

                Button(
                    onClick = { viewModel.setFilterForGroups(FilterGroupsState.Now) },
                    colors = ButtonColors(

                        contentColor = MaterialTheme.colorScheme.primary,
                        containerColor = Color.Transparent,
                        disabledContainerColor = Color.Gray,
                        disabledContentColor = Color.DarkGray
                    ),
                    modifier = Modifier
                        .size(height = 38.dp, width = 110.dp)
                        .border(
                            width = 1.dp,
                            color = if (filterForGroups.value == FilterGroupsState.Now) {
                                MaterialTheme.colorScheme.primary
                            } else { MaterialTheme.colorScheme.onSecondary},
                            shape = RoundedCornerShape(16.dp)
                        )

                ) { Text(
                    text = stringResource(R.string.grouplistscreen_button_now),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                ) }
            }
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
                    isInternet = isInternet.value
                )


            }

            is GroupListScreenState.Loading -> {
                when (currentState.loadFrom) {
                    LoadInet -> {
//                    isInternet = true
                        LoadingListGroup("load from Internet")
                    }

                    LoadRoom -> {
//                    isInternet = false
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
    isInternet: Boolean,
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

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(top = 96.dp, bottom = 132.dp)
//            .padding(paddingValues)
    )
    {

        /* Offline */
        HeaderGroupList(
            isInternet = isInternet,
            resStringTitle = R.string.grouplistscreen_offline_title
        )

        groups
            .filter { it.latitude != 0.0 }
            .filter {
                when(filterForGroups){
                    is FilterGroupsState.All -> { true }
                    FilterGroupsState.Today -> { isTodayGroup(group = it) }
                    FilterGroupsState.Now -> { isActiveGroup(group = it) }
                }
            }
            .sortedBy { it.distance }
            .forEach { group ->
            CardGroups(
                group = group,
                onGroupClickListener
            )
        }

        Spacer(modifier = Modifier.padding(4.dp))

        /* Online */
        HeaderGroupList(
            isInternet = isInternet,
            resStringTitle = R.string.grouplistscreen_online_title
        )
        Spacer(modifier = Modifier.padding(4.dp))

        groups
            .filter { it.latitude == 0.0 }
            .filter {
                when(filterForGroups){
                    is FilterGroupsState.All -> { true }
                    FilterGroupsState.Today -> { isTodayGroup(group = it) }
                    FilterGroupsState.Now -> { isActiveGroup(group = it) }
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

@Composable
private fun HeaderGroupList(
    isInternet: Boolean,
    resStringTitle: Int
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

            Text(
                text = if(isInternet) "internet" else "local DB",
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                modifier = Modifier
                    .padding(start = 8.dp)
            )


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