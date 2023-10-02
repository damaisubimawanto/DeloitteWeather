package com.damai.deloitteweather.ui.main

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.damai.base.BaseViewModel
import com.damai.base.coroutines.DispatcherProvider
import com.damai.base.extensions.asLiveData
import com.damai.domain.models.CityModel

/**
 * Created by damai007 on 02/October/2023
 */
class MainViewModel(
    app: Application,
    private val dispatcher: DispatcherProvider
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

    private fun generateDummySavedCities() {
        val savedCity1 = CityModel(
            id = 1,
            name = "Bekasi",
            temperature = 34,
            weatherType = "Clear"
        )
        val savedCity2 = CityModel(
            id = 2,
            name = "Jakarta",
            temperature = 33,
            weatherType = null
        )
        val savedCity3 = CityModel(
            id = 3,
            name = "Batam",
            temperature = 32,
            weatherType = null
        )
        val savedCity4 = CityModel(
            id = 4,
            name = "Bogor",
            temperature = 30,
            weatherType = null
        )
        val savedCity5 = CityModel(
            id = 5,
            name = "Bandung",
            temperature = 28,
            weatherType = null
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