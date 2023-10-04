package com.damai.deloitteweather.ui.addnewcity

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.damai.base.BaseViewModel
import com.damai.base.coroutines.DispatcherProvider
import com.damai.base.extensions.asLiveData
import com.damai.base.extensions.orFalse
import com.damai.base.networks.Resource
import com.damai.base.utils.Event
import com.damai.data.mappers.GeoCityEntityToCityModelMapper
import com.damai.deloitteweather.R
import com.damai.deloitteweather.application.MyApplication
import com.damai.domain.daos.GeoCityDao
import com.damai.domain.models.CityModel
import com.damai.domain.models.CurrentWeatherRequestModel
import com.damai.domain.usecases.GetCurrentWeatherUseCase
import com.damai.domain.usecases.GetGeoLocationCityUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * Created by damai007 on 03/October/2023
 */
class AddNewCityViewModel(
    app: Application,
    private val dispatcher: DispatcherProvider,
    private val getGeoLocationCityUseCase: GetGeoLocationCityUseCase,
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val geoCityDao: GeoCityDao,
    private val geoCityEntityToModelMapper: GeoCityEntityToCityModelMapper
) : BaseViewModel(app = app) {

    //region Live Data
    private val _cityListLiveData = MutableLiveData<List<CityModel>>()
    val cityListLiveData = _cityListLiveData.asLiveData()

    private val _selectedCityLiveData = MutableLiveData<Event<CityModel>>()
    val selectedCityLiveData = _selectedCityLiveData.asLiveData()

    private val _emptyCityLiveData = MutableLiveData<Event<Boolean>>()
    val emptyCityLiveData = _emptyCityLiveData.asLiveData()

    private val _errorSelectCityLiveData = MutableLiveData<Event<Boolean>>()
    val errorSelectCityLiveData = _errorSelectCityLiveData.asLiveData()
    //endregion `Live Data`

    //region Variables
    private val tempCityList = mutableListOf<CityModel>()
    //endregion `Variables`

    fun getGeoLocationCities() {
        viewModelScope.launch(dispatcher.io()) {
            val localGeoLocationCityList = geoCityDao.getAllSavedCities()
            if (localGeoLocationCityList.isNotEmpty()) {
                tempCityList.clear()
                geoCityEntityToModelMapper.map(localGeoLocationCityList).let {
                    tempCityList.addAll(it)
                    _cityListLiveData.postValue(it)
                }
                return@launch
            }

            val cityList = getApplication<MyApplication>().resources.getStringArray(
                R.array.all_cities
            ).toList()
            val geoLocationCityList = cityList.map { cityName ->
                async {
                    getGeoLocationCityUseCase(cityName).first()
                }
            }.awaitAll()

            tempCityList.clear()
            geoLocationCityList.forEach { resource ->
                when (resource) {
                    is Resource.Success -> {
                        resource.model?.cityModel?.let(tempCityList::add)
                    }
                    is Resource.Error -> Unit
                }
            }
            tempCityList.let(_cityListLiveData::postValue)
        }
    }

    fun getCurrentWeatherCity(selectedCityModel: CityModel) {
        viewModelScope.launch(dispatcher.io()) {
            val requestModel = CurrentWeatherRequestModel(
                latitude = selectedCityModel.latitude,
                longitude = selectedCityModel.longitude
            )
            getCurrentWeatherUseCase(requestModel).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        resource.model?.cityModel?.let {
                            Event(it).let(_selectedCityLiveData::postValue)
                        } ?: run {
                            Event(true).let(_errorSelectCityLiveData::postValue)
                        }
                    }
                    is Resource.Error -> {
                        Event(true).let(_errorSelectCityLiveData::postValue)
                    }
                }
            }
        }
    }

    fun searchCity(query: String) {
        if (query.isBlank()) {
            Event(false).let(_emptyCityLiveData::postValue)
            tempCityList.let(_cityListLiveData::postValue)
            return
        }

        val searchedCityList = tempCityList.filter {
            when {
                query.length < 3 -> {
                    it.name?.startsWith(prefix = query, ignoreCase = true).orFalse()
                }
                else -> {
                    it.name?.contains(other = query, ignoreCase = true).orFalse()
                }
            }
        }

        if (searchedCityList.isEmpty()) {
            Event(true).let(_emptyCityLiveData::postValue)
            return
        }

        Event(false).let(_emptyCityLiveData::postValue)
        searchedCityList.let(_cityListLiveData::postValue)
    }
}