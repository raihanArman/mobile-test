package com.raydev.mobile_test.data.datasource.local

import com.raydev.mobile_test.data.datasource.local.dao.UserDao
import com.raydev.mobile_test.data.datasource.local.entity.CityEntity
import com.raydev.mobile_test.data.datasource.local.entity.UserEntity
import com.raydev.mobile_test.data.model.User
import com.raydev.mobile_test.util.SortType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val dao: UserDao,
) {
    fun getUserLocal(): Flow<List<UserEntity>> = dao.getUserLocal()
    suspend fun insertUserLocal(data: List<UserEntity>) = dao.insertUserLocal(data)
    fun getUserFilter(name: String, city: String, sortType: SortType): Flow<List<UserEntity>> =
        dao.getUserFilter(name, city, sortType)

    fun getCityLocal(): Flow<List<CityEntity>> = dao.getCityLocal()
    suspend fun insertCityLocal(data: List<CityEntity>) = dao.insertCityLocal(data)
}