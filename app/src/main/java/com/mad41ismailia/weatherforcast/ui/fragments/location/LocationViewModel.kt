package com.mad41ismailia.weatherforcast.ui.fragments.location

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.Locations
import com.mad41ismailia.weatherforcast.repo.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocationViewModel : ViewModel() {
    val repo = Repository.getRepoObject()
    val list = repo.loadCitiesNew()

    fun loadCities(): ArrayList<String?> {
        return repo.loadCitiesNew()
    }

    private val comingList:MutableLiveData<ArrayList<String?>> = MutableLiveData()
    init {
        comingList.postValue(list)
    }

    fun saveCity(city:String) {
        repo.saveCity(city)
    }

    fun fetchCityData(city: String) {
        val list:ArrayList<String?> = arrayListOf()
        list.add(city)
        repo.fetchAllCitiesData(list)
    }

    @SuppressLint("LogNotTimber")
    fun deleteCity(city: String) {
        repo.deleteCity(city)
        Log.i("deletecity","inside view model")
    }
    fun getCurrentLocationStandAlone():String?{
        return repo.getCurrentLocationStandAlone()
    }

}