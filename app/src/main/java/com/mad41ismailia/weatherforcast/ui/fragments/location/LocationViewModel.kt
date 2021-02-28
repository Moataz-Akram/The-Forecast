package com.mad41ismailia.weatherforcast.ui.fragments.location

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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



}