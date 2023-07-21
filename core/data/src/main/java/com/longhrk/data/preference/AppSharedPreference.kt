package com.longhrk.data.preference

import android.content.SharedPreferences
import javax.inject.Inject

class AppSharedPreference @Inject constructor(private val pref: SharedPreferences) {

    fun setIntPreference(value: Int) {
        pref.edit()
            .putInt("IntData", value)
            .apply()
    }

    fun getIntPreference(): Int {
        return pref.getInt("IntData", 0)
    }
}