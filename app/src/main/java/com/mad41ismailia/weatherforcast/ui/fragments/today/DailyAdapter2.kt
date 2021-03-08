package com.mad41ismailia.weatherforcast.ui.fragments.today

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.mad41ismailia.weatherforcast.R
import com.mad41ismailia.weatherforcast.entity.comingData.Daily
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class DailyAdapter2(private val list: List<Daily>,val context: Context) :
    RecyclerView.Adapter<DailyAdapter2.ViewHolder>() {

    val dateFormat = SimpleDateFormat("dd-MM-yyyy")
    val sunRiseSetFormat = SimpleDateFormat("HH:mm a")

    private var myList: List<Daily> = list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.daily_card, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("LogNotTimber", "SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = myList[position]
        val calender = Calendar.getInstance()
        calender.timeInMillis = (task.dt?.toLong() ?: 10) * 1000L
        holder.txtDayName.text = dateFormat.format(calender.time)

        calender.timeInMillis = (task.sunrise*1000).toLong()
        holder.txtsunrise.text = sunRiseSetFormat.format(calender.time)

//        holder.txtDayName.text = task.dt.toString()
        holder.txtDayTemp.text = task.temp.day.toInt().toString()
        holder.txtNightTemp.text = task.temp.night.toInt().toString()
        holder.txtWind.text = context.resources.getString(R.string.wind) + task.wind_speed.toInt().toString()
        holder.txtHumidity.text =context.resources.getString(R.string.humidity) + task.humidity.toString()
        holder.txtClouds.text =context.resources.getString(R.string.clouds) + task.clouds.toString()
        holder.txtPressure.text = task.pressure.toString()
        holder.iconId.text = task.weather[0].description
        holder.lottieIcon.setAnimation(setImgLottie(task.weather[0].icon))
        holder.lottieIconClouds.setAnimation(R.raw.d04)
        holder.imgLottieHumidity.setAnimation(R.raw.humidity_icon)
        holder.expand.setOnClickListener {
            holder.expandableLayout.visibility = if (holder.expandableLayout.visibility == GONE) {
                                                    View.VISIBLE
                                                } else {
                                                    View.GONE
                                                }
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
        val txtsunrise: TextView = view.findViewById(R.id.txtsunrise)
        val txtDayTemp: TextView = view.findViewById(R.id.txtDayTemp)
        val txtNightTemp: TextView = view.findViewById(R.id.txtNightTemp)
        val txtWind: TextView = view.findViewById(R.id.txtWind)
        val txtClouds: TextView = view.findViewById(R.id.txtClouds)
        val txtPressure: TextView = view.findViewById(R.id.txtsunrise)
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
