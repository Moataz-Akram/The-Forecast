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
    @Insert(onConflict = OnConflictStrategy.REPLACE)//needed
    fun addWeatherCityData(weatherData: CityWeatherData)

    @Query("update CityWeatherData set weatherData=:data where cityName =:city")//needed
    fun updateCityData(city:String,data:String)

    @Query("Delete from CityWeatherData where cityName =:city")//needed
    fun deleteWeatherCityData(city:String)

    @Query("Delete from CityWeatherData where cityName not in (:cityList)")//fixed a bug
    fun clearDBNotInList(cityList:List<String?>)

    @Query("Delete from CityWeatherData")//not need yet, will be needed in refactor
    fun deleteWeatherCityDataAll()

    @Query("SELECT * From CityWeatherData")//needed
    fun getWeatherLiveData():LiveData<List<CityWeatherData>>

    @Query("SELECT * From CityWeatherData where cityName =:city")//needed in add or update
    suspend fun getCityWeatherDataList(city: String):List<CityWeatherData>

    @Query("SELECT * From CityWeatherData")//needed in add or update
    suspend fun getAllWeatherDataList():List<CityWeatherData>
}