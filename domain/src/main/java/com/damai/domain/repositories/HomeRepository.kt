package com.damai.domain.repositories

import com.damai.base.networks.Resource
import com.damai.domain.models.CurrentWeatherModel
import com.damai.domain.models.CurrentWeatherRequestModel
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
}