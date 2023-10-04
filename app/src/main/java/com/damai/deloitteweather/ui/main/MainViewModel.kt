package com.damai.deloitteweather.ui.main

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.damai.base.BaseViewModel
import com.damai.base.coroutines.DispatcherProvider
import com.damai.base.extensions.asLiveData
import com.damai.base.extensions.getMutableList
import com.damai.base.networks.Resource
import com.damai.data.mappers.CityEntityToCityModelMapper
import com.damai.domain.daos.CityDao
import com.damai.domain.models.CityModel
import com.damai.domain.models.CurrentWeatherRequestModel
import com.damai.domain.usecases.GetCurrentWeatherUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * Created by damai007 on 02/October/2023
 */
class MainViewModel(
    app: Application,
    private val dispatcher: DispatcherProvider,
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val cityDao: CityDao,
    private val cityEntityToModelMapper: CityEntityToCityModelMapper
) : BaseViewModel(app = app) {

    //region Live Data
    private val _savedCityListLiveData = MutableLiveData<List<CityModel>>()
    val savedCityListLiveData = _savedCityListLiveData.asLiveData()

    private val _emptySavedCityLiveData = MutableLiveData(false)
    val emptySavedCityLiveData = _emptySavedCityLiveData.asLiveData()
    //endregion `Live Data`

    fun getCurrentWeatherCities() {
        viewModelScope.launch(dispatcher.io()) {
            if (_savedCityListLiveData.value.isNullOrEmpty()) {
                val localSavedCityList = cityDao.getAllSavedCities()
                if (localSavedCityList.isEmpty()) {
                    _emptySavedCityLiveData.postValue(true)
                } else {
                    cityEntityToModelMapper.map(localSavedCityList).let(
                        _savedCityListLiveData::postValue
                    )
                }
                return@launch
            }

            val newCurrentWeatherList = requireNotNull(_savedCityListLiveData.value).map { cityModel ->
                async {
                    val requestModel = CurrentWeatherRequestModel(
                        latitude = cityModel.latitude,
                        longitude = cityModel.longitude
                    )
                    getCurrentWeatherUseCase(requestModel).first()
                }
            }.awaitAll()

            val currentList = _savedCityListLiveData.getMutableList()
            newCurrentWeatherList.forEach { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val index = currentList.indexOfFirst {
                            it.id == resource.model?.cityModel?.id
                        }
                        if (index > -1) {
                            resource.model?.cityModel?.let {
                                currentList.removeAt(index)
                                currentList.add(index, it)
                            }
                        }
                    }
                    else -> Unit
                }
            }
            currentList.toList().let(_savedCityListLiveData::postValue)
            _emptySavedCityLiveData.postValue(false)
        }
    }

    fun addNewCity(cityModel: CityModel) {
        val currentList = _savedCityListLiveData.getMutableList()
        val existedIndex = currentList.indexOfFirst {
            it.id == cityModel.id
        }
        if (existedIndex == -1) {
            currentList.add(cityModel)
            currentList.toList().let(_savedCityListLiveData::postValue)
            _emptySavedCityLiveData.value = false
        }
    }

    fun removeCity(cityId: Int) {
        val currentList = _savedCityListLiveData.getMutableList()
        val existedIndex = currentList.indexOfFirst {
            it.id == cityId
        }
        if (existedIndex > -1) {
            currentList.removeAt(existedIndex)
            currentList.toList().let(_savedCityListLiveData::postValue)

            viewModelScope.launch(dispatcher.io()) {
                cityDao.delete(cityId = cityId)
            }

            if (currentList.isEmpty()) {
                _emptySavedCityLiveData.value = true
            }
        }
    }
}