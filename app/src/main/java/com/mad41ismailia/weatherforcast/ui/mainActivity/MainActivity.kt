package com.mad41ismailia.weatherforcast.ui.mainActivity

import android.Manifest
import android.annotation.SuppressLint
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
import com.google.android.gms.location.*
import com.mad41ismailia.weatherforcast.PREF_NAME
import com.mad41ismailia.weatherforcast.R
import com.mad41ismailia.weatherforcast.databinding.ActivityMainBinding
import com.mad41ismailia.weatherforcast.repo.Repository
import java.io.IOException
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var viewModel: MainActivityViewModel

    private lateinit var locationRequest: LocationRequest
    private lateinit var fusedLocation: FusedLocationProviderClient
    private var longt = 0.0
    private var lat = 0.0
    private val PERMISSION_ID = 3

    var sharedPreferences: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null


    private val locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            val location = locationResult.lastLocation
            longt = location.longitude
            lat = location.latitude
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //createRepo & database
        Repository.createObject(application)
        //viewModel
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))
                .get(MainActivityViewModel::class.java)
        //dataBinding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        //bottomNavBar
        val  navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController = navHostFragment.findNavController()
        appBarConfiguration = AppBarConfiguration(
                setOf(R.id.today, R.id.sevenDays, R.id.location, R.id.settings)
        )
        binding.bottomNavBar.setupWithNavController(navController)

        //check GPS, location
        fusedLocation = LocationServices.getFusedLocationProviderClient(this)
        getAddress()
//        getCurrentLocation()
        //sharedPreference
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        editor = sharedPreferences!!.edit()

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
        fusedLocation.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
    }

    fun getAddress() {
        getLastLocation()
        val geocoder = Geocoder(this)
        try {
            val addresses = geocoder.getFromLocation(longt, lat, 3)
//            Log.i("city",""+addresses[0])
            if (addresses.size > 0) {
                var address = addresses[0].getAddressLine(0).toString()
//                binding.textView4.setText(address)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermissions()) {
            if (gpsEnabled()) {

//                val geocoder = Geocoder(this, Locale.getDefault())
//                runBlocking {
//                    val addresses: List<Address> = geocoder.getFromLocation(29.3132, 30.8508, 1)
//                    Log.i("comingdata", "coming list" + addresses.toString())
//                    Toast.makeText(applicationContext, "${addresses[0].locality}", Toast.LENGTH_LONG).show()
//                }

                requestNewLocationData()
                fusedLocation.lastLocation.addOnCompleteListener { task ->
                    val location = task.result
                    longt = location.longitude
                    lat = location.latitude
                    val geocoder = Geocoder(this, Locale.getDefault())
                    val addresses: List<Address> = geocoder.getFromLocation(lat, longt, 1)

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
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation()
            }
        }
    }

}