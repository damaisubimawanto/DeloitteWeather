package com.damai.deloitteweather.ui.detail

import com.damai.base.BaseActivity
import com.damai.base.extensions.observe
import com.damai.base.extensions.showShortToast
import com.damai.base.utils.EventObserver
import com.damai.deloitteweather.R
import com.damai.deloitteweather.databinding.ActivityDetailCityWeatherBinding
import com.damai.deloitteweather.ui.detail.adapter.WeatherDailyAdapter
import com.damai.deloitteweather.ui.detail.adapter.WeatherHourlyAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by damai007 on 03/October/2023
 */
class WeatherDetailActivity : BaseActivity<ActivityDetailCityWeatherBinding, WeatherDetailViewModel>() {

    //region Variables
    private lateinit var weatherHourlyAdapter: WeatherHourlyAdapter
    private lateinit var weatherDailyAdapter: WeatherDailyAdapter
    //endregion `Variables`

    override val layoutResource: Int = R.layout.activity_detail_city_weather

    override val viewModel: WeatherDetailViewModel by viewModel()

    override fun ActivityDetailCityWeatherBinding.viewInitialization() {
        vm = viewModel
        lifecycleOwner = this@WeatherDetailActivity
        viewModel.initFromIntent(intent)

        with(rvWeatherHourly) {
            weatherHourlyAdapter = WeatherHourlyAdapter()
            adapter = weatherHourlyAdapter
        }

        with(rvWeatherDaily) {
            weatherDailyAdapter = WeatherDailyAdapter()
            adapter = weatherDailyAdapter
        }
    }

    override fun ActivityDetailCityWeatherBinding.setupListeners() {
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    override fun ActivityDetailCityWeatherBinding.setupObservers() {
        observe(viewModel.weatherHourlyListLiveData) {
            weatherHourlyAdapter.setNewData(newData = it)
        }

        observe(viewModel.weatherDailyListLiveData) {
            weatherDailyAdapter.setNewData(newData = it)
        }

        observe(viewModel.errorMessageLiveData, EventObserver {
            if (it.first) {
                it.second?.let { errorMessage ->
                    showShortToast(message = errorMessage)
                }
            }
        })
    }

    override fun ActivityDetailCityWeatherBinding.onPreparationFinished() {
        viewModel.getForecastWeather()
    }
}