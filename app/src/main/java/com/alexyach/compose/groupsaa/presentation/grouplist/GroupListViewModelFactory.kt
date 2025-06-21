package com.alexyach.compose.groupsaa.presentation.grouplist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alexyach.compose.groupsaa.App

class GroupListViewModelFactory(
    private val application: App
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GroupListViewModel(application.groupDao) as T
    }
}