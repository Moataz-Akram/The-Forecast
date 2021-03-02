package com.mad41ismailia.weatherforcast.ui.mainActivity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.Locations
import com.mad41ismailia.weatherforcast.repo.Repository


class MainActivityViewModel(application: Application) : AndroidViewModel(application){
    //createRepo & database
    private val repo = Repository.createObject(application)
    private val repository = Repository.getRepoObject()

    //shared pref
    fun setCurrentLocation(currentLocation:String){
        repository.setCurrentLocation(currentLocation)
    }

    //DB
    suspend fun addLocation(location: Locations){
        repository.addCityDB(location)
    }

    //DB
    fun getCurrentLocation(id:Int): Locations {
        return repository.getCurrentLocation(id)
    }

}