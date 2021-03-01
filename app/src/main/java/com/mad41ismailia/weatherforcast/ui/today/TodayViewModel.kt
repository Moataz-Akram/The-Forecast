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
    suspend fun fetchData(lat:Double,lon:Double, units:String, lang:String){
        if(!checkCall()){
            flag = true
            repo.getWeatherData(lat,lon,units,lang)
        }
    }

    private fun checkCall(): Boolean {
        return flag
    }

    fun getDaily(): LiveData<List<DailyDatabase>> {
        return repo.getDaily()
    }

    suspend fun checkCities():Boolean{
//        val current = repo.getCurrentLocation(1)
//        val myCity = current.cityAddress
//        //shared pref list not database list
//        val cityList = repo.loadCities()
//
//        val cityListDb = repo.getLocations()
////        Log.i("comingdata","today check ${cityListDb.size }")
//        Log.i("comingdata","today check ${current }")
//        if(myCity!==null){
//            return true
//        }
        return true
    }

}