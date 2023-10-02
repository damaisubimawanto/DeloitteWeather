package com.damai.deloitteweather.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import com.damai.base.BaseViewHolder
import com.damai.deloitteweather.R
import com.damai.deloitteweather.databinding.ItemRecyclerSavedCitiesBinding
import com.damai.domain.models.CityModel

/**
 * Created by damai007 on 02/October/2023
 */
class SavedCityAdapter : ListAdapter<CityModel, SavedCityAdapter.SavedCityVH>(
    SavedCityComparator
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedCityVH {
        val binding = DataBindingUtil.inflate<ItemRecyclerSavedCitiesBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_recycler_saved_cities,
            parent,
            false
        )
        return SavedCityVH(binding = binding)
    }

    override fun onBindViewHolder(holder: SavedCityVH, position: Int) {
        holder.bind(
            data = currentList[position],
            position = position
        )
    }

    inner class SavedCityVH(
        binding: ItemRecyclerSavedCitiesBinding
    ) : BaseViewHolder<ItemRecyclerSavedCitiesBinding, CityModel>(binding = binding) {

        override fun bind(data: CityModel, position: Int) {
            with(binding) {
                model = data
                isTopLineVisible = position == 0
                if (hasPendingBindings()) {
                    executePendingBindings()
                }
            }
        }
    }
}