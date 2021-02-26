package com.mad41ismailia.weatherforcast.repo

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
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
import com.mad41ismailia.weatherforcast.entity.DailyDatabase
import com.mad41ismailia.weatherforcast.entity.HourlyDatabase
import com.mad41ismailia.weatherforcast.entity.WeatherData
import com.mad41ismailia.weatherforcast.entity.WeatherX
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
    private lateinit var appBarConfiguration2: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val  navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController = navHostFragment.findNavController()
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.today, R.id.sevenDays, R.id.location, R.id.settings)
        )
        setSupportActionBar(binding.toolBar)
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomNavBar.setupWithNavController(navController)


        val db = Room.databaseBuilder(applicationContext, WeatherDatabase::class.java,"Weather6Database").allowMainThreadQueries().build()
        val weatherDao = db.WeatherDao()

        val data = UseRetrofit()
        var data3 : WeatherData? = null
        CoroutineScope(Dispatchers.IO + Job()).launch {
            weatherDao.deleteDaily()
            weatherDao.deleteHourly()
            data.retrofitInterfaceObject.getWeather(29.3132,30.8508).enqueue(object :
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
//                        val m1 = WeatherX("","",0,"")
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
//                        val d1 = DailyDatabase(0.0,0.0,m1,0,0.0)
//                        val d2 = DailyDatabase(0.0,0.0,m1,0,0.0)
//                        weatherDao.addDaily1(d1)
//                        weatherDao.addDaily1(d2)
                    }
                }
            })
            delay(5000)
            withContext(Dispatchers.Main){
                binding.textView4.text = data3.toString()
            }
        }
//        val drawerLayout: DrawerLayout = binding.drawerLayout
//        val navView: NavigationView = binding.navView
//        appBarConfiguration2 = AppBarConfiguration(
//            setOf(R.id.settings,R.id.alarm),drawerLayout
//        )

//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)

//        val m = applicationContext.packageManager.getApplicationInfo(applicationContext.packageName, PackageManager.GET_META_DATA)
//        val s = m.metaData.getString("com.google.android.maps.v2.API_KEY","notworking")
//        Log.i("TAG",""+R.string.api_key)

        // Initialize the SDK
        Places.initialize(applicationContext, API_KEY)
//        // Create a new PlacesClient instance
//        val placesClient = Places.createClient(this)
    }

//    override fun onSupportNavigateUp(): Boolean {
//        return navController.navigateUp() || super.onSupportNavigateUp()
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.options_menu,menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return item.onNavDestinationSelected(navController) || super.onSupportNavigateUp()
//    }
}