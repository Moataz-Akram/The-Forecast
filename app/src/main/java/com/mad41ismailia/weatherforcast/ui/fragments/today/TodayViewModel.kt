package com.mad41ismailia.weatherforcast.ui.fragments.today

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.CityWeatherData
import com.mad41ismailia.weatherforcast.repo.Repository

@SuppressLint("LogNotTimber")
class TodayViewModel : ViewModel(){
    val repo = Repository.getRepoObject()


//    fun fetchData2(): LiveData<List<CityWeatherData>> {
//        val list = repo.loadAllCities()
//        repo.clearDBNotInList(list)
//        Log.i("fixingBugs","list $list")
//        return repo.fetchAllCitiesData(list)
//    }

    fun updateAllCities(){
//        repo.updateAllCities()
        repo.clearDBNotInList()
    }

    fun observeWeatherData(): LiveData<List<CityWeatherData>> {
        return repo.observeWeatherData()
    }

    fun getCurrentLocation(): String? {
        Log.i("mynewui","check again")
        return repo.getCurrentLocation()
    }

}