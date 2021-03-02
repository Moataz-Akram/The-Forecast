package com.mad41ismailia.weatherforcast.ui.fragments.settings

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.preference.EditTextPreference
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.mad41ismailia.weatherforcast.PREF_NAME
import com.mad41ismailia.weatherforcast.R
import com.mad41ismailia.weatherforcast.repo.Repository
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
        myPreference = MyPreference(requireContext())
    }

    override fun onPause() {
        val lang: ListPreference? = findPreference("lang")
        val units: ListPreference? = findPreference("units")
        lang?.value?.let { Repository.getRepoObject().setLang(it) }
        units?.value?.let { Repository.getRepoObject().setUnits(it) }

//        val oldLang = sharedPreferences.getString("lang","en")
//        if(lang?.value.equals(oldLang)){
//            editor.putBoolean("restartactivity",false)
//        }else{editor.putBoolean("restartactivity",true)}

        super.onPause()

    }
}