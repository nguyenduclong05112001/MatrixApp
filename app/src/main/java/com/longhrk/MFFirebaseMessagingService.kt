package com.longhrk

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.longhrk.matrix.viewmodel.MatrixViewModel
import javax.inject.Inject

class MFFirebaseMessagingService @Inject constructor(
    private val matrixViewModel: MatrixViewModel
) : FirebaseMessagingService(){

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
    }
}