package com.damai.deloitteweather.navigations

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.FragmentActivity

/**
 * Created by damai007 on 03/October/2023
 */
interface PageNavigationApi {

    fun openAddNewCityBottomSheetDialog(
        fragmentActivity: FragmentActivity,
        tag: String
    )

    fun navigateToWeatherDetailActivity(
        context: Context,
        launcher: ActivityResultLauncher<Intent>,
        cityName: String,
        latitude: Double,
        longitude: Double,
        temperature: Int,
        weatherIconUrl: String
    )
}