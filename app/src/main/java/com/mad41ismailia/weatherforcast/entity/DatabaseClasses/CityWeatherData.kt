package com.mad41ismailia.weatherforcast.entity.DatabaseClasses

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CityWeatherData")
class CityWeatherData(
@PrimaryKey
val cityName:String,
val lat:Double,
val lon:Double,
val weatherData: String
)
//{
//    @PrimaryKey(autoGenerate = true)
//    var id: Int = 0
//}