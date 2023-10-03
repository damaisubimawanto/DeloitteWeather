package com.damai.deloitteweather.navigations

import androidx.fragment.app.FragmentActivity
import com.damai.deloitteweather.ui.addnewcity.AddNewCityBottomSheetDialog

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
}