package com.mad41ismailia.weatherforcast.ui.activites.mainActivity

import android.app.Application
import android.appwidget.AppWidgetManager
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.mad41ismailia.weatherforcast.repo.Repository
import com.mad41ismailia.weatherforcast.ui.widget.WeatherWidget
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


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

    //    fun fetchAllCitiesData(city:String?){
//        val list : ArrayList<String?> = arrayListOf(city)
//        repository.fetchAllCitiesData(list)
//    }
    fun addCurrentCity(city: String, latitude: Double, longitude: Double) {
        repository.addDataForNewCity(city, latitude, longitude)
        repository.updateWidget()
    }
//    fun updateCurrentCity(city: String, newName: String, latitude: Double, longitude: Double){
//        repository.updateCurrentCity(city,newName,latitude,longitude)
//    }
//
//    fun deleteOldCurrent(currentCity: String) {
//        repository.deleteCity(currentCity)
//    }

}