package com.mad41ismailia.weatherforcast.entity.DatabaseClasses

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "CityWeatherData")
class CityWeatherData(
//@PrimaryKey
val cityName:String,
val lat:Double,
val lon:Double,
val weatherData: String,
)
{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}