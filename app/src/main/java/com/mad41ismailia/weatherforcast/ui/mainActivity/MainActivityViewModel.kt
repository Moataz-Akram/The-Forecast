package com.mad41ismailia.weatherforcast.ui.mainActivity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.mad41ismailia.weatherforcast.repo.Repository


class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    //createRepo & database
    private val repo = Repository.createObject(application)
    private val repository = Repository.getRepoObject()

    fun setCurrentLocation(currentLocation: String) {
        repository.setCurrentLocation(currentLocation)
    }

    fun getCurrentLocation(): String? {
        return repository.getCurrentLocation()
    }

    //    fun fetchAllCitiesData(city:String?){
//        val list : ArrayList<String?> = arrayListOf(city)
//        repository.fetchAllCitiesData(list)
//    }
    fun addCurrentCity(city: String, latitude: Double, longitude: Double) {
        repository.addDataForNewCity(city, latitude, longitude)
    }
    fun updateCurrentCity(city: String, newName: String, latitude: Double, longitude: Double){
        repository.updateCurrentCity(city,newName,latitude,longitude)
    }

    fun deleteOldCurrent(currentCity: String) {
        repository.deleteCity(currentCity)
    }

}