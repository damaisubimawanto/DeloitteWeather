package com.damai.data.mappers

import com.damai.base.BaseMapper
import com.damai.base.extensions.orZero
import com.damai.base.utils.Constants.EMPTY_CODE
import com.damai.data.responses.GeoLocationCityResponse
import com.damai.domain.models.CityModel
import com.damai.domain.models.GeoLocationCityModel

/**
 * Created by damai007 on 03/October/2023
 */
class GeoLocationCityResponseToGeoLocationCityModelMapper : BaseMapper<GeoLocationCityResponse, GeoLocationCityModel>() {
    private var incrementId: Int = 0

    override fun map(value: GeoLocationCityResponse): GeoLocationCityModel {
        return GeoLocationCityModel(
            cityModel = CityModel(
                id = ++incrementId,
                name = value.name,
                latitude = value.lat.orZero(),
                longitude = value.lon.orZero(),
                temperature = 0,
                weatherIcon = null,
                weatherIconUrl = null,
                state = value.state
            )
        )
    }

    fun generateEmptyModel() = GeoLocationCityModel(
        cityModel = CityModel(
            id = 0,
            name = null,
            latitude = 0.0,
            longitude = 0.0,
            temperature = 0,
            weatherIcon = null,
            weatherIconUrl = null,
            state = null
        )
    ).also {
        it.status = EMPTY_CODE
    }
}