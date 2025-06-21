package com.alexyach.compose.groupsaa.presentation.grouplist

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alexyach.compose.groupsaa.App
import com.alexyach.compose.groupsaa.domain.model.Group
import com.alexyach.compose.groupsaa.presentation.grouplist.GroupListScreenState.*
import com.alexyach.compose.groupsaa.presentation.grouplist.LoadingFrom.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


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

    val screenState = viewModel.screenState.collectAsState()
//    observeAsState(GroupListScreenState.Initial)
    val currentState = screenState.value

    when (currentState) {
        is GroupListScreenState.Groups -> {
            Groups(
                groups = currentState.group,
                viewModel = viewModel,
                paddingValues = paddingValues,
                onGroupClickListener = onGroupClickListener
            )
        }

        is GroupListScreenState.Loading -> {
            when (currentState.loadFrom) {
                LoadInet -> LoadingListGroup("load from Internet")
                LoadRoom -> LoadingListGroup("load from local BD")
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
           text = "Не вдалося загрузити список груп із сайту, спробуйте пізніше"
       )
    }
}

@Composable
fun LoadingListGroup(
    loadFrom : String
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


@Composable
private fun Groups(
    groups: List<Group>,
    viewModel: GroupListViewModel,
    onGroupClickListener: (Group) -> Unit,
    paddingValues: PaddingValues
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .background(MaterialTheme.colorScheme.secondaryContainer)
//            .background(MaterialTheme.colorScheme.background)
            .padding(paddingValues)
//            .padding(top = 40.dp)
    )
    {

        LaunchCurrentLocation(viewModel, groups)

        HeaderGroupList()

        groups.forEach {
            CardGroup(
                group = it,
                onGroupClickListener
            )
        }

    }
}

@Composable
private fun HeaderGroupList() {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Офлайн групи",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}


/* TEST MAP */

@Composable
fun LaunchCurrentLocation(
    viewModel: GroupListViewModel,
    groups: List<Group>
) {

    val context = LocalContext.current

    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions.all { it.value }) {
            viewModel.getCurrentLocation(fusedLocationClient, groups)
        }

    }


    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        viewModel.getCurrentLocation(fusedLocationClient, groups)
    } else {
        Log.d("Logs", "No Permission")
        requestPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )

    }
    /* ************ */
//    currentLocation?.let { location ->
//        testText = location.latitude.toString()


//        val uri = Uri.parse("geo:${location.latitude},${location.longitude}?q=${location.latitude},${location.longitude}(Your Location)")
//        val mapIntent = Intent(Intent.ACTION_VIEW, uri)
//        context.startActivity(mapIntent)

//    }


}

/* END TEST MAP */


//@Preview
//@Composable
//private fun ViewGroupListScreen() {
//    GroupListScreen(
//        paddingValues = PaddingValues(0.dp),
//        onGroupClickListener = {}
//    )
//}