package com.alexyach.compose.groupsaa.presentation.grouplist

//import androidx.hilt.navigation.compose.hiltViewModel // old
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.alexyach.compose.groupsaa.R
import com.alexyach.compose.groupsaa.domain.model.Group
import com.alexyach.compose.groupsaa.presentation.grouplist.LoadingFrom.LoadInet
import com.alexyach.compose.groupsaa.presentation.grouplist.LoadingFrom.LoadRoom
import com.alexyach.compose.groupsaa.presentation.grouplist.components.GroupsContent
import com.alexyach.compose.groupsaa.presentation.grouplist.components.TopAppBarContent
import com.google.accompanist.permissions.ExperimentalPermissionsApi


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
                        LoadingListGroup(stringResource(R.string.grouplistscreen_load_from_net))
                    }

                    LoadRoom -> {
                        LoadingListGroup(stringResource(R.string.grouplistscreen_load_from_database))
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