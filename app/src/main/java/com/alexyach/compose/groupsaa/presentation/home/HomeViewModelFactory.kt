package com.alexyach.compose.groupsaa.presentation.home

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alexyach.compose.groupsaa.data.repository.DataStoreManager

class HomeViewModelFactory(
    private val application: Application,
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(application) as T
    }
}