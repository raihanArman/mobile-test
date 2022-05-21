package com.raydev.mobile_test.data.mapper

import com.raydev.mobile_test.data.datasource.local.entity.CityEntity
import com.raydev.mobile_test.data.datasource.local.entity.UserEntity
import com.raydev.mobile_test.data.model.City
import com.raydev.mobile_test.data.model.User

object CityMapper {
    fun mapResponseToEntities(input: List<City>): List<CityEntity>{
        val data = ArrayList<CityEntity>()
        input.map {
            val city = CityEntity(
                name = it.name,
                id = it.id,
            )
            data.add(city)
        }
        return data
    }

    fun mapEntitiesToDomain(input: List<CityEntity>): List<City> =
        input.map {
            City(
                id = it.id,
                name = it.name,
            )
        }

    fun mapDomainToEntity(input: City) = CityEntity(
        id = input.id,
        name = input.name,
    )
}