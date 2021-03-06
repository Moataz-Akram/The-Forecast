package com.mad41ismailia.weatherforcast.repo

import android.annotation.SuppressLint
import android.app.Application
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.AlertDatabase
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.CityWeatherData
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.DailyDatabase
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.HourlyDatabase
import com.mad41ismailia.weatherforcast.entity.comingData.WeatherData
import com.mad41ismailia.weatherforcast.repo.Room.WeatherDatabase
import com.mad41ismailia.weatherforcast.repo.retrofit.UseRetrofit
import com.mad41ismailia.weatherforcast.repo.sharedPreference.SharedPreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList

class Repository private constructor(val application: Application) {

    private val db = Room.databaseBuilder(application, WeatherDatabase::class.java, "Weather4Database").build()
    private val weatherDao = db.WeatherDao()
    private val sharedPreference = SharedPreference(application)
    val geocoder = Geocoder(application, Locale.getDefault())
    private val weatherDataConverter: Type = object : TypeToken<WeatherData>() {}.type
    private var gson = GsonBuilder().create()

    companion object{
        private var INSTANCE:Repository? = null
        fun createObject(application: Application){
            INSTANCE = Repository(application)
        }

        fun getRepoObject(): Repository {
            return INSTANCE!!
        }
    }

    @SuppressLint("LogNotTimber")
    suspend fun getWeatherData(city: String, lat:Double, lon:Double, units:String, lang:String){
        val weatherData = UseRetrofit.retrofitInterfaceObject.getWeather(lat , lon, units, lang)
        val weatherDataString = gson.toJson(weatherData)
        Log.i("changingDatabase",weatherDataString)
        weatherDao.deleteWeatherCityData(city)
        weatherDao.addWeatherCityData(CityWeatherData(city,weatherDataString))
        val dailyList = ArrayList<DailyDatabase>()
        val hourlyList = ArrayList<HourlyDatabase>()
        val alertList = ArrayList<AlertDatabase>()

        for (i in weatherData.daily) {
            val m = DailyDatabase(city, lat,lon, i)
            dailyList.add(m)
        }
        addDaily(city, dailyList)

        for (i in weatherData.hourly) {
            val m = HourlyDatabase(city, lat,lon, i)
            hourlyList.add(m)
        }
        addHourly(city, hourlyList)

        if(weatherData.alerts!==null) {
            for (i in weatherData.alerts) {
                val m = AlertDatabase(city, lat, lon, i)
                alertList.add(m)
            }
            addAlert(city, alertList)
        }
    }



    //today
    @SuppressLint("LogNotTimber")
    fun fetchAllCitiesData(list:ArrayList<String?>): LiveData<List<CityWeatherData>> {
        val lang = sharedPreference.getLang()
        val units = sharedPreference.getUnits()
        Log.i("settingsNow","lang '$lang' units '$units'")
        CoroutineScope(Dispatchers.IO).launch {
        for (city in list){
            val latLong = geocoder.getFromLocationName(city, 1)
            getWeatherData(city!!,latLong[0].latitude,latLong[0].longitude,units,lang)
        }
        }
        return weatherDao.getWeatherData()
    }
    //today
    @SuppressLint("LogNotTimber")
    suspend fun fetchAllCitiesData2(list:ArrayList<String?>) {
        val lang = sharedPreference.getLang()
        val units = sharedPreference.getUnits()
        Log.i("settingsNow","lang '$lang' units '$units'")
        for (city in list){
            val latLong = geocoder.getFromLocationName(city, 1)
            getWeatherData2(city!!,latLong[0].latitude,latLong[0].longitude,units,lang)
        }
    }
    @SuppressLint("LogNotTimber")
    private suspend fun getWeatherData2(city: String, latitude: Double, longitude: Double, units: String, lang: String) {
        val weatherData = UseRetrofit.retrofitInterfaceObject.getWeather(latitude , longitude, units, lang)
        val weatherDataString = gson.toJson(weatherData)
//        weatherDao.deleteWeatherCityData(city)
        val existCity = weatherDao.getCityWeatherData(city)
        if(existCity.isNotEmpty()) {
            Log.i("newdatabase","inside update ${existCity.size}")
            weatherDao.updateCityData(city, weatherDataString)
        }else{
            Log.i("newdatabase","inside insert ${existCity.size}")
            weatherDao.addWeatherCityData(CityWeatherData(city, weatherDataString))

        }
    }


    fun getCityAllLiveData():ArrayList<LiveData<List<DailyDatabase>>>{
        val list = sharedPreference.loadCitiesCurrentAlone()
        val listDaily:ArrayList<LiveData<List<DailyDatabase>>> = ArrayList()
        for (city in list){
            listDaily.add(getDaily(city!!))
        }
        return listDaily
    }
    //today
    fun getDaily(city: String): LiveData<List<DailyDatabase>> {
        return weatherDao.getDaily(city)
    }
    //today
    suspend fun getDaily2(city: String): List<DailyDatabase> {
        return weatherDao.getDaily2(city)
    }
    //today
    suspend fun getHourly2(city: String): List<HourlyDatabase> {
        return weatherDao.getHourly2(city)
    }

    fun getHourly(city: String): LiveData<List<HourlyDatabase>> {
        return weatherDao.getHourly(city)
    }

    fun getAlert(city: String): LiveData<List<AlertDatabase>> {
        return weatherDao.getAlert(city)
    }

    private fun addAlert(city: String, list:List<AlertDatabase>){
        weatherDao.deleteAlert(city)
        weatherDao.addAlert(list)
    }

    private fun addDaily(city: String, list:List<DailyDatabase>){
        weatherDao.deleteDaily(city)
        weatherDao.addDaily(list)
    }

    private fun addHourly(city: String, list:List<HourlyDatabase>){
        weatherDao.deleteHourly(city)
        weatherDao.addHourly(list)
    }

    //main view model
    fun setCurrentLocation(currentLocation:String){
        sharedPreference.setCurrentLocation(currentLocation)
    }
    fun setCurrentLocationStandAlone(currentLocation:String){
        sharedPreference.setCurrentLocationStandAlone(currentLocation)
    }
    fun getCurrentLocationStandAlone():String?{
        return sharedPreference.getCurrentLocationStandAlone()
    }

    fun loadCitiesNew(): ArrayList<String?> {
        return sharedPreference.loadCitiesCurrentAlone()
    }
    //location settings
    fun getLang(): String {
        return sharedPreference.getLang()
    }
    //location settings
    fun getUnits(): String {
        return sharedPreference.getUnits()
    }
    //settings
    fun setLang(lang:String) {
        sharedPreference.setLang(lang)
    }
    fun setUnits(units:String) {
        sharedPreference.setUnits(units)
    }
    //location
    fun saveCity(city:String) {
        sharedPreference.saveCity(city)
    }
    //location
    fun deleteCity(city: String) {
        CoroutineScope(Dispatchers.Default).launch {
            weatherDao.deleteDaily(city)
            weatherDao.deleteHourly(city)
            weatherDao.deleteAlert(city)
            weatherDao.deleteWeatherCityData(city)
        }
        sharedPreference.deleteCity(city)
    }

}
