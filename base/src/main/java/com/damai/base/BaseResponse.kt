package com.damai.base

import com.google.gson.annotations.SerializedName

/**
 * Created by damai007 on 02/October/2023
 */
open class BaseResponse {
    @SerializedName("cod")
    var cod: Int? = null
}