package com.mad41ismailia.weatherforecast.repo.retrofit

import com.mad41ismailia.weatherforecast.DATA_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit {
    val myRetrofit = Retrofit.Builder().baseUrl(DATA_URL).addConverterFactory(GsonConverterFactory.create()).build()
}