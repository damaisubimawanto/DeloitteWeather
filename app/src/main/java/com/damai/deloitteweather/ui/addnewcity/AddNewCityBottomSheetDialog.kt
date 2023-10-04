package com.damai.deloitteweather.ui.addnewcity

import android.view.inputmethod.EditorInfo
import com.damai.base.BaseBottomSheetDialogFragment
import com.damai.base.extensions.addOnTextChanged
import com.damai.base.extensions.getScreenHeight
import com.damai.base.extensions.gone
import com.damai.base.extensions.hideKeyboard
import com.damai.base.extensions.observe
import com.damai.base.extensions.showShortToast
import com.damai.base.extensions.visible
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
        etSearchCity.addOnTextChanged { query ->
            viewModel.searchCity(query = query)
        }
        etSearchCity.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    requireActivity().hideKeyboard(view = etSearchCity)
                    true
                }
                else -> false
            }
        }
    }

    override fun FragmentAddNewCityBinding.setupObservers() {
        observe(viewModel.cityListLiveData) {
            addNewCityAdapter.submitList(it)
        }

        observe(viewModel.selectedCityLiveData, EventObserver {
            mainViewModel.addNewCity(cityModel = it)
            dialog?.dismiss()
        })

        observe(viewModel.emptyCityLiveData, EventObserver { isEmpty ->
            if (isEmpty) {
                tvEmptySearchCityText.visible()
                rvCities.gone()
            } else {
                tvEmptySearchCityText.gone()
                rvCities.visible()
            }
        })

        observe(viewModel.errorSelectCityLiveData, EventObserver { isError ->
            if (isError) {
                context?.showShortToast(message = getString(R.string.error_select_city))
            }
        })
    }

    override fun FragmentAddNewCityBinding.onPreparationFinished() {
        viewModel.getGeoLocationCities()
    }
}