package com.raydev.mobile_test.di

import com.google.gson.GsonBuilder
import com.raydev.mobile_test.BuildConfig
import com.raydev.mobile_test.data.network.ApiRequest
import com.raydev.mobile_test.util.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        return httpLoggingInterceptor.apply { httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY }
    }

    @Singleton
    @Provides
    fun provideHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {

        val lists: List<ConnectionSpec> =
            Arrays.asList(ConnectionSpec.COMPATIBLE_TLS, ConnectionSpec.CLEARTEXT)

        val clientBuilder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            clientBuilder.addInterceptor(loggingInterceptor)
        }

        clientBuilder.readTimeout(120, TimeUnit.SECONDS)
        clientBuilder.connectTimeout(30, TimeUnit.SECONDS)
        clientBuilder.connectionSpecs(lists)
        clientBuilder.retryOnConnectionFailure(true)

        return clientBuilder.build()
    }

    @Singleton
    @Provides
    fun provideGsonBuilder(): GsonBuilder {
        return GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
    }

    @Singleton
    @Provides
    fun provideConverterFactory(
        gsonBuilder: GsonBuilder
    ): GsonConverterFactory {
        return GsonConverterFactory.create(gsonBuilder.create())
    }

    @Singleton
    @Provides
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }



    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiRequest {
        return retrofit.create(ApiRequest::class.java)
    }


}