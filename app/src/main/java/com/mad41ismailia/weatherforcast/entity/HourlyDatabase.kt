package com.mad41ismailia.weatherforcast.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Hourly")
class HourlyDatabase(
        val lat: Double,
        val lon: Double,
        val clouds: Int,
        val dew_point: Double,
        val dt: Int,
        val feels_like: Double,
        val humidity: Int,
        val pop: Double,
        val pressure: Int,
        val temp: Double,
        val uvi: Double,
        val visibility: Int,
        @Embedded(prefix = "Weather_")
        val weather: WeatherXX,
        val wind_deg: Int,
        val wind_speed: Double
){

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    constructor(lat: Double,long: Double,hourly:Hourly):this(
                lat, long, hourly.clouds, hourly.dew_point, hourly.dt, hourly.feels_like, hourly.humidity, hourly.pop, hourly.pressure, hourly.temp, hourly.uvi, hourly.visibility,
                hourly.weather[0],hourly.wind_deg,hourly.wind_speed)
//
//
//
//
//    this(lat,long,hourly.clouds,hourly.dew_point,hourly.dt,
//    hourly.feels_like,hourly.humidity,hourly.pop,hourly.pressure,
//    hourly.temp,hourly.uvi,hourly.visibility,hourly.weather,
//    hourly.wind_deg,hourly.wind_speed)
}