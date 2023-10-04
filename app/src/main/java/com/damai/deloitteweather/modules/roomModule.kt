package com.damai.deloitteweather.modules

import com.damai.deloitteweather.application.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

/**
 * Created by damai007 on 04/October/2023
 */
val roomModule = module {
    single {
        AppDatabase.buildDatabase(application = androidApplication())
    }

    factory {
        get<AppDatabase>().cityDao()
    }

    factory {
        get<AppDatabase>().geoCityDao()
    }
}