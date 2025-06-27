package com.alexyach.compose.groupsaa.data.repository

import android.util.Log
import com.alexyach.compose.groupsaa.data.db.GroupDao
import com.alexyach.compose.groupsaa.data.db.GroupEntity
import com.alexyach.compose.groupsaa.data.model.toGroup
import com.alexyach.compose.groupsaa.data.network.ApiFactory
import com.alexyach.compose.groupsaa.domain.model.Group
import com.alexyach.compose.groupsaa.domain.model.toGroupEntity
import com.alexyach.compose.groupsaa.domain.usecase.IRepository
import com.alexyach.compose.groupsaa.utils.getListGroupTest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class GroupsRepositoryImpl(val groupDao: GroupDao) : IRepository {
    val apiService = ApiFactory.apiService


    override fun getAllGroupList() = flow {

        Log.d("Logs", "retrofitImpl() getAllGroupList Start")

        /* TEST */
//        throw Exception("This is an error message.")
//        emit(getListGroupTest())
        /* END TEST */

        val groupsDTO = apiService.getGroupList()
        emit(groupsDTO.map { it.toGroup() }) // to viewModel


    }.catch {
        Log.d("Logs", "retrofitImpl() Error : $it")
    }


    fun getAllFromRoom(): Flow<List<GroupEntity>> {
        Log.d("Logs", "GroupsRepositoryImpl getAllFromRoom")

        return groupDao.getAll()
    }

    suspend fun saveToRoom(groupsDto: List<Group>) {
        Log.d("Logs", "GroupsRepositoryImpl saveToRoom")

        groupsDto.forEach { item ->
            groupDao.insertGroup(item.toGroupEntity())
        }

    }

    suspend fun deleteAllFromRoom() {
        groupDao.deleteAll()

        Log.d("Logs", "GroupsRepositoryImpl deleteAllFromRoom")
    }


}