package com.mad41ismailia.weatherforcast.ui.fragments.today

import android.location.Geocoder
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mad41ismailia.weatherforcast.INTERNECT_CONNECTION
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.DailyDatabase
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.HourlyDatabase
import com.mad41ismailia.weatherforcast.repo.Repository
import java.util.*

class TodayViewModel : ViewModel(){
    val repo = Repository.getRepoObject()

    suspend fun fetchData() {
//        val list = repo.loadCities()
        val list = repo.loadCitiesNew()
        if(list.isNotEmpty()){
            repo.fetchAllCitiesData(list)
            }
    }

//    suspend fun fetchData2(geocoder: Geocoder) {
//        if(INTERNECT_CONNECTION) {
//            val geocoder = geocoder
//            val list = repo.loadCities()
//            val lang = repo.getLang()
//            val units = repo.getUnits()
//            if(list.isNotEmpty()){
//                repo.fetchAllCitiesData(list)
//                for(city in list){
//                    val latLong = geocoder.getFromLocationName(city, 1)
//                    if (city != null) {
//                        repo.getWeatherData(city, latLong[0].latitude,latLong[0].longitude, units, lang)
//                    }
//                }
//            }
//        }
//    }

    fun getDaily(city:String): LiveData<List<DailyDatabase>> {
        return repo.getDaily(city)
    }

    fun getCityAllLiveData():ArrayList<LiveData<List<DailyDatabase>>>{
        return repo.getCityAllLiveData()
    }


    suspend fun getDaily2(city: String): List<DailyDatabase> {
        return repo.getDaily2(city)
    }

    fun getHourly(city: String): LiveData<List<HourlyDatabase>> {
        return repo.getHourly(city)
    }

    fun loadCities(): ArrayList<String?> {
        return repo.loadCitiesNew()
    }

//    fun checkCities():Boolean{
//        val list = repo.loadCities()
//        if(list.size==1 && list[0]==null){
//            return false
//        }
//        return true
//    }
}