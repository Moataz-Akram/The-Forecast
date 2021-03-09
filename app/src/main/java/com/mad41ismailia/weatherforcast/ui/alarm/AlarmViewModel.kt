package com.mad41ismailia.weatherforcast.ui.alarm

import androidx.lifecycle.ViewModel
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.AlarmData
import com.mad41ismailia.weatherforcast.repo.Repository

class AlarmViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    val repo = Repository.getRepoObject()

    fun getUnits():String{
        return repo.getUnits()
    }

    fun addAlarmToDB(newAlarm: AlarmData) {
        repo.addAlarmToDB(newAlarm)
    }
}