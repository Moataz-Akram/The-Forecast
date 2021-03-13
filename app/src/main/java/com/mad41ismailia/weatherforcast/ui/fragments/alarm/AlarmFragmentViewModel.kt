package com.mad41ismailia.weatherforcast.ui.fragments.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mad41ismailia.weatherforcast.broadcast.MyReceiver
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.AlarmData
import com.mad41ismailia.weatherforcast.repo.Repository

class AlarmFragmentViewModel:ViewModel() {
    val repo = Repository.getRepoObject()

    fun getAlarms(): LiveData<List<AlarmData>> {
        return repo.getAlarms()
    }

    fun deleteAlarm(id:String,context: Context){
        repo.deleteAlarm(id)
        val intent = Intent(context.applicationContext, MyReceiver::class.java)
        intent.putExtra("ID", id)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
        val alarmManager = context.applicationContext.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }

    fun getUnits(): String {
        return repo.getUnits()
    }
}