package com.alexyach.compose.groupsaa.domain.model

 data class Group (
     val name: String,
     val addresses: String,
     val schedule: String,
     val email: String,
     val telephone: String,
     val note: String,
     val latitude: Double,
     val longitude: Double,
     var distance: Double = 0.0,
     val addressForMap: String = ""
)