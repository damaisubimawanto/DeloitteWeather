package com.damai.data.responses

import com.google.gson.annotations.SerializedName

/**
 * Created by damai007 on 03/October/2023
 */
class GeoLocationCityResponse {
    @SerializedName("name")
    var name: String? = null

    @SerializedName("lat")
    var lat: Double? = null

    @SerializedName("lon")
    var lon: Double? = null

    @SerializedName("country")
    var country: String? = null

    @SerializedName("state")
    var state: String? = null
}