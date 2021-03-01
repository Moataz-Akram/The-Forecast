package com.mad41ismailia.weatherforcast.ui.mainActivity

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mad41ismailia.weatherforcast.CITIES_LIST
import com.mad41ismailia.weatherforcast.PREF_NAME
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.Locations
import com.mad41ismailia.weatherforcast.repo.Repository
import java.lang.reflect.Type


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