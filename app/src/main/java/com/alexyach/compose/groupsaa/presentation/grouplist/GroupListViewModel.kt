package com.alexyach.compose.groupsaa.presentation.grouplist

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexyach.compose.groupsaa.data.db.GroupDao
import com.alexyach.compose.groupsaa.data.db.GroupEntity
import com.alexyach.compose.groupsaa.data.db.toGroup
import com.alexyach.compose.groupsaa.data.repository.GroupsRepositoryImpl
import com.alexyach.compose.groupsaa.domain.model.Group
import com.alexyach.compose.groupsaa.presentation.grouplist.LoadingFrom.LoadInet
import com.alexyach.compose.groupsaa.utils.getListGroupTest
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
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
        MutableLiveData<GroupListScreenState>(GroupListScreenState.Initial)
    val screenState: LiveData<GroupListScreenState> = _screenState

    private val _isInternet = MutableStateFlow <Boolean>(true)
    val isInternet: StateFlow<Boolean> = _isInternet




    init {
//        _screenState.value = GroupListScreenState.Groups(getListGroupTest()) // TEST

        getGroupList()
    }

    private fun getGroupList() {
        _screenState.value = GroupListScreenState.Loading(LoadInet)

        viewModelScope.launch {

            /* From Internet*/
            repository.getAllGroupList().collect {currentListGroup->
                _screenState.value = GroupListScreenState.Groups(currentListGroup)

                _isInternet.value = true

                /* Оновити Room */
                updateRoomGroups(currentListGroup)

            }.runCatching {
                _screenState.value = GroupListScreenState.Loading(LoadingFrom.LoadRoom)

                _isInternet.value = false

//                Log.d("Logs", "GroupListViewModel runCatching")
//                delay(3000)

                /* From Room */
                repository.getAllFromRoom().collect { listGroupEntity ->

                    val listFromRoom = listGroupEntity.map { it.toGroup() }

                    if (listFromRoom.isEmpty()) {
                        Log.d("Logs", "GroupListViewModel listFromRoom.isEmpty()")
                        _screenState.value = GroupListScreenState.Groups(
                            listOf(
                                Group("База пуста", "", listOf("","","","","","",""),
                                    "", "", "", 0.0, 0.0)
                            )
                        )
                    } else {
                        _screenState.value = GroupListScreenState.Groups(listFromRoom)
                        Log.d("Logs", "GroupListViewModel listFromRoom NO Empty")
                    }

                }.runCatching {
                    Log.d("Logs", "Error ROOM")
                    _screenState.value = GroupListScreenState.Error
                }
            }
        }
    }

    private suspend fun updateRoomGroups(currentListGroup: List<Group>) {
        var listFromRoom : List<Group>
        repository.getAllFromRoom().collect{ listGroupEntity->
            listFromRoom = listGroupEntity.map { it.toGroup() }


            if (currentListGroup != listFromRoom) {
                if (listFromRoom.isNotEmpty()){
                    repository.deleteAllFromRoom()
                }
                repository.saveToRoom(currentListGroup)
            }
        }

    }



    /*  New Code Distance */
    @OptIn(ExperimentalPermissionsApi::class)
    @SuppressLint("MissingPermission")
    fun getLocation(
        context: Context,
        permissionState: PermissionState,
        groups: List<Group>
    ) {

        if (permissionState.status.isGranted) {

            viewModelScope.launch {
                try {
                    val locationClient =
                        LocationServices.getFusedLocationProviderClient(context)
                    val locationResult = locationClient.lastLocation.await()

                    locationResult?.let { location ->
                        distanceToGroups(
                            currentLocation = location,
                            groups = groups
                        )
                    }

                } catch (e: Exception) {
                    Log.d("Logs", "ViewModel getLocation error: $e")
                }
            }

        } else {
            permissionState.launchPermissionRequest()
        }
    }
    /* END New Code */

    private fun distanceToGroups(
        currentLocation: Location,
        groups: List<Group>
    ) {
//        Log.d("Logs", "GroupListViewModel distanceToGroups start")

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

//                Log.d("Logs", "distanceToGroups name: ${group.name} distance: $distance")

                group.copy(distance = distance)
            }
        }

        _screenState.value = GroupListScreenState.Groups(newGroupList)

    }


}