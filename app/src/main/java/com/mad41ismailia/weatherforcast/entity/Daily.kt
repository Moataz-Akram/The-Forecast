package com.mad41ismailia.weatherforcast.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

data class Daily(
        @PrimaryKey(autoGenerate = true)
        var id: String = UUID.randomUUID().toString(),
        @ColumnInfo
        val clouds: Int,
        @ColumnInfo
        val dew_point: Double,
        @ColumnInfo
        val dt: Int,
        @Embedded(prefix = "FeelsLike_")
        val feels_like: FeelsLike,
        @ColumnInfo
        val humidity: Int,
        @ColumnInfo
        val pop: Double,
        @ColumnInfo
        val pressure: Int,
        @ColumnInfo
        val sunrise: Int,
        @ColumnInfo
        val sunset: Int,
        @ColumnInfo
        val temp: Temp,
        @ColumnInfo
        val uvi: Double,
        @Embedded(prefix = "Weather_")
        val weather: List<WeatherX>,
        @ColumnInfo
        val wind_deg: Int,
        @ColumnInfo
        val wind_speed: Double
){

}