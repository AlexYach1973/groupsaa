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
import com.alexyach.compose.groupsaa.presentation.grouplist.components.GroupsContent
import com.alexyach.compose.groupsaa.presentation.grouplist.components.TopAppBarContent
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState


@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun GroupListScreen(
    paddingValues: PaddingValues,
    onGroupClickListener: (Group) -> Unit
) {
    val context = LocalContext.current
    val viewModel: GroupListViewModel = hiltViewModel()

    val screenState = viewModel.screenState.observeAsState(GroupListScreenState.Initial)
    val filterForGroups = viewModel.filterForGroups.collectAsState(FilterGroupsState.All)

    /* Buttons */
    Scaffold(
        topBar = {
            TopAppBarContent(
                viewModel = viewModel,
                filterForGroups = filterForGroups.value
            )
        }

    ) { paddingValue ->
        val pV = paddingValue

        when (val currentState = screenState.value) {
            is GroupListScreenState.Groups -> {

                GroupsContent(
                    context = context,
                    groups = currentState.group,
                    viewModel = viewModel,
                    onGroupClickListener = onGroupClickListener,
                    filterForGroups = filterForGroups.value,
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