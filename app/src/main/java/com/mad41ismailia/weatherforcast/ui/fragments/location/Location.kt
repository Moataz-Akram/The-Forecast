package com.mad41ismailia.weatherforcast.ui.fragments.location

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
//import androidx.fragment.app.FragmentTransaction
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.mad41ismailia.weatherforcast.MAPBOX_API_KEY
import com.mad41ismailia.weatherforcast.R
import com.mad41ismailia.weatherforcast.databinding.LocationFragmentBinding
//import com.mapbox.api.geocoding.v5.models.CarmenFeature
import com.mapbox.mapboxsdk.plugins.places.autocomplete.ui.PlaceAutocompleteFragment
//import com.mapbox.mapboxsdk.plugins.places.autocomplete.ui.PlaceSelectionListener

class Location : Fragment(R.layout.location_fragment) {
    private lateinit var binding:LocationFragmentBinding
    private lateinit var viewModel: LocationViewModel
//    private lateinit var autocompleteFragment: PlaceAutocompleteFragment
//    private var transaction : FragmentTransaction? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = LocationFragmentBinding.inflate(inflater,container,false)
        val view = binding.root

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LocationViewModel::class.java)
        // TODO: Use the ViewModel

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
                Log.i("TAG", "Place: ${place.name}, ${place.id}")
                binding.textView3.text = place.name
            }
            override fun onError(status: Status) {
                // TODO: Handle the error.
                Log.i("TAG", "An error occurred: $status")
            }
        })
    }



}