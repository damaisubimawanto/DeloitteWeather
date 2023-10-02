package com.damai.deloitteweather.modules

import com.damai.deloitteweather.ui.main.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by damai007 on 02/October/2023
 */

val viewModelModule = module {
    viewModel {
        MainViewModel(
            app = androidApplication(),
            dispatcher = get()
        )
    }
}