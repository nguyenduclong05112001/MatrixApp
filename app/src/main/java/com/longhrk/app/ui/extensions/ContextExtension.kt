package com.longhrk.app.ui.extensions

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import org.matrix.android.sdk.api.util.getApplicationInfoCompat

fun Context.getAppName(): String {
    return try {
        val appPackageName = this.applicationContext.packageName
        var appName = this.getApplicationLabel(appPackageName)

        // Use appPackageName instead of appName if appName contains any non-ASCII character
        if (!appName.matches("\\A\\p{ASCII}*\\z".toRegex())) {
            appName = appPackageName
        }
        appName
    } catch (e: Exception) {
        Log.e("Exception", "## AppNameProvider() : failed")
        "ElementAndroid"
    }
}

fun Context.getApplicationLabel(packageName: String): String {
    return try {
        val ai = packageManager.getApplicationInfoCompat(packageName, 0)
        packageManager.getApplicationLabel(ai).toString()
    } catch (e: PackageManager.NameNotFoundException) {
        packageName
    }
}