package com.mad41ismailia.weatherforcast.ui.fragments.sevenDays

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mad41ismailia.weatherforcast.R
import com.mad41ismailia.weatherforcast.databinding.SevenDaysFragmentBinding
import com.mad41ismailia.weatherforcast.ui.mainActivity.MainActivity
import java.util.*

class SevenDays : Fragment(R.layout.seven_days_fragment) {
    private lateinit var binding : SevenDaysFragmentBinding
    private lateinit var viewModel: SevenDaysViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = SevenDaysFragmentBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SevenDaysViewModel::class.java)


//        val locale = Locale("ar")
//        val res = resources
//        val dm = res.displayMetrics
//        val conf = res.configuration
//        conf.setLocale(locale)
//        res.updateConfiguration(conf, dm)
//        val refresh = Intent(activity, MainActivity::class.java)
//        startActivity(refresh)
    }

}