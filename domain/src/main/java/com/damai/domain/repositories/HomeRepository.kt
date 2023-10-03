package com.damai.domain.repositories

import com.damai.base.networks.Resource
import com.damai.domain.models.WeatherForecastModel
import com.damai.domain.models.CurrentWeatherModel
import com.damai.domain.models.CurrentWeatherRequestModel
import com.damai.domain.models.GeoLocationCityModel
import kotlinx.coroutines.flow.Flow
import kotlin.jvm.Throws

/**
 * Created by damai007 on 02/October/2023
 */
interface HomeRepository {

    @Throws(Exception::class)
    fun getCurrentWeather(
        requestModel: CurrentWeatherRequestModel
    ): Flow<Resource<CurrentWeatherModel>>

    @Throws(Exception::class)
    fun getGeoLocationCity(
        cityName: String
    ): Flow<Resource<GeoLocationCityModel>>

    @Throws(Exception::class)
    fun getForecastWeather(
        requestModel: CurrentWeatherRequestModel
    ): Flow<Resource<WeatherForecastModel>>
}