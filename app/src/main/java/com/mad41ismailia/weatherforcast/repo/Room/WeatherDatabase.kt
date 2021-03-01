package com.mad41ismailia.weatherforcast.repo.Room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.AlertDatabase
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.DailyDatabase
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.HourlyDatabase
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.Locations

//, DailyDatabase::class, AlertDatabase::class
@Database(entities = [AlertDatabase::class, DailyDatabase::class, HourlyDatabase::class, Locations::class],version = 1)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun WeatherDao(): WeatherDao
}