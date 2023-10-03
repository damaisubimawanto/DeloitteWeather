package com.damai.deloitteweather.ui.addnewcity.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import com.damai.base.BaseViewHolder
import com.damai.base.extensions.setCustomOnClickListener
import com.damai.deloitteweather.R
import com.damai.deloitteweather.databinding.ItemRecyclerCitiesBinding
import com.damai.deloitteweather.ui.main.adapter.SavedCityComparator
import com.damai.domain.models.CityModel

/**
 * Created by damai007 on 03/October/2023
 */
class AddNewCityAdapter(
    private val onClicked: (CityModel) -> Unit
) : ListAdapter<CityModel, AddNewCityAdapter.AddNewCityVH>(
    SavedCityComparator
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddNewCityVH {
        val binding = DataBindingUtil.inflate<ItemRecyclerCitiesBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_recycler_cities,
            parent,
            false
        )
        return AddNewCityVH(binding = binding)
    }

    override fun onBindViewHolder(holder: AddNewCityVH, position: Int) {
        holder.bind(
            data = currentList[position],
            position = position
        )
    }

    inner class AddNewCityVH(
        binding: ItemRecyclerCitiesBinding
    ) : BaseViewHolder<ItemRecyclerCitiesBinding, CityModel>(binding = binding) {

        override fun bind(data: CityModel, position: Int) {
            with(binding) {
                model = data
                isBottomLineVisible = currentList.size.let { size ->
                    position < (size - 1)
                }
                if (hasPendingBindings()) {
                    executePendingBindings()
                }

                clItemView.setCustomOnClickListener {
                    onClicked.invoke(data)
                }
            }
        }
    }
}