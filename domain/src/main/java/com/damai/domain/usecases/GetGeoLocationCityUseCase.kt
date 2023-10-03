package com.damai.domain.usecases

import com.damai.base.networks.FlowUseCase
import com.damai.base.networks.Resource
import com.damai.domain.models.GeoLocationCityModel
import com.damai.domain.repositories.HomeRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by damai007 on 03/October/2023
 */
class GetGeoLocationCityUseCase(
    private val homeRepository: HomeRepository
) : FlowUseCase<String, GeoLocationCityModel>() {

    override suspend fun execute(parameters: String?): Flow<Resource<GeoLocationCityModel>> {
        return homeRepository.getGeoLocationCity(cityName = requireNotNull(parameters))
    }
}