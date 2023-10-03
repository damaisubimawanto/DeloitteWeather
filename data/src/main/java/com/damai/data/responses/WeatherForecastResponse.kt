package com.damai.data.responses

import com.damai.base.BaseResponse
import com.google.gson.annotations.SerializedName

/**
 * Created by damai007 on 03/October/2023
 */
class WeatherForecastResponse : BaseResponse() {
    @SerializedName("list")
    var list: List<ForecastResponse>? = null
}

class ForecastResponse {
    @SerializedName("dt")
    var dt: Long? = null

    @SerializedName("main")
    var main: WeatherMainResponse? = null

    @SerializedName("weather")
    var weather: List<WeatherStatusResponse>? = null
}