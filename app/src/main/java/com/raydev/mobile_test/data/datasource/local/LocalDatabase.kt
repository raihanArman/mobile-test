package com.raydev.mobile_test.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.raydev.mobile_test.data.datasource.local.dao.UserDao
import com.raydev.mobile_test.data.datasource.local.entity.CityEntity
import com.raydev.mobile_test.data.datasource.local.entity.UserEntity

@Database(
    entities = [UserEntity::class, CityEntity::class],
    version = 1
)
abstract class LocalDatabase : RoomDatabase(){
    abstract val dao: UserDao
}