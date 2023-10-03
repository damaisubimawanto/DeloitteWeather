package com.damai.deloitteweather.modules

import com.damai.domain.usecases.GetCurrentWeatherUseCase
import com.damai.domain.usecases.GetForecastWeatherUseCase
import com.damai.domain.usecases.GetGeoLocationCityUseCase
import org.koin.dsl.module

/**
 * Created by damai007 on 02/October/2023
 */

val useCaseModule = module {
    single {
        GetCurrentWeatherUseCase(
            homeRepository = get()
        )
    }
    single {
        GetGeoLocationCityUseCase(
            homeRepository = get()
        )
    }
    single {
        GetForecastWeatherUseCase(
            homeRepository = get()
        )
    }
}