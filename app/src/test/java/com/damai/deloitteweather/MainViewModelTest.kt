package com.damai.deloitteweather

import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.damai.data.mappers.CityEntityToCityModelMapper
import com.damai.deloitteweather.ui.main.MainViewModel
import com.damai.deloitteweather.utils.CoroutineTestRule
import com.damai.deloitteweather.utils.InstantExecutorExtension
import com.damai.domain.daos.CityDao
import com.damai.domain.models.CityModel
import com.damai.domain.repositories.HomeRepository
import com.damai.domain.usecases.GetCurrentWeatherUseCase
import com.jraska.livedata.test
import io.mockk.confirmVerified
import io.mockk.excludeRecords
import io.mockk.mockk
import io.mockk.verify
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runner.RunWith

/**
 * Created by damai007 on 04/October/2023
 */
@RunWith(AndroidJUnit4::class)
@ExtendWith(InstantExecutorExtension::class)
@MediumTest
class MainViewModelTest {

    @get:Rule
    val coroutineRule = CoroutineTestRule()

    private val getCurrentWeatherUseCase = mockk<GetCurrentWeatherUseCase>()
    private val cityDao = mockk<CityDao>()
    private val cityEntityToModelMapper = mockk<CityEntityToCityModelMapper>()
    private val homeRepository = mockk<HomeRepository>()

    private val viewModel = MainViewModel(
        app = ApplicationProvider.getApplicationContext(),
        dispatcher = coroutineRule.dispatcherProvider,
        getCurrentWeatherUseCase = getCurrentWeatherUseCase,
        cityDao = cityDao,
        cityEntityToModelMapper = cityEntityToModelMapper
    )

    private val savedCityListObserver = mockk<Observer<List<CityModel>>>(relaxed = true)
    private val emptySavedCityObserver = mockk<Observer<Boolean>>(relaxed = true)

    @Before
    fun setup() {
        viewModel.savedCityListLiveData.observeForever(savedCityListObserver)
        viewModel.emptySavedCityLiveData.observeForever(emptySavedCityObserver)
    }

    @After
    fun cleanUp() {
        viewModel.savedCityListLiveData.removeObserver(savedCityListObserver)
        viewModel.emptySavedCityLiveData.removeObserver(emptySavedCityObserver)
    }

    @Test
    fun `get current weather at first install should be empty and update empty live data`() {
        viewModel.getCurrentWeatherCities()

        verify(exactly = 1) {
            emptySavedCityObserver.onChanged(any())
        }

        val testObserver = viewModel.emptySavedCityLiveData.test()
            .assertHasValue()
        val content = testObserver.value()
        assertEquals(false, content)

        excludeRecords {
            viewModel.emptySavedCityLiveData.observeForever(testObserver)
        }

        confirmVerified(emptySavedCityObserver)
    }
}