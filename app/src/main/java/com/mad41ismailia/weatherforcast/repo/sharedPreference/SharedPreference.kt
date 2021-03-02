package com.mad41ismailia.weatherforcast.repo.sharedPreference

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.JsonReader
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.mad41ismailia.weatherforcast.CITIES_LIST
import com.mad41ismailia.weatherforcast.CURRENT_LOCATION
import com.mad41ismailia.weatherforcast.PREF_NAME
import java.lang.reflect.Type

class SharedPreference(application: Application) {
    private val sharedPreferences: SharedPreferences = application.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()
    private var gson = GsonBuilder().create()
    private val typeList: Type = object : TypeToken<ArrayList<String?>?>() {}.type
    private var citiesList:ArrayList<String?>? = null

    init {
        val json = sharedPreferences.getString( CITIES_LIST, null)
        citiesList = gson.fromJson(json, typeList)
        if (citiesList === null) {
            citiesList = ArrayList()
            citiesList!!.add(null)
        }
    }

    fun loadCities(): ArrayList<String?> {
        return citiesList!!
    }

    fun saveCity(city:String) {
        citiesList?.add(city)
        Log.i("saveCity","$citiesList")
        val json = gson.toJson(citiesList)
        editor.putString(CITIES_LIST, json)
        editor.apply()
    }

    fun setCurrentLocation(currentLocation:String){
        citiesList!![0] = currentLocation
        val json = gson.toJson(citiesList)
        editor.putString(CITIES_LIST, json)
        editor.apply()
    }

    fun getLang(): String {
        return sharedPreferences.getString("lang","en")!!
    }
    fun getUnits(): String {
        return sharedPreferences.getString("units","en")!!
    }
    fun setLang(lang:String) {
        editor.putString("lang", lang)
        editor.apply()
    }
    fun setUnits(units:String) {
        editor.putString("units", units)
        editor.apply()
    }
}