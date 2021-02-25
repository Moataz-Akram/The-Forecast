package com.mad41ismailia.weatherforcast.repo.retrofit

import com.mad41ismailia.weatherforcast.entity3.alerts.AlertData
import com.mad41ismailia.weatherforcast.entity3.daily.DailyData
import com.mad41ismailia.weatherforcast.entity3.hourly.HourlyData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface NetworkQueries {
    @GET("/data/2.5/onecall?units=metric&exclude=minutely,current,Hourly,alarm&appid=2793471ee23491bf4da5d081017f8163")
    public fun getDaily2(@Query("lat") lat:Double, @Query("lon") lon:Double): Call<DailyData>

    @GET("/data/2.5/onecall?units=metric&exclude=minutely,current,Daily,alarm&appid=2793471ee23491bf4da5d081017f8163")
    public fun getHourly2(@Query("lat") lat:Double, @Query("lon") lon:Double): Call<HourlyData>

    @GET("/data/2.5/onecall?units=metric&exclude=minutely,current,hourly,daily&appid=2793471ee23491bf4da5d081017f8163")
    public fun getAlerts2(@Query("lat")lat:Double, @Query("lon") long:Double): Call<AlertData>
}



//@GET("/data/2.5/onecall?lat=29.3132&lon=30.8508&units=metric&exclude=minutely,current,hourly,alarm&appid=2793471ee23491bf4da5d081017f8163")
//public fun getDaily(): Call<DailyData>
//
//@GET("/data/2.5/{url}")
//public fun getHourly(@Query("url") url:String): Call<HourlyData>
//
//
//@GET("/data/2.5/onecall?lat=29.3132&lon=30.8508&units=metric&exclude=minutely,current,hourly,daily&appid=2793471ee23491bf4da5d081017f8163")
//public fun getAlerts(@Query("latitude")lat:Double,@Query("long") long:Double): Call<AlertData>