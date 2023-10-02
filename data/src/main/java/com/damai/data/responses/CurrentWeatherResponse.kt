package com.damai.data.responses

import com.damai.base.BaseResponse
import com.google.gson.annotations.SerializedName

/**
 * Created by damai007 on 02/October/2023
 */
class CurrentWeatherResponse : BaseResponse() {
    @SerializedName("id")
    var id: Int? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("timezone")
    var timezone: Int? = null

    @SerializedName("dt")
    var dt: Int? = null

    @SerializedName("visibility")
    var visibility: Int? = null

    @SerializedName("coord")
    var coord: WeatherCoordinateResponse? = null

    @SerializedName("main")
    var main: WeatherMainResponse? = null

    @SerializedName("weather")
    var weather: List<WeatherStatusResponse>? = null
}

class WeatherCoordinateResponse {
    @SerializedName("lon")
    var lon: Double? = null

    @SerializedName("lat")
    var lat: Double? = null
}

class WeatherMainResponse {
    @SerializedName("temp")
    var temp: Double? = null

    @SerializedName("feels_like")
    var feelsLike: Double? = null

    @SerializedName("temp_min")
    var tempMin: Double? = null

    @SerializedName("temp_max")
    var tempMax: Double? = null

    @SerializedName("pressure")
    var pressure: Int? = null

    @SerializedName("humidity")
    var humidity: Int? = null
}

class WeatherStatusResponse {
    @SerializedName("id")
    var id: Int? = null

    @SerializedName("main")
    var main: String? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("icon")
    var icon: String? = null
}