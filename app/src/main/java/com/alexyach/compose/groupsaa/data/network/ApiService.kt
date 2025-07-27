package com.alexyach.compose.groupsaa.data.network

import com.alexyach.compose.groupsaa.data.model.ResponseDto
import retrofit2.http.GET

interface ApiService {
    @GET("wp-json/groups/v1/listgroup")
    suspend fun getGroupList(): List<ResponseDto>
}