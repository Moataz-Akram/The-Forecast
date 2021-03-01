package com.mad41ismailia.weatherforcast.ui.fragments.settings

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.preference.EditTextPreference
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.mad41ismailia.weatherforcast.R

class Settings : PreferenceFragmentCompat() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor:SharedPreferences.Editor

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    override fun onPause() {
        super.onPause()
        val lang: ListPreference? = findPreference("lang")
        val units: ListPreference? = findPreference("units")
        editor.putString("lang",lang?.value)
        editor.putString("units",units?.value)
        editor.commit()
        Log.i("comingdata ","setting saved lang = "+lang?.value)
    }
}