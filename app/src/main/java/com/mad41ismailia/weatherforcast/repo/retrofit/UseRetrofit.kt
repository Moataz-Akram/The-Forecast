package com.mad41ismailia.weatherforcast.repo.retrofit


object UseRetrofit {
    val retrofitInterfaceObject = Retrofit.myRetrofit.create(NetworkQueries::class.java)
}