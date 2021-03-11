package com.mad41ismailia.weatherforcast.ui.widget

import android.annotation.SuppressLint
import android.app.Application
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.util.Log
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
}

@SuppressLint("LogNotTimber")
internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val widgetText = context.getString(R.string.appwidget_text)
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.weather_widget)
    views.setTextViewText(R.id.widgetTemperature, widgetText)
    views.setImageViewResource(R.id.imageView,R.drawable.notification_icon)

    if (!Repository.isCreated()) {
        Repository.createObject(context.applicationContext as Application)
    }
    val repo = Repository.getRepoObject()
    Log.i("weatherWidget","current repo $repo")

    runBlocking {
        val currentLocation = repo.getCurrentLocation()
        Log.i("weatherWidget","current location $currentLocation")
        if (currentLocation!=null){
            val  curretData = repo.getCurrentData()
            Log.i("weatherWidget","current data $curretData")
            views.setTextViewText(R.id.widgetCityName, currentLocation)
            views.setTextViewText(R.id.widgetCityName3, currentLocation)

            val imgIcon = curretData?.current?.weather?.get(0)?.icon
            val imgSource = setImg(imgIcon!!)
            val currentTemperature = curretData.current.temp
            views.setImageViewResource(R.id.imageView,R.drawable.notification_icon)
            views.setImageViewResource(R.id.imageView2,R.drawable.notification_icon)
            views.setImageViewResource(R.id.imageView3,R.drawable.home_location)

            views.setTextViewText(R.id.widgetTemperature, currentTemperature.toInt().toString())
            views.setTextViewText(R.id.widgetTemperature2, currentTemperature.toInt().toString())
            views.setTextViewText(R.id.widgetWeatherState, curretData.current.weather[0].main)
            views.setTextViewText(R.id.widgetWeatherState2, curretData.current.weather[0].main)

            views.setTextViewText(R.id.widgetTempFeels, curretData.current.feels_like.toInt().toString())
            views.setTextViewText(R.id.widgetTempFeels2, curretData.current.feels_like.toInt().toString())


        }
    }


    Toast.makeText(context,"hello",Toast.LENGTH_LONG).show()
    appWidgetManager.updateAppWidget(appWidgetId, views)
}