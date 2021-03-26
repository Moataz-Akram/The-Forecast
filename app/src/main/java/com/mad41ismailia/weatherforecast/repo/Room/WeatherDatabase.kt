package com.mad41ismailia.weatherforecast.repo.Room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mad41ismailia.weatherforecast.entity.DatabaseClasses.*

@Database(entities = [CityWeatherData::class,AlarmData::class],version = 1)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun WeatherDao(): WeatherDao
}