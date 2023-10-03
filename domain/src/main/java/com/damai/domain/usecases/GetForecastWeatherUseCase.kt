package com.damai.domain.usecases

import com.damai.base.networks.FlowUseCase
import com.damai.base.networks.Resource
import com.damai.domain.models.CurrentWeatherRequestModel
import com.damai.domain.models.WeatherForecastModel
import com.damai.domain.repositories.HomeRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by damai007 on 03/October/2023
 */
class GetForecastWeatherUseCase(
    private val homeRepository: HomeRepository
) : FlowUseCase<CurrentWeatherRequestModel, WeatherForecastModel>() {

    override suspend fun execute(parameters: CurrentWeatherRequestModel?): Flow<Resource<WeatherForecastModel>> {
        return homeRepository.getForecastWeather(requestModel = requireNotNull(parameters))
    }
}