package com.mad41ismailia.weatherforcast.ui.fragments.today

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.mad41ismailia.weatherforcast.R
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.CityWeatherData
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.DailyDatabase
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.HourlyDatabase
import com.mad41ismailia.weatherforcast.entity.comingData.WeatherData
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList

@SuppressLint("LogNotTimber")
class ViewPagerAdapter2(val context: Context,val list:List<CityWeatherData>) : RecyclerView.Adapter<ViewPagerAdapter2.ViewHolder>() {


    private var myList: List<CityWeatherData> = list
    private lateinit var dailyAdapter:DailyAdapter2
    private lateinit var hourlyAdapter: HourlyAdapter2

    private val gson = GsonBuilder().create()
    val weatherDataConverter: Type = object : TypeToken<WeatherData>() {}.type


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_pager_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val city = myList[position]
        holder.userId.text = city.cityName
        val weatherData = gson.fromJson<WeatherData>(city.weatherData,weatherDataConverter)
        val daily = weatherData.daily
            if (daily.isNotEmpty()){
            val icon = daily[0].weather[0].icon
            Log.i("imageview", "${daily}")
            icon?.let { setImg(it) }?.let { holder.weatherDetail.setImageResource(R.drawable.cloud_location) }
        }
        val recyclerViewPool = RecyclerView.RecycledViewPool()
        val recyclerViewPool2 = RecyclerView.RecycledViewPool()
        val layoutManager = LinearLayoutManager(context)
        val layoutManager2 = LinearLayoutManager(context)

        layoutManager.orientation = HORIZONTAL
        layoutManager.initialPrefetchItemCount = weatherData.hourly.size

        hourlyAdapter = HourlyAdapter2(weatherData.hourly)
        holder.HourlyRecyclerView.layoutManager = layoutManager
        holder.HourlyRecyclerView.adapter = hourlyAdapter
//        holder.HourlyRecyclerView.setRecycledViewPool(recyclerViewPool)
        holder.HourlyRecyclerView.setHasFixedSize(true)


        layoutManager2.initialPrefetchItemCount = weatherData.daily.size
        dailyAdapter = DailyAdapter2(weatherData.daily)
        holder.dailyRecyclerView.layoutManager = layoutManager2
        holder.dailyRecyclerView.adapter = dailyAdapter
//        holder.dailyRecyclerView.setRecycledViewPool(recyclerViewPool2)
//        holder.dailyRecyclerView.setHasFixedSize(true)
        holder.lottieIcon.setAnimation(R.raw.lottie)
    }



    override fun getItemCount(): Int {
        return myList.size
    }

    fun setList(list: List<CityWeatherData>) {
        myList = list
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userId: TextView = view.findViewById(R.id.cityNameViewPager)
        val weatherDetail:ImageView = view.findViewById(R.id.imgWeatherState)
        val dailyRecyclerView: RecyclerView = view.findViewById(R.id.DailyRecyclerView)
        val HourlyRecyclerView: RecyclerView = view.findViewById(R.id.HourlyRecyclerView)
        val lottieIcon: LottieAnimationView = view.findViewById(R.id.imgLottie)
    }
}

