package com.damai.deloitteweather.ui.detail

import android.app.Application
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.damai.base.BaseViewModel
import com.damai.base.coroutines.DispatcherProvider
import com.damai.base.extensions.asLiveData
import com.damai.base.extensions.orZero
import com.damai.base.networks.Resource
import com.damai.base.utils.Constants.ARGS_CITY_NAME
import com.damai.base.utils.Constants.ARGS_LATITUDE
import com.damai.base.utils.Constants.ARGS_LONGITUDE
import com.damai.base.utils.Constants.ARGS_TEMPERATURE
import com.damai.base.utils.Constants.ARGS_WEATHER_ICON_URL
import com.damai.base.utils.Event
import com.damai.deloitteweather.R
import com.damai.deloitteweather.application.MyApplication
import com.damai.domain.models.CityModel
import com.damai.domain.models.CurrentWeatherRequestModel
import com.damai.domain.models.ForecastModel
import com.damai.domain.usecases.GetForecastWeatherUseCase
import kotlinx.coroutines.launch

/**
 * Created by damai007 on 03/October/2023
 */
class WeatherDetailViewModel(
    app: Application,
    private val dispatcher: DispatcherProvider,
    private val getForecastWeatherUseCase: GetForecastWeatherUseCase
) : BaseViewModel(app = app) {

    //region Live Data
    private val _weatherHourlyListLiveData = MutableLiveData<List<ForecastModel>>()
    val weatherHourlyListLiveData = _weatherHourlyListLiveData.asLiveData()

    private val _weatherDailyListLiveData = MutableLiveData<List<ForecastModel>>()
    val weatherDailyListLiveData = _weatherDailyListLiveData.asLiveData()

    private val _cityModelLiveData = MutableLiveData<CityModel>()
    val cityModelLiveData = _cityModelLiveData.asLiveData()

    private val _errorMessageLiveData = MutableLiveData<Event<Pair<Boolean, String?>>>()
    val errorMessageLiveData = _errorMessageLiveData.asLiveData()
    //endregion `Live Data`

    //region Private Functions
    private fun groupingDailyForecasts(list: List<ForecastModel>) {
        val dailyForecastList = mutableListOf<ForecastModel>()
        var currentDayName = ""
        list.forEach { forecastModel ->
            if (forecastModel.dayName != currentDayName) {
                currentDayName = forecastModel.dayName
                forecastModel.let(dailyForecastList::add)
            }
        }
        dailyForecastList.toList().let(_weatherDailyListLiveData::postValue)
    }
    //endregion `Private Functions`

    fun initFromIntent(intent: Intent) {
        if (intent.hasExtra(ARGS_LATITUDE)) {
            CityModel(
                name = intent.getStringExtra(ARGS_CITY_NAME),
                latitude = intent.getDoubleExtra(ARGS_LATITUDE, 0.0),
                longitude = intent.getDoubleExtra(ARGS_LONGITUDE, 0.0),
                temperature = intent.getIntExtra(ARGS_TEMPERATURE, 0),
                weatherIconUrl = intent.getStringExtra(ARGS_WEATHER_ICON_URL),
                weatherIcon = null,
                id = 0,
                state = null
            ).let(_cityModelLiveData::setValue)
        }
    }

    fun getForecastWeather() {
        viewModelScope.launch(dispatcher.io()) {
            val requestModel = CurrentWeatherRequestModel(
                latitude = _cityModelLiveData.value?.latitude.orZero(),
                longitude = _cityModelLiveData.value?.longitude.orZero()
            )
            getForecastWeatherUseCase(requestModel).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        resource.model?.list?.let {
                            it.let(_weatherHourlyListLiveData::postValue)
                            groupingDailyForecasts(list = it)
                        } ?: run {
                            Event(
                                Pair(
                                    true,
                                    getApplication<MyApplication>().resources.getString(
                                        R.string.empty_forecast
                                    )
                                )
                            ).let(_errorMessageLiveData::postValue)
                        }
                    }
                    is Resource.Error -> {
                        Event(
                            Pair(true, resource.errorMessage)
                        ).let(_errorMessageLiveData::postValue)
                    }
                }
            }
        }
    }
}