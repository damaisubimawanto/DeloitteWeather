package com.damai.data.apiservices

import com.damai.base.utils.Constants.API_VERSION_1
import com.damai.base.utils.Constants.API_VERSION_2_POINT_5
import com.damai.data.responses.CurrentWeatherResponse
import com.damai.data.responses.GeoLocationCityResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by damai007 on 02/October/2023
 */
interface HomeService {

    @GET("/data/${API_VERSION_2_POINT_5}/weather")
    suspend fun getCurrentWeather(
        @Query("appid") appId: String,
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String
    ): CurrentWeatherResponse

    @GET("/geo/${API_VERSION_1}/direct")
    suspend fun getGeoLocationCity(
        @Query("appid") appId: String,
        @Query("q") query: String,
        @Query("limit") limit: Int
    ): List<GeoLocationCityResponse>
}