package com.alexyach.compose.groupsaa.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alexyach.compose.groupsaa.domain.model.Group

@Entity
data class GroupEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
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

fun GroupEntity.toGroup(): Group {
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