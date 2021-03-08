package com.mad41ismailia.weatherforcast.repo.sharedPreference

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.mad41ismailia.weatherforcast.CITIES_LIST
import com.mad41ismailia.weatherforcast.CURRENT_LOCATION
import com.mad41ismailia.weatherforcast.PREF_NAME
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
        return sharedPreferences.getString("lang","EN")!!
    }
    fun getUnits(): String {
        return sharedPreferences.getString("units","metric")!!
    }
    fun setLang(lang:String) {
        editor.putString("lang", lang)
        editor.apply()
    }
    fun setUnits(units:String) {
        editor.putString("units", units)
        editor.apply()
    }

//    fun setUpdateDate(date:Long){
//        editor.putLong(UPDATE_DATE, date)
//        editor.apply()
//    }
//
//    fun getUpdateDate():Long{
//        return sharedPreferences.getLong(UPDATE_DATE,0)
//    }
}