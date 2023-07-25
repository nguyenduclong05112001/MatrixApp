package com.longhrk

import android.app.Application
import android.util.Log
import com.longhrk.app.R
import com.longhrk.app.util.pushNewToken
import com.longhrk.matrix.viewmodel.MatrixViewModel
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class MFApplication : Application() {
    @Inject
    lateinit var matrixViewModel: MatrixViewModel

    private val scope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        matrixViewModel.getSession()
        val token = matrixViewModel.getToken()
        val currentSession = matrixViewModel.currentSession.value
        if (currentSession != null &&
            !matrixViewModel.getStatusSession() &&
            token.isNotEmpty()
        ) {
            matrixViewModel.setStatusSession(true)
            scope.launch {
                pushNewToken(
                    currentSession,
                    this@MFApplication,
                    token,
                    getString(R.string.pusher_http_url)
                )
            }
        }
    }
}