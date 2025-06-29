package com.alexyach.compose.groupsaa.presentation.grouplist

import android.Manifest
import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alexyach.compose.groupsaa.App
import com.alexyach.compose.groupsaa.domain.model.Group
import com.alexyach.compose.groupsaa.presentation.grouplist.LoadingFrom.LoadInet
import com.alexyach.compose.groupsaa.presentation.grouplist.LoadingFrom.LoadRoom
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.CoroutineScope
import kotlin.Any

@OptIn(ExperimentalPermissionsApi::class)
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

    val screenState = viewModel.screenState.observeAsState(GroupListScreenState.Initial)


    var isInternet = viewModel.isInternet.collectAsState(true)

    when (val currentState = screenState.value) {
        is GroupListScreenState.Groups -> {
//
//            /* Get Current Location */
//            viewModel.getLocation(
//                context = context,
//                permissionState = permissionState,
//                groups = screenState.group
//            )

            Groups(
                context = context,
                permissionState = permissionState,
                groups = currentState.group,
                viewModel = viewModel,
                paddingValues = paddingValues,
                onGroupClickListener = onGroupClickListener,
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

@Composable
fun ErrorListGroup() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Не вдалося загрузити список груп, спробуйте пізніше"
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
    paddingValues: PaddingValues
) {
    val scrollState = rememberScrollState()

    /* Get Current Location */
//    SideEffect {
    viewModel.getLocation(
        context = context,
        permissionState = permissionState,
        groups = groups
    )
//    }


    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondaryContainer)
//            .background(MaterialTheme.colorScheme.background)
            .padding(paddingValues)
//            .padding(top = 40.dp)
    )
    {

        /* OLD Code */
//        LaunchCurrentLocation(viewModel, groups)

        HeaderGroupList(
            isInternet = isInternet
        )

        groups.forEach {
            Log.d("Logs", "Distance= ${it.distance}")
            CardGroup(
                group = it,
                onGroupClickListener
            )
        }

    }
}

@Composable
private fun HeaderGroupList(
    isInternet: Boolean
) {
    Row(
        horizontalArrangement = Arrangement.Center,
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
                text = "Офлайн групи",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Icon(
                painter = painterResource(id = com.alexyach.compose.groupsaa.R.drawable.wifi),
                contentDescription = null,
                tint = if (isInternet) Color.Green else Color.Red,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(50.dp)
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