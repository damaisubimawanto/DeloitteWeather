package com.damai.domain.models

import com.damai.base.BaseModel

/**
 * Created by damai007 on 03/October/2023
 */
data class ForecastModel(
    val timestamp: Long,
    val temperature: Int,
    val temperatureMin: Double,
    val temperatureMax: Double,
    val weatherType: String?,
    val dayName: String
)

data class WeatherForecastModel(
    val list: List<ForecastModel>
) : BaseModel()