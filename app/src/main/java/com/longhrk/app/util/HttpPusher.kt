package com.longhrk.app.util

import android.content.Context
import androidx.core.os.ConfigurationCompat
import com.longhrk.app.R
import com.longhrk.app.ui.extensions.getAppName
import org.matrix.android.sdk.api.session.Session
import org.matrix.android.sdk.api.session.pushers.HttpPusher
import java.util.Locale
import kotlin.math.abs

internal const val DEFAULT_PUSHER_FILE_TAG = "mobile"

suspend fun pushNewToken(
    currentSession: Session?,
    context: Context,
    pushKey: String,
    gateway: String
) {
    if (currentSession == null) return

    val pusher = createHttpPusher(context, pushKey, gateway, currentSession)
    if (pusher != null) {
        currentSession.pushersService().enqueueAddHttpPusher(pusher)
    }
}

suspend fun createHttpPusher(
    context: Context,
    pushKey: String,
    gateway: String,
    currentSession: Session
) = currentSession.sessionParams.deviceId?.let {
    HttpPusher(
        pushkey = pushKey,
        appId = context.getString(R.string.pusher_app_id),
        profileTag = DEFAULT_PUSHER_FILE_TAG + "_" + abs(currentSession.myUserId.hashCode()),
        lang = (ConfigurationCompat.getLocales(context.resources.configuration).get(0)
            ?: Locale.getDefault()).language,
        appDisplayName = context.getAppName(),
        deviceDisplayName = currentSession.cryptoService().getMyDevice().displayName()
            .orEmpty(),
        url = gateway,
        enabled = true,
        deviceId = it,
        append = false,
        withEventIdOnly = true,
    )
}