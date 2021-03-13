package com.mad41ismailia.weatherforcast.ui.widget

import android.annotation.SuppressLint
import android.app.Application
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.RemoteViews
import android.widget.Toast
import com.mad41ismailia.weatherforcast.R
import com.mad41ismailia.weatherforcast.repo.Repository
import com.mad41ismailia.weatherforcast.ui.fragments.today.adapters.setImg
import kotlinx.coroutines.runBlocking

/**
 * Implementation of App Widget functionality.
 */
class WeatherWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them

        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
    }

    override fun onAppWidgetOptionsChanged(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int,
        newOptions: Bundle?
    ) {
        updateAppWidget(context, appWidgetManager, appWidgetId)
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
    }


    companion object{
        @SuppressLint("LogNotTimber")
        internal fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.weather_widget)

            if (!Repository.isCreated()) {
                Repository.createObject(context.applicationContext as Application)
            }
            val repo = Repository.getRepoObject()
            repo.saveAppWidgetId(appWidgetId)
            Log.i("weatherWidget","current repo $repo")
            runBlocking {
                val currentLocation = repo.getCurrentLocation()
                Log.i("weatherWidget","current location $currentLocation")
                Log.i("weatherWidget","widget id $appWidgetId")
                if (currentLocation!=null){
                    Log.i("alarmalarm", "from widget")
                    val  curretData = repo.getCurrentData()
                    Log.i("weatherWidget","current data $curretData")
                    views.setTextViewText(R.id.widgetCityName3, currentLocation)

                    val imgIcon = curretData?.current?.weather?.get(0)?.icon
                    val imgSource = setImg(imgIcon!!)
                    val currentTemperature = curretData.current.temp
                    views.setImageViewResource(R.id.imageView,R.drawable.notification_icon)
                    views.setImageViewResource(R.id.imageView2,imgSource)

                    val hourlyList = curretData.hourly.filter {  it.dt.toDouble() *1000 +3600000> System.currentTimeMillis() }

                    views.setTextViewText(R.id.widgetTemperature2, hourlyList[0].temp.toInt().toString())
                    views.setTextViewText(R.id.widgetWeatherState2, hourlyList[0].weather[0].main)

                    views.setTextViewText(R.id.widgetTempFeels2, hourlyList[0].feels_like.toInt().toString())
                    Log.i("weatherWidget","reached final ")
                    appWidgetManager.updateAppWidget(appWidgetId, views)
                }else{
                    views.setViewVisibility(R.id.widgetDetails,GONE)
                    views.setViewVisibility(R.id.widgetMessage, VISIBLE)
                }
            }
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}

