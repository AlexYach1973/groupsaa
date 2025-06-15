package com.alexyach.compose.groupsaa.data.repository

import android.util.Log
import com.alexyach.compose.groupsaa.data.network.ApiFactory
import kotlinx.coroutines.flow.flow

class GroupListRepository {

    fun retrofitImpl() = flow {
        Log.d("Logs", "retrofitImpl flow{}")

        val apiService = ApiFactory.apiService

        emit(apiService.getGroupList())

    }
}