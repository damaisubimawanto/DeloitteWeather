package com.damai.deloitteweather.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.damai.base.BaseRecyclerViewAdapter
import com.damai.base.BaseViewHolder
import com.damai.deloitteweather.R
import com.damai.deloitteweather.databinding.ItemRecyclerWeatherDailyBinding
import com.damai.domain.models.ForecastModel

/**
 * Created by damai007 on 03/October/2023
 */
class WeatherDailyAdapter : BaseRecyclerViewAdapter<ItemRecyclerWeatherDailyBinding, ForecastModel>(
    data = listOf()
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ItemRecyclerWeatherDailyBinding, ForecastModel> {
        val binding = DataBindingUtil.inflate<ItemRecyclerWeatherDailyBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_recycler_weather_daily,
            parent,
            false
        )
        return WeatherDailyVH(binding = binding)
    }

    inner class WeatherDailyVH(
        binding: ItemRecyclerWeatherDailyBinding
    ) : BaseViewHolder<ItemRecyclerWeatherDailyBinding, ForecastModel>(
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