package com.alexyach.compose.groupsaa.presentation.grouplist

import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexyach.compose.groupsaa.domain.entity.Group
import com.alexyach.compose.groupsaa.utils.getListGroup
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.atan
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

class GroupListViewModel : ViewModel() {

    private val _screenState =
        MutableLiveData<GroupListScreenState>(GroupListScreenState.Initial)
    val screenState: LiveData<GroupListScreenState> = _screenState

    /* Location */
    private val _testLoc = MutableLiveData<Double>(0.0)
    val testLoc: LiveData<Double> = _testLoc

    init {
        getGroupList()
    }

    private fun getGroupList() {
        _screenState.value = GroupListScreenState.Loading

        viewModelScope.launch {
            delay(2000L)
            _screenState.value = GroupListScreenState.Groups(getListGroup())
        }

    }


    /* TEST MAP */
    fun getCurrentLocation(
        fusedLocationClient: FusedLocationProviderClient,
        groups: List<Group>
    ) {
        try {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: android.location.Location? ->
                    location?.let { location ->
                        _testLoc.value = location.latitude

                        distanceToGroups(
                            currentLocation = location,
                            groups = groups
                        )
                    }
                }
        } catch (e: SecurityException) {
            e.printStackTrace()
            Log.d("Logs", "getCurrentLocation error: $e")
        }
    }

    private fun distanceToGroups(currentLocation: Location, groups: List<Group>) {
        var distance: Double
        val latCurrent = currentLocation.latitude
        val lonCurrent = currentLocation.longitude
        val R = 6371 // Радиус Земли в километрах

        groups.forEach {group ->
            val latDistance = Math.toRadians(group.latitude - latCurrent)
            val lonDistance = Math.toRadians(group.longitude - lonCurrent)
            val a = sin(latDistance / 2).pow(2) +
                    cos(Math.toRadians(latCurrent)) * cos(Math.toRadians(group.latitude)) *
                    sin(lonDistance / 2).pow(2)
            val c = 2 * atan2(sqrt(a), sqrt(1 - a))
            distance = R * c

            group.distance = distance
        }

        _screenState.value = GroupListScreenState.Groups(groups)

    }



//    private fun haversineDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
//        val R = 6371 // Радиус Земли в километрах
//
//        val latDistance = Math.toRadians(lat2 - lat1)
//        val lonDistance = Math.toRadians(lon2 - lon1)
//        val a = sin(latDistance / 2).pow(2) +
//                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
//                sin(lonDistance / 2).pow(2)
//        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
//        return R * c
//    }



}