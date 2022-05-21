package com.raydev.mobile_test.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raydev.mobile_test.data.datasource.local.entity.CityEntity
import com.raydev.mobile_test.data.datasource.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM tb_user")
    fun getUserLocal(): Flow<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUserLocal(users: List<UserEntity>)

    @Query("SELECT * FROM tb_user WHERE city LIKE :city ORDER BY name ASC")
    fun sortAscending(city: String): Flow<List<UserEntity>>

    @Query("SELECT * FROM tb_user WHERE city LIKE :city ORDER BY name DESC")
    fun sortDescending(city: String): Flow<List<UserEntity>>

    @Query("SELECT * FROM tb_city")
    fun getCityLocal(): Flow<List<CityEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCityLocal(cities: List<CityEntity>)

}