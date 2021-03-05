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
import com.mad41ismailia.weatherforcast.INTERNECT_CONNECTION
import com.mad41ismailia.weatherforcast.R
import com.mad41ismailia.weatherforcast.databinding.TodayFragmentBinding
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.DailyDatabase
import com.mad41ismailia.weatherforcast.ui.mainActivity.MainActivity
import kotlinx.coroutines.*
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList


class Today : Fragment(R.layout.today_fragment) {

    private lateinit var viewModel: TodayViewModel
    private lateinit var binding: TodayFragmentBinding
    private lateinit var geocoder: Geocoder


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = TodayFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //view model
//        viewModel = ViewModelProvider(this).get(TodayViewModel::class.java)
//        viewModel.getDaily().observe(viewLifecycleOwner, {
//            Log.i("comingdata", it.toString())
//            binding.textNoData.text = it.toString()
//        })

//        val cityListViewPager = viewModel.loadCities()
        val list = viewModel.loadCities()
        var listDaily: ArrayList<List<DailyDatabase>> = ArrayList()

        var listTrial:List<LiveData<List<DailyDatabase>>> = ArrayList()
        CoroutineScope(Dispatchers.Default).launch {
            val job = launch {
                listTrial = viewModel.getCityAllLiveData()
                for (city in list) {
                    listDaily.add(viewModel.getDaily2(city!!))
                }

            }
            job.join()
            withContext(Dispatchers.Main){
                if (list.isNotEmpty()) {
                    binding.viewPager.adapter = ViewPagerAdapter(list, listDaily)
                    val indicator = binding.indicatior
                    indicator.setViewPager(binding.viewPager)
                    binding.textNoData.visibility = GONE
                } else {
                    binding.viewPager.visibility = GONE
                    binding.indicatior.visibility = GONE
                }

            }
        }

        if (INTERNECT_CONNECTION) {
            CoroutineScope(Dispatchers.IO).launch {
                delay(100)  //this delay is because saved location in settings get saved after it is read here without the delay and it don't get the updates
                geocoder = Geocoder(requireContext(), Locale.getDefault())
//            viewModel.fetchData2(geocoder)
                //add check to update only in new day if there is internet connection
                viewModel.fetchData()
            }
        }
    }

    private fun checkInternetConnection(): Boolean {
        return MainActivity.instance.checkInternetConnection()
    }
}