package com.mad41ismailia.weatherforcast.ui.alarm

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.mad41ismailia.weatherforcast.R
import com.mad41ismailia.weatherforcast.broadcast.MyReceiver
import com.mad41ismailia.weatherforcast.databinding.ActivityAlarmBinding
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.AlarmData
import java.util.*

@SuppressLint("LogNotTimber")
class AlarmActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener,
    TimePicker.OnTimeChangedListener {
    private lateinit var binding: ActivityAlarmBinding
    private lateinit var condition: String
    private lateinit var viewModel: AlarmViewModel
    var calendar: Calendar = Calendar.getInstance()
    var hrs = 0
    var minutes = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_alarm)
        viewModel = ViewModelProvider(this).get(AlarmViewModel::class.java)

        createNotificationChannel()

        //spinner
        val spinnerList = arrayOf(
            resources.getString(R.string.temp_more),
            resources.getString(R.string.temp_less)
        )
        val spinnerListAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            spinnerList
        )
        binding.conditionSpinner.adapter = spinnerListAdapter
        binding.conditionSpinner.onItemSelectedListener = this
        //time picker
        binding.alarmTimePicker.setOnTimeChangedListener(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.alarmTimePicker.hour = 0
            binding.alarmTimePicker.minute = 0
        } else {
            binding.alarmTimePicker.currentHour = 0
            binding.alarmTimePicker.currentMinute = 0
        }


        //add button
        binding.btnAddAlarm.setOnClickListener {

            if (binding.valueFireAlarm.text.isEmpty()) {
                Toast.makeText(this, "add temperature value", Toast.LENGTH_LONG).show()
            } else {
                val timeInstance = Calendar.getInstance()
                timeInstance.set(Calendar.HOUR, hrs)
                timeInstance.set(Calendar.MINUTE, minutes)
                val time = DateFormat.format("hh:mm:aa", timeInstance).toString()

                val type = binding.txtCondition.text
                val alarmValue = Integer.parseInt(binding.valueFireAlarm.text.toString())
                val units = getUnits(viewModel.getUnits())
                val alarmTime = calculateAlarmTime()


                Log.i("alarm", "inside else ${binding.valueFireAlarm.text.length} ")
                val newAlarm = AlarmData("alarm", alarmValue, condition, time, units)
                viewModel.addAlarmToDB(newAlarm)

                Toast.makeText(this, "time is $time", Toast.LENGTH_LONG).show()
                Log.i("alarmalarm", "time returned $alarmTime ")
                registerAlarm(alarmTime, newAlarm.uniqueID)
                finish()
            }
        }
    }

    private fun registerAlarm(time: Long, id: String) {
        val intent = Intent(this, MyReceiver::class.java)
        intent.putExtra("ID", id)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + time, pendingIntent)
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() + time, 24*60*60*1000,pendingIntent)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val description = "Channel"
            val notificationChannel = NotificationChannel(
                "ALARM_CHANNEL",
                "Alarm_Notification",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.description = description
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun calculateAlarmTime(): Long {
        val calendar2 = Calendar.getInstance()
//        calendar2.timeInMillis = SystemClock.elapsedRealtime()
        val m1 = calendar2.timeInMillis
        Log.i("alarmalarm", "m1 $m1 ")
        Log.i("alarmalarm", "m1 ${calendar2.time} ")
        calendar2.set(Calendar.HOUR, hrs)
        calendar2.set(Calendar.MINUTE, minutes)
        var m2 = calendar2.timeInMillis
        Log.i("alarmalarm", "m2 $m2 ")
        Log.i("alarmalarm", "m2 ${calendar2.time} ")
        var m3 = m2 - m1
        return if (m3 > 0) {
            Log.i("alarmalarm", "inside if m3 $m3 ")
            Log.i("alarmalarm", "inside if m3 ${calendar2.time} ")
            m3
        } else {
            calendar2.set(Calendar.HOUR, hrs + 24)
            m2 = calendar2.timeInMillis
            Log.i("alarmalarm", "inside else m2 $m2 ")
            Log.i("alarmalarm", "inside else m2 ${calendar2.time} ")
            m3 = m2 - m1
            Log.i("alarmalarm", "inside else m3 $m3 ")
            Log.i("alarmalarm", "inside else m3 ${calendar2.time} ")
            m3
        }
    }

    private fun getUnits(units: String): String {
        return when (units) {
            "metric" -> "C"
            "standard" -> "K"
            "imperial" -> "F"
            else -> "C"
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        condition = parent?.getItemAtPosition(position).toString()
        if (parent?.getItemAtPosition(position).toString().equals("Temperature more than")) {
            condition = resources.getString(R.string.temp_more)
        } else {
            condition = resources.getString(R.string.temp_less)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onTimeChanged(view: TimePicker?, hourOfDay: Int, minute: Int) {
        hrs = hourOfDay-12
        minutes = minute
    }
}