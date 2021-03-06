package com.mad41ismailia.weatherforcast.entity.DatabaseClasses

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CityWeatherData")
class CityWeatherData(
val cityName:String,
val weatherData: String
)
{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}