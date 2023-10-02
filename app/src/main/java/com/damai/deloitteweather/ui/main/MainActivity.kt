package com.damai.deloitteweather.ui.main

import com.damai.base.BaseActivity
import com.damai.base.extensions.observe
import com.damai.base.extensions.setCustomOnClickListener
import com.damai.deloitteweather.R
import com.damai.deloitteweather.databinding.ActivityMainBinding
import com.damai.deloitteweather.ui.main.adapter.SavedCityAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    //region Variables
    private lateinit var savedCityAdapter: SavedCityAdapter
    //endregion `Variables`

    override val layoutResource: Int = R.layout.activity_main

    override val viewModel: MainViewModel by viewModel()

    override fun ActivityMainBinding.viewInitialization() {
        vm = viewModel
        lifecycleOwner = this@MainActivity

        with(rvSavedCities) {
            savedCityAdapter = SavedCityAdapter()
            adapter = savedCityAdapter
        }
    }

    override fun ActivityMainBinding.setupListeners() {
        rlAddCityButton.setCustomOnClickListener {

        }
    }

    override fun ActivityMainBinding.setupObservers() {
        observe(viewModel.savedCityListLiveData) {
            savedCityAdapter.submitList(it)
        }
    }
}