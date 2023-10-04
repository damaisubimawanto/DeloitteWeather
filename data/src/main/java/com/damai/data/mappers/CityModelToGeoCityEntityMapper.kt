package com.damai.data.mappers

import com.damai.base.BaseMapper
import com.damai.domain.entities.GeoCityEntity
import com.damai.domain.models.CityModel

/**
 * Created by damai007 on 04/October/2023
 */
class CityModelToGeoCityEntityMapper : BaseMapper<CityModel, GeoCityEntity>() {

    override fun map(value: CityModel): GeoCityEntity {
        return GeoCityEntity(
            id = value.id,
            name = value.name,
            latitude = value.latitude,
            longitude = value.longitude,
            state = value.state,
            createdTime = System.currentTimeMillis(),
            latestUpdated = System.currentTimeMillis()
        )
    }
}