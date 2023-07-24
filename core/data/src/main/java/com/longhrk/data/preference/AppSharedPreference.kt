package com.longhrk.data.preference

import android.content.SharedPreferences
import com.longhrk.data.Const
import com.longhrk.data.Const.STATUS_SESSION
import com.longhrk.data.Const.TOKEN_FROM_FIREBASE
import javax.inject.Inject

class AppSharedPreference @Inject constructor(private val pref: SharedPreferences) {

    fun setToken(token: String) = pref.edit()
        .putString(TOKEN_FROM_FIREBASE, token)
        .apply()

    fun getToken() = pref.getString(TOKEN_FROM_FIREBASE, "") ?: ""

    fun setStatusSession(status: Boolean) = pref.edit()
        .putBoolean(STATUS_SESSION, status)
        .apply()
    fun getStatusSession() = pref.getBoolean(STATUS_SESSION, false)
}