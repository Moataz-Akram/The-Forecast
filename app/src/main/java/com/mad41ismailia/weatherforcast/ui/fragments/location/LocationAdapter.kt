package com.mad41ismailia.weatherforcast.ui.fragments.location

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mad41ismailia.weatherforcast.R
import java.util.ArrayList

class LocationAdapter(private var myList: ArrayList<String?>) : RecyclerView.Adapter<LocationAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.location_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val location = myList[position]
        holder.userId.text = location
        holder.card.setOnClickListener(View.OnClickListener {

        })
    }

    override fun getItemCount(): Int {
        return myList.size
    }

    fun setList(list: ArrayList<String?>) {
        myList = list
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userId: TextView = view.findViewById(R.id.cityName)
        val card: CardView = view.findViewById(R.id.location_card)
    }

}
