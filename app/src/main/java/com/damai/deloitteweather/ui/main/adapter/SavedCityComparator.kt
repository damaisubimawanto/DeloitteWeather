package com.damai.deloitteweather.ui.main.adapter

import androidx.recyclerview.widget.DiffUtil
import com.damai.domain.models.CityModel

/**
 * Created by damai007 on 02/October/2023
 */
object SavedCityComparator : DiffUtil.ItemCallback<CityModel>() {

    override fun areItemsTheSame(oldItem: CityModel, newItem: CityModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CityModel, newItem: CityModel): Boolean {
        return oldItem == newItem
    }
}