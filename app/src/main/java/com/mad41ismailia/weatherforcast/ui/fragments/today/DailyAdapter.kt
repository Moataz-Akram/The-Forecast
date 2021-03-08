package com.mad41ismailia.weatherforcast.ui.fragments.today

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mad41ismailia.weatherforcast.R
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.DailyDatabase
import java.text.SimpleDateFormat
import java.util.*

class DailyAdapter(private val list:List<DailyDatabase>) : RecyclerView.Adapter<DailyAdapter.ViewHolder>() {

    private var myList: List<DailyDatabase> = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.daily_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = myList[position]
        val calender = Calendar.getInstance()
        calender.timeInMillis = (task.dt?.toLong() ?: 10)*1000L
        val dateFormat = SimpleDateFormat("dd-MM-yyyy");
        holder.txtDayName.text = dateFormat.format(calender.time)

//        holder.txtDayName.text = task.dt.toString()
        holder.txtDayTemp.text = task.temp.day.toString()
        holder.txtNightTemp.text = task.temp.night.toString()
        holder.txtMaxTemp.text = task.temp.max.toString()
        holder.txtMinTemp.text = task.temp.min.toString()
    }

    override fun getItemCount(): Int {
        return myList.size
    }

    fun setList(list: List<DailyDatabase>) {
        myList = list
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtDayName: TextView = view.findViewById(R.id.txtDayName)
        val txtDayTemp: TextView = view.findViewById(R.id.txtDayTemp)
        val txtNightTemp: TextView = view.findViewById(R.id.txtNightTemp)
        val txtMaxTemp: TextView = view.findViewById(R.id.txtHumidty)
        val txtMinTemp: TextView = view.findViewById(R.id.txtWind)
//        val card: CardView = view.findViewById(R.id.myCard)
    }

}
