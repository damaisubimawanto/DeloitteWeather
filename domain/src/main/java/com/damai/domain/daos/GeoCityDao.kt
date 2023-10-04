package com.damai.domain.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.damai.domain.entities.GeoCityEntity

/**
 * Created by damai007 on 04/October/2023
 */
@Dao
interface GeoCityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(geoCityEntity: GeoCityEntity)

    @Query("SELECT * FROM geo_city_entity ORDER BY name ASC")
    suspend fun getAllSavedCities(): List<GeoCityEntity>

    @Query("SELECT * FROM geo_city_entity WHERE name = :cityName")
    suspend fun getGeoCity(cityName: String): GeoCityEntity?
}