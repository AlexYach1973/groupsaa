package com.alexyach.compose.groupsaa.presentation.group

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alexyach.compose.groupsaa.domain.entity.Group

class GroupViewModelFactory(
    private val group: Group
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GroupViewModel(group = group) as T
    }
}