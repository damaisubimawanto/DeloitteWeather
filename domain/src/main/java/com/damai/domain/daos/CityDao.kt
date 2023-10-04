package com.damai.domain.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.damai.domain.entities.CityEntity

/**
 * Created by damai007 on 04/October/2023
 */
@Dao
interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cityEntity: CityEntity)

    @Query("SELECT * FROM city_entity ORDER BY created_time ASC")
    suspend fun getAllSavedCities(): List<CityEntity>

    @Query("SELECT * FROM city_entity WHERE latitude = :latitude AND longitude = :longitude")
    suspend fun getSavedCity(
        latitude: Double,
        longitude: Double
    ): CityEntity?

    @Query("SELECT * FROM city_entity WHERE name = :cityName")
    suspend fun getSavedCityFromName(cityName: String): CityEntity?
}