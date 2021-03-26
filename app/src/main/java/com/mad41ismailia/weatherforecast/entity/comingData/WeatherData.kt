package com.mad41ismailia.weatherforecast.entity.comingData

data class WeatherData(
        val alerts: List<Alert>,
        val current: Current,
        val daily: List<Daily>,
        val hourly: List<Hourly>,
        val lat: Double,
        val lon: Double,
        val timezone: String,
        val timezone_offset: Int
)