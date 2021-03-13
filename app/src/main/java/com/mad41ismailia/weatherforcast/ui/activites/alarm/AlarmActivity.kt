package com.mad41ismailia.weatherforcast.ui.activites.alarm

import android.annotation.SuppressLint
import android.app.AlarmManager
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
import com.mad41ismailia.weatherforcast.ALARM_ID
import com.mad41ismailia.weatherforcast.R
import com.mad41ismailia.weatherforcast.broadcast.MyReceiver
import com.mad41ismailia.weatherforcast.databinding.ActivityAlarmBinding
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.AlarmData
import com.mad41ismailia.weatherforcast.ui.fragments.today.adapters.isDarkModeOn
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

        //DarkMode
        if(isDarkModeOn(this)){
            binding.alarmLayout.setBackgroundResource(R.drawable.darkmode_backgroud)
        }

        viewModel.createNotificationChannel(this)

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

//                Toast.makeText(this, "time is $time", Toast.LENGTH_LONG).show()
                Log.i("alarmalarm", "time returned $alarmTime ")
                Log.i("alarmalarm", "alarm id ${newAlarm.uniqueID} ")
                registerAlarm(alarmTime, newAlarm.uniqueID)
                finish()
            }
        }
    }

    private fun registerAlarm(time: Long, id: String) {
        val intent = Intent(applicationContext, MyReceiver::class.java)
        intent.putExtra(ALARM_ID, id)
        val pendingIntent = PendingIntent.getBroadcast(applicationContext, 0, intent, 0)
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + time, pendingIntent)
    }


    private fun calculateAlarmTime(): Long {
        val calendar2 = Calendar.getInstance()
        val m1 = calendar2.timeInMillis
        Log.i("alarmalarm", "m1 $m1 ")
        Log.i("alarmalarm", "m1 ${calendar2.time} ")
        calendar2.set(Calendar.HOUR, hrs)
        calendar2.set(Calendar.MINUTE, minutes)
        calendar2.set(Calendar.SECOND, 0)
        var m2 = calendar2.timeInMillis
        Log.i("alarmalarm", "m2 $m2 ")
        Log.i("alarmalarm", "m2 ${calendar2.time} ")
        var m3 = m2 - m1
        return if (m3 > 0) {
            Log.i("alarmalarm", "inside if m3 $m3 ")
            Log.i("alarmalarm", "inside if m3 ${calendar2.time} ")
            m3
        } else {
            m3 += 24 * 60 * 60 * 1000
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
        val HOD = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        Log.i("alarmAlarm","hour of day ${Calendar.getInstance().get(Calendar.HOUR_OF_DAY)}  hrs of clock $hourOfDay")
        hrs = if (HOD>=12){
            Log.i("alarmAlarm","hrs - 12")
            hourOfDay-12
        }else {
            Log.i("alarmAlarm","hrs only")
            hourOfDay//-12
        }
        minutes = minute
    }
}