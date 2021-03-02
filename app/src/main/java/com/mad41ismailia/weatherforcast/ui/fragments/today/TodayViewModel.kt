package com.mad41ismailia.weatherforcast.ui.fragments.today

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.DailyDatabase
import com.mad41ismailia.weatherforcast.repo.Repository
import com.mad41ismailia.weatherforcast.ui.mainActivity.MainActivity
import java.util.*

class TodayViewModel : ViewModel(){
    val repo = Repository.getRepoObject()
    var flag = false//remove
    //language
    lateinit var locale: Locale


    suspend fun fetchData(lat: Double, lon: Double, units: String, lang: String){
        if(!checkCall()){
            flag = true
            repo.getWeatherData(lat, lon, units, lang)
        }
    }
    fun getDaily(): LiveData<List<DailyDatabase>> {
        return repo.getDaily()
    }
    fun getLang(): String {
        return repo.getLang()
    }
    fun getUnits(): String {
        return repo.getUnits()
    }

    fun loadCities(): ArrayList<String?> {
        return repo.loadCities()
    }


    //to be done, check if i need to send a new api or not -> change current location, add new place, after 12AM
    private fun checkCall(): Boolean {
        return flag
    }
    suspend fun checkCities():Boolean{
//        val current = repo.getCurrentLocation(1)
//        val myCity = current.cityAddress
//        //shared pref list not database list
//        val cityList = repo.loadCities()
//
//        val cityListDb = repo.getLocations()
////        Log.i("comingdata","today check ${cityListDb.size }")
//        Log.i("comingdata","today check ${current }")
//        if(myCity!==null){
//            return true
//        }
        return true
    }

    //change app language
    //to be removed to sharedPref
    fun checkLanguage(activity: Activity) {
        val sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE)
        val lang = sharedPreferences.getString("lang", "en")
        val currentLang = Locale.getDefault().language
        Log.i("comingdata", "SP lang $lang")
        Log.i("comingdata", "device lang$currentLang")
        Thread.sleep(1000)
        locale = Locale(lang)
        if(sharedPreferences.getBoolean("restartactivity",false)){
            sharedPreferences.edit().putBoolean("restartactivity",false)
        //language
        val res = activity.resources
        val dm = res.displayMetrics
        val conf = res.configuration
        conf.setLocale(locale)
//                requireActivity().baseContext.createConfigurationContext(conf)
        res.updateConfiguration(conf, dm)
        val refresh = Intent(activity, MainActivity::class.java)
        activity.startActivity(refresh)
        }
    }
}