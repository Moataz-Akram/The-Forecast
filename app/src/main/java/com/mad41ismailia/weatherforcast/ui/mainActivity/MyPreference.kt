package com.mad41ismailia.weatherforcast.ui.mainActivity

import android.content.Context
import com.mad41ismailia.weatherforcast.PREF_NAME

val PREFERENCE_LANGUAGE = "Language"

class MyPreference(context : Context){


    val preference = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE)

    fun getLoginCount() : String? {
        return preference.getString("lang","en")
    }

    fun setLoginCount(Language:String){
        val editor = preference.edit()
        editor.putString(PREFERENCE_LANGUAGE,Language)
        editor.apply()
    }

}