package com.alexyach.compose.groupsaa.domain.repository

import com.alexyach.compose.groupsaa.data.db.GroupEntity
import com.alexyach.compose.groupsaa.domain.model.Group
import com.alexyach.compose.groupsaa.presentation.grouplist.GroupListScreenState
import kotlinx.coroutines.flow.Flow

interface IRepository {
    fun getAllGroupList() : Flow<Pair<List<Group>, Boolean>>
}