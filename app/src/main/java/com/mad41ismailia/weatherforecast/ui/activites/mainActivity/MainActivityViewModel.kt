package com.mad41ismailia.weatherforecast.ui.activites.mainActivity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.mad41ismailia.weatherforecast.repo.Repository


class MainActivityViewModel(  application: Application) : AndroidViewModel(application) {
    //createRepo & database
    init {
        Repository.createObject(application)
    }

    private val repository = Repository.getRepoObject()

    fun setCurrentLocation(currentLocation: String) {
        repository.setCurrentLocation(currentLocation)
    }

    fun getCurrentLocation(): String? {
        return repository.getCurrentLocation()
    }

    fun addCurrentCity(city: String, latitude: Double, longitude: Double) {
        repository.addDataForNewCity(city, latitude, longitude)
        repository.updateWidget()
    }

    fun deleteOldCurrentCityData(currentCity: String) {
        repository.deleteOldCurrentCityData(currentCity)
    }
}