package com.mad41ismailia.weatherforcast.ui.today

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.DailyDatabase
import com.mad41ismailia.weatherforcast.repo.Repository

class TodayViewModel(application: Application) : AndroidViewModel(application){
    // TODO: Implement the ViewModel
    val repo = Repository.getRepoObject()

    suspend fun fetchData(lat:Double,lon:Double){
        checkCall()
        repo.getWeatherData(lat,lon)
    }

    private fun checkCall() {
        
    }

    fun getDaily(): LiveData<List<DailyDatabase>> {
        return repo.getDaily()
    }


}