package com.mad41ismailia.weatherforcast.ui.fragments.today

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.mad41ismailia.weatherforcast.INTERNECT_CONNECTION
import com.mad41ismailia.weatherforcast.R
import com.mad41ismailia.weatherforcast.databinding.TodayFragmentBinding
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.DailyDatabase
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.HourlyDatabase
import com.mad41ismailia.weatherforcast.ui.mainActivity.MainActivity
import kotlinx.coroutines.*
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

    @SuppressLint("LogNotTimber")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //view model
        viewModel = ViewModelProvider(this).get(TodayViewModel::class.java)
//        viewModel.getDaily(viewModel.loadCitiesNew()[0]!!).observe(viewLifecycleOwner, {
//            Log.i("comingdata","observe"+ it.toString())
//            binding.textNoData.text = it.toString()
//        })

//        val cityListViewPager = viewModel.loadCities()
        val list = viewModel.loadCitiesNew()
        var listDaily: ArrayList<List<DailyDatabase>> = ArrayList()
        var listHourly: ArrayList<List<HourlyDatabase>> = ArrayList()
        val context = requireContext()
        var listTrial:ArrayList<LiveData<List<DailyDatabase>>> = ArrayList()
        CoroutineScope(Dispatchers.Default).launch {
            val job = launch {
                listTrial = viewModel.getCityAllLiveData()
                for (city in list) {
                    listDaily.add(viewModel.getDaily2(city!!))
                    listHourly.add(viewModel.getHourly2(city!!))
                }
                Log.i("viewpager","list size ${listDaily.size} list live data size ${listTrial.size}")

            }
            job.join()
            Log.i("viewpager","list size ${listDaily.size} list live data size ${listTrial.size}")
            withContext(Dispatchers.Main){
                if (list.isNotEmpty()) {
                    binding.viewPager.adapter = ViewPagerAdapter(context, list, listDaily,listHourly)

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
//                geocoder = Geocoder(requireContext(), Locale.getDefault())
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