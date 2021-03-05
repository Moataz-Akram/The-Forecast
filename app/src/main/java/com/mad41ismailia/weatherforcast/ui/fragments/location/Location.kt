package com.mad41ismailia.weatherforcast.ui.fragments.location

//import androidx.fragment.app.FragmentTransaction
//import com.mapbox.api.geocoding.v5.models.CarmenFeature
import android.content.Context
import android.content.SharedPreferences
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mad41ismailia.weatherforcast.R
import com.mad41ismailia.weatherforcast.databinding.LocationFragmentBinding
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.Locations
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.reflect.Type
import java.util.*


//import com.mapbox.mapboxsdk.plugins.places.autocomplete.ui.PlaceSelectionListener

class Location : Fragment(R.layout.location_fragment) {
    private lateinit var binding:LocationFragmentBinding
    private lateinit var viewModel: LocationViewModel
    private var cityList:ArrayList<String?> = arrayListOf()
    lateinit var adapter : LocationAdapter

    private lateinit var sharedPreferences: SharedPreferences//delete
    private lateinit var editor:SharedPreferences.Editor//delete
    var gson = Gson()//delete
    val typeList: Type = object : TypeToken<ArrayList<String?>>() {}.type//delete

//    private lateinit var autocompleteFragment: PlaceAutocompleteFragment
//    private var transaction : FragmentTransaction? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = LocationFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LocationViewModel::class.java)
        var layoutManager = LinearLayoutManager(requireContext())
        binding.locationRecycler.layoutManager = layoutManager
        binding.locationRecycler.setHasFixedSize(true)

        cityList = viewModel.loadCities()
        adapter = LocationAdapter(cityList)
        binding.locationRecycler.adapter = adapter

        addLocation()
//        //initialize mapBox
//        if (savedInstanceState == null) {
//            autocompleteFragment = PlaceAutocompleteFragment.newInstance(MAPBOX_API_KEY)
//            transaction = requireActivity().supportFragmentManager.beginTransaction()
//            transaction!!.add(R.id.autocomplete_fragment, autocompleteFragment, "SEARCH")
//            transaction!!.commit()
//        } else {
//            autocompleteFragment = requireActivity().supportFragmentManager.findFragmentByTag("SEARCH") as PlaceAutocompleteFragment
//        }

//        autocompleteMapbox()
    }



//    private fun autocompleteMapbox() {
//        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
//            override fun onPlaceSelected(carmenFeature: CarmenFeature) {
//                Toast.makeText(requireContext(), "Hello", Toast.LENGTH_LONG).show()
//                Toast.makeText(requireContext(), carmenFeature.text(), Toast.LENGTH_LONG).show()
//                activity?.supportFragmentManager?.beginTransaction()?.remove(autocompleteFragment)?.commit()
//
//            }
//
//            override fun onCancel() {
//
//            }
//        })
//}

    private fun addLocation() {
        // Initialize the AutocompleteSupportFragment.
        val autocompleteFragment = childFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment
        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME))
        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // TODO: Get info about the selected place.
                val latlon = place.address
                val geocoder = Geocoder(requireContext(), Locale.getDefault())
                val latlong = geocoder.getFromLocationName(place.name, 1)

                val loc = Locations(place.name!!, latlong[0].latitude, latlong[0].longitude)
                viewModel.saveCity(place.name!!)
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.addCityDB(loc)
                    viewModel.fetchCityData(place.name!!, latlong[0].latitude, latlong[0].longitude)
                }
            }

            override fun onError(status: Status) {
                // TODO: Handle the error.
                Log.i("TAG", "An error occurred: $status")
            }
        })
    }



}