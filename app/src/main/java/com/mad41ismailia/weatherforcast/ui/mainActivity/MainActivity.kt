package com.mad41ismailia.weatherforcast.ui.mainActivity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.room.Room
import com.google.android.libraries.places.api.Places
import com.mad41ismailia.weatherforcast.API_KEY
import com.mad41ismailia.weatherforcast.R
import com.mad41ismailia.weatherforcast.databinding.ActivityMainBinding
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.DailyDatabase
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.HourlyDatabase
import com.mad41ismailia.weatherforcast.entity.comingData.WeatherData
import com.mad41ismailia.weatherforcast.repo.Room.WeatherDatabase
import com.mad41ismailia.weatherforcast.repo.retrofit.UseRetrofit
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val  navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController = navHostFragment.findNavController()
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.today, R.id.sevenDays, R.id.location, R.id.settings)
        )
        binding.bottomNavBar.setupWithNavController(navController)


        useRoom()
        Places.initialize(applicationContext, API_KEY)
    }

    private fun useRoom() {
        val db = Room.databaseBuilder(applicationContext, WeatherDatabase::class.java,"Weather7Database").allowMainThreadQueries().build()
        val weatherDao = db.WeatherDao()

        val data = UseRetrofit()
        var data3 : WeatherData? = null
        CoroutineScope(Dispatchers.IO + Job()).launch {
            weatherDao.deleteDaily()
            weatherDao.deleteHourly()
            val map = mapOf(29.3132 to 30.8508, 45.0317 to -63.4558, 29.9955 to 31.2311)
            for(i in map){

            data.retrofitInterfaceObject.getWeather(i.key,i.value).enqueue(object :
                    Callback<WeatherData> {
                override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                    Log.i("comingdata on failure", t.message+"")
                }

                override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
                    response.body()?.let {
                        data3 = it
                        Log.i("comingdata on response", data3.toString())
                        var list =  ArrayList<DailyDatabase>()
                        var list3 =  ArrayList<HourlyDatabase>()
                        val list2 = it.daily
                        val list4 = it.hourly
                        for(i in list2){
                            val m = DailyDatabase(29.3132,30.8508,i)
                            list.add(m)
                        }
                        for(i in list4){
                            val m = HourlyDatabase(29.3132,30.8508,i)
                            list3.add(m)
                        }
                        weatherDao.addDaily(list)
                        weatherDao.addHourly(list3)
                    }
                }
            })
            delay(5000)
            withContext(Dispatchers.Main){
                binding.textView4.text = data3.toString()
            }

            }
        }

    }
}