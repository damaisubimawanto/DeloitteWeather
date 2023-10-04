package com.damai.deloitteweather.ui.main

import androidx.activity.result.contract.ActivityResultContracts
import com.damai.base.BaseActivity
import com.damai.base.extensions.observe
import com.damai.base.extensions.setCustomOnClickListener
import com.damai.base.utils.Constants.TAG_ADD_NEW_CITY_BOTTOMSHEET_DIALOG
import com.damai.base.utils.Constants.WEATHER_ICON_HEAD
import com.damai.base.utils.Constants.WEATHER_ICON_TAIL_2X
import com.damai.deloitteweather.R
import com.damai.deloitteweather.databinding.ActivityMainBinding
import com.damai.deloitteweather.navigations.PageNavigationApi
import com.damai.deloitteweather.ui.main.adapter.SavedCityAdapter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    //region Variables
    private lateinit var savedCityAdapter: SavedCityAdapter

    private val pageNavigationApi: PageNavigationApi by inject()

    private val activityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            /* Do nothing. */
        }
    //endregion `Variables`

    override val layoutResource: Int = R.layout.activity_main

    override val viewModel: MainViewModel by viewModel()

    override fun ActivityMainBinding.viewInitialization() {
        vm = viewModel
        lifecycleOwner = this@MainActivity

        with(rvSavedCities) {
            savedCityAdapter = SavedCityAdapter { clickedItem, isLongClick ->
                if (isLongClick) {
                    pageNavigationApi.openRemoveCityBottomSheetDialog(
                        fragmentActivity = this@MainActivity,
                        tag = "DeleteCityBottomSheetDialog",
                        cityId = clickedItem.id,
                        cityName = clickedItem.name.orEmpty()
                    )
                    return@SavedCityAdapter
                }
                pageNavigationApi.navigateToWeatherDetailActivity(
                    context = context,
                    launcher = activityLauncher,
                    cityName = clickedItem.name.orEmpty(),
                    latitude = clickedItem.latitude,
                    longitude = clickedItem.longitude,
                    temperature = clickedItem.temperature,
                    weatherIconUrl = "${WEATHER_ICON_HEAD}${clickedItem.weatherIcon}${WEATHER_ICON_TAIL_2X}"
                )
            }
            adapter = savedCityAdapter
        }
    }

    override fun ActivityMainBinding.setupListeners() {
        rlAddCityButton.setCustomOnClickListener {
            pageNavigationApi.openAddNewCityBottomSheetDialog(
                fragmentActivity = this@MainActivity,
                tag = TAG_ADD_NEW_CITY_BOTTOMSHEET_DIALOG
            )
        }
    }

    override fun ActivityMainBinding.setupObservers() {
        observe(viewModel.savedCityListLiveData) {
            savedCityAdapter.submitList(it)
        }
    }

    override fun ActivityMainBinding.onPreparationFinished() {
        viewModel.getCurrentWeatherCities()
    }
}