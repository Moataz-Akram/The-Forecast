package com.mad41ismailia.weatherforcast.repo.sharedPreference

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.mad41ismailia.weatherforcast.*
import java.lang.reflect.Type

@SuppressLint("LogNotTimber")
class SharedPreference(application: Application) {
    private val sharedPreferences: SharedPreferences = application.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()
    private var gson = GsonBuilder().create()
    private val typeList: Type = object : TypeToken<ArrayList<String?>?>() {}.type
    private var citiesList:ArrayList<String?>? = null

    init {
        val json = sharedPreferences.getString( CITIES_LIST, null)
        citiesList = gson.fromJson(json, typeList)
        if (citiesList == null) {
            citiesList = ArrayList()
        }
    }

    fun saveNewCity(city:String) {
        citiesList?.add(city)
        Log.i("savedPref","$citiesList")
        val json = gson.toJson(citiesList)
        editor.putString(CITIES_LIST, json)
        editor.apply()
    }

    fun deleteCity(city: String) {
        Log.i("deletecity","inside shared pref $city from $citiesList")
        citiesList!!.remove(city)
        val json = gson.toJson(citiesList)
        editor.putString(CITIES_LIST, json)
        editor.commit()
    }

    fun setCurrentLocation(currentLocation:String){
        Log.i("savedPref","set current location $citiesList")
        val city = sharedPreferences.getString(CURRENT_LOCATION,null)
        editor.putString(CURRENT_LOCATION,currentLocation)
        editor.apply()
    }

    fun getCurrentLocation():String?{
        return sharedPreferences.getString(CURRENT_LOCATION,null)
    }

    fun loadAllCities(): ArrayList<String?> {
        val current = getCurrentLocation()
        val list = ArrayList<String?>()
        if(current!=null){
            list.add(current!!)
            for(city in citiesList!!){
                list.add(city)
            }
        }else{
            return citiesList!!
        }
        return list
    }

    fun getLang(): String {
        return sharedPreferences.getString(LANGUAGE,"EN")!!
    }
    fun setLang(lang:String) {
        editor.putString(LANGUAGE, lang)
        editor.apply()
    }
    fun getUnits(): String {
        return sharedPreferences.getString(UNITS,"metric")!!
    }
    fun setUnits(units:String) {
        editor.putString(UNITS, units)
        editor.apply()
    }

    fun getLastDayUpdated(): Int {
        return sharedPreferences.getInt(UPDATE_DATE,0)
    }

    fun setLastDayUpdated(today: Int) {
        editor.putInt(UPDATE_DATE, today)
        editor.apply()
    }

    fun setNeedUpdate(flag:Boolean) {
        editor.putBoolean(NEED_UPDATE,flag)
        editor.apply()
    }

    fun getNeedUpdate(): Boolean {
        return sharedPreferences.getBoolean(NEED_UPDATE,false)
    }

    fun saveAppWidgetId(appWidgetId: Int) {
        editor.putInt(APP_WIDGET_ID,appWidgetId)
        editor.apply()
    }
    fun getAppWidgetId():Int{
        return sharedPreferences.getInt(APP_WIDGET_ID,0)
    }
}