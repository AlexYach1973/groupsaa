package com.alexyach.compose.groupsaa.domain.model

import com.alexyach.compose.groupsaa.data.db.GroupEntity
import kotlin.String

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

fun Group.toGroupEntity(): GroupEntity {
    return GroupEntity(
        id = 0,
        name = name,
        addresses = addresses,
        schedule = schedule,
        email = email,
        telephone = telephone,
        note = note,
        latitude = latitude,
        longitude = longitude,
        distance = distance,
        addressForMap = addressForMap
    )
}