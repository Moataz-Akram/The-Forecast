package com.mad41ismailia.weatherforcast.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Alert Table")
class AlertDatabase(
){
    @PrimaryKey(autoGenerate = true)
    var id:Int?=null
    @ColumnInfo
    var lat: Double = 0.0
    @ColumnInfo
    var long: Double = 0.0
    @ColumnInfo
    var description: String = ""
    @ColumnInfo
    var end: Int = 0
    @ColumnInfo
    var event: String = ""
    @ColumnInfo
    var sender_name: String = ""
    @ColumnInfo
    var start: Int = 0
    constructor(lat:Double,long:Double,description:String,end:Int,event:String,sender_name:String,start:Int) : this(){
        this.lat = lat
        this.long = long
        this.description = description
        this.end = end
        this.sender_name = sender_name
        this.start = start

    }
//    constructor(lat: Double,long: Double,alert: Alert):this(lat,long,alert.description,alert.end,alert.event,alert.sender_name,alert.start)
}

//@Entity(tableName = "Alert Table")
//data class AlertDatabase(
//        @PrimaryKey(autoGenerate = true)
//        val id:Int?=null,
//        @ColumnInfo
//        val lat: Double,
//        @ColumnInfo
//        val long: Double,
//        @ColumnInfo
//        val description: String,
//        @ColumnInfo
//        val end: Int,
//        @ColumnInfo
//        val event: String,
//        @ColumnInfo
//        val sender_name: String,
//        @ColumnInfo
//        val start: Int
//){
////    constructor(lat: Double,long: Double,alert: Alert):this(lat,long,alert.description,alert.end,alert.event,alert.sender_name,alert.start)
//}