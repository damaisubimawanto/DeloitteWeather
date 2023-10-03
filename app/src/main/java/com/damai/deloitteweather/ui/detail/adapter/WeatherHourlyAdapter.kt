package com.damai.deloitteweather.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.damai.base.BaseRecyclerViewAdapter
import com.damai.base.BaseViewHolder
import com.damai.deloitteweather.R
import com.damai.deloitteweather.databinding.ItemRecyclerWeatherHourlyBinding
import com.damai.domain.models.ForecastModel

/**
 * Created by damai007 on 03/October/2023
 */
class WeatherHourlyAdapter : BaseRecyclerViewAdapter<ItemRecyclerWeatherHourlyBinding, ForecastModel>(
    data = listOf()
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ItemRecyclerWeatherHourlyBinding, ForecastModel> {
        val binding = DataBindingUtil.inflate<ItemRecyclerWeatherHourlyBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_recycler_weather_hourly,
            parent,
            false
        )
        return WeatherHourlyVH(binding = binding)
    }

    inner class WeatherHourlyVH(
        binding: ItemRecyclerWeatherHourlyBinding
    ) : BaseViewHolder<ItemRecyclerWeatherHourlyBinding, ForecastModel>(
        binding = binding
    ) {

        override fun bind(data: ForecastModel, position: Int) {
            with(binding) {
                model = data
                if (hasPendingBindings()) {
                    executePendingBindings()
                }
            }
        }
    }
}