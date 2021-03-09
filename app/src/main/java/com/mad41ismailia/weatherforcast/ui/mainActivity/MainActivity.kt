package com.mad41ismailia.weatherforcast.ui.mainActivity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.location.*
import com.google.android.libraries.places.api.Places
import com.mad41ismailia.weatherforcast.*
import com.mad41ismailia.weatherforcast.R
import com.mad41ismailia.weatherforcast.databinding.ActivityMainBinding
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
//        Repository.createObject(application)

        //initialize for the autoComplete
        Places.initialize(this, API_KEY)

        //viewModel
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))
                .get(MainActivityViewModel::class.java)
        //dataBinding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //bottomNavBar
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController = navHostFragment.findNavController()
        appBarConfiguration = AppBarConfiguration(
                setOf(R.id.today, R.id.location, R.id.settings2, R.id.alarmf)
        )
        binding.bottomNavBar.setupWithNavController(navController)

        //internet connection
        INTERNECT_CONNECTION = checkInternetConnection()


        //check GPS, location
        fusedLocation = LocationServices.getFusedLocationProviderClient(this)
        Log.i("comingdata", "before check" + currentLocation.toString())
        getLastLocation()
    }

    //wrapper base class for the language -> get called before onCreate
    override fun attachBaseContext(newBase: Context?) {
        val myPreference = MyPreference(newBase!!)
        val lang = myPreference.getLoginCount()
        super.attachBaseContext(MyContextWrapper.wrap(newBase!!, lang!!))
    }


    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        fusedLocation.lastLocation.addOnSuccessListener { location: Location? ->

        }
    }

    private fun checkPermissions(): Boolean {
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

    fun checkInternetConnection(): Boolean {
        return (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo?.isConnected == true
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
                    }
                }, Looper.myLooper())
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermissions()) {
            if (INTERNECT_CONNECTION) {
                //add check if local not null don't ask for gps again
                if (gpsEnabled() || viewModel.getCurrentLocation() != null) {
                    requestNewLocationData()
                    updateCurrentLocation()
                } else {
                    requestGpsEnable()
                }
            }
        } else {
            requestPermissions()
        }
    }

    @SuppressLint("MissingPermission", "LogNotTimber")
    private fun updateCurrentLocation() {
        fusedLocation.lastLocation.addOnCompleteListener { task ->
            val location = task.result
            Log.i("comingData", "coming fused location $location")
            if (location != null) {
                longt = location.longitude ?: 0.0
                lat = location.latitude ?: 0.0

                val geoCoder = Geocoder(this, Locale.getDefault())
                val addresses: List<Address> = geoCoder.getFromLocation(lat, longt, 1)
                currentLocation = addresses[0].locality

                val currentCity = viewModel.getCurrentLocation()
//                if (addresses[0].locality != null && !addresses[0].locality.equals(currentCity)) {
                if (addresses[0].locality != null) {
                if(currentCity!=null && currentCity!=addresses[0].locality){
                    viewModel.deleteOldCurrent(currentCity)
                }
                    viewModel.setCurrentLocation(addresses[0].locality)
                    viewModel.addOrUpdateDataForCurrentCity(addresses[0].locality,lat,longt)
                }
            }
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
}