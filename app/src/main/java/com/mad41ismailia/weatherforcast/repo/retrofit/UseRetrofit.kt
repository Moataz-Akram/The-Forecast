package com.mad41ismailia.weatherforcast.repo.retrofit

import com.mad41ismailia.weatherforcast.entity3.daily.DailyData
import retrofit2.Call

object UseRetrofit {
    val retrofitInterfaceObject = Retrofit.myRetrofit.create(NetworkQueries::class.java)
}