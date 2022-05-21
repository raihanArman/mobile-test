package com.raydev.mobile_test.data.mapper

import com.raydev.mobile_test.data.datasource.local.entity.UserEntity
import com.raydev.mobile_test.data.model.User

object UserMapper {
    fun mapResponseToEntities(input: List<User>): List<UserEntity>{
        val data = ArrayList<UserEntity>()
        input.map {
            val user = UserEntity(
                name = it.name,
                address = it.address,
                phoneNumber = it.phoneNumber,
                id = it.id,
                city = it.city,
            )
            data.add(user)
        }
        return data
    }

    fun mapEntitiesToDomain(input: List<UserEntity>): List<User> =
        input.map {
            User(
                id = it.id,
                name = it.name,
                address = it.address,
                phoneNumber = it.phoneNumber,
                city = it.city,
            )
        }

    fun mapDomainToEntity(input: User) = UserEntity(
        id = input.id,
        name = input.name,
        address = input.address,
        phoneNumber = input.phoneNumber,
        city = input.city,
    )
}