package com.mad41ismailia.weatherforcast.ui.fragments.alarm

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mad41ismailia.weatherforcast.R
import com.mad41ismailia.weatherforcast.entity.DatabaseClasses.AlarmData

class AlarmDataAdapter(private val viewModel: AlarmFragmentViewModel, list: List<AlarmData>,private val context: Context) :
    RecyclerView.Adapter<AlarmDataAdapter.ViewHolder>() {

    private var myList: List<AlarmData> = list
    private val units = viewModel.getUnits()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.alarm_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val alarm = myList[position]
        holder.txtAlarmCondition.text = alarm.condition
        holder.txtAlarmValue.text = alarm.value.toString()
        holder.txtAlarmUnits.text = alarm.units
        holder.txtAlarmTime.text = alarm.time
        holder.imgDeleteAlarm.setOnClickListener(View.OnClickListener {
            val builder = android.app.AlertDialog.Builder(context)
            builder.setMessage(R.string.deleteAlarm)
                .setPositiveButton(R.string.yes
                ) { _, _ -> viewModel.deleteAlarm(alarm.uniqueID,context) }
                .setNegativeButton(R.string.no
                ) { _, _ -> }
            var alert: AlertDialog = builder.create()
            alert.show()
        })
    }

    override fun getItemCount(): Int {
        return myList.size
    }

    fun setList(list: List<AlarmData>) {
        myList = list
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtAlarmCondition: TextView = view.findViewById(R.id.txtAlarmCondition)
        val txtAlarmValue: TextView = view.findViewById(R.id.txtAlarmValue)
        val txtAlarmUnits: TextView = view.findViewById(R.id.txtAlarmUnits)
        val txtAlarmTime: TextView = view.findViewById(R.id.txtAlarmTime)
        val imgDeleteAlarm: ImageView = view.findViewById(R.id.imgDeleteAlarm)
    }

}





