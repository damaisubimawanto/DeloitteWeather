package com.damai.deloitteweather.ui.deletecity

import com.damai.base.BaseBottomSheetDialogFragment
import com.damai.base.extensions.orFalse
import com.damai.base.extensions.orZero
import com.damai.base.extensions.setCustomOnClickListener
import com.damai.base.utils.Constants.ARGS_CITY_ID
import com.damai.base.utils.Constants.ARGS_CITY_NAME
import com.damai.deloitteweather.R
import com.damai.deloitteweather.databinding.FragmentDeleteCityBinding
import com.damai.deloitteweather.ui.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

/**
 * Created by damai007 on 04/October/2023
 */
class DeleteCityBottomSheetDialog : BaseBottomSheetDialogFragment<FragmentDeleteCityBinding, MainViewModel>() {

    //region Variables
    private var cityId: Int? = null
    //endregion `Variables`

    override val layoutResource: Int = R.layout.fragment_delete_city

    override val viewModel: MainViewModel by activityViewModel()

    override fun onStart() {
        super.onStart()
        setExpanded(skipCollapsed = true)
    }

    override fun FragmentDeleteCityBinding.viewInitialization() {
        if (arguments?.containsKey(ARGS_CITY_ID).orFalse()) {
            requireNotNull(arguments).let {
                cityId = it.getInt(ARGS_CITY_ID, 0).orZero()
                it.getString(ARGS_CITY_NAME).orEmpty().let { cityName ->
                    val titleText = getString(R.string.remove_city_confirmation, cityName)
                    tvRemoveCityText.text = titleText
                }
            }
        }
    }

    override fun FragmentDeleteCityBinding.setupListeners() {
        btnYes.setCustomOnClickListener {
            cityId?.let(viewModel::removeCity)
            dialog?.dismiss()
        }

        btnNo.setCustomOnClickListener {
            dialog?.dismiss()
        }
    }
}