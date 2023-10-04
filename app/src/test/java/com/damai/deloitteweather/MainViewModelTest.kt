@file:OptIn(ExperimentalCoroutinesApi::class)

package com.damai.deloitteweather

import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.damai.base.networks.Resource
import com.damai.data.mappers.CityEntityToCityModelMapper
import com.damai.data.responses.CurrentWeatherResponse
import com.damai.data.responses.WeatherCoordinateResponse
import com.damai.data.responses.WeatherMainResponse
import com.damai.data.responses.WeatherStatusResponse
import com.damai.deloitteweather.application.AppDatabase
import com.damai.deloitteweather.ui.main.MainViewModel
import com.damai.deloitteweather.utils.CoroutineTestRule
import com.damai.deloitteweather.utils.InstantExecutorExtension
import com.damai.domain.daos.CityDao
import com.damai.domain.entities.CityEntity
import com.damai.domain.models.CityModel
import com.damai.domain.models.CurrentWeatherModel
import com.damai.domain.models.CurrentWeatherRequestModel
import com.damai.domain.repositories.HomeRepository
import com.damai.domain.usecases.GetCurrentWeatherUseCase
import com.jraska.livedata.test
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.excludeRecords
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import retrofit2.Response
import java.util.concurrent.CountDownLatch

/**
 * Created by damai007 on 04/October/2023
 */
@RunWith(AndroidJUnit4::class)
@ExtendWith(InstantExecutorExtension::class)
@MediumTest
@Config(manifest = Config.NONE)
class MainViewModelTest {

    @get:Rule
    val coroutineRule = CoroutineTestRule()

    private lateinit var database: AppDatabase
    private lateinit var cityDao: CityDao
    private lateinit var viewModel: MainViewModel

    private val getCurrentWeatherUseCase = mockk<GetCurrentWeatherUseCase>()
    private val cityEntityToModelMapper = mockk<CityEntityToCityModelMapper>()
    private val homeRepository = mockk<HomeRepository>()

    private val savedCityListObserver = mockk<Observer<List<CityModel>>>(relaxed = true)
    private val emptySavedCityObserver = mockk<Observer<Boolean>>(relaxed = true)

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        cityDao = database.cityDao()

        viewModel = MainViewModel(
            app = ApplicationProvider.getApplicationContext(),
            dispatcher = coroutineRule.dispatcherProvider,
            getCurrentWeatherUseCase = getCurrentWeatherUseCase,
            cityDao = cityDao,
            cityEntityToModelMapper = cityEntityToModelMapper
        )
        viewModel.savedCityListLiveData.observeForever(savedCityListObserver)
        viewModel.emptySavedCityLiveData.observeForever(emptySavedCityObserver)
    }

    @After
    fun cleanUp() {
        viewModel.savedCityListLiveData.removeObserver(savedCityListObserver)
        viewModel.emptySavedCityLiveData.removeObserver(emptySavedCityObserver)
        database.close()
    }

    @Test
    fun `get current weather at first install should be empty and update empty live data`() = runBlocking {
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

    @Test
    fun `get current weather use case and success`() = runTest {
        val requestModel: CurrentWeatherRequestModel = mockk()
        val response: Response<CurrentWeatherResponse> = mockk()
        val body: CurrentWeatherResponse = mockk()
        val coordinateResponse: WeatherCoordinateResponse = mockk()
        val weatherMainResponse: WeatherMainResponse = mockk()
        val weatherStatusListResponse: List<WeatherStatusResponse> = mockk()

        val responseBody: CurrentWeatherModel = mockk()
        val cityModel: CityModel = mockk()
        val flowResponse = flow<Resource<CurrentWeatherModel>> {
            emit(Resource.Success())
        }

        every { response.isSuccessful } returns true
        every { response.code() } returns 200
        every { response.body() } returns body
        every { body.coord } returns coordinateResponse
        every { body.main } returns weatherMainResponse
        every { body.weather } returns weatherStatusListResponse
        every { responseBody.cityModel } returns cityModel
        every { runBlocking { getCurrentWeatherUseCase(requestModel) } } returns flowResponse
        every { homeRepository.getCurrentWeather(requestModel) } returns flowResponse

        getCurrentWeatherUseCase(requestModel).collectLatest {
            assertTrue(it is Resource.Success)

            confirmVerified(
                homeRepository,
                response
            )
        }
    }

    @Test
    fun `insert new city returns true`() = runBlocking {
        val city = CityEntity(
            id = 1,
            name = "Bekasi",
            temperature = 0,
            weatherIcon = null,
            weatherIconUrl = null,
            latitude = 0.0,
            longitude = 0.0,
            state = null,
            createdTime = 0,
            latestUpdated = 0
        )
        cityDao.insert(cityEntity = city)

        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
            val savedCityList = cityDao.getAllSavedCities()
            assertThat(savedCityList).isNotEmpty
            latch.countDown()
        }
        latch.await()
        job.cancelAndJoin()
    }
}