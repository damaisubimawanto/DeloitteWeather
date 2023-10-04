package com.damai.base.utils

/**
 * Created by damai007 on 02/October/2023
 */
object Constants {

    const val BASE_URL = "https://api.openweathermap.org"
    const val WEATHER_ICON_HEAD = "https://openweathermap.org/img/wn/"
    const val WEATHER_ICON_TAIL_1X = ".png"
    const val WEATHER_ICON_TAIL_2X = "@2x.png"
    const val ROOM_DATABASE_NAME = "deloitte-weather-database"
    const val TIMEOUT = 60L
    const val QUERY_LIMIT = 5
    const val CACHE_DAYS = 3    // days

    const val SUCCESS_CODE = 200
    const val EMPTY_CODE = 404
    const val GLIDE_CROSS_FADE = 300   // milliseconds
    const val ONE_DAY_IN_MILLISECONDS = 3_600_000L

    const val API_VERSION_1 = "1.0"
    const val API_VERSION_2_POINT_5 = "2.5"
    const val API_KEY = "640a41f85270f8bd9953cf1ec2651147"
    const val API_METRIC_UNITS = "metric"

    const val ARGS_CITY_NAME = "argsCityName"
    const val ARGS_CITY_ID = "argsCityId"
    const val ARGS_LATITUDE = "argsLatitude"
    const val ARGS_LONGITUDE = "argsLongitude"
    const val ARGS_TEMPERATURE = "argsTemperature"
    const val ARGS_WEATHER_ICON_URL = "argsWeatherIconUrl"

    const val TAG_ADD_NEW_CITY_BOTTOMSHEET_DIALOG = "AddNewCityBottomSheetDialog"
}