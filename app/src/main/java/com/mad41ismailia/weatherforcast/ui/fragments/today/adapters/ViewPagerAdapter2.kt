package com.mad41ismailia.weatherforcast.ui.fragments.today.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ScrollView
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
import com.mad41ismailia.weatherforcast.ui.fragments.today.TodayViewModel
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

@SuppressLint("LogNotTimber")
class ViewPagerAdapter2(val context: Context, val list:List<CityWeatherData>, private val viewModel: TodayViewModel) : RecyclerView.Adapter<ViewPagerAdapter2.ViewHolder>() {
    val current: String? = viewModel.getCurrentLocation()
    val lang:String = viewModel.getLang()
    val units:String = viewModel.getUnits()
    private var myList: List<CityWeatherData> = list
    private lateinit var dailyAdapter: DailyAdapter2
    private lateinit var hourlyAdapter: HourlyAdapter2
    @RequiresApi(Build.VERSION_CODES.O)
    val currentDateTime: LocalDateTime = LocalDateTime.now()
    private val gson = GsonBuilder().create()
    private val weatherDataConverter: Type = object : TypeToken<WeatherData>() {}.type

    private val isDarkMode:Boolean = isDarkModeOn(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_pager_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val city = myList[position]
        val weatherData = gson.fromJson<WeatherData>(city.weatherData,weatherDataConverter)
        holder.userId.text = city.cityName

        //daily & hourly recyclers
        val layoutManager = LinearLayoutManager(context)
        val layoutManager2 = LinearLayoutManager(context)
        layoutManager.orientation = HORIZONTAL
        layoutManager.initialPrefetchItemCount = weatherData.hourly.size
        val hourlyList = weatherData.hourly.filter { it.dt.toDouble() *1000 +3600000 > System.currentTimeMillis()}.subList(0,24)
        hourlyAdapter = HourlyAdapter2(hourlyList,context)
        holder.hourlyRecyclerView.layoutManager = layoutManager
        holder.hourlyRecyclerView.adapter = hourlyAdapter
        holder.hourlyRecyclerView.setHasFixedSize(true)
        layoutManager2.initialPrefetchItemCount = weatherData.daily.size
        dailyAdapter = DailyAdapter2(weatherData.daily,context)
        holder.dailyRecyclerView.layoutManager = layoutManager2
        holder.dailyRecyclerView.adapter = dailyAdapter
        holder.imgTempLottie.setAnimation(R.raw.temp2)

        //temp degree from hrs List
        holder.txtNowTemp.text = hourlyList[0].temp.toInt().toString()
        holder.txtFeelsLike.text = context.resources.getString(R.string.feelsLike) + hourlyList[0].feels_like.toInt().toString()
        holder.txtWeatherState.text = hourlyList[0].weather[0].description
        if (isDarkMode){
            holder.lottieIcon.setAnimation(setImgLottieDark(hourlyList[0].weather[0].icon))
        }else {
            holder.lottieIcon.setAnimation(setImgLottie(hourlyList[0].weather[0].icon))
        }

        //background
        val sunsetTime = weatherData.current.sunset.toDouble() *1000
        val sunriseTime = weatherData.current.sunrise.toDouble() *1000
        val currentTime = System.currentTimeMillis()
        Log.i("makingUI", "city ${city.cityName} sunset $sunsetTime current $currentTime ${sunriseTime<currentTime&&sunsetTime>currentTime}")
        if (sunriseTime<currentTime&&sunsetTime>currentTime){
            holder.pagerScrollView.setBackgroundResource(R.drawable.cloud_morning)
        }
        if(isDarkMode){
            holder.pagerScrollView.setBackgroundResource(R.drawable.darkmode_backgroud)
        }

        //units
        when (units) {
            "metric" -> {
                holder.txtDegree.text = context.resources.getString(R.string.c)
                holder.txtSpeed.text = context.resources.getString(R.string.m_s)
            }
            "standard" -> {
                holder.txtDegree.text = context.resources.getString(R.string.k)
                holder.txtSpeed.text = context.resources.getString(R.string.m_s)
            }
            else -> {
                holder.txtDegree.text = context.resources.getString(R.string.f)
                holder.txtSpeed.text = context.resources.getString(R.string.m_h)
            }
        }

        holder.txtDate.text = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            currentDateTime.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
        }else ({
            val sdf = SimpleDateFormat("MMM d, yyyy")
            val currentDate = sdf.format(Date())
        }).toString()

