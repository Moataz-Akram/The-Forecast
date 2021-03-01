package com.mad41ismailia.weatherforcast.ui.today

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.DailyDatabase
import com.mad41ismailia.weatherforcast.repo.Repository

class TodayViewModel(application: Application) : AndroidViewModel(application){
    // TODO: Implement the ViewModel
    val repo = Repository.getRepoObject()
    var flag = false
    suspend fun fetchData(lat:Double,lon:Double){
        if(!checkCall()){
            flag = true
            repo.getWeatherData(lat,lon)
        }
    }

    private fun checkCall(): Boolean {
        return flag
    }

    fun getDaily(): LiveData<List<DailyDatabase>> {
        return repo.getDaily()
    }

    fun checkCities():Boolean{
        val current = repo.getCurrentLocation()
        val cityList = repo.loadCities()
        Log.i("comingdata from here","${cityList.size }")
        Log.i("comingdata from here","${current }")
        if(current!==null|| cityList.size>1){
            return true
        }
        return false
    }

}