package com.damai.data.repos

import com.damai.base.coroutines.DispatcherProvider
import com.damai.base.extensions.isCacheValid
import com.damai.base.networks.NetworkResource
import com.damai.base.networks.Resource
import com.damai.base.utils.Constants.API_KEY
import com.damai.base.utils.Constants.API_METRIC_UNITS
import com.damai.base.utils.Constants.CACHE_DAYS
import com.damai.base.utils.Constants.QUERY_LIMIT
import com.damai.base.utils.Constants.SUCCESS_CODE
import com.damai.data.apiservices.HomeService
import com.damai.data.mappers.CityEntityToCityModelMapper
import com.damai.data.mappers.CityModelToCityEntityMapper
import com.damai.data.mappers.CityModelToGeoCityEntityMapper
import com.damai.data.mappers.CurrentWeatherResponseToCurrentWeatherModelMapper
import com.damai.data.mappers.ForecastResponseToForecastModelMapper
import com.damai.data.mappers.GeoCityEntityToCityModelMapper
import com.damai.data.mappers.GeoLocationCityResponseToGeoLocationCityModelMapper
import com.damai.domain.daos.CityDao
import com.damai.domain.daos.GeoCityDao
import com.damai.domain.models.WeatherForecastModel
import com.damai.domain.models.CurrentWeatherModel
import com.damai.domain.models.CurrentWeatherRequestModel
import com.damai.domain.models.GeoLocationCityModel
import com.damai.domain.repositories.HomeRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by damai007 on 02/October/2023
 */
class HomeRepositoryImpl(
    private val homeService: HomeService,
    private val dispatcher: DispatcherProvider,
    private val cityDao: CityDao,
    private val geoCityDao: GeoCityDao,
    private val currentWeatherMapper: CurrentWeatherResponseToCurrentWeatherModelMapper,
    private val geoLocationCityMapper: GeoLocationCityResponseToGeoLocationCityModelMapper,
    private val forecastMapper: ForecastResponseToForecastModelMapper,
    private val cityEntityToModelMapper: CityEntityToCityModelMapper,
    private val cityModelToEntityMapper: CityModelToCityEntityMapper,
    private val geoCityEntityToModelMapper: GeoCityEntityToCityModelMapper,
    private val geoCityModelToEntityMapper: CityModelToGeoCityEntityMapper
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
                return currentWeatherMapper.map(response)
            }

            override fun shouldFetchFromRemote(): Boolean = false

            override fun shouldSaveToLocal(): Boolean = true

            override suspend fun localFetch(): CurrentWeatherModel? {
                val cityEntity = cityDao.getSavedCity(
                    latitude = requestModel.latitude,
                    longitude = requestModel.longitude
                )
                return cityEntity?.let {
                    if (it.latestUpdated.isCacheValid(days = CACHE_DAYS)) {
                        CurrentWeatherModel(
                            cityModel = cityEntityToModelMapper.map(it)
                        )
                    } else {
                        null
                    }
                }
            }

            override suspend fun saveLocal(data: CurrentWeatherModel) {
                cityDao.insert(cityEntity = cityModelToEntityMapper.map(data.cityModel))
            }
        }.asFlow()
    }

    override fun getGeoLocationCity(cityName: String): Flow<Resource<GeoLocationCityModel>> {
        return object : NetworkResource<GeoLocationCityModel>(
            dispatcherProvider = dispatcher
        ) {
            override suspend fun remoteFetch(): GeoLocationCityModel {
                val response = homeService.getGeoLocationCity(
                    appId = API_KEY,
                    query = cityName,
                    limit = QUERY_LIMIT
                )
                return response.firstOrNull()?.let { geoLocationCity ->
                    geoLocationCityMapper.map(value = geoLocationCity).also {
                        it.status = SUCCESS_CODE
                    }
                } ?: geoLocationCityMapper.generateEmptyModel()
            }

            override fun shouldFetchFromRemote(): Boolean = false

            override fun shouldSaveToLocal(): Boolean = true

            override suspend fun localFetch(): GeoLocationCityModel? {
                val geoCityEntity = geoCityDao.getGeoCity(cityName = cityName)
                return geoCityEntity?.let {
                    if (it.latestUpdated.isCacheValid(days = CACHE_DAYS)) {
                        GeoLocationCityModel(
                            cityModel = geoCityEntityToModelMapper.map(it)
                        )
                    } else {
                        null
                    }
                }
            }

            override suspend fun saveLocal(data: GeoLocationCityModel) {
                geoCityDao.insert(geoCityEntity = geoCityModelToEntityMapper.map(data.cityModel))
            }
        }.asFlow()
    }

    override fun getForecastWeather(requestModel: CurrentWeatherRequestModel): Flow<Resource<WeatherForecastModel>> {
        return object : NetworkResource<WeatherForecastModel>(
            dispatcherProvider = dispatcher
        ) {
            override suspend fun remoteFetch(): WeatherForecastModel {
                val response = homeService.getForecastWeather(
                    appId = API_KEY,
                    latitude = requestModel.latitude,
                    longitude = requestModel.longitude,
                    units = API_METRIC_UNITS
                )
                return response.list?.let { forecastResponseList ->
                    WeatherForecastModel(
                        list = forecastMapper.map(forecastResponseList)
                    ).also {
                        it.status = response.cod
                    }
                } ?: forecastMapper.generateEmptyWeatherForecastModel()
            }
        }.asFlow()
    }
}