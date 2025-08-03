package com.alexyach.compose.groupsaa.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

const val baseURL="https://gso.aa.kiev.ua/"
//const val baseURL="https://slavaukraine.whf.bz/"
object ApiFactory {
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create()
}

