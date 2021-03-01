package com.mad41ismailia.weatherforcast.ui.mainActivity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.Locations
import com.mad41ismailia.weatherforcast.repo.Repository


class MainActivityViewModel(application: Application) : AndroidViewModel(application){
    //createRepo & database
    private val repo = Repository.createObject(application)
    private val repository = Repository.getRepoObject()

    fun setCurrentLocation(currentLocation:String){
        repository.setCurrentLocation(currentLocation)
    }

    suspend fun addLocation(location: Locations){
        repository.addLocation(location)
    }

    suspend fun getCurrentLocation(id:Int): Locations {
        return repository.getCurrentLocation(id)
    }

}