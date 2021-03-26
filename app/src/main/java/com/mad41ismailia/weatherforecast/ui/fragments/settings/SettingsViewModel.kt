package com.mad41ismailia.weatherforecast.ui.fragments.settings

import androidx.lifecycle.ViewModel
import com.mad41ismailia.weatherforecast.repo.Repository

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