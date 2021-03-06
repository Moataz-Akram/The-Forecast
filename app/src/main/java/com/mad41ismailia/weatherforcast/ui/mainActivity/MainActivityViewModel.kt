package com.mad41ismailia.weatherforcast.ui.mainActivity

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.Locations
import com.mad41ismailia.weatherforcast.repo.Repository
import java.util.*


class MainActivityViewModel(application: Application) : AndroidViewModel(application){
    //createRepo & database
    private val repo = Repository.createObject(application)
    private val repository = Repository.getRepoObject()

    fun setCurrentLocationStandAlone(currentLocation:String){
        repository.setCurrentLocationStandAlone(currentLocation)
    }
    fun getCurrentLocationStandAlone():String?{
        return repository.getCurrentLocationStandAlone()
    }
}