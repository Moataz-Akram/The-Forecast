package com.mad41ismailia.weatherforcast.ui.fragments.today.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.mad41ismailia.weatherforcast.R
import com.mad41ismailia.weatherforcast.entity.comingData.Daily
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
class DailyAdapter2(private val list: List<Daily>,val context: Context) :
    RecyclerView.Adapter<DailyAdapter2.ViewHolder>() {

    val dateFormat = SimpleDateFormat("MMM d, yyyy")
    val sunRiseSetFormat = SimpleDateFormat("hh:mm a")
    val res = context.resources
    private var myList: List<Daily> = list
    private val isDarkMode:Boolean = isDarkModeOn(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.daily_card, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("LogNotTimber", "SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val day = myList[position]
        val calender = Calendar.getInstance()
        calender.timeInMillis = (day.dt?.toLong() ?: 10) * 1000L
        holder.txtDayName.text = dateFormat.format(calender.time)

        calender.timeInMillis = (day.sunrise?.toLong() ?: 10) * 1000L
        holder.txtSunRise.text = sunRiseSetFormat.format(calender.time)

        calender.timeInMillis = (day.sunset?.toLong() ?: 10) * 1000L
        holder.txtSunset.text = sunRiseSetFormat.format(calender.time)

//        holder.txtDayName.text = task.dt.toString()
        holder.txtDayTemp.text = day.temp.day.toInt().toString()
        holder.txtNightTemp.text = day.temp.night.toInt().toString()
        holder.txtWind.text = day.wind_speed.toInt().toString()
        holder.txtHumidity.text =day.humidity.toString()
        holder.txtClouds.text = day.clouds.toString()
        holder.iconId.text = day.weather[0].description
        if (isDarkMode){
            holder.lottieIcon.setAnimation(setImgLottieDark(day.weather[0].icon))
        }else {
            holder.lottieIcon.setAnimation(setImgLottie(day.weather[0].icon))
        }

        holder.lottieIconClouds.setAnimation(R.raw.d04)
        holder.imgLottieHumidity.setAnimation(R.raw.humidity_icon)
        holder.expand.setOnClickListener {
            holder.expandableLayout.visibility = if (holder.expandableLayout.visibility == GONE) { View.VISIBLE } else { View.GONE }
        }
    }

    override fun getItemCount(): Int {
        return myList.size
    }

    fun setList(list: List<Daily>) {
        myList = list
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtDayName: TextView = view.findViewById(R.id.txtDayName)
        val txtSunRise: TextView = view.findViewById(R.id.txtSunRise)
        val txtSunset: TextView = view.findViewById(R.id.txtSunset)
        val txtDayTemp: TextView = view.findViewById(R.id.txtDayTemp)
        val txtNightTemp: TextView = view.findViewById(R.id.txtNightTemp)
        val txtWind: TextView = view.findViewById(R.id.txtWind)
        val txtClouds: TextView = view.findViewById(R.id.txtClouds)
        val txtHumidity: TextView = view.findViewById(R.id.txtHumidty)
        val lottieIcon: LottieAnimationView = view.findViewById(R.id.imgLottie)
        val lottieIconClouds: LottieAnimationView = view.findViewById(R.id.imgLottieClouds)
        val imgLottieHumidity: LottieAnimationView = view.findViewById(R.id.imgLottieHumidity)
        val iconId: TextView = view.findViewById(R.id.txtDailyDescription)
        val expandableLayout: ConstraintLayout = view.findViewById(R.id.expandableLayout)
        val expand: ConstraintLayout = view.findViewById(R.id.expand)
        val card: CardView = view.findViewById(R.id.dailyCard)
    }
}
