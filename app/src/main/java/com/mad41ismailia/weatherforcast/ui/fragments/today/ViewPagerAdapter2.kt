package com.mad41ismailia.weatherforcast.ui.fragments.today

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.mad41ismailia.weatherforcast.R
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.CityWeatherData
import com.mad41ismailia.weatherforcast.entity.comingData.WeatherData
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

@SuppressLint("LogNotTimber")
class ViewPagerAdapter2(val context: Context,val list:List<CityWeatherData>,val current: String?) : RecyclerView.Adapter<ViewPagerAdapter2.ViewHolder>() {


    private var myList: List<CityWeatherData> = list
    private lateinit var dailyAdapter:DailyAdapter2
    private lateinit var hourlyAdapter: HourlyAdapter2
    @RequiresApi(Build.VERSION_CODES.O)
    val currentDateTime = LocalDateTime.now()
    private val gson = GsonBuilder().create()
    val weatherDataConverter: Type = object : TypeToken<WeatherData>() {}.type


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_pager_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val city = myList[position]
        holder.userId.text = city.cityName
        val weatherData = gson.fromJson<WeatherData>(city.weatherData,weatherDataConverter)
        holder.txtNowTemp.text = weatherData.current.temp.toInt().toString()
        holder.txtFeelsLike.text = context.resources.getString(R.string.feelsLike) + weatherData.current.feels_like.toInt().toString()
        holder.txtWeatherState.text = weatherData.current.weather[0].description

        holder.txtDate.text = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            currentDateTime.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
        }else ({
            val sdf = SimpleDateFormat("MMM d, yyyy")
            val currentDate = sdf.format(Date())
        }).toString()

        if(current!=null){
            if(current == myList[position].cityName){
                holder.imgLocation.setImageResource(R.drawable.home_location)
            }else{
                holder.imgLocation.setImageResource(R.drawable.ic_fav_location)
            }
        }else{
            holder.imgLocation.setImageResource(R.drawable.ic_fav_location)
        }

        //daily & hourly recyclers
        val layoutManager = LinearLayoutManager(context)
        val layoutManager2 = LinearLayoutManager(context)
        layoutManager.orientation = HORIZONTAL
        layoutManager.initialPrefetchItemCount = weatherData.hourly.size
        hourlyAdapter = HourlyAdapter2(weatherData.hourly)
        holder.HourlyRecyclerView.layoutManager = layoutManager
        holder.HourlyRecyclerView.adapter = hourlyAdapter
        holder.HourlyRecyclerView.setHasFixedSize(true)
        layoutManager2.initialPrefetchItemCount = weatherData.daily.size
        dailyAdapter = DailyAdapter2(weatherData.daily,context)
        holder.dailyRecyclerView.layoutManager = layoutManager2
        holder.dailyRecyclerView.adapter = dailyAdapter
        holder.lottieIcon.setAnimation(setImgLottie(weatherData.current.weather[0].icon))
        holder.imgTempLottie.setAnimation(R.raw.temp2)
    }

    override fun getItemCount(): Int {
        return myList.size
    }

    fun setList(list: List<CityWeatherData>) {
        myList = list
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userId: TextView = view.findViewById(R.id.cityNameViewPager)
        val txtNowTemp: TextView = view.findViewById(R.id.txtNowTemp)
        val txtFeelsLike: TextView = view.findViewById(R.id.txtFeelsLikeTemp)
        val txtWeatherState: TextView = view.findViewById(R.id.txtWeatherState)
        val txtDate: TextView = view.findViewById(R.id.txtDate)
        val imgLocation:ImageView = view.findViewById(R.id.imgLocation)
        val dailyRecyclerView: RecyclerView = view.findViewById(R.id.DailyRecyclerView)
        val HourlyRecyclerView: RecyclerView = view.findViewById(R.id.HourlyRecyclerView)
        val lottieIcon: LottieAnimationView = view.findViewById(R.id.imgLottie)
        val imgTempLottie: LottieAnimationView = view.findViewById(R.id.imgTempLottie)
    }
}

