package com.mad41ismailia.weatherforcast.ui.mainActivity

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mad41ismailia.weatherforcast.CITIES_LIST
import com.mad41ismailia.weatherforcast.PREF_NAME
import java.lang.reflect.Type


class MainActivityViewModel(application: Application) : AndroidViewModel(application){
    var citiesList:ArrayList<String>? = null
    private val sharedPreferences: SharedPreferences = application.getSharedPreferences(PREF_NAME, MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()
    val gson = Gson()
    val type: Type = object : TypeToken<ArrayList<String?>?>() {}.type


    fun loadCities() {
        val json = sharedPreferences.getString( CITIES_LIST, null)
        citiesList = gson.fromJson(json, type)
        if (citiesList == null) {
            citiesList = ArrayList()
        }
    }

    fun saveCity(city:String) {
        citiesList?.add(city)
        val editor = sharedPreferences.edit()
        val json = gson.toJson(citiesList)
        editor.putString("task list", json)
        editor.apply()
    }

    companion object{

    }
}