package com.alexyach.compose.groupsaa.data.model

import com.alexyach.compose.groupsaa.data.db.GroupDao
import com.alexyach.compose.groupsaa.data.db.GroupEntity
import com.alexyach.compose.groupsaa.domain.model.Group

data class ResponseDto(
    val name: String,
    val addresses: String,
    val schedule: List<String>,
    val telephone: String,
    val email: String,
    val note: String,
    val latitude: Double,
    val longitude: Double,
    val addressForMap: String
)
/*PHP array('19:00','19:00','19:00','19:00','19:00','19:00','19:00') */

fun ResponseDto.toGroup() : Group {
    return Group(
        name = this.name,
        addresses = this.addresses,
        schedule = this.schedule,
        email = this.email,
        telephone = this.telephone,
        note = this.note,
        latitude = this.latitude,
        longitude = this.longitude,
        addressForMap = this.addressForMap
    )
}

fun ResponseDto.toGroupEntity() : GroupEntity {
    return GroupEntity(
        id = 0,
        name = this.name,
        addresses = this.addresses,
        schedule = this.schedule,
        email = this.email,
        telephone = this.telephone,
        note = this.note,
        latitude = this.latitude,
        longitude = this.longitude,
        addressForMap = this.addressForMap
    )
}