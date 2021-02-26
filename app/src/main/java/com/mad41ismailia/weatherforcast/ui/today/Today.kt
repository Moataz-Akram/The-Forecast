package com.mad41ismailia.weatherforcast.ui.today

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.mad41ismailia.weatherforcast.R
import com.mad41ismailia.weatherforcast.databinding.TodayFragmentBinding
import com.mad41ismailia.weatherforcast.entity.WeatherData
import com.mad41ismailia.weatherforcast.entity3.hourly.HourlyData
import com.mad41ismailia.weatherforcast.repo.Room.WeatherDao
import com.mad41ismailia.weatherforcast.repo.Room.WeatherDatabase
import com.mad41ismailia.weatherforcast.repo.retrofit.UseRetrofit
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Today : Fragment(R.layout.today_fragment) {

    companion object {
        fun newInstance() = Today()
    }

    private lateinit var viewModel: TodayViewModel
    private lateinit var binding: TodayFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TodayFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.textView.text = "Working!!!!! today fragment"
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TodayViewModel::class.java)
        // TODO: Use the ViewModel


    }

}