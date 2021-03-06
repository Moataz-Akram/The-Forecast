package com.mad41ismailia.weatherforcast.ui.fragments.today

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mad41ismailia.weatherforcast.R
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.DailyDatabase
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.HourlyDatabase
import java.text.SimpleDateFormat
import java.util.*

class HourlyAdapter(private val list:List<HourlyDatabase>) : RecyclerView.Adapter<HourlyAdapter.ViewHolder>() {

    private var myList: List<HourlyDatabase> = list

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
        holder.txtHourTemp.text = task.temp.toString()
    }

    override fun getItemCount(): Int {
        return myList.size
    }

    fun setList(list: List<HourlyDatabase>) {
        myList = list
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtHourTemp: TextView = view.findViewById(R.id.txtHourTemp)
        val txtHour: TextView = view.findViewById(R.id.txtHour)
//        val card: CardView = view.findViewById(R.id.myCard)
    }

}
