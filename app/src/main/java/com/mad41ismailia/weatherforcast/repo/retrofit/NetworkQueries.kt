package com.mad41ismailia.weatherforcast.repo.retrofit

import com.mad41ismailia.weatherforcast.entity.comingData.WeatherData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkQueries {
    @GET("/data/2.5/onecall?&exclude=minutely&appid=2793471ee23491bf4da5d081017f8163")
    suspend fun getWeather(@Query("lat") lat:Double, @Query("lon") lon:Double,
                            @Query("units")units:String, @Query("lang")lang:String): WeatherData
}