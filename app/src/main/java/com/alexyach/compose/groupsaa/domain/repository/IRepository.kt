package com.alexyach.compose.groupsaa.domain.repository

import com.alexyach.compose.groupsaa.data.db.GroupEntity
import com.alexyach.compose.groupsaa.domain.model.Group
import kotlinx.coroutines.flow.Flow

interface IRepository {
    fun getAllGroupList() : Flow<List<Group>>
    fun getAllFromRoom(): Flow<List<GroupEntity>>
    suspend fun saveToRoom(groupsDto: List<Group>)
    suspend fun deleteAllFromRoom()
}