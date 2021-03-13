package com.mad41ismailia.weatherforcast.ui.fragments.settings

import androidx.lifecycle.ViewModel
import com.mad41ismailia.weatherforcast.repo.Repository

class SettingsViewModel:ViewModel() {
    val repo = Repository.getRepoObject()

     fun updateAllCities() {
        repo.updateAllCities()
     }

    fun updateAlarms(oldUnits: String, newUnits: String) {
        repo.updateAlarms(oldUnits,newUnits)
    }

    fun setNeedUpdate(flag:Boolean) {
        repo.setNeedUpdate(flag)
    }
}