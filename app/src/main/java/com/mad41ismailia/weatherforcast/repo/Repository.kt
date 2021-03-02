package com.mad41ismailia.weatherforcast.repo

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.AlertDatabase
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.DailyDatabase
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.HourlyDatabase
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.Locations
import com.mad41ismailia.weatherforcast.repo.Room.WeatherDatabase
import com.mad41ismailia.weatherforcast.repo.retrofit.UseRetrofit
import com.mad41ismailia.weatherforcast.repo.sharedPreference.SharedPreference

class Repository private constructor(application: Application) {

    private val db = Room.databaseBuilder(application, WeatherDatabase::class.java, "Weather Database").build()
    private val weatherDao = db.WeatherDao()
    private val sharedPreference = SharedPreference(application)

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
        val weatherData = UseRetrofit.retrofitInterfaceObject.getWeather(lat , lon, units, lang)
        val dailyList = ArrayList<DailyDatabase>()
        val hourlyList = ArrayList<HourlyDatabase>()
        val alertList = ArrayList<AlertDatabase>()
        for (i in weatherData.daily) {
            val m = DailyDatabase(lat,lon, i)
            dailyList.add(m)
        }

        addDaily(dailyList)
        for (i in weatherData.hourly) {
            val m = HourlyDatabase(lat,lon, i)
            hourlyList.add(m)
        }
        addHourly(hourlyList)

        if(weatherData.alerts!==null) {
            for (i in weatherData.alerts) {
                val m = AlertDatabase(lat, lon, i)
                alertList.add(m)
            }
            addAlert(alertList)
        }
    }

    fun getDaily(): LiveData<List<DailyDatabase>> {
        return weatherDao.getDaily()
    }

    private fun addAlert(list:List<AlertDatabase>){
        weatherDao.deleteAlert()
        weatherDao.addAlert(list)
    }

    private fun addDaily(list:List<DailyDatabase>){
        weatherDao.deleteDaily()
        weatherDao.addDaily(list)
    }

    private fun addHourly(list:List<HourlyDatabase>){
        weatherDao.deleteHourly()
        weatherDao.addHourly(list)
    }
    //location view model
    suspend fun addCityDB(location:Locations){
        weatherDao.addCityDB(location)
    }
    //location view model && main view model
    fun getCurrentLocation(id:Int): Locations {
        return weatherDao.getCurrentLocation(id)
    }
    //location view model
    fun getLocations(): LiveData<List<Locations>> {
        return weatherDao.getLocations()
    }


//    fun getCurrentLocationSharedPref():String?{
//        return sharedPreference.getCurrentLocation()
//    }
    //main view model
    fun setCurrentLocation(currentLocation:String){
        sharedPreference.setCurrentLocation(currentLocation)
    }

    //location frag
    fun loadCities(): ArrayList<String?> {
        return sharedPreference.loadCities()
    }

    fun getLang(): String {
        return sharedPreference.getLang()
    }
    fun getUnits(): String {
        return sharedPreference.getUnits()
    }
    fun setLang(lang:String) {
        sharedPreference.setLang(lang)
    }
    fun setUnits(units:String) {
        sharedPreference.setUnits(units)
    }

    fun saveCity(city:String) {
        sharedPreference.saveCity(city)
    }
}
