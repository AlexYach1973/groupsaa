package com.alexyach.compose.groupsaa.di

import com.alexyach.compose.groupsaa.data.repository.GroupsRepositoryImpl
import com.alexyach.compose.groupsaa.domain.repository.IRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindIRepository(
        groupsRepositoryImpl: GroupsRepositoryImpl
    ): IRepository

}

