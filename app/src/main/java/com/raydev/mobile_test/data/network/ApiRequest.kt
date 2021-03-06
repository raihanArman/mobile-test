package com.raydev.mobile_test.data.network

import com.raydev.mobile_test.data.model.City
import com.raydev.mobile_test.data.model.User
import com.raydev.mobile_test.data.request.UserRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiRequest {

    @GET("user")
    suspend fun getUsers(): Response<List<User>>

    @GET("city")
    suspend fun getCities(): Response<List<City>>

    @POST("user")
    suspend fun addUser(@Body user: UserRequest): Response<User>

}