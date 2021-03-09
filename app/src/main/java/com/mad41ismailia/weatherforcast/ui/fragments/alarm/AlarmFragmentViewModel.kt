package com.mad41ismailia.weatherforcast.ui.fragments.alarm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.AlarmData
import com.mad41ismailia.weatherforcast.repo.Repository

class AlarmFragmentViewModel:ViewModel() {
    val repo = Repository.getRepoObject()

    fun getAlarms(): LiveData<List<AlarmData>> {
        return repo.getAlarms()
    }

    fun deleteAlarm(id:String){
        repo.deleteAlarm(id)
    }

    fun getUnits(): String {
        return repo.getUnits()
    }
}