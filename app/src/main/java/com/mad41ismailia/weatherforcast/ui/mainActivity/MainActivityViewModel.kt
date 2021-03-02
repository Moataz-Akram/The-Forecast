package com.mad41ismailia.weatherforcast.ui.mainActivity

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.Locations
import com.mad41ismailia.weatherforcast.repo.Repository
import java.util.*


class MainActivityViewModel(application: Application) : AndroidViewModel(application){
    //createRepo & database
    private val repo = Repository.createObject(application)
    private val repository = Repository.getRepoObject()

    //shared pref
    fun setCurrentLocation(currentLocation:String){
        repository.setCurrentLocation(currentLocation)
    }

    //DB
    suspend fun addLocation(location: Locations){
        repository.addCityDB(location)
    }

    //DB
    fun getCurrentLocation(id:Int): Locations {
        return repository.getCurrentLocation(id)
    }


    fun checkLanguage(activity: Activity) {
//        val sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE)
        var lang = repository.getLang()
//        lang = "ar"
//        val currentLang = Locale.getDefault().language
//        Log.i("comingdata", "SP lang $lang")
//        Log.i("comingdata", "device lang$currentLang")
//        Thread.sleep(1000)
        val locale = Locale(lang)
//        if(sharedPreferences.getBoolean("restartactivity",false)){
//            sharedPreferences.edit().putBoolean("restartactivity",false)
        //language
        val res = activity.resources
        val dm = res.displayMetrics
        val conf = res.configuration
        conf.setLocale(locale)
//                requireActivity().baseContext.createConfigurationContext(conf)
        res.updateConfiguration(conf, dm)

        val refresh = Intent(activity, MainActivity::class.java)
        activity.startActivity(refresh)
//        }

    }
}