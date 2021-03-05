package com.mad41ismailia.weatherforcast.ui.fragments.location

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.Locations
import com.mad41ismailia.weatherforcast.repo.Repository

class LocationViewModel : ViewModel() {
    val repo = Repository.getRepoObject()
    val list = repo.loadCities()

    fun loadCities(): ArrayList<String?> {
        return repo.loadCities()
    }

    private val comingList:MutableLiveData<ArrayList<String?>> = MutableLiveData()
    init {
        comingList.postValue(list)
    }

    suspend fun addCityDB(location: Locations){
        repo.addCityDB(location)
    }

    fun getCurrentLocation(id:Int): Locations {
        return repo.getCurrentLocation(id)
    }

//    fun getLocations(): LiveData<List<Locations>> {
//        return repo.getLocationsFromDB()
//    }

    fun saveCity(city:String) {
        repo.saveCity(city)
    }

    suspend fun fetchCityData(city: String, lat:Double, lon:Double) {
        repo.getWeatherData(city,lat,lon,repo.getUnits(), repo.getLang())
    }

}