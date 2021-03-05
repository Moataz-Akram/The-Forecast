package com.mad41ismailia.weatherforcast.entity.DatabaseClasses

import androidx.room.*
import com.mad41ismailia.weatherforcast.entity.comingData.*

@Entity(tableName = "Daily")
data class DailyDatabase(
    val cityName:String,
    val lat: Double,
    val lon: Double,
    val clouds: Int,
    val dew_point: Double,
    val dt: Int,
    @Embedded(prefix = "FeelsLike_")
    val feels_like: FeelsLike,
    val humidity: Int,
    val pop: Double,
    val pressure: Int,
    val sunrise: Int,
    val sunset: Int,
    @Embedded(prefix = "Temp_")
    val temp: Temp,
    val uvi: Double,
    @Embedded(prefix = "Weather_")
    val weather: Weather,
    val wind_deg: Int,
    val wind_speed: Double
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
        constructor(cityName: String, lat: Double,long: Double,daily: Daily):this(cityName, lat,long,daily.clouds,daily.dew_point,daily.dt,daily.feels_like,
                                                            daily.humidity,daily.pop,daily.pressure,daily.sunrise,daily.sunset,daily.temp,
                                                            daily.uvi,daily.weather[0],daily.wind_deg,daily.wind_speed)
}