package com.mad41ismailia.weatherforcast.entity.DatabaseClasses

import androidx.room.Entity

@Entity(tableName = "Alarm")
class Alarm(
    val id : String,
    val type:String,//alarm or alert
    val name:String,
    var value:Int,
    var condition:String,// temp more, temp less, ....
    var time:Int,   //check if it will still be string  -> to be in milli
    var units:String,// metric, default, imperial
) {
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