package com.raydev.mobile_test.data.datasource.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "tb_user")
data class UserEntity(
    @PrimaryKey
    @NotNull
    @ColumnInfo(name = "id")
    var id: String,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "address")
    var address: String,

    @ColumnInfo(name = "phoneNumber")
    var phoneNumber: String,

    @ColumnInfo(name = "city")
    var city: String,
)