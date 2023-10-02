package com.damai.domain.usecases

import com.damai.base.networks.FlowUseCase
import com.damai.base.networks.Resource
import com.damai.domain.models.CurrentWeatherModel
import com.damai.domain.models.CurrentWeatherRequestModel
import com.damai.domain.repositories.HomeRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by damai007 on 03/October/2023
 */
class GetCurrentWeatherUseCase(
    private val homeRepository: HomeRepository
) : FlowUseCase<CurrentWeatherRequestModel, CurrentWeatherModel>() {

    override suspend fun execute(parameters: CurrentWeatherRequestModel?): Flow<Resource<CurrentWeatherModel>> {
        return homeRepository.getCurrentWeather(requestModel = requireNotNull(parameters))
    }
}