package com.mad41ismailia.weatherforcast.ui.fragments.today

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.mad41ismailia.weatherforcast.R
import com.mad41ismailia.weatherforcast.entity.comingData.Hourly
import java.text.SimpleDateFormat
import java.util.*

class HourlyAdapter2(private val list:List<Hourly>) : RecyclerView.Adapter<HourlyAdapter2.ViewHolder>() {

    private var myList: List<Hourly> = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hourly_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = myList[position]
        val calender = Calendar.getInstance()
        calender.timeInMillis = (task.dt?.toLong() ?: 10)*1000L
        val dateFormat = SimpleDateFormat("hh:mm aa");
        holder.txtHour.text = dateFormat.format(calender.time)
        holder.txtHourTemp.text = task.temp.toInt().toString() + "°"
        holder.lottieIcon.setAnimation(setImgLottie(task.weather[0].icon))
    }

    override fun getItemCount(): Int {
        return myList.size
    }

    fun setList(list: List<Hourly>) {
        myList = list
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtHourTemp: TextView = view.findViewById(R.id.txtHourTemp)
        val txtHour: TextView = view.findViewById(R.id.txtHour)
        val lottieIcon: LottieAnimationView = view.findViewById(R.id.imgLottie)

//        val card: CardView = view.findViewById(R.id.myCard)
    }

}
