package com.damai.deloitteweather.modules

import com.damai.data.mappers.CityEntityToCityModelMapper
import com.damai.data.mappers.CityModelToCityEntityMapper
import com.damai.data.mappers.CityModelToGeoCityEntityMapper
import com.damai.data.mappers.CurrentWeatherResponseToCurrentWeatherModelMapper
import com.damai.data.mappers.ForecastResponseToForecastModelMapper
import com.damai.data.mappers.GeoCityEntityToCityModelMapper
import com.damai.data.mappers.GeoLocationCityResponseToGeoLocationCityModelMapper
import org.koin.dsl.module

/**
 * Created by damai007 on 02/October/2023
 */

val mapperModule = module {
    factory {
        CurrentWeatherResponseToCurrentWeatherModelMapper()
    }
    factory {
        GeoLocationCityResponseToGeoLocationCityModelMapper()
    }
    factory {
        ForecastResponseToForecastModelMapper()
    }
    factory {
        CityEntityToCityModelMapper()
    }
    factory {
        CityModelToCityEntityMapper()
    }
    factory {
        GeoCityEntityToCityModelMapper()
    }
    factory {
        CityModelToGeoCityEntityMapper()
    }
}