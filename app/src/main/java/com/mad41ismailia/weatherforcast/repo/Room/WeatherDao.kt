package com.mad41ismailia.weatherforcast.repo.Room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.AlertDatabase
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.DailyDatabase
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.HourlyDatabase

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

    @Query("Delete from Hourly")
    suspend fun deleteHourly()
    @Query("Delete from Daily")
    suspend fun deleteDaily()
    @Query("Delete from `Alert Table`")
    suspend fun deleteAlert()

    @Query("SELECT * From Daily")
    fun getDaily():LiveData<List<DailyDatabase>>

}