package com.mad41ismailia.weatherforcast.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


data class Alert(
        @ColumnInfo
        val description: String,
        @ColumnInfo
        val end: Int,
        @ColumnInfo
        val event: String,
        @ColumnInfo
        val sender_name: String,
        @ColumnInfo
        val start: Int
){
        @PrimaryKey(autoGenerate = true)
        lateinit var id:Integer
}