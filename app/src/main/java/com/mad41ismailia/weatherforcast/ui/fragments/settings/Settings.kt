package com.mad41ismailia.weatherforcast.ui.fragments.settings

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.preference.EditTextPreference
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.mad41ismailia.weatherforcast.PREF_NAME
import com.mad41ismailia.weatherforcast.R
import com.mad41ismailia.weatherforcast.repo.Repository
import com.mad41ismailia.weatherforcast.ui.mainActivity.MainActivity
import com.mad41ismailia.weatherforcast.ui.mainActivity.MyPreference

class Settings : PreferenceFragmentCompat() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor:SharedPreferences.Editor
    lateinit var myPreference: MyPreference

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        myPreference = MyPreference(requireActivity())
    }

    @SuppressLint("LogNotTimber")// for the log print
    override fun onPause() {
        val lang: ListPreference? = findPreference("lang")
        val units: ListPreference? = findPreference("units")
        val oldLang = Repository.getRepoObject().getLang()
        lang?.value?.let { Repository.getRepoObject().setLang(it) }
        units?.value?.let { Repository.getRepoObject().setUnits(it) }
        lang?.value?.let { myPreference.setLoginCount(it) }
        val langString = lang?.value.toString()
        if(langString!=oldLang){
            Log.i("languageRestart inside","not equals old lang is '$oldLang' new lang is '${lang?.value}'")
            requireActivity().recreate()
        }else{
            Log.i("languageRestart outside","equals old lang is '$oldLang' new lang is '${lang?.value}'")
        }
        super.onPause()
    }
}