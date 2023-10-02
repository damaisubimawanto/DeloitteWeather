package com.damai.deloitteweather.ui.main

import com.damai.base.BaseActivity
import com.damai.deloitteweather.R
import com.damai.deloitteweather.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override val layoutResource: Int = R.layout.activity_main

    override val viewModel: MainViewModel by viewModel()

    override fun ActivityMainBinding.viewInitialization() {
        TODO("Not yet implemented")
    }

    override fun ActivityMainBinding.setupListeners() {
        TODO("Not yet implemented")
    }

    override fun ActivityMainBinding.setupObservers() {
        TODO("Not yet implemented")
    }

    override fun ActivityMainBinding.onPreparationFinished() {
        TODO("Not yet implemented")
    }
}