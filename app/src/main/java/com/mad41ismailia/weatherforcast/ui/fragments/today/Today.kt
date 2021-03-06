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
import android.widget.Toast
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

            viewModel.fetchData2().observe(viewLifecycleOwner, {
                Log.i("comingdata","observe"+ it.toString())
                if (it.isNotEmpty()) {
                    binding.viewPager.adapter = ViewPagerAdapter2(requireContext(),it)

                    val indicator = binding.indicatior
                    indicator.setViewPager(binding.viewPager)
                    binding.textNoData.visibility = GONE
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

    private fun checkInternetConnection(): Boolean {
        return MainActivity.instance.checkInternetConnection()
    }
}