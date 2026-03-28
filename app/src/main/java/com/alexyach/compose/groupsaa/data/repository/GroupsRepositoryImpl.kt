package com.alexyach.compose.groupsaa.data.repository

import android.util.Log
import com.alexyach.compose.groupsaa.data.db.GroupDao
import com.alexyach.compose.groupsaa.data.db.GroupEntity
import com.alexyach.compose.groupsaa.data.db.toGroup
import com.alexyach.compose.groupsaa.data.model.toGroup
import com.alexyach.compose.groupsaa.data.network.ApiService
import com.alexyach.compose.groupsaa.domain.model.Group
import com.alexyach.compose.groupsaa.domain.model.toGroupEntity
import com.alexyach.compose.groupsaa.domain.repository.IRepository
import com.alexyach.compose.groupsaa.presentation.grouplist.LoadingListGroup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GroupsRepositoryImpl @Inject constructor(
    private val groupDao: GroupDao,
    private val apiService: ApiService,
    private val networkHelper: NetworkHelper // check Internet
) : IRepository {


    /* New Code */
    override fun getAllGroupList(): Flow<Pair<List<Group>, Boolean>> = flow {

        /* Сначала всегда показываем кэш из Room (быстро) */
//        emit(Pair<List<Group>, Boolean>(downloadFromRoom(), false))

        /* Если есть интернет — обновляем из сети */
        if (networkHelper.isOnline()) {
            try {
                /* TEST */ //throw IOException()

                val groupsFromInternet = apiService.getGroupList().map { it.toGroup() }
                emit(Pair<List<Group>, Boolean>(groupsFromInternet, true))

                /* Обновить Room*/
                updateRoomGroups(groupsFromInternet)

            } catch (e: IOException) {
                Log.i("Logs", "Не вдалося оновити дані")
                Log.i("Logs", "Помилка $e")
                emit(Pair<List<Group>, Boolean>(downloadFromRoom(), false))
            }

        } else {
            emit(Pair<List<Group>, Boolean>(downloadFromRoom(), false))
        }
    }.flowOn(Dispatchers.IO)

    private suspend fun downloadFromRoom(): List<Group> {

        return groupDao.getAll().first().map { it.toGroup() }
    }

    private suspend fun updateRoomGroups(listFromInternet: List<Group>) {

        val listFromRoom: List<Group> = downloadFromRoom()

        if (listFromInternet != listFromRoom) {
            if (listFromRoom.isNotEmpty()) {
                groupDao.deleteAll()
            }

            listFromInternet.forEach { group ->
                groupDao.insertGroup(group.toGroupEntity())
            }
        }
    }


}