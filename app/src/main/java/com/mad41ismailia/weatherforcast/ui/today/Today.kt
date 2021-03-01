package com.mad41ismailia.weatherforcast.ui.today

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.libraries.places.api.Places
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.mad41ismailia.weatherforcast.API_KEY
import com.mad41ismailia.weatherforcast.R
import com.mad41ismailia.weatherforcast.databinding.TodayFragmentBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.reflect.Type
import java.util.*


class Today : Fragment(R.layout.today_fragment) {

    companion object {
        fun newInstance() = Today()
    }

    private lateinit var viewModel: TodayViewModel
    private lateinit var binding: TodayFragmentBinding

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor:SharedPreferences.Editor
    private var cityList:ArrayList<String?> = arrayListOf()
    var gson = GsonBuilder().create()
    val typeList: Type = object : TypeToken<ArrayList<String?>>() {}.type

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
        //initialize for the autoComplete
        Places.initialize(requireActivity().applicationContext, API_KEY)
        //to get units ad lang
//        sharedPreferences = requireActivity().application.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        val json = sharedPreferences.getString("list", "")
        cityList = gson.fromJson(json,typeList)
//        val data = gson.fromJson(json,Array<String>::class.java).toList()
        Log.i("comingdata", "${cityList.toString()}")




        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(TodayViewModel::class.java)
        // TODO: Use the ViewModel
        Log.i("comingdata ", "")

        CoroutineScope(Dispatchers.IO).launch {
            if(viewModel.checkCities()){
                //this delay is because saved location in settings get saved after it is
                //iiread here without the delay and it don't get the updates
                delay(100)
                val lang = sharedPreferences.getString("lang", "en")
                val units = sharedPreferences.getString("units", "metric")

                Log.i("comingdata ", "coming lang = " + lang)
                Log.i("comingdata ", "coming lang = " + units)
                viewModel.fetchData(55.755825, 37.617298, units!!, lang!!)

                CoroutineScope(Dispatchers.Main).launch {
                viewModel.getDaily().observe(viewLifecycleOwner, {
                    Log.i("comingdata", it.toString())
                    binding.textView.text = it.toString()
                })
                }

            }else{
                CoroutineScope(Dispatchers.Main).launch {
                binding.textView.text = "Please check you GPS and internet connection or add a favourite place"
            }

        }
        }
    }

}