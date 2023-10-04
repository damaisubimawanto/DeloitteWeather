package com.damai.data.mappers

import com.damai.base.BaseMapper
import com.damai.domain.entities.GeoCityEntity
import com.damai.domain.models.CityModel

/**
 * Created by damai007 on 04/October/2023
 */
class GeoCityEntityToCityModelMapper : BaseMapper<GeoCityEntity, CityModel>() {

    override fun map(value: GeoCityEntity): CityModel {
        return CityModel(
            id = value.id,
            name = value.name,
            latitude = value.latitude,
            longitude = value.longitude,
            state = value.state,
            temperature = 0,
            weatherIcon = null,
            weatherIconUrl = null
        )
    }
}