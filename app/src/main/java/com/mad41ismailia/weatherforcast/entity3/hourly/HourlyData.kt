package com.mad41ismailia.weatherforcast.entity3.hourly

data class HourlyData(
    val hourly: List<Hourly>,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int
)