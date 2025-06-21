package com.alexyach.compose.groupsaa.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface GroupDao {

    @Query("SELECT * FROM GroupEntity")
    fun getAll(): Flow<List<GroupEntity>>

    @Insert
    suspend fun insertGroup(group: GroupEntity)


    @Query("DELETE FROM GroupEntity")
    suspend fun deleteAll()


}