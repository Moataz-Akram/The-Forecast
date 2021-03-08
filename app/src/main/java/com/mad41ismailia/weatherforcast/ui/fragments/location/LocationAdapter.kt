package com.mad41ismailia.weatherforcast.ui.fragments.location

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mad41ismailia.weatherforcast.R
import java.util.ArrayList

class LocationAdapter(private var myList: ArrayList<String?>,private val viewModel: LocationViewModel) : RecyclerView.Adapter<LocationAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.location_card, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("LogNotTimber")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val location = myList[position]
        holder.userId.text = location
        holder.card.setOnClickListener(View.OnClickListener {

        })
        val current = viewModel.getCurrentLocationStandAlone()
        if(current!=null){
            if(current == myList[position]){
                holder.deleteLocation.visibility = GONE
                holder.myLocation.setImageResource(R.drawable.home_location)
            }
        }
        holder.deleteLocation.setOnClickListener(View.OnClickListener {
            Log.i("deletecity", "inside adapter ${myList[position]} from $myList")
            viewModel.deleteCity(myList[position]!!)
            myList.removeAt(position)
            notifyDataSetChanged()
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
        val myLocation:ImageView = view.findViewById(R.id.imgMyLocation)
        val deleteLocation:ImageView = view.findViewById(R.id.imgDeleteLocation)
    }

}
