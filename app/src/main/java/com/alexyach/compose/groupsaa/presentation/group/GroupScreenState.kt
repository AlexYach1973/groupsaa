package com.alexyach.compose.groupsaa.presentation.group

import com.alexyach.compose.groupsaa.domain.model.Group

sealed class GroupScreenState {
    object initial : GroupScreenState()

    data class GroupItem(val group: Group) : GroupScreenState()
}