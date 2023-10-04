package com.damai.data.mappers

import com.damai.base.BaseMapper
import com.damai.domain.entities.CityEntity
import com.damai.domain.models.CityModel

/**
 * Created by damai007 on 04/October/2023
 */
class CityModelToCityEntityMapper : BaseMapper<CityModel, CityEntity>() {

    override fun map(value: CityModel): CityEntity {
        return CityEntity(
            id = value.id,
            name = value.name,
            temperature = value.temperature,
            weatherIcon = value.weatherIcon,
            weatherIconUrl = value.weatherIconUrl,
            latitude = value.latitude,
            longitude = value.longitude,
            state = value.state,
            createdTime = System.currentTimeMillis(),
            latestUpdated = System.currentTimeMillis()
        )
    }
}