package com.alexyach.compose.groupsaa.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [GroupEntity::class], version = 1)
abstract class GroupDatabase : RoomDatabase() {

    abstract fun groupDao(): GroupDao

    companion object {
        @Volatile
        private var INSTANCE: GroupDatabase? = null

        fun getDatabase(context: Context): GroupDatabase {
            return INSTANCE?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context = context,
                    klass = GroupDatabase::class.java,
                    name = "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }

    }

}