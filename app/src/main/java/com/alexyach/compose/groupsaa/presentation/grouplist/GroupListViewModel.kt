package com.alexyach.compose.groupsaa.presentation.grouplist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexyach.compose.groupsaa.domain.entity.Group

class GroupListViewModel : ViewModel() {


    /* TEMP */
    private val sourceLit = mutableListOf<Group>().apply {
        repeat(50) {
            add(Group(
                name = "name + $it",
                schedule = "always",
                addresses = " qw",
                email = "aa@s",
                telephone = "12345",
                note = "№ $it"
            ))
        }
    }

    private val initialState = GroupListScreenState.Groups(group = sourceLit)
    /* END TEMP */

    private val _screenState = MutableLiveData<GroupListScreenState>(initialState)
    val screenState: LiveData<GroupListScreenState> = _screenState




}