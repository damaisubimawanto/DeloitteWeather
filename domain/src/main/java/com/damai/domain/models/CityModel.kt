package com.damai.domain.models

import com.damai.base.BaseModel

/**
 * Created by damai007 on 02/October/2023
 */
data class CityModel(
    val id: Int,
    val name: String?,
    var temperature: Int,
    var weatherIcon: String?,
    var weatherIconUrl: String?,
    val latitude: Double,
    val longitude: Double,
    val state: String?
)

data class CurrentWeatherModel(
    val cityModel: CityModel
) : BaseModel()

data class GeoLocationCityModel(
    val cityModel: CityModel
) : BaseModel()
