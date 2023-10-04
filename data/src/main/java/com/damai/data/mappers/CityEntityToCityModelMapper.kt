package com.damai.data.mappers

import com.damai.base.BaseMapper
import com.damai.domain.entities.CityEntity
import com.damai.domain.models.CityModel

/**
 * Created by damai007 on 04/October/2023
 */
class CityEntityToCityModelMapper : BaseMapper<CityEntity, CityModel>() {

    override fun map(value: CityEntity): CityModel {
        return CityModel(
            id = value.id,
            name = value.name,
            temperature = value.temperature,
            weatherIcon = value.weatherIcon,
            weatherIconUrl = value.weatherIconUrl,
            latitude = value.latitude,
            longitude = value.longitude,
            state = value.state
        )
    }
}