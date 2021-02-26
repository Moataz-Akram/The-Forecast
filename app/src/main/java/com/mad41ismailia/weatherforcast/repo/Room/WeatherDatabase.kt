package com.mad41ismailia.weatherforcast.repo.Room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mad41ismailia.weatherforcast.entity.*

//, DailyDatabase::class, AlertDatabase::class
@Database(entities = [AlertDatabase::class,DailyDatabase::class,HourlyDatabase::class],version = 1)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun WeatherDao(): WeatherDao
}