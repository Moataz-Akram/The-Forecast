package com.mad41ismailia.weatherforcast.ui.fragments.today

import android.annotation.SuppressLint
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mad41ismailia.weatherforcast.INTERNECT_CONNECTION
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.CityWeatherData
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.DailyDatabase
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.HourlyDatabase
import com.mad41ismailia.weatherforcast.repo.Repository
import java.util.*

class TodayViewModel : ViewModel(){
    val repo = Repository.getRepoObject()


    fun fetchData2(): LiveData<List<CityWeatherData>> {
        val list = repo.loadCitiesNew()
        return repo.fetchAllCitiesData(list)
    }

    @SuppressLint("LogNotTimber")
    fun getCurrentLocationStandAlone(): String? {
        Log.i("mynewui","check again")
        return repo.getCurrentLocationStandAlone()
    }

}