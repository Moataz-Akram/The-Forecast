package com.mad41ismailia.weatherforcast.ui.fragments.today

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mad41ismailia.weatherforcast.INTERNECT_CONNECTION
import com.mad41ismailia.weatherforcast.R
import com.mad41ismailia.weatherforcast.databinding.TodayFragmentBinding
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.CityWeatherData
import com.mad41ismailia.weatherforcast.ui.mainActivity.MainActivity
import kotlinx.coroutines.*


class Today : Fragment(R.layout.today_fragment) {

    private lateinit var viewModel: TodayViewModel
    private lateinit var binding: TodayFragmentBinding



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
        viewModel.updateAllCities()
        viewModel.observeWeatherData().observe(viewLifecycleOwner, {
            Log.i("comingdata","observe"+ it.toString())
            if (it.isNotEmpty()) {
//                    val current = viewModel.getCurrentLocation()
//                    if (current!=null){
                    //make current city the first on list
//                        val list = orderList(it,current)
//                    }
                binding.viewPager.adapter = ViewPagerAdapter2(requireContext(),it,viewModel.getCurrentLocation())

                val indicator = binding.indicatior
                indicator.setViewPager(binding.viewPager)
                binding.textNoData.visibility = GONE
                binding.viewPager.visibility = VISIBLE
                binding.indicatior.visibility = VISIBLE
            } else {
                binding.viewPager.visibility = GONE
                binding.indicatior.visibility = GONE
            }
        })


        if (INTERNECT_CONNECTION) {
            CoroutineScope(Dispatchers.IO).launch {
                //add check to update only in new day if there is internet connection
//                viewModel.fetchData2()
            }
        }
    }

//    private fun orderList(list2: List<CityWeatherData>?, current: String): Any {
//        val list:ArrayList<CityWeatherData> = (list2 as ArrayList<CityWeatherData>?)!!
//        var order = 0
//        for (cityData in list!!){
//            if (current== cityData.cityName){
//                order = list.indexOf(cityData)
//            }
//        }
//        if (order!=0){
//            var current = list[order]
//            for (i in order..1){
//                val temp = list[order-1]
//                list[order] = temp
//            }
//            list[0] = current
//        }
//        return list
//    }

    private fun checkInternetConnection(): Boolean {
        return MainActivity.instance.checkInternetConnection()
    }
}