package com.mad41ismailia.weatherforcast.repo.retrofit

import com.mad41ismailia.weatherforcast.entity3.daily.DailyData
import retrofit2.Call

class UseRetrofit {
    val retrofitInterfaceObject = Retrofit.myRetrofit.create(NetworkQueries::class.java)
//    fun getData(): Call<DailyData> {
//        return retrofitInterfaceObject.getDaily2()
//    }
}