package com.damai.deloitteweather.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.damai.base.BaseViewModel
import com.damai.base.coroutines.DispatcherProvider
import com.damai.base.extensions.asLiveData
import com.damai.base.networks.Resource
import com.damai.domain.models.CityModel
import com.damai.domain.models.CurrentWeatherRequestModel
import com.damai.domain.usecases.GetCurrentWeatherUseCase
import kotlinx.coroutines.launch

/**
 * Created by damai007 on 02/October/2023
 */
class MainViewModel(
    app: Application,
    private val dispatcher: DispatcherProvider,
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase
) : BaseViewModel(app = app) {

    //region Live Data
    private val _savedCityListLiveData = MutableLiveData<List<CityModel>>()
    val savedCityListLiveData = _savedCityListLiveData.asLiveData()

    private val _emptySavedCityLiveData = MutableLiveData(false)
    val emptySavedCityLiveData = _emptySavedCityLiveData.asLiveData()
    //endregion `Live Data`

    init {
        generateDummySavedCities()
    }

    fun getCurrentWeatherCities() {
        _savedCityListLiveData.value?.forEach { cityModel ->
            viewModelScope.launch(dispatcher.io()) {
                val requestModel = CurrentWeatherRequestModel(
                    id = cityModel.id,
                    latitude = cityModel.latitude,
                    longitude = cityModel.longitude
                )
                getCurrentWeatherUseCase(requestModel).collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            resource.model?.cityModel?.let { response ->
                                Log.d("zxczxc", "name = ${response.name}, lat = ${response.latitude}, lon = ${response.longitude}, temperature = ${response.temperature}")
                            }
                        }
                        is Resource.Error -> {

                        }
                    }
                }
            }
        }
    }

    private fun generateDummySavedCities() {
        val savedCity1 = CityModel(
            id = 1,
            name = "Bekasi",
            temperature = 12,
            weatherType = "Clear",
            latitude = -6.241586,
            longitude = 106.992416
        )
        val savedCity2 = CityModel(
            id = 2,
            name = "Jakarta",
            temperature = 12,
            weatherType = null,
            latitude = -6.200000,
            longitude = 106.816666
        )
        val savedCity3 = CityModel(
            id = 3,
            name = "Batam",
            temperature = 12,
            weatherType = null,
            latitude = 1.045626,
            longitude = 104.030457
        )
        val savedCity4 = CityModel(
            id = 4,
            name = "Bogor",
            temperature = 12,
            weatherType = null,
            latitude = -6.595038,
            longitude = 106.816635
        )
        val savedCity5 = CityModel(
            id = 5,
            name = "Bandung",
            temperature = 12,
            weatherType = null,
            latitude = -6.914864,
            longitude = 107.608238
        )

        val dummyList = listOf(
            savedCity1,
            savedCity2,
            savedCity3,
            savedCity4,
            savedCity5
        )
        dummyList.let(_savedCityListLiveData::setValue)
    }
}