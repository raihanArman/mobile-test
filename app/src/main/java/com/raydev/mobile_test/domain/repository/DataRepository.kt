package com.raydev.mobile_test.domain.repository

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.work.WorkInfo
import com.raydev.mobile_test.data.datasource.local.entity.UserEntity
import com.raydev.mobile_test.data.model.City
import com.raydev.mobile_test.data.model.User
import com.raydev.mobile_test.data.request.UserRequest
import com.raydev.mobile_test.util.ResponseState
import kotlinx.coroutines.flow.Flow

interface DataRepository{
    fun getUsers(): Flow<ResponseState<List<User>>>
    fun getCities(): Flow<ResponseState<List<City>>>
    fun addUser(user: UserRequest): Flow<WorkInfo>
    fun sortAscending(city: String): Flow<List<User>>
    fun sortDescending(city: String): Flow<List<User>>
}