package com.alexyach.compose.groupsaa.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ApiFactory {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://slavaukraine.whf.bz/")
//        .baseUrl("https://alexyach.whf.bz/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create()
}

