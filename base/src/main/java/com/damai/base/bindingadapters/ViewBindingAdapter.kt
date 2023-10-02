package com.damai.base.bindingadapters

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
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
}