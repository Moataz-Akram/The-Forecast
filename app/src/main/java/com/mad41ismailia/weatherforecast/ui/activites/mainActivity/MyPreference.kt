package com.mad41ismailia.weatherforecast.ui.activites.mainActivity

import android.content.Context
import com.mad41ismailia.weatherforecast.LANGUAGE
import com.mad41ismailia.weatherforecast.PREF_NAME

const val PREFERENCE_LANGUAGE = "Language"

class MyPreference(context : Context){


    private val preference = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE)

    fun getLoginCount() : String? {
        return preference.getString(LANGUAGE,"en")
    }

    fun setLoginCount(Language:String){
        val editor = preference.edit()
        editor.putString(PREFERENCE_LANGUAGE,Language)
        editor.apply()
    }

}