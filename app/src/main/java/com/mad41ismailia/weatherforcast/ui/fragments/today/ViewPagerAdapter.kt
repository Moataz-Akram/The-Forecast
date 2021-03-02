package com.mad41ismailia.weatherforcast.ui.fragments.today

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mad41ismailia.weatherforcast.R

class ViewPagerAdapter(list:List<String?>) : RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {
    private var myList: List<String?> = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_pager_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val city = myList[position]
        holder.userId.text = city
    }

    override fun getItemCount(): Int {
        return myList.size
    }

    fun setList(list: List<String?>) {
        myList = list
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userId: TextView = view.findViewById(R.id.cityNameViewPager)
    }

}
