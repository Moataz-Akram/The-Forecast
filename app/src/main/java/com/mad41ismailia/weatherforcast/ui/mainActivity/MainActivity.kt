package com.mad41ismailia.weatherforcast.ui.mainActivity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.datatransport.runtime.scheduling.SchedulingConfigModule_ConfigFactory
import com.google.android.gms.location.*
import com.google.android.libraries.places.api.Places
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mad41ismailia.weatherforcast.*
import com.mad41ismailia.weatherforcast.R
import com.mad41ismailia.weatherforcast.databinding.ActivityMainBinding
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.Locations
import com.mad41ismailia.weatherforcast.repo.Repository
import kotlinx.coroutines.*
import java.io.IOException
import java.lang.reflect.Type
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var viewModel: MainActivityViewModel

    private var currentLocation: String? = null
    private lateinit var locationRequest: LocationRequest
    private lateinit var fusedLocation: FusedLocationProviderClient
    private var longt = 0.0
    private var lat = 0.0
    private val PERMISSION_ID = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //createRepo & database
        Repository.createObject(application)
        //initialize for the autoComplete
        Places.initialize(this, API_KEY)
        //viewModel
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))
                            .get(MainActivityViewModel::class.java)
        //dataBinding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        //bottomNavBar
        val  navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController = navHostFragment.findNavController()
        appBarConfiguration = AppBarConfiguration(
                setOf(R.id.today, R.id.sevenDays, R.id.location, R.id.settings2,R.id.alarmf)
        )
        binding.bottomNavBar.setupWithNavController(navController)

        //check GPS, location
        fusedLocation = LocationServices.getFusedLocationProviderClient(this)
        Log.i("comingdata","before check"+currentLocation.toString())
        getLastLocation()


        //check for local language
//        val sharedPreferences: SharedPreferences = application.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
//        val editor = sharedPreferences.edit()
//        val restart = sharedPreferences.getInt("restart",1)
//        if(restart===0){
//            viewModel.checkLanguage(this)
//            editor.putInt("restart",1)
//            editor.commit()
//        }else if(restart===1){
//            editor.putInt("restart",2)
//            editor.commit()
//        }
    }


    override fun attachBaseContext(newBase: Context?) {
        val myPreference = MyPreference(newBase!!)
        val lang = myPreference.getLoginCount()
        super.attachBaseContext(MyContextWrapper.wrap(newBase!!,lang!!))
    }


    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        fusedLocation.lastLocation.addOnSuccessListener { location: Location? ->
                    
        }
    }

    fun checkPermissions():Boolean{
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_ID)
    }

    private fun gpsEnabled(): Boolean {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    private fun requestGpsEnable() {
        val settingIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivity(settingIntent)
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.numUpdates = 1
        fusedLocation.requestLocationUpdates(locationRequest,
                                            object : LocationCallback() {
                                                override fun onLocationResult(locationResult: LocationResult) {
                                                    super.onLocationResult(locationResult)
                                                    val location = locationResult.lastLocation
                                                    longt = location.longitude
                                                    lat = location.latitude
                                                }
                                            }
                                            , Looper.myLooper())
    }

    fun getAddress(): String? {
        getLastLocation()
        val geocoder = Geocoder(this)
        val addresses = geocoder.getFromLocation(longt, lat, 1)
        Log.i("comingdata",addresses[0]?.locality.toString())

        if (addresses.isEmpty()){
            return null
        }
        return addresses[0]?.locality
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermissions()) {
            //add check in local not null don't ask
            if (gpsEnabled()) {
                requestNewLocationData()
                fusedLocation.lastLocation.addOnCompleteListener { task ->
                    val location = task.result
                    Log.i("comingdata","coming fused location "+location.toString())
                    longt = location.longitude ?: 0.0
                    lat = location.latitude ?: 0.0
                    //not need but left for toast
                    val geocoder = Geocoder(this, Locale.getDefault())
                    val addresses: List<Address> = geocoder.getFromLocation(lat, longt, 1)
                    currentLocation = addresses[0].locality
                    Log.i("comingdata",currentLocation.toString())

                    //in shared preference
                    viewModel.setCurrentLocation(addresses[0].locality)

                    //need to change table to a list in shared pref
                    val loc = Locations(addresses[0].locality,lat,longt)
                    loc.id = 1

                    CoroutineScope(Dispatchers.Default).launch {
                        //update current in database
                        viewModel.addLocation(loc)
                        val r= viewModel.getCurrentLocation(1)
//                        Toast.makeText(applicationContext, "city: ${r.cityAddress}\n Lat: ${r.lat}\n id:${r.id}", Toast.LENGTH_LONG).show()
//                        Log.i("comingdata", "coming location $r")
//                        Log.i("comingdata","coming location "+r.cityAddress+" "+r.id+" "+r.lat+" "+r.lon)
                    }
                    Toast.makeText(this, "Long: $longt\n Lat: $lat\n city:${addresses[0].locality}", Toast.LENGTH_LONG).show()
                }
            } else {
                requestGpsEnable()
            }
        } else {
            requestPermissions()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_ID) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation()
            }
        }
    }

    override fun onStop() {
//        val sharedPreferences: SharedPreferences = application.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
//        val editor = sharedPreferences.edit()
//        val restart = sharedPreferences.getInt("restart",1)
//        if(restart===2){
//            editor.putInt("restart",0)
//            editor.commit()
//        }
        super.onStop()
    }

}