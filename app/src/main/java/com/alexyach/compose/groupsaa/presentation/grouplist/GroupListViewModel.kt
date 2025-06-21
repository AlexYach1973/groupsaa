package com.alexyach.compose.groupsaa.presentation.grouplist

import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexyach.compose.groupsaa.data.db.GroupDao
import com.alexyach.compose.groupsaa.data.db.toGroup
import com.alexyach.compose.groupsaa.data.repository.GroupsRepositoryImpl
import com.alexyach.compose.groupsaa.domain.model.Group
import com.alexyach.compose.groupsaa.presentation.grouplist.LoadingFrom.LoadInet
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

class GroupListViewModel(
    private val groupDao: GroupDao
) : ViewModel() {

    private val repository = GroupsRepositoryImpl(groupDao = groupDao)

    private val _screenState =
        MutableStateFlow<GroupListScreenState>(GroupListScreenState.Initial)
    val screenState: StateFlow<GroupListScreenState> = _screenState


    init {
        getGroupList()
    }

    private fun getGroupList() {
        _screenState.value = GroupListScreenState.Loading(LoadInet)

        viewModelScope.launch {

            /* From Internet*/
            repository.getAllGroupList().collect {
                _screenState.value = GroupListScreenState.Groups(it)

            }.runCatching {
                _screenState.value = GroupListScreenState.Loading(LoadingFrom.LoadRoom)

                /* From Room */
                repository.getAllFromRoom().collect { listGroupEntity ->
                    _screenState.value =
                        GroupListScreenState.Groups(listGroupEntity.map { it.toGroup() })
                }
//                Log.d("Logs", "GroupListViewModel runCatching")
            }


        }
    }



    fun getCurrentLocation(
        fusedLocationClient: FusedLocationProviderClient,
        groups: List<Group>
    ) {
        try {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: android.location.Location? ->
                    location?.let { location ->
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
        val radiusEarth = 6371 // Радиус Земли в километрах

        val newGroupList = groups.toMutableList().apply {
            replaceAll { group ->

                val latDistance = Math.toRadians(group.latitude - latCurrent)
                val lonDistance = Math.toRadians(group.longitude - lonCurrent)
                val a = sin(latDistance / 2).pow(2) +
                        cos(Math.toRadians(latCurrent)) * cos(Math.toRadians(group.latitude)) *
                        sin(lonDistance / 2).pow(2)
                val c = 2 * atan2(sqrt(a), sqrt(1 - a))
                distance = radiusEarth * c

                group.copy(distance = distance)

            }
        }

        _screenState.value = GroupListScreenState.Groups(newGroupList)

    }

}