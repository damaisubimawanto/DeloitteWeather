package com.damai.deloitteweather.ui.detail

import androidx.activity.viewModels
import com.damai.base.BaseActivity
import com.damai.deloitteweather.R
import com.damai.deloitteweather.databinding.ActivityDetailCityWeatherBinding

/**
 * Created by damai007 on 03/October/2023
 */
class WeatherDetailActivity : BaseActivity<ActivityDetailCityWeatherBinding, WeatherDetailViewModel>() {

    //region Variables

    //endregion `Variables`

    override val layoutResource: Int = R.layout.activity_detail_city_weather

    override val viewModel: WeatherDetailViewModel by viewModels()

    override fun ActivityDetailCityWeatherBinding.viewInitialization() {
        TODO("Not yet implemented")
    }

    override fun ActivityDetailCityWeatherBinding.setupListeners() {
        TODO("Not yet implemented")
    }

    override fun ActivityDetailCityWeatherBinding.setupObservers() {
        TODO("Not yet implemented")
    }

    override fun ActivityDetailCityWeatherBinding.onPreparationFinished() {
        TODO("Not yet implemented")
    }
}