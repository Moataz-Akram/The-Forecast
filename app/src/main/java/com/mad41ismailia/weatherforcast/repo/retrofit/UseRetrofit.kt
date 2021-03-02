package com.mad41ismailia.weatherforcast.repo.retrofit


object UseRetrofit {
    val retrofitInterfaceObject: NetworkQueries = Retrofit.myRetrofit.create(NetworkQueries::class.java)
}