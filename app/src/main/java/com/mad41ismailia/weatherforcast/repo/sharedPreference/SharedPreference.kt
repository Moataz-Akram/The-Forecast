package com.mad41ismailia.weatherforcast.repo.sharedPreference

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mad41ismailia.weatherforcast.CITIES_LIST
import com.mad41ismailia.weatherforcast.CURRENT_LOCATION
import com.mad41ismailia.weatherforcast.PREF_NAME
import java.lang.reflect.Type

class SharedPreference(application: Application) {
//    private var citiesMutableList: MutableLiveData<List<String?>> = null
    private var citiesList:ArrayList<String?>? = null
    var current:String? = null
    val map:MutableMap<String, Pair<Double,Double>> = mutableMapOf()

    private val sharedPreferences: SharedPreferences = application.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()
    val gson = Gson()
    val typeList: Type = object : TypeToken<ArrayList<String>?>() {}.type
    val typeString: Type = object : TypeToken<String>() {}.type


    fun loadCities(): ArrayList<String?> {

        val json = sharedPreferences.getString( CITIES_LIST, null)
        citiesList = gson.fromJson(json, typeList)
        if (citiesList === null) {
            citiesList = ArrayList()
            citiesList!!.add(getCurrentLocation())
        }
//        citiesMutableList.postValue(citiesList)
        return citiesList!!
    }

    fun saveCity(city:String) {
        citiesList?.add(city)
        val json = gson.toJson(citiesList)
        editor.putString(CITIES_LIST, json)
        editor.apply()
    }

    fun getCurrentLocation(): String? {
        map.put("sd",(2.2 to 2.2))
        Log.i("comingdata",map.toString())
        val json = sharedPreferences.getString( CURRENT_LOCATION, null)
        current = gson.fromJson(json, typeString)
        return current
    }

    fun setCurrentLocation(currentLocation:String){
        editor.putString(CURRENT_LOCATION, currentLocation)
        editor.apply()
    }

}