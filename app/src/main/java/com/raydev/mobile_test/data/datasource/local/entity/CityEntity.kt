package com.raydev.mobile_test.data.datasource.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "tb_city")
data class CityEntity(
    @ColumnInfo(name = "name")
    val name: String,

    @PrimaryKey
    @NotNull
    @ColumnInfo(name = "id")
    val id: String,
)