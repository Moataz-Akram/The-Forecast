package com.mad41ismailia.weatherforcast.entity.DatabaseClasses

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Locations")
class Locations(
        val cityAddress:String?,
        val lat:Double,
        val lon:Double
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

}