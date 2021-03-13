package com.mad41ismailia.weatherforcast.ui.fragments.location

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mad41ismailia.weatherforcast.repo.Repository

@SuppressLint("LogNotTimber")
class LocationViewModel : ViewModel() {
    val repo = Repository.getRepoObject()
    val list = repo.loadAllCities()

    fun loadAllCities(): ArrayList<String?> {
        return repo.loadAllCities()
    }

    fun saveNewCity(city:String) {
        repo.saveNewCity(city)
    }

    fun addDataForNewCity(city: String, latitude: Double, longitude: Double) {
        repo.addDataForNewCity(city,latitude,longitude)
    }

        fun deleteCity(city: String) {
        repo.deleteCity(city)
        Log.i("deletecity","inside view model")
    }

    fun getCurrentLocation():String?{
        return repo.getCurrentLocation()
    }
}