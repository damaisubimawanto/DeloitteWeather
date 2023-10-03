package com.damai.deloitteweather.navigations

import androidx.fragment.app.FragmentActivity

/**
 * Created by damai007 on 03/October/2023
 */
interface PageNavigationApi {

    fun openAddNewCityBottomSheetDialog(
        fragmentActivity: FragmentActivity,
        tag: String
    )
}