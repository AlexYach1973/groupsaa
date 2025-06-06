package com.alexyach.compose.groupsaa.domain.entity

 data class Group (
     val name: String,
     val addresses: String,
     val schedule: String,
     val email: String,
     val telephone: String,
     val note: String,
     val longitude: Double,
     val latitude: Double,
     val addressForMap: String = ""
)