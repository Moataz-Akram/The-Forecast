package com.mad41ismailia.weatherforcast.ui.today

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.libraries.places.api.Places
import com.mad41ismailia.weatherforcast.API_KEY
import com.mad41ismailia.weatherforcast.R
import com.mad41ismailia.weatherforcast.databinding.TodayFragmentBinding
import kotlinx.coroutines.*


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
        //initialize for the autoComplete
        Places.initialize(requireActivity().applicationContext, API_KEY)

        viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(TodayViewModel::class.java)
        // TODO: Use the ViewModel
        Log.i("comingdata ","")

        CoroutineScope(Dispatchers.IO).launch {
            viewModel.fetchData(29.3132 , 30.8508)
        }

        viewModel.getDaily().observe(viewLifecycleOwner,{
            Log.i("comingdata", it.toString())
            binding.textView.text = it.toString()
        })

    }

}