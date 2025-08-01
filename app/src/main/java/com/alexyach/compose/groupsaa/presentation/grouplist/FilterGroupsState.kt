package com.alexyach.compose.groupsaa.presentation.grouplist

sealed class FilterGroupsState {
    object All : FilterGroupsState()
    object Today : FilterGroupsState()
    object Now : FilterGroupsState()
}