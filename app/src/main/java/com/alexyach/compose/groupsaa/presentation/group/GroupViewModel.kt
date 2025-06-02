package com.alexyach.compose.groupsaa.presentation.group

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexyach.compose.groupsaa.domain.entity.Group

class GroupViewModel(
    group : Group
) : ViewModel() {

    private val _screenState = MutableLiveData<GroupScreenState>(
        GroupScreenState.GroupItem(group = group))
    val screenState: LiveData<GroupScreenState> = _screenState

}


