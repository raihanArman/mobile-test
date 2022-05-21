package com.raydev.mobile_test.data.datasource.local

import com.raydev.mobile_test.data.datasource.local.dao.UserDao
import com.raydev.mobile_test.data.datasource.local.entity.CityEntity
import com.raydev.mobile_test.data.datasource.local.entity.UserEntity
import com.raydev.mobile_test.data.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val dao: UserDao,
) {
    fun getUserLocal(): Flow<List<UserEntity>> = dao.getUserLocal()
    suspend fun insertUserLocal(data: List<UserEntity>) = dao.insertUserLocal(data)
    fun sortAscending(city: String): Flow<List<UserEntity>> = dao.sortAscending(city)
    fun sortDescending(city: String): Flow<List<UserEntity>> = dao.sortDescending(city)

    fun getCityLocal(): Flow<List<CityEntity>> = dao.getCityLocal()
    suspend fun insertCityLocal(data: List<CityEntity>) = dao.insertCityLocal(data)
}