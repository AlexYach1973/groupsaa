package com.alexyach.compose.groupsaa.presentation.grouplist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexyach.compose.groupsaa.utils.getListGroup

class GroupListViewModel : ViewModel() {


    /* TEMP */
    private val sourceLit = getListGroup()
//        mutableListOf<Group>().apply {
//        repeat(50) {
//            add(Group(
//                name = "name $it",
//                schedule = "always $it",
//                addresses = " qw $it",
//                email = "aa@s $it",
//                telephone = "12345 $it",
//                note = "№ $it"
//            ))
//        }
//    }

    private val initialState = GroupListScreenState.Groups(group = sourceLit)
    /* END TEMP */

    private val _screenState = MutableLiveData<GroupListScreenState>(initialState)
    val screenState: LiveData<GroupListScreenState> = _screenState




}