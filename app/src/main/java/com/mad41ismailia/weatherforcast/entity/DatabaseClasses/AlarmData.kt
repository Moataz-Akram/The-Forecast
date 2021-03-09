package com.mad41ismailia.weatherforcast.entity.DatabaseClasses

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Alarm")
class AlarmData(
    val type:String,//alarm or alert
//    val name:String = "alarm",
    var value:Int, // the degree more or less than
    var condition:String,// temp more, temp less, ....
    var time:String,   //time will be written in recycler view
    var units:String,// metric, default, imperial
) {
    @PrimaryKey
    var uniqueID = UUID.randomUUID().toString()


    fun fromCelsiusToKelvin(){
        value += 273
    }
    fun fromCelsiusToFahrenheit(){
        value = value*(9/5) + 32
    }
    fun fromKelvinToCelsius(){
        value -= 273
    }
    fun fromKelvinToFahrenheit(){
        value = (value-273) * (9/5) +32
    }
    fun fromFahrenheitToCelsius(){
        value = (value-32) *(5/9)
    }
    fun fromFahrenheitToKelvin(){
        value = (value - 32 ) * (5/9) + 273
    }
}