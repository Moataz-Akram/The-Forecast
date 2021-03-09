package com.mad41ismailia.weatherforcast.repo

import android.annotation.SuppressLint
import android.app.Application
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.mad41ismailia.weatherforcast.INTERNECT_CONNECTION
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.AlarmData
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.CityWeatherData
import com.mad41ismailia.weatherforcast.entity.comingData.WeatherData
import com.mad41ismailia.weatherforcast.repo.Room.WeatherDatabase
import com.mad41ismailia.weatherforcast.repo.retrofit.UseRetrofit
import com.mad41ismailia.weatherforcast.repo.sharedPreference.SharedPreference
import com.mad41ismailia.weatherforcast.ui.fragments.alarm.Alarm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList

@SuppressLint("LogNotTimber")
class Repository private constructor(private val application: Application) {
    private val db =
        Room.databaseBuilder(application, WeatherDatabase::class.java, "Weather13Database").build()
    private val weatherDao = db.WeatherDao()
    private val sharedPreference = SharedPreference(application)
    //    val geocoder = Geocoder(application, Locale.getDefault())
//    private val weatherDataConverter: Type = object : TypeToken<WeatherData>() {}.type
    private var gson = GsonBuilder().create()

    companion object {
        private var INSTANCE: Repository? = null
        fun createObject(application: Application) {
            INSTANCE = Repository(application)
        }

        fun getRepoObject(): Repository {
            return INSTANCE!!
        }
    }

    fun addDataForNewCity(city: String, latitude: Double, longitude: Double) {
        val lang = sharedPreference.getLang()
        val units = sharedPreference.getUnits()
        addOrUpdateCity(city!!, latitude, longitude, units, lang)
    }


    fun updateAllCities() {
        var list: List<CityWeatherData>? = null
        CoroutineScope(Dispatchers.Default).launch {
            list = weatherDao.getAllWeatherDataList()
//            weatherDao.deleteWeatherCityDataAll()
            val units = sharedPreference.getUnits()
            val language = sharedPreference.getLang()
            if (list != null && list!!.isNotEmpty()) {
                for (city in list!!) {
                    addOrUpdateCity(city.cityName, city.lat, city.lon, units, language)
                }
            }
            weatherDao.clearDBNotInList(sharedPreference.loadAllCities())
        }
    }


//    //today
//    fun fetchAllCitiesData(list:ArrayList<String?>): LiveData<List<CityWeatherData>> {
//        if(INTERNECT_CONNECTION) {
//            val lang = sharedPreference.getLang()
//            val units = sharedPreference.getUnits()
//            Log.i("settingsNow", "lang '$lang' units '$units'")
//                for (city in list) {
//                    val latLong = geocoder.getFromLocationName(city, 1)
//                    addOrUpdateCity(city!!, latLong[0].latitude, latLong[0].longitude, units, lang)
//                }
//        }
//        return weatherDao.getWeatherLiveData()
//    }

    private fun addOrUpdateCity(
        city: String,
        latitude: Double,
        longitude: Double,
        units: String,
        lang: String
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val weatherData =
                UseRetrofit.retrofitInterfaceObject.getWeather(latitude, longitude, units, lang)
            val weatherDataString = gson.toJson(weatherData)
            val existCity = weatherDao.getCityWeatherDataList(city)
            if (existCity.isNotEmpty()) {
                weatherDao.updateCityData(city, weatherDataString)
            } else {
                weatherDao.addWeatherCityData(
                    CityWeatherData(
                        city,
                        latitude,
                        longitude,
                        weatherDataString
                    )
                )
            }
        }
    }

    fun setCurrentLocation(currentLocation: String) {
        sharedPreference.setCurrentLocation(currentLocation)
    }

    fun getCurrentLocation(): String? {
        return sharedPreference.getCurrentLocation()
    }

    fun loadAllCities(): ArrayList<String?> {
        return sharedPreference.loadAllCities()
    }

    //settings
    fun getLang(): String {
        return sharedPreference.getLang()
    }

    //settings
    fun getUnits(): String {
        return sharedPreference.getUnits()
    }

    //settings
    fun setLang(lang: String) {
        sharedPreference.setLang(lang)
    }

    fun setUnits(units: String) {
        sharedPreference.setUnits(units)
    }

    //location
    fun saveNewCity(city: String) {
        sharedPreference.saveNewCity(city)
    }

    //location
    fun deleteCity(city: String) {
        CoroutineScope(Dispatchers.Default).launch {
            val i = weatherDao.deleteWeatherCityData(city)
            Log.i("deletecity", "inside coroutine repo $i")
        }
        sharedPreference.deleteCity(city)
    }

    //today
    fun clearDBNotInList() {
        val list = sharedPreference.loadAllCities()
        Log.i("fixingBugs", "$list")
        CoroutineScope(Dispatchers.Default).launch {
            weatherDao.clearDBNotInList(list)
        }
    }

    fun observeWeatherData(): LiveData<List<CityWeatherData>> {
        return weatherDao.getWeatherLiveData()
    }

    fun addAlarmToDB(newAlarm: AlarmData) {
        CoroutineScope(Dispatchers.Default).launch {
            weatherDao.addAlarmToDB(newAlarm)
        }
    }

    fun getAlarms(): LiveData<List<AlarmData>> {
        return weatherDao.getAlarms()
    }

    fun deleteAlarm(id:String){
        CoroutineScope(Dispatchers.Default).launch {
            weatherDao.deleteAlarm(id)
        }
    }

    fun getAlarm(id: String?): AlarmData {
        return weatherDao.getAlarm(id)
    }
}
