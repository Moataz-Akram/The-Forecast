package com.mad41ismailia.weatherforcast.ui.fragments.today

import android.content.Context
import android.content.SharedPreferences
import android.location.Geocoder
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.google.android.libraries.places.api.Places
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.mad41ismailia.weatherforcast.API_KEY
import com.mad41ismailia.weatherforcast.R
import com.mad41ismailia.weatherforcast.databinding.TodayFragmentBinding
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.DailyDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.reflect.Type
import java.util.*


class Today : Fragment(R.layout.today_fragment) {

    private lateinit var viewModel: TodayViewModel
    private lateinit var binding: TodayFragmentBinding
    private lateinit var geocoder :Geocoder


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = TodayFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //view model
        viewModel = ViewModelProvider(this).get(TodayViewModel::class.java)
//        viewModel.getDaily().observe(viewLifecycleOwner, {
//            Log.i("comingdata", it.toString())
//            binding.textView.text = it.toString()
//        })

        Toast.makeText(context,"${Locale.getDefault().language}", Toast.LENGTH_LONG).show()
//        val cityListViewPager = viewModel.loadCities()
        val list = viewModel.loadCities()
        var listDaily : ArrayList<List<DailyDatabase>> = ArrayList()


        CoroutineScope(Dispatchers.Default).launch {
            delay(1000)
            for (city in list){
                listDaily.add(viewModel.getDaily2(city!!))
            }
        }
        if(list.isNotEmpty()){
        binding.viewPager.adapter = ViewPagerAdapter(list, listDaily)
        val indicator = binding.indicatior
        indicator.setViewPager(binding.viewPager)
            binding.textNoData.visibility = GONE
        }else{
            binding.viewPager.visibility = GONE
            binding.indicatior.visibility = GONE
        }

        CoroutineScope(Dispatchers.IO).launch {
            delay(100)  //this delay is because saved location in settings get saved after it is read here without the delay and it don't get the updates
            geocoder = Geocoder(requireContext(), Locale.getDefault())

            viewModel.fetchData2(geocoder)
        }
    }

}