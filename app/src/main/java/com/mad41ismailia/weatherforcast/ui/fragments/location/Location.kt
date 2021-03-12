package com.mad41ismailia.weatherforcast.ui.fragments.location

//import androidx.fragment.app.FragmentTransaction
//import com.mapbox.api.geocoding.v5.models.CarmenFeature
import android.annotation.SuppressLint
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.mad41ismailia.weatherforcast.R
import com.mad41ismailia.weatherforcast.databinding.LocationFragmentBinding
import java.util.*


//import com.mapbox.mapboxsdk.plugins.places.autocomplete.ui.PlaceSelectionListener

class Location : Fragment(R.layout.location_fragment) {
    private lateinit var binding:LocationFragmentBinding
    private lateinit var viewModel: LocationViewModel
    private var cityList:ArrayList<String?> = arrayListOf()
    lateinit var adapter : LocationAdapter


//    private lateinit var autocompleteFragment: PlaceAutocompleteFragment
//    private var transaction : FragmentTransaction? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = LocationFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LocationViewModel::class.java)
        val layoutManager = LinearLayoutManager(requireContext())
        binding.locationRecycler.layoutManager = layoutManager
        binding.locationRecycler.setHasFixedSize(true)
//        Thread.sleep(100)
        cityList = viewModel.loadAllCities()
        adapter = LocationAdapter(cityList,viewModel,requireContext())
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
            @SuppressLint("LogNotTimber")
            override fun onPlaceSelected(place: Place) {
                // TODO: Get info about the selected place.
                val geocoder = Geocoder(requireContext(), Locale.getDefault())
                val latlong = geocoder.getFromLocationName(place.name, 1)
                Log.i("googleplaces", "lat array: ${latlong.toString()} ")
                Log.i("googleplaces", "place object: ${place.toString()} ")
                Log.i("googleplaces", "place object: ${place.latLng.toString()} ")

                if(latlong.isNotEmpty()) {
                    viewModel.saveNewCity(place.name!!)
                    viewModel.addDataForNewCity(place.name!!,latlong[0].latitude,latlong[0].longitude)
                    val list = viewModel.loadAllCities()
                    adapter.setList(list)
                    adapter.notifyDataSetChanged()

//                    Locations(place.name!!, latlong[0].latitude, latlong[0].longitude)
                    Log.i("googleplaces", "An error occurred: ${latlong[0].latitude} ${latlong[0].longitude}")

                }
            }

            override fun onError(status: Status) {
                // TODO: Handle the error.
                Log.i("TAG", "An error occurred: $status")
            }
        })
    }
}