package com.damai.data.mappers

import com.damai.base.BaseMapper
import com.damai.base.extensions.orZero
import com.damai.base.utils.Constants.EMPTY_CODE
import com.damai.base.utils.SimpleDateUtil
import com.damai.data.responses.ForecastResponse
import com.damai.domain.models.ForecastModel
import com.damai.domain.models.WeatherForecastModel
import kotlin.math.roundToInt

/**
 * Created by damai007 on 03/October/2023
 */
class ForecastResponseToForecastModelMapper : BaseMapper<ForecastResponse, ForecastModel>() {

    override fun map(value: ForecastResponse): ForecastModel {
        return ForecastModel(
            timestamp = value.dt.orZero(),
            temperature = value.main?.temp.orZero().roundToInt(),
            temperatureMin = value.main?.tempMin.orZero(),
            temperatureMax = value.main?.tempMax.orZero(),
            weatherType = value.weather?.firstOrNull()?.main,
            dayName = SimpleDateUtil.getDayNameFromUnixTimestamp(unixTimestamp = value.dt.orZero())
        )
    }

    fun generateEmptyWeatherForecastModel() = WeatherForecastModel(
        list = listOf()
    ).also {
        it.status = EMPTY_CODE
    }
}