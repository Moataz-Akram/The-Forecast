package com.mad41ismailia.weatherforcast.ui.fragments.settings

import androidx.lifecycle.ViewModel
import com.mad41ismailia.weatherforcast.repo.Repository

class SettingsViewModel:ViewModel() {
    val repo = Repository.getRepoObject()

     fun updateAllCities() {
            val list = repo.loadCitiesNew()
            if(list.isNotEmpty()){
                repo.fetchAllCitiesData(list)
            }
    }

    fun deleteCurrentFromDatabase() {
        repo.deleteCurrentFromDatabase()
    }

}