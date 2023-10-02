package com.damai.deloitteweather.modules

import com.damai.data.mappers.CurrentWeatherResponseToCurrentWeatherModelMapper
import org.koin.dsl.module

/**
 * Created by damai007 on 02/October/2023
 */

val mapperModule = module {
    factory {
        CurrentWeatherResponseToCurrentWeatherModelMapper()
    }
}