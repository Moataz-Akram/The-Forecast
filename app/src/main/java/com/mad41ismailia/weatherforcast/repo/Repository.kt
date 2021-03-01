package com.mad41ismailia.weatherforcast.repo

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.mad41ismailia.weatherforcast.CITIES_LIST
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.AlertDatabase
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.DailyDatabase
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.HourlyDatabase
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.Locations
import com.mad41ismailia.weatherforcast.repo.Room.WeatherDatabase
import com.mad41ismailia.weatherforcast.repo.retrofit.UseRetrofit
import com.mad41ismailia.weatherforcast.repo.sharedPreference.SharedPreference
import kotlinx.coroutines.delay

class Repository private constructor(application: Application) {

    private val db = Room.databaseBuilder(application, WeatherDatabase::class.java, "Weather Database").build()
    private val weatherDao = db.WeatherDao()
    private val sharedPreference = SharedPreference(application)
//    private val data = UseRetrofit()

    companion object{
        private var INSTANCE:Repository? = null
        fun createObject(application: Application){
            INSTANCE = Repository(application)
        }

        fun getRepoObject(): Repository {
            return INSTANCE!!
        }
    }

    suspend fun getWeatherData(lat:Double,lon:Double,units:String, lang:String){
        Log.i("comingdata ","new request")
        val weatherData = UseRetrofit.retrofitInterfaceObject.getWeather2(lat , lon, units, lang)
        val dailyList = ArrayList<DailyDatabase>()
        val hourlyList = ArrayList<HourlyDatabase>()
        val alertList = ArrayList<AlertDatabase>()
        val comingDailyList = weatherData.daily
        val list4 = weatherData.hourly
        val list6 = weatherData.alerts
        for (i in comingDailyList) {
            val m = DailyDatabase(lat,lon, i)
            dailyList.add(m)
        }
        addDaily(dailyList)
        for (i in list4) {
            val m = HourlyDatabase(lat,lon, i)
            hourlyList.add(m)
        }
        addHourly(hourlyList)
        if(list6!==null) {
            for (i in list6) {
                val m = AlertDatabase(lat, lon, i)
                alertList.add(m)
            }
            addAlert(alertList)
        }
    }

    fun getDaily(): LiveData<List<DailyDatabase>> {
        return weatherDao.getDaily()
    }

    suspend fun addAlert(list:List<AlertDatabase>){
        weatherDao.deleteAlert()
        weatherDao.addAlert(list)
    }

    suspend fun addDaily(list:List<DailyDatabase>){
        weatherDao.deleteDaily()
        weatherDao.addDaily(list)
    }

    suspend fun addHourly(list:List<HourlyDatabase>){
        weatherDao.deleteHourly()
        weatherDao.addHourly(list)
    }

    fun getCurrentLocationSharedPref():String?{
        return sharedPreference.getCurrentLocation()
    }

    fun setCurrentLocation(currentLocation:String){
        sharedPreference.setCurrentLocation(currentLocation)
    }

    fun loadCities(): ArrayList<String?> {
        return sharedPreference.loadCities()
    }

    fun saveCity(city:String) {
        sharedPreference.saveCity(city)
    }

    suspend fun addLocation(location:Locations){
        weatherDao.addLocation(location)
    }

    suspend fun getCurrentLocation(id:Int): Locations {
        return weatherDao.getCurrentLocation(id)
    }

    suspend fun getLocations(): LiveData<List<Locations>> {
        return weatherDao.getLocations()
    }



}
