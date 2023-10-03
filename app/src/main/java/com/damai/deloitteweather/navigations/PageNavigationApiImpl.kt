package com.damai.deloitteweather.navigations

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.FragmentActivity
import com.damai.base.utils.Constants.ARGS_CITY_NAME
import com.damai.base.utils.Constants.ARGS_LATITUDE
import com.damai.base.utils.Constants.ARGS_LONGITUDE
import com.damai.base.utils.Constants.ARGS_TEMPERATURE
import com.damai.deloitteweather.ui.addnewcity.AddNewCityBottomSheetDialog
import com.damai.deloitteweather.ui.detail.WeatherDetailActivity

/**
 * Created by damai007 on 03/October/2023
 */
class PageNavigationApiImpl : PageNavigationApi {

    override fun openAddNewCityBottomSheetDialog(fragmentActivity: FragmentActivity, tag: String) {
        val fragment = AddNewCityBottomSheetDialog()
        fragment.show(
            fragmentActivity.supportFragmentManager,
            tag
        )
    }

    override fun navigateToWeatherDetailActivity(
        context: Context,
        launcher: ActivityResultLauncher<Intent>,
        cityName: String,
        latitude: Double,
        longitude: Double,
        temperature: Int
    ) {
        Intent(context, WeatherDetailActivity::class.java).apply {
            putExtra(ARGS_CITY_NAME, cityName)
            putExtra(ARGS_LATITUDE, latitude)
            putExtra(ARGS_LONGITUDE, longitude)
            putExtra(ARGS_TEMPERATURE, temperature)
        }.also {
            launcher.launch(it)
        }
    }
}