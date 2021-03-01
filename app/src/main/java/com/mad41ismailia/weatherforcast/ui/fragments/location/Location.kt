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
    private val cityList:ArrayList<String?> = arrayListOf()


    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor:SharedPreferences.Editor
    var gson = Gson()
    val typeList: Type = object : TypeToken<ArrayList<String?>>() {}.type

    lateinit var adapter : LocationAdapter

    //    private lateinit var autocompleteFragment: PlaceAutocompleteFragment
//    private var transaction : FragmentTransaction? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = LocationFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application))
            .get(LocationViewModel::class.java)
        var layoutManager = LinearLayoutManager(requireContext())

        binding.locationRecycler.layoutManager = layoutManager
        binding.locationRecycler.setHasFixedSize(true)
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getLocations().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                adapter = LocationAdapter(it)
                binding.locationRecycler.adapter = adapter
            })
        }
        // TODO: Use the ViewModel
//        list = viewModel.loadCities()





        sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        val lang = sharedPreferences.getString("lang", "en")
        val units = sharedPreferences.getString("units", "metric")
        Log.i("comingdata ", "location lang = " + lang)
        Log.i("comingdata ", "location units = " + units)
        cityList.add("Fayoum")
        cityList.add("Cairo")
        cityList.add("Ismailia")

//        cityList.add(place.name)
        val json = gson.toJson(cityList)
        Log.i("comingdata", "list to string $json")
        editor.putString("list",json)
        editor.commit()




//        //initialize mapBox
//        if (savedInstanceState == null) {
//            autocompleteFragment = PlaceAutocompleteFragment.newInstance(MAPBOX_API_KEY)
//            transaction = requireActivity().supportFragmentManager.beginTransaction()
//            transaction!!.add(R.id.autocomplete_fragment, autocompleteFragment, "SEARCH")
//            transaction!!.commit()
//        } else {
//            autocompleteFragment = requireActivity().supportFragmentManager.findFragmentByTag("SEARCH") as PlaceAutocompleteFragment
//        }

        addLocation()
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

                //cities list on shared pref
//                cityList.add(place.name)
//                val json = gson.toJson(cityList)
//                Log.i("comingdata", "$json")
//                editor.putString("list",json)
//                editor.commit()


                latlong[0].latitude
                val loc = Locations(place.name!!, latlong[0].latitude, latlong[0].longitude)
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.addLocation(loc)
                }
                Log.i("comingdata", "Place: ${place.name}, ${place.id}, ${latlon.toString()}")
                Log.i("comingdata", "Place: ${latlong[0].latitude}, ${latlong[0].longitude}, ${latlong.toString()}")
            }

            override fun onError(status: Status) {
                // TODO: Handle the error.
                Log.i("TAG", "An error occurred: $status")
            }
        })
    }



}