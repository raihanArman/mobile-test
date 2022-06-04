package com.raydev.mobile_test.data.repository

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.work.WorkInfo
import com.raydev.mobile_test.data.datasource.local.LocalDataSource
import com.raydev.mobile_test.data.datasource.local.entity.UserEntity
import com.raydev.mobile_test.data.datasource.remote.RemoteDataSource
import com.raydev.mobile_test.data.mapper.CityMapper
import com.raydev.mobile_test.data.mapper.UserMapper
import com.raydev.mobile_test.data.model.City
import com.raydev.mobile_test.data.model.User
import com.raydev.mobile_test.data.request.UserRequest
import com.raydev.mobile_test.domain.repository.DataRepository
import com.raydev.mobile_test.util.CheckConnection
import com.raydev.mobile_test.util.NetworkBoundResource
import com.raydev.mobile_test.util.ResponseState
import com.raydev.mobile_test.util.SortType
import com.raydev.mobile_test.work.UserHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val checkConnection: CheckConnection,
    private val userHelper: UserHelper
): DataRepository {
    override fun getUsers(): Flow<ResponseState<List<User>>> =
        object : NetworkBoundResource<List<User>, List<User>>(){
            override fun loadFromDB(): Flow<List<User>> {
                return localDataSource.getUserLocal().map {
                    UserMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<User>?): Boolean {
                return checkConnection.isOnline()
            }

            override suspend fun createCall(): Flow<ResponseState<List<User>>> {
                return remoteDataSource.getUsers()
            }

            override suspend fun saveCallResult(data: List<User>) {
                val users = UserMapper.mapResponseToEntities(data)
                localDataSource.insertUserLocal(users)
            }

        }.asFlow()

    override fun getCities(): Flow<ResponseState<List<City>>> =
        object : NetworkBoundResource<List<City>, List<City>>(){
            override fun loadFromDB(): Flow<List<City>> {
                return localDataSource.getCityLocal().map {
                    CityMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<City>?): Boolean {
                return data == null || data.isEmpty()
            }

            override suspend fun createCall(): Flow<ResponseState<List<City>>> {
                return remoteDataSource.getCities()
            }

            override suspend fun saveCallResult(data: List<City>) {
                val cities = CityMapper.mapResponseToEntities(data)
                localDataSource.insertCityLocal(cities)
            }

        }.asFlow()

    override fun addUser(user: UserRequest): Flow<WorkInfo> {
        return userHelper.addUser(user)
    }

    override fun getUserFilter(name: String, city: String, sortType: SortType): Flow<List<User>> {
        return localDataSource.getUserFilter(name, city, sortType).map {
            UserMapper.mapEntitiesToDomain(it)
        }
    }
}