package com.mad41ismailia.weatherforcast.entity3.daily

//val url = "https://api.openweathermap.org/data/2.5/onecall?lat=29.3132&lon=30.8508&units=metric&exclude=minutely,current,hourly&appid=2793471ee23491bf4da5d081017f8163"

data class DailyData(
    val daily: List<Daily>,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int
)