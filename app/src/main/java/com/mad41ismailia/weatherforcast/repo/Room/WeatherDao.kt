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
    suspend fun addHourly(hourly: List<HourlyDatabase>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDaily(daily: List<DailyDatabase>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDaily1(daily: DailyDatabase)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAlert(alert: List<AlertDatabase>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addLocation(daily: Locations)


    @Query("Delete from Hourly")
    fun deleteHourly()
    @Query("Delete from Daily")
    fun deleteDaily()
    @Query("Delete from Alert")
    fun deleteAlert()
    @Query("Delete from Locations where cityAddress=:address")
    fun deleteLocation( address:String)


    @Query("SELECT * From Daily")
    fun getDaily():LiveData<List<DailyDatabase>>

    @Query("SELECT * From Locations")
    fun getLocations():LiveData<List<Locations>>

    @Query("SELECT * From Locations where id=:id")
    fun getCurrentLocation( id:Int):Locations





}