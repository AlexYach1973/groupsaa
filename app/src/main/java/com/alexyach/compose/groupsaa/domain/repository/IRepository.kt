package com.alexyach.compose.groupsaa.domain.repository

import com.alexyach.compose.groupsaa.domain.model.Group
import kotlinx.coroutines.flow.Flow

interface IRepository {
    fun getAllGroupList() : Flow<List<Group>>
}