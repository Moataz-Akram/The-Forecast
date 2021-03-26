package com.mad41ismailia.weatherforecast.repo.retrofit


object UseRetrofit {
    val retrofitInterfaceObject: NetworkQueries = Retrofit.myRetrofit.create(NetworkQueries::class.java)
}