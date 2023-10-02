package com.damai.data.repos

import com.damai.base.coroutines.DispatcherProvider
import com.damai.base.networks.NetworkResource
import com.damai.base.networks.Resource
import com.damai.base.utils.Constants.API_KEY
import com.damai.base.utils.Constants.API_METRIC_UNITS
import com.damai.data.apiservices.HomeService
import com.damai.data.mappers.CurrentWeatherResponseToCurrentWeatherModelMapper
import com.damai.domain.models.CurrentWeatherModel
import com.damai.domain.models.CurrentWeatherRequestModel
import com.damai.domain.repositories.HomeRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by damai007 on 02/October/2023
 */
class HomeRepositoryImpl(
    private val homeService: HomeService,
    private val dispatcher: DispatcherProvider,
    private val currentWeatherMapper: CurrentWeatherResponseToCurrentWeatherModelMapper
) : HomeRepository {

    override fun getCurrentWeather(
        requestModel: CurrentWeatherRequestModel
    ): Flow<Resource<CurrentWeatherModel>> {
        return object : NetworkResource<CurrentWeatherModel>(
            dispatcherProvider = dispatcher
        ) {
            override suspend fun remoteFetch(): CurrentWeatherModel {
                val response = homeService.getCurrentWeather(
                    appId = API_KEY,
                    latitude = requestModel.latitude,
                    longitude = requestModel.longitude,
                    units = API_METRIC_UNITS
                )
                return currentWeatherMapper.map(response).also {
                    it.id = requestModel.id
                }
            }
        }.asFlow()
    }
}