package com.alexyach.compose.groupsaa.data.repository

import android.util.Log
import androidx.compose.ui.platform.LocalContext
import com.alexyach.compose.groupsaa.App
import com.alexyach.compose.groupsaa.data.db.GroupDao
import com.alexyach.compose.groupsaa.data.db.GroupEntity
import com.alexyach.compose.groupsaa.data.db.toGroup
import com.alexyach.compose.groupsaa.data.model.ResponseDto
import com.alexyach.compose.groupsaa.data.model.toGroup
import com.alexyach.compose.groupsaa.data.model.toGroupEntity
import com.alexyach.compose.groupsaa.data.network.ApiFactory
import com.alexyach.compose.groupsaa.domain.model.Group
import com.alexyach.compose.groupsaa.domain.usecase.IRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList

class GroupsRepositoryImpl(val groupDao: GroupDao) : IRepository {
    val apiService = ApiFactory.apiService


    override fun getAllGroupList() = flow {

        Log.d("Logs", "retrofitImpl() getAllGroupList Start")

        val groupsDTO = apiService.getGroupList()
        emit(groupsDTO.map { it.toGroup() }) // to viewModel

        saveToRoom(groupsDTO)

        Log.d("Logs", "retrofitImpl() success groupsDTO: $groupsDTO")

    }.catch { }


    fun getAllFromRoom(): Flow<List<GroupEntity>> {
        return groupDao.getAll()
    }

    private suspend fun saveToRoom(groupsDto: List<ResponseDto>) {

        groupDao.deleteAll()

        groupsDto.forEach { item ->
            groupDao.insertGroup(item.toGroupEntity())
        }
    }


}