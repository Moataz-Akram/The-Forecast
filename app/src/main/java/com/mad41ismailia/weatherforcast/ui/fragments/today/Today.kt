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
import com.mad41ismailia.weatherforcast.R
import com.mad41ismailia.weatherforcast.databinding.TodayFragmentBinding
import com.mad41ismailia.weatherforcast.ui.fragments.today.adapters.ViewPagerAdapter2
import com.mad41ismailia.weatherforcast.ui.activites.mainActivity.MainActivity
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

        binding.textNoData.visibility = GONE
        if((requireActivity() as MainActivity).checkInternetConnection()) {
            viewModel.updateDataIfNewDay()
        }
        viewModel.observeWeatherData().observe(viewLifecycleOwner, {
            Log.i("comingdata", "comingListSize ${it.size}")
            Log.i("comingdata", "comingListSize ${it.isNotEmpty()}")
//            viewModel.updateAllCities()

            if (it.isNotEmpty()) {
                val list = viewModel.orderList(it!!,viewModel.getCurrentLocation())
                binding.viewPager.adapter = ViewPagerAdapter2(requireContext(), list,viewModel)
                val indicator = binding.indicatior
                indicator.setViewPager(binding.viewPager)
                binding.textNoData.visibility = GONE
                binding.viewPager.visibility = VISIBLE
                binding.indicatior.visibility = VISIBLE
            } else {
                CoroutineScope(Dispatchers.Main).launch {
                    delay(1000)
                    binding.viewPager.visibility = GONE
                    binding.indicatior.visibility = GONE
                    binding.textNoData.visibility = VISIBLE
                }
            }
        })
    }



}