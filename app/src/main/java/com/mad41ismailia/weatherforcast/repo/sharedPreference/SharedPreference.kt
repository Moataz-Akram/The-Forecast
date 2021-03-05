package com.mad41ismailia.weatherforcast.repo.sharedPreference

import android.annotation.SuppressLint
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
import com.mad41ismailia.weatherforcast.UPDATE_DATE
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
            citiesList!!.add(null)
        }
        Log.i("savedPref","init $citiesList")
    }

    fun loadCities(): ArrayList<String?> {
        Log.i("savedPref"," load cities$citiesList")
//        if (citiesList!![0]==null){
//            citiesList!!.removeAt(0)
//        }
        return citiesList!!
    }

    fun saveCity(city:String) {
        citiesList?.add(city)
        Log.i("savedPref","$citiesList")
        val json = gson.toJson(citiesList)
        editor.putString(CITIES_LIST, json)
        editor.apply()
    }

    fun setCurrentLocation(currentLocation:String){
        Log.i("savedPref","set current location $citiesList")
        if(citiesList!!.isNotEmpty()){
        citiesList!![0] = currentLocation
        val json = gson.toJson(citiesList)
        editor.putString(CITIES_LIST, json)
        editor.apply()}else{citiesList!!.add(currentLocation)}
    }

    fun setCurrentLocationStandAlone(currentLocation:String){
        Log.i("savedPref","set current location $citiesList")
        val city = sharedPreferences.getString(CURRENT_LOCATION,null)
        editor.putString(CURRENT_LOCATION,currentLocation)
        editor.apply()
    }

    fun getCurrentLocationStandAlone():String?{
        return sharedPreferences.getString(CURRENT_LOCATION,null)
    }

    fun loadCitiesCurrentAlone(): ArrayList<String?> {
        val current = getCurrentLocationStandAlone()
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

    fun setUpdateDate(date:Long){
        editor.putLong(UPDATE_DATE, date)
        editor.apply()
    }

    fun getUpdateDate():Long{
        return sharedPreferences.getLong(UPDATE_DATE,0)
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