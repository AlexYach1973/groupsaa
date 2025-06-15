package com.alexyach.compose.groupsaa.data.network

import com.alexyach.compose.groupsaa.data.model.ResponseDto
import retrofit2.http.GET

interface ApiService {
    @GET("wp-json/custom/v2/grouplist")
    suspend fun getGroupList(): List<ResponseDto>
}