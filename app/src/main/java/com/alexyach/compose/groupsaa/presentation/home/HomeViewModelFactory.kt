package com.alexyach.compose.groupsaa.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alexyach.compose.groupsaa.data.repository.DataStoreManager

class HomeViewModelFactory(
    private val dataStoreManager: DataStoreManager
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(dataStoreManager) as T
    }
}