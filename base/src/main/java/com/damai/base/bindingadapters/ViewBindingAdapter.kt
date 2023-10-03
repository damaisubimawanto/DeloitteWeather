package com.damai.base.bindingadapters

import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import com.damai.base.R
import com.damai.base.extensions.loadImageWithCenterCrop
import com.damai.base.utils.SimpleDateUtil

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
    @BindingAdapter("loadWeatherIcon")
    fun bindLoadWeatherIcon(view: AppCompatImageView, url: String?) {
        url?.let {
            view.loadImageWithCenterCrop(url = it)
        } ?: view.setImageResource(R.drawable.ic_cloud_24px)
    }

    @JvmStatic
    @BindingAdapter("temperature")
    fun bindTemperature(view: AppCompatTextView, temperature: Int) {
        val temperatureText = view.resources.getString(R.string.temperature_in_celcius, temperature)
        view.text = temperatureText
    }

    @JvmStatic
    @BindingAdapter("hourTime")
    fun bindTime(view: AppCompatTextView, unixTimestamp: Long) {
        val date = SimpleDateUtil.getDateFromUnixTimestamp(unixTimestamp = unixTimestamp)
        val hourText = SimpleDateUtil.parseDateToString(
            givenDate = date,
            outputFormat = SimpleDateUtil.DateFormat.KK_MM
        )
        view.text = hourText
    }
}