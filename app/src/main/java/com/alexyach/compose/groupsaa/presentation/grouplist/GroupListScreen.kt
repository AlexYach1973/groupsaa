package com.alexyach.compose.groupsaa.presentation.grouplist

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alexyach.compose.groupsaa.domain.entity.Group
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


@Composable
fun GroupListScreen(
    paddingValues: PaddingValues,
    onGroupClickListener : (Group) -> Unit
) {
    val viewModel: GroupListViewModel = viewModel()
    val screenState = viewModel.screenState.observeAsState(GroupListScreenState.Initial)
    val currentState = screenState.value

    /* Tet Location */
    val testLoc = viewModel.testLoc.observeAsState(0.0)

//    val testText by remember { mutableStateOf("?") }

    when(currentState) {
        is GroupListScreenState.Groups -> {
            Groups(
                testLoc = testLoc.value,
                groups = currentState.group,
                viewModel = viewModel,
                paddingValues = paddingValues,
                onGroupClickListener = onGroupClickListener
            )
        }
        GroupListScreenState.Initial -> {}
        GroupListScreenState.Loading -> { LoadingListGroup() }
    }
}

@Composable
fun LoadingListGroup() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}


@Composable
private fun Groups(
    testLoc: Double,
    groups: List<Group>,
    viewModel: GroupListViewModel,
    onGroupClickListener : (Group) -> Unit,
    paddingValues: PaddingValues
) {

    Column(
        modifier = Modifier.padding(top = 40.dp)
    )
    {

        MapIntentWithCurrentLocation(viewModel, groups, testLoc)

        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .background(
                    color = MaterialTheme.colorScheme.background
                ),
            contentPadding = PaddingValues(
                top = 16.dp,
                start = 0.dp,
                end = 0.dp,
                bottom = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            items(
                items = groups,
                key = { it.name }
            ) {

                CardGroup(
                    group = it,
                    onGroupClickListener
                )

            }

        }
    }


}


/* TEST MAP */

@Composable
fun MapIntentWithCurrentLocation(
    viewModel: GroupListViewModel,
    groups: List<Group>,
    testLoc: Double) {

    val context = LocalContext.current
//    var currentLocation by remember { mutableStateOf<android.location.Location?>(null) }
    val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions.all { it.value }) {
            viewModel.getCurrentLocation(fusedLocationClient, groups)
        }

    }

    Button(onClick = {
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
    }) {
        Text(text = testLoc.toString())
    }

//    currentLocation?.let { location ->
//        testText = location.latitude.toString()


//        val uri = Uri.parse("geo:${location.latitude},${location.longitude}?q=${location.latitude},${location.longitude}(Your Location)")
//        val mapIntent = Intent(Intent.ACTION_VIEW, uri)
//        context.startActivity(mapIntent)

//    }


}

//private fun getCurrentLocation(
//    fusedLocationClient: FusedLocationProviderClient,
//    onLocationReceived: (android.location.Location) -> Unit
//) {
//    try {
//        fusedLocationClient.lastLocation
//            .addOnSuccessListener { location: android.location.Location? ->
//                location?.let {
////                    Log.d("Logs", "Location: $location")
//                    onLocationReceived(it)
//                }
//            }
//    } catch (e: SecurityException) {
//        e.printStackTrace()
//        Log.d("Logs", "getCurrentLocation error: $e")
//    }
//}


/* END TEST MAP */



//@Preview
//@Composable
//private fun ViewGroupListScreen() {
//    GroupListScreen(
//        paddingValues = PaddingValues(0.dp),
//        onGroupClickListener = {}
//    )
//}