package com.alexyach.compose.groupsaa.data.model

import com.alexyach.compose.groupsaa.domain.entity.Group

data class ResponseDto(
    val name: String,
    val addresses: String,
    val schedule: String,
    val telephone: String,
    val email: String,
    val note: String,
    val latitude: Double,
    val longitude: Double,
    val addressForMap: String
)

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