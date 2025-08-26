package com.alexyach.compose.groupsaa.di

import android.content.Context
import androidx.room.Room
import com.alexyach.compose.groupsaa.data.db.GroupDao
import com.alexyach.compose.groupsaa.data.db.GroupDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun  provideDatabase(@ApplicationContext context: Context): GroupDatabase =
        Room.databaseBuilder(
            context = context,
            klass = GroupDatabase::class.java,
            name = "app_database"
        ).build()

    @Provides
    fun provideDao(db: GroupDatabase): GroupDao = db.groupDao()

}