package com.raydev.mobile_test.di

import android.content.Context
import com.raydev.mobile_test.domain.repository.DataRepository
import com.raydev.mobile_test.domain.usecase.AddUserUseCase
import com.raydev.mobile_test.domain.usecase.GetCitiesUseCase
import com.raydev.mobile_test.domain.usecase.GetUsersUseCase
import com.raydev.mobile_test.util.CheckConnection
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @Provides
    fun providesGetUserUseCase(dataRepository: DataRepository): GetUsersUseCase {
        return GetUsersUseCase(dataRepository)
    }

    @Provides
    fun providesGetCitiesUseCase(dataRepository: DataRepository): GetCitiesUseCase {
        return GetCitiesUseCase(dataRepository)
    }

    @Provides
    fun providesAddUseryUseCase(dataRepository: DataRepository): AddUserUseCase {
        return AddUserUseCase(dataRepository)
    }

}