package com.mad41ismailia.weatherforcast.repo.Room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mad41ismailia.weatherforcast.entity.AlertDatabase
import com.mad41ismailia.weatherforcast.entity.DailyDatabase
import com.mad41ismailia.weatherforcast.entity.HourlyDatabase

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addHourly(hourly: List<HourlyDatabase>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDaily(daily: List<DailyDatabase>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDaily1(daily: DailyDatabase)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAlert(alert: List<AlertDatabase>)

    @Query("Delete from Hourly")
    suspend fun deleteHourly()
    @Query("Delete from Daily")
    suspend fun deleteDaily()
    @Query("Delete from `Alert Table`")
    suspend fun deleteAlert()

}