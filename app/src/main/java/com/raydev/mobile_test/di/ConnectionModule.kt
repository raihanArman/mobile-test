package com.raydev.mobile_test.di

import android.content.Context
import com.raydev.mobile_test.util.CheckConnection
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ConnectionModule {
    @Singleton
    @Provides
    fun providesConnection(@ApplicationContext context: Context): CheckConnection {
        return CheckConnection(context)
    }
}