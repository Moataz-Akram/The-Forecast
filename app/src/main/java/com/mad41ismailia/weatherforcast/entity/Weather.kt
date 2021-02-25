package com.mad41ismailia.weatherforcast.entity

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)