        //home icon
        if(current!=null){
            if(current == myList[position].cityName){
                holder.imgLocation.setImageResource(R.drawable.home_location)
            }else{
                holder.imgLocation.setImageResource(R.drawable.ic_fav_location)
            }
        }else{
            holder.imgLocation.setImageResource(R.drawable.ic_fav_location)
        }

    }


    override fun getItemCount(): Int {
        return myList.size
    }

    fun setList(list: List<CityWeatherData>) {
        myList = list
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val pagerScrollView: ScrollView = view.findViewById(R.id.pagerScrollView)
        val userId: TextView = view.findViewById(R.id.cityNameViewPager)
        val txtDegree: TextView = view.findViewById(R.id.txtDegree)
        val txtSpeed: TextView = view.findViewById(R.id.txtSpeed)
        val txtNowTemp: TextView = view.findViewById(R.id.txtNowTemp)
        val txtFeelsLike: TextView = view.findViewById(R.id.txtFeelsLikeTemp)
        val txtWeatherState: TextView = view.findViewById(R.id.txtWeatherState)
        val txtDate: TextView = view.findViewById(R.id.txtDate)
        val imgLocation:ImageView = view.findViewById(R.id.imgLocation)
        val dailyRecyclerView: RecyclerView = view.findViewById(R.id.DailyRecyclerView)
        val hourlyRecyclerView: RecyclerView = view.findViewById(R.id.HourlyRecyclerView)
        val lottieIcon: LottieAnimationView = view.findViewById(R.id.imgLottie)
        val imgTempLottie: LottieAnimationView = view.findViewById(R.id.imgTempLottie)
    }
}

fun isDarkModeOn(context: Context): Boolean {
    val currentNightMode = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
    return currentNightMode == Configuration.UI_MODE_NIGHT_YES
}

fun setImg(icon: String):Int{
    return when(icon){
        "01d" -> R.drawable.widget_sun
        "02d" -> R.drawable.widget_sun
        "03d" -> R.drawable.widget_sun_clouds
        "04d" -> R.drawable.widget_sun_clouds
        "09d" -> R.drawable.widget_rain
        "10d" -> R.drawable.widget_rain
        "11d" -> R.drawable.widget_thunderstorm
        "13d" -> R.drawable.widget_snow
        "50d" -> R.drawable.widget_sun_clouds
        "01n" -> R.drawable.widget_moon
        "02n" -> R.drawable.widget_moon
        "03n" -> R.drawable.widget_moon_clouds
        "04n" -> R.drawable.widget_moon_clouds
        "09n" -> R.drawable.widget_rain
        "10n" -> R.drawable.widget_rain
        "11n" -> R.drawable.widget_thunderstorm
        "13n" -> R.drawable.widget_snow
        "50n" -> R.drawable.widget_moon_clouds
        else -> R.drawable.widget_sun
    }
}

fun setImgLottie(icon: String):Int{
    return when(icon){
        "01d" -> R.raw.dd01
        "02d" -> R.raw.dd02
        "03d" -> R.raw.dd03
        "04d" -> R.raw.dd04
        "09d" -> R.raw.dd09
        "10d" -> R.raw.dd10
        "11d" -> R.raw.dd11
        "13d" -> R.raw.dd13
        "50d" -> R.raw.dd50
        "01n" -> R.raw.n01
        "02n" -> R.raw.n02
        "03n" -> R.raw.n03
        "04n" -> R.raw.n04
        "09n" -> R.raw.n09
        "10n" -> R.raw.n10
        "11n" -> R.raw.n11
        "13n" -> R.raw.n13
        "50n" -> R.raw.n50
        else -> R.raw.d01
    }
}

fun setImgLottieDark(icon: String):Int{
    return when(icon){
        "01d" -> R.raw.dd01
        "02d" -> R.raw.dd02
        "03d" -> R.raw.dd03
        "04d" -> R.raw.dd04
        "09d" -> R.raw.dd09
        "10d" -> R.raw.dd10
        "11d" -> R.raw.dd11
        "13d" -> R.raw.dd13
        "50d" -> R.raw.dd50
        "01n" -> R.raw.dn01
        "02n" -> R.raw.dn02
        "03n" -> R.raw.dn03
        "04n" -> R.raw.dn04
        "09n" -> R.raw.dn09
        "10n" -> R.raw.dn10
        "11n" -> R.raw.dn11
        "13n" -> R.raw.dn13
        "50n" -> R.raw.dn50
        else -> R.raw.dd01
    }
}

