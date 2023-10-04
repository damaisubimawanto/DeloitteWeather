package com.damai.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by damai007 on 04/October/2023
 */
@Entity(tableName = "city_entity")
data class CityEntity(
    @PrimaryKey val id: Int,
    val name: String?,
    var temperature: Int,
    @ColumnInfo(name = "weather_icon") var weatherIcon: String?,
    @ColumnInfo(name = "weather_icon_url") var weatherIconUrl: String?,
    val latitude: Double,
    val longitude: Double,
    val state: String?,
    @ColumnInfo(name = "created_time") val createdTime: Long,
    @ColumnInfo(name = "latest_updated") val latestUpdated: Long
)