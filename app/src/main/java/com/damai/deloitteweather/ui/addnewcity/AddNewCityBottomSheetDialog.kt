package com.damai.deloitteweather.ui.addnewcity

import com.damai.base.BaseBottomSheetDialogFragment
import com.damai.base.extensions.getScreenHeight
import com.damai.base.extensions.observe
import com.damai.base.extensions.showShortToast
import com.damai.base.utils.EventObserver
import com.damai.deloitteweather.R
import com.damai.deloitteweather.databinding.FragmentAddNewCityBinding
import com.damai.deloitteweather.ui.addnewcity.adapter.AddNewCityAdapter
import com.damai.deloitteweather.ui.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by damai007 on 03/October/2023
 */
class AddNewCityBottomSheetDialog : BaseBottomSheetDialogFragment<FragmentAddNewCityBinding, AddNewCityViewModel>() {

    //region Variables
    private lateinit var addNewCityAdapter: AddNewCityAdapter

    private val mainViewModel: MainViewModel by activityViewModel()
    //endregion `Variables`

    override val layoutResource: Int = R.layout.fragment_add_new_city

    override val viewModel: AddNewCityViewModel by viewModel()

    override fun onStart() {
        super.onStart()
        setBottomSheetFullScreen(
            height = getScreenHeight(),
            bottomSheet = binding.root,
            skipCollapsed = true
        )
    }

    override fun FragmentAddNewCityBinding.viewInitialization() {
        with(rvCities) {
            addNewCityAdapter = AddNewCityAdapter { clickedItem ->
                viewModel.getCurrentWeatherCity(selectedCityModel = clickedItem)
            }
            adapter = addNewCityAdapter
        }
    }

    override fun FragmentAddNewCityBinding.setupListeners() {
        // TODO("Not yet implemented")
    }

    override fun FragmentAddNewCityBinding.setupObservers() {
        observe(viewModel.cityListLiveData) {
            addNewCityAdapter.submitList(it)
        }

        observe(viewModel.selectedCityLiveData, EventObserver {
            mainViewModel.addNewCity(cityModel = it)
            dialog?.dismiss()
        })

        observe(viewModel.errorSelectCityLiveData, EventObserver {
            context?.showShortToast(message = getString(R.string.error_select_city))
        })
    }

    override fun FragmentAddNewCityBinding.onPreparationFinished() {
        viewModel.getGeoLocationCities()
    }
}