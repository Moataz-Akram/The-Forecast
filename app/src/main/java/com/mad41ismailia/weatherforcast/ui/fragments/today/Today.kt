package com.mad41ismailia.weatherforcast.ui.fragments.today

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mad41ismailia.weatherforcast.R
import com.mad41ismailia.weatherforcast.databinding.TodayFragmentBinding


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