package com.mad41ismailia.weatherforcast.entity3.alerts

data class Alert(
    val description: String,
    val end: Int,
    val event: String,
    val sender_name: String,
    val start: Int
)