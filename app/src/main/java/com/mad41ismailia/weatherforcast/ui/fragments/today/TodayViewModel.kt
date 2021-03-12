package com.mad41ismailia.weatherforcast.ui.fragments.today

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.CityWeatherData
import com.mad41ismailia.weatherforcast.repo.Repository
import java.util.*
import kotlin.collections.ArrayList

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

    fun orderList(list2: List<CityWeatherData>, current: String?): List<CityWeatherData> {
        if (current==null)
            return list2
        val list:ArrayList<CityWeatherData> = (list2 as ArrayList<CityWeatherData>?)!!
        var order = 0
        var currentCity:CityWeatherData? = null
        for (cityData in list!!){
            if (current== cityData.cityName){
                order = list.indexOf(cityData)
                currentCity = cityData
            }
        }
        if (order!=0){
            list.removeAt(order)
            list.add(0,currentCity!!)
            var current = list[order]
        }
        return list
    }

    fun updateDataIfNewDay() {
        val needUpdate = repo.getNeedUpdate()
        val lastDayUpdate:Int = repo.getLastDayUpdated()
        val today = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
        Log.i("finishingWork"," lastUpdate $lastDayUpdate today $today needupdate $needUpdate")
        if(today!=lastDayUpdate || needUpdate){
            repo.setNeedUpdate(false)
            repo.setLastDayUpdated(today)
            repo.updateAllCities()
        }
    }

    fun getLang(): String {
        return repo.getLang()
    }

    fun getUnits(): String {
        return repo.getUnits()
    }
}