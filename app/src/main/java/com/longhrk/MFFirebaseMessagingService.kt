package com.longhrk

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.MutableLiveData
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.longhrk.app.MainActivity
import com.longhrk.app.R
import com.longhrk.app.util.pushNewToken
import com.longhrk.matrix.viewmodel.MatrixViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

object EventFromService {
    val serviceEvent: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
}

@AndroidEntryPoint
class MFFirebaseMessagingService : FirebaseMessagingService() {
    init {
        Log.d("sdadsada", "MFFirebaseMessagingService: ")
    }

    @Inject
    lateinit var matrixViewModel: MatrixViewModel

    private val scope = CoroutineScope(SupervisorJob())

    private val currentSession = matrixViewModel.currentSession.value

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d("sdadsada", "onMessageReceived: ")
        currentSession?.syncService()?.requireBackgroundSync()
        sendNotification(currentSession?.myUserId.toString())
    }


    override fun onNewToken(token: String) {
        Log.d("sdadsada", "onNewToken: ")
        super.onNewToken(token)

        if (currentSession == null && !matrixViewModel.getStatusSession()) {
            matrixViewModel.setToken(token)
        } else {
            val context = applicationContext
            scope.launch {
                pushNewToken(currentSession, context, token, getString(R.string.pusher_http_url))
            }
        }
    }

    private fun sendNotification(messageBody: String?) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )
        val channelId = "CHANNEL1"
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, channelId)
                .setSmallIcon(android.R.drawable.ic_delete)
                .setLargeIcon(
                    BitmapFactory.decodeResource(
                        resources,
                        android.R.drawable.ic_input_add
                    )
                )
                .setContentTitle("Title")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(NotificationManager.IMPORTANCE_HIGH)
                .addAction(
                    NotificationCompat.Action(
                        android.R.drawable.sym_call_missed,
                        "Cancel",
                        PendingIntent.getActivity(
                            this,
                            0,
                            intent,
                            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
                        )
                    )
                )
                .addAction(
                    NotificationCompat.Action(
                        android.R.drawable.sym_call_outgoing,
                        "OK",
                        PendingIntent.getActivity(
                            this,
                            0,
                            intent,
                            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
                        )
                    )
                )
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            channelId,
            "Channel human readable title",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)
        notificationManager.notify(0, notificationBuilder.build())
    }
}
