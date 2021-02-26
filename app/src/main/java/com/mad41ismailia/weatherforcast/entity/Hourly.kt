package com.mad41ismailia.weatherforcast.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

data class Hourly(
        @ColumnInfo
        val clouds: Int,
        @ColumnInfo
        val dew_point: Double,
        @ColumnInfo
        val dt: Int,
        @ColumnInfo
        val feels_like: Double,
        @ColumnInfo
        val humidity: Int,
        @ColumnInfo
        val pop: Double,
        @ColumnInfo
        val pressure: Int,
        @ColumnInfo
        val temp: Double,
        @ColumnInfo
        val uvi: Double,
        @ColumnInfo
        val visibility: Int,
        @Embedded(prefix = "Weather_")
        val weather: List<WeatherXX>,
        @ColumnInfo
        val wind_deg: Int,
        @ColumnInfo
        val wind_speed: Double
){
    @PrimaryKey(autoGenerate = true)
    lateinit var id:Integer
}