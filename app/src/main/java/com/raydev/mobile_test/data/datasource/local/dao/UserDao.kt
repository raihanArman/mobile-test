package com.raydev.mobile_test.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raydev.mobile_test.data.datasource.local.entity.CityEntity
import com.raydev.mobile_test.data.datasource.local.entity.UserEntity
import com.raydev.mobile_test.util.SortType
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    fun getUserFilter(query: String, city: String, sortType: SortType): Flow<List<UserEntity>> =
        if(city.isNotEmpty())
            when(sortType){
                SortType.ASCENDING -> sortAscending(city, query)
                SortType.DESCENDING -> sortDescending(city, query)
            }
        else
            when(sortType){
                SortType.ASCENDING -> sortWithoutCityAscending(query)
                SortType.DESCENDING -> sortWithoutCityDescending(query)
            }

    @Query("SELECT * FROM tb_user")
    fun getUserLocal(): Flow<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUserLocal(users: List<UserEntity>)

    @Query("SELECT * FROM tb_user WHERE city = :city AND name LIKE '%' || :name || '%'  ORDER BY name ASC")
    fun sortAscending(city: String, name: String): Flow<List<UserEntity>>

    @Query("SELECT * FROM tb_user WHERE city = :city AND name LIKE '%' || :name || '%'  ORDER BY name DESC")
    fun sortDescending(city: String, name: String): Flow<List<UserEntity>>

    @Query("SELECT * FROM tb_user WHERE name LIKE '%' || :name || '%'  ORDER BY name ASC")
    fun sortWithoutCityAscending(name: String): Flow<List<UserEntity>>

    @Query("SELECT * FROM tb_user WHERE name LIKE '%' || :name || '%'  ORDER BY name DESC")
    fun sortWithoutCityDescending(name: String): Flow<List<UserEntity>>

    @Query("SELECT * FROM tb_city")
    fun getCityLocal(): Flow<List<CityEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCityLocal(cities: List<CityEntity>)

}