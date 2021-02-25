package com.mad41ismailia.weatherforcast.ui.today

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mad41ismailia.weatherforcast.R
import com.mad41ismailia.weatherforcast.databinding.TodayFragmentBinding
import com.mad41ismailia.weatherforcast.entity3.alerts.AlertData
import com.mad41ismailia.weatherforcast.entity3.daily.DailyData
import com.mad41ismailia.weatherforcast.entity3.hourly.HourlyData
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
        val data = UseRetrofit()

        var data3 : DailyData? = null
        CoroutineScope(Dispatchers.IO + Job()).launch {
            data.retrofitInterfaceObject.getDaily2(29.3132,30.8508).enqueue(object :
                Callback<DailyData> {
                override fun onFailure(call: Call<DailyData>, t: Throwable) {
                    Log.i("comingdata on failure", data3.toString())

                }

                override fun onResponse(call: Call<DailyData>, response: Response<DailyData>) {
                    response.body()?.let {
                        data3 = it
                        Log.i("comingdata on response", data3.toString())
                    }
                }
            })
            delay(5000)
            withContext(Dispatchers.Main){
                binding.textView.text = data3.toString()
            }
        }
    }

}