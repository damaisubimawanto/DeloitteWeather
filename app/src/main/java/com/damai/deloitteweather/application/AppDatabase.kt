package com.damai.deloitteweather.application

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.damai.base.utils.Constants.ROOM_DATABASE_NAME
import com.damai.domain.daos.CityDao
import com.damai.domain.entities.CityEntity

/**
 * Created by damai007 on 04/October/2023
 */
@Database(
    entities = [
        CityEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cityDao(): CityDao

    companion object {

        fun buildDatabase(application: Application): AppDatabase {
            return Room.databaseBuilder(
                application,
                AppDatabase::class.java,
                ROOM_DATABASE_NAME
            ).fallbackToDestructiveMigration().build()
        }
    }
}