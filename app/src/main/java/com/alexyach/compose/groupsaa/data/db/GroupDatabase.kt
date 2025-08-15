package com.alexyach.compose.groupsaa.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [GroupEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class GroupDatabase : RoomDatabase() {

    abstract fun groupDao(): GroupDao
}