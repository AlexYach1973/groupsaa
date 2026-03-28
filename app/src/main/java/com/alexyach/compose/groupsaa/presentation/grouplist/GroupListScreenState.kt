package com.alexyach.compose.groupsaa.presentation.grouplist

import com.alexyach.compose.groupsaa.domain.model.Group

sealed class GroupListScreenState {
    object Initial : GroupListScreenState()
    object Error : GroupListScreenState()
    object Loading : GroupListScreenState()

    data class Groups(val group: List<Group>) : GroupListScreenState()
}
