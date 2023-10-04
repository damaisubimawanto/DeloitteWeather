package com.damai.deloitteweather.modules

import com.damai.deloitteweather.application.AppDatabase
import com.damai.domain.daos.CityDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

/**
 * Created by damai007 on 04/October/2023
 */
val roomModule = module {
    single {
        AppDatabase.buildDatabase(application = androidApplication())
    }

    factory<CityDao> {
        get<AppDatabase>().cityDao()
    }
}