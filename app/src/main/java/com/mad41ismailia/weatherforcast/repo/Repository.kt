package com.mad41ismailia.weatherforcast.repo

import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.AlarmData
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.CityWeatherData
import com.mad41ismailia.weatherforcast.entity.comingData.WeatherData
import com.mad41ismailia.weatherforcast.repo.Room.WeatherDatabase
import com.mad41ismailia.weatherforcast.repo.retrofit.UseRetrofit
import com.mad41ismailia.weatherforcast.repo.sharedPreference.SharedPreference
import com.mad41ismailia.weatherforcast.ui.widget.WeatherWidget
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList

@SuppressLint("LogNotTimber")
class Repository private constructor(private val application: Application) {
    private var db =
        Room.databaseBuilder(application, WeatherDatabase::class.java, "Weather16Database").build()
    private val weatherDao = db.WeatherDao()
    private val sharedPreference = SharedPreference(application)
    val geocoder = Geocoder(application, Locale.getDefault())
    private val weatherDataConverter: Type = object : TypeToken<WeatherData>() {}.type
    private var gson = GsonBuilder().create()

    companion object {
        private var isCreated = false
        private var INSTANCE: Repository? = null
        fun createObject(application: Application) {
            INSTANCE = Repository(application)
            isCreated = true
        }
        @JvmName("isCreated1")
        fun isCreated():Boolean{
            return isCreated
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
            runBlocking {
                list = weatherDao.getAllWeatherDataList()
//            weatherDao.deleteWeatherCityDataAll()
                val units = sharedPreference.getUnits()
                val language = sharedPreference.getLang()
                if (list != null && list!!.isNotEmpty()) {
                    for (city in list!!) {
                        addOrUpdateCity(city.cityName, city.lat, city.lon, units, language)
                    }
                }
//                weatherDao.clearDBNotInList(sharedPreference.loadAllCities())
                val intent = Intent(application,WeatherWidget::class.java)
                intent.action = "android.appwidget.action.APPWIDGET_UPDATE"
                application.sendBroadcast(intent)
            }
        }


    }


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

    fun deleteAlarm(id: String) {
        CoroutineScope(Dispatchers.Default).launch {
            weatherDao.deleteAlarm(id)
        }
    }

    fun getAlarm(id: String?): AlarmData {
        return weatherDao.getAlarm(id)
    }

    //alarm
    suspend fun getCurrentData(): WeatherData? {
        val currentLocation = sharedPreference.getCurrentLocation()
        if (currentLocation != null) {
            val list = weatherDao.getCityWeatherDataList(currentLocation)
            val json = list[0].weatherData
            Log.i("alarmalarm", "return object")
            return gson.fromJson(json, weatherDataConverter)
        }
        Log.i("alarmalarm", "return null")
        return null
    }

    fun updateAlarms(oldUnits: String, newUnits: String) {
        CoroutineScope(Dispatchers.Default).launch {
            val alarmList = weatherDao.getAlarmList()
            if (alarmList.isNotEmpty()) {
                for (alarm in alarmList) {
                    changeUnits(alarm, oldUnits, newUnits)
                }
            }
        }
    }

    private suspend fun changeUnits(alarm: AlarmData, oldUnits: String, newUnits: String) {
        if (oldUnits == "metric" && newUnits == "imperial") {
            alarm.fromCelsiusToFahrenheit()
            alarm.units = "F"
        } else if (oldUnits == "metric" && newUnits == "standard") {
            alarm.fromCelsiusToKelvin()
            alarm.units = "K"
        } else if (oldUnits == "imperial" && newUnits == "standard") {
            alarm.fromFahrenheitToKelvin()
            alarm.units = "K"
        } else if (oldUnits == "imperial" && newUnits == "metric") {
            alarm.fromFahrenheitToCelsius()
            alarm.units = "C"
        } else if (oldUnits == "standard" && newUnits == "metric") {
            alarm.fromKelvinToCelsius()
            alarm.units = "C"
        } else if (oldUnits == "standard" && newUnits == "imperial") {
            alarm.fromKelvinToFahrenheit()
            alarm.units = "F"
        }
        weatherDao.addAlarmToDB(alarm)
    }

    fun updateCurrentCity(city: String, newName: String, latitude: Double, longitude: Double) {
        CoroutineScope(Dispatchers.IO).launch {
            val units = sharedPreference.getUnits()
            val lang = sharedPreference.getLang()
            val weatherData = UseRetrofit.retrofitInterfaceObject.getWeather(latitude, longitude, units, lang)
            val data = gson.toJson(weatherData)
            Log.i("currentLocation", "old name $city new name $newName")
            weatherDao.updateCurrent(city,newName,data,latitude,longitude)
        }
    }

    fun getLastDayUpdated(): Int {
        return sharedPreference.getLastDayUpdated()
    }

    fun setLastDayUpdated(today: Int) {
        sharedPreference.setLastDayUpdated(today)
    }
}
