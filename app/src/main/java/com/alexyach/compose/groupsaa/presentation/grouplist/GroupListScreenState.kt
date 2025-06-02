package com.alexyach.compose.groupsaa.presentation.grouplist

import com.alexyach.compose.groupsaa.domain.entity.Group

sealed class GroupListScreenState {
    object initial : GroupListScreenState()

    data class Groups(val group: List<Group>) : GroupListScreenState()
}