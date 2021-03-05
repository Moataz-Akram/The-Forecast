package com.mad41ismailia.weatherforcast.repo.Room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.AlertDatabase
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.DailyDatabase
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.HourlyDatabase
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.Locations
import retrofit2.http.Path

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addHourly(hourly: List<HourlyDatabase>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDaily(daily: List<DailyDatabase>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAlert(alert: List<AlertDatabase>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCityDB(daily: Locations)


    @Query("Delete from Hourly where cityName =:city")
    fun deleteHourly(city:String)
    @Query("Delete from Daily where cityName =:city")
    fun deleteDaily(city:String)
    @Query("Delete from Alert where cityName =:city")
    fun deleteAlert(city:String)

    @Query("Delete from Locations where cityAddress=:address")
    fun deleteLocation( address:String)


    @Query("SELECT * From Daily where cityName =:city")
    fun getDaily(city: String):LiveData<List<DailyDatabase>>
    @Query("SELECT * From Hourly where cityName =:city")
    fun getHourly(city: String):LiveData<List<HourlyDatabase>>
    @Query("SELECT * From Alert where cityName =:city")
    fun getAlert(city: String):LiveData<List<AlertDatabase>>


    @Query("SELECT * From Daily where cityName =:city")
    suspend fun getDaily2(city: String):List<DailyDatabase>
    @Query("SELECT * From Hourly where cityName =:city")
    suspend fun getHourly2(city: String):List<HourlyDatabase>
    @Query("SELECT * From Alert where cityName =:city")
    suspend fun getAlert2(city: String):List<AlertDatabase>


//    @Query("SELECT * From Locations")
//    fun getLocationsFromDB():LiveData<List<Locations>>
    @Query("SELECT * From Locations where id=:id")
    fun getCurrentLocation( id:Int):Locations
}