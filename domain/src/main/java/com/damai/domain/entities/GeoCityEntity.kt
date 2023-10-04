package com.damai.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by damai007 on 04/October/2023
 */
@Entity(tableName = "geo_city_entity")
data class GeoCityEntity(
    @PrimaryKey val id: Int,
    val name: String?,
    val latitude: Double,
    val longitude: Double,
    val state: String?,
    @ColumnInfo(name = "created_time") val createdTime: Long,
    @ColumnInfo(name = "latest_updated") val latestUpdated: Long
)
