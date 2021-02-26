package com.mad41ismailia.weatherforcast.entity.DatabaseClasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mad41ismailia.weatherforcast.entity.comingData.Alert

@Entity(tableName = "Alert Table")
data class AlertDatabase(
        val lat: Double,
        val lon: Double,
        val description: String,
        val end: Int,
        val event: String,
        val sender_name: String,
        val start: Int
){
    @PrimaryKey(autoGenerate = true)
    var id:Int=0
    constructor(lat: Double,lon: Double,alert: Alert):this(lat,lon,alert.description,alert.end,alert.event,alert.sender_name,alert.start)
}