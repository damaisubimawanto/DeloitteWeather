package com.damai.domain.models

import com.damai.base.BaseModel

/**
 * Created by damai007 on 02/October/2023
 */
data class CityModel(
    val id: Int,
    val name: String?,
    var temperature: Int,
    var weatherType: String?,
    val latitude: Double,
    val longitude: Double
)

data class CurrentWeatherModel(
    var id: Int? = null,
    val cityModel: CityModel
) : BaseModel()
