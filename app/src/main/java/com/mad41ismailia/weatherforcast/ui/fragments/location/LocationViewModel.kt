package com.mad41ismailia.weatherforcast.ui.fragments.location

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.Locations
import com.mad41ismailia.weatherforcast.repo.Repository

class LocationViewModel(application: Application) : AndroidViewModel(application) {
    val repo = Repository.getRepoObject()
    val list = repo.loadCities()

    fun loadCities(): ArrayList<String?> {
        return repo.loadCities()
    }

    private val comingList:MutableLiveData<ArrayList<String?>> = MutableLiveData()
    init {
        comingList.postValue(list)
    }

    suspend fun addLocation(location: Locations){
        repo.addLocation(location)
    }

    suspend fun getCurrentLocation(id:Int): Locations {
        return repo.getCurrentLocation(id)
    }

    suspend fun getLocations(): LiveData<List<Locations>> {
        return repo.getLocations()
    }

}