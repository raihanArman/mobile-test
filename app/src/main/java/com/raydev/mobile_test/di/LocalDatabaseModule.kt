package com.raydev.mobile_test.di

import android.app.Application
import androidx.room.Room
import com.raydev.mobile_test.data.datasource.local.LocalDatabase
import com.raydev.mobile_test.data.datasource.local.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDatabaseModule {
    @Provides
    @Singleton
    fun provideLocalDatabase(app: Application): LocalDatabase{
        return Room.databaseBuilder(
            app,
            LocalDatabase::class.java,
            "user_app"
        ).build()
    }

    @Provides
    fun provideUserDao(localDatabase: LocalDatabase): UserDao{
        return localDatabase.dao
    }

}