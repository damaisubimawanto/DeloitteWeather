package com.damai.deloitteweather.modules

import com.damai.deloitteweather.navigations.PageNavigationApi
import com.damai.deloitteweather.navigations.PageNavigationApiImpl
import org.koin.dsl.module

/**
 * Created by damai007 on 02/October/2023
 */

val pageNavigationModule = module {
    factory<PageNavigationApi> {
        PageNavigationApiImpl()
    }
}