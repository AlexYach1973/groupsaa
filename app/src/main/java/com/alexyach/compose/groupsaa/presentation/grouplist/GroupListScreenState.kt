package com.alexyach.compose.groupsaa.presentation.grouplist

import com.alexyach.compose.groupsaa.domain.model.Group

sealed class GroupListScreenState {
    object Initial : GroupListScreenState()
    object Error : GroupListScreenState()

    data class Loading(val loadFrom: LoadingFrom) : GroupListScreenState()
    data class Groups(val group: List<Group>) : GroupListScreenState()
}

sealed class LoadingFrom{
    object LoadInet : LoadingFrom()
    object LoadRoom : LoadingFrom()
}
