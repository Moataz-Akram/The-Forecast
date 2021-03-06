package com.mad41ismailia.weatherforcast.repo.Room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.*
import com.mad41ismailia.weatherforcast.entity.comingData.WeatherData
import retrofit2.http.Path

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addWeatherCityData(weatherData: CityWeatherData)

    @Query("update CityWeatherData set weatherData=:data where cityName =:city")
    fun updateCityData(city:String,data:String)

    @Query("Delete from CityWeatherData where cityName =:city")
    fun deleteWeatherCityData(city:String)

    @Query("Delete from CityWeatherData")
    fun deleteWeatherCityDataAll()

    @Query("SELECT * From CityWeatherData")
    fun getWeatherData():LiveData<List<CityWeatherData>>

    @Query("SELECT * From CityWeatherData where cityName =:city")
    suspend fun getCityWeatherData(city: String):List<CityWeatherData>
}