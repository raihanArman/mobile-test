package com.raydev.mobile_test.di

import com.raydev.mobile_test.data.repository.DataRepositoryImpl
import com.raydev.mobile_test.domain.repository.DataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindIterator(
        repository: DataRepositoryImpl
    ): DataRepository

}