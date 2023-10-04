package com.damai.deloitteweather.modules

import com.damai.data.repos.HomeRepositoryImpl
import com.damai.domain.repositories.HomeRepository
import org.koin.dsl.module

/**
 * Created by damai007 on 02/October/2023
 */

val repositoryModule = module {
    single<HomeRepository> {
        HomeRepositoryImpl(
            homeService = get(),
            dispatcher = get(),
            cityDao = get(),
            geoCityDao = get(),
            currentWeatherMapper = get(),
            geoLocationCityMapper = get(),
            forecastMapper = get(),
            cityEntityToModelMapper = get(),
            cityModelToEntityMapper = get(),
            geoCityEntityToModelMapper = get(),
            geoCityModelToEntityMapper = get()
        )
    }
}