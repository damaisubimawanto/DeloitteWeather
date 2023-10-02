package com.damai.base.bindingadapters

import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import com.damai.base.R
import com.damai.base.extensions.loadImageWithCenterCrop

/**
 * Created by damai007 on 02/October/2023
 */
object ViewBindingAdapter {

    @JvmStatic
    @BindingAdapter("loadImage")
    fun bindLoadImage(view: AppCompatImageView, url: String?) {
        view.loadImageWithCenterCrop(url = url)
    }

    @JvmStatic
    @BindingAdapter("temperature")
    fun bindTemperature(view: AppCompatTextView, temperature: Int) {
        val temperatureText = view.resources.getString(R.string.temperature_in_celcius, temperature)
        view.text = temperatureText
    }

    @JvmStatic
    @BindingAdapter("weatherTypeIcon")
    fun bindWeatherIcon(view: AppCompatImageView, weatherType: String?) {
        view.setImageResource(
            when (weatherType) {
                "Clear" -> R.drawable.ic_clear_day_24px
                else -> R.drawable.ic_cloud_24px
            }
        )
    }
}