package com.damai.data.mappers

import com.damai.base.BaseMapper
import com.damai.base.extensions.orZero
import com.damai.base.utils.Constants.WEATHER_ICON_HEAD
import com.damai.base.utils.Constants.WEATHER_ICON_TAIL_1X
import com.damai.data.responses.CurrentWeatherResponse
import com.damai.domain.models.CityModel
import com.damai.domain.models.CurrentWeatherModel
import kotlin.math.roundToInt

/**
 * Created by damai007 on 03/October/2023
 */
class CurrentWeatherResponseToCurrentWeatherModelMapper : BaseMapper<CurrentWeatherResponse, CurrentWeatherModel>() {

    override fun map(value: CurrentWeatherResponse): CurrentWeatherModel {
        return CurrentWeatherModel(
            cityModel = CityModel(
                id = value.id.orZero(),
                name = value.name.orEmpty(),
                temperature = value.main?.feelsLike.orZero().roundToInt(),
                weatherIcon = value.weather?.firstOrNull()?.icon,
                weatherIconUrl = value.weather?.firstOrNull()?.icon?.let {
                    "${WEATHER_ICON_HEAD}$it${WEATHER_ICON_TAIL_1X}"
                },
                latitude = value.coord?.lat.orZero(),
                longitude = value.coord?.lon.orZero(),
                state = null
            )
        ).also {
            it.status = value.cod
        }
    }
}