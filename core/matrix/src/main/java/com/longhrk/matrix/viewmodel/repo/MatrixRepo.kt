package com.longhrk.matrix.viewmodel.repo

import android.content.Context
import android.net.Uri
import android.widget.Toast
import org.matrix.android.sdk.api.Matrix
import org.matrix.android.sdk.api.auth.data.HomeServerConnectionConfig
import org.matrix.android.sdk.api.session.Session
import javax.inject.Inject

class MatrixRepo @Inject constructor(private val matrix: Matrix) {

    fun getCurrentSession(): Session? {
        val session = matrix.authenticationService().getLastAuthenticatedSession()
        if (session != null){
            session.open()
            session.syncService().startSync(true)
        }
        return session
    }

    suspend fun launchAuthProcess(
        username: String,
        password: String,
        homeServer: String,
        context: Context
    ): Session? {
        val homeServerConnectionConfig =
            try {
                HomeServerConnectionConfig
                    .Builder()
                    .withHomeServerUri(Uri.parse(homeServer))
                    .build()
            } catch (failure: Throwable) {
                Toast.makeText(context, "Home server is not valid", Toast.LENGTH_SHORT).show()
                return null
            }

        try {
            matrix.authenticationService().directAuthentication(
                homeServerConnectionConfig,
                username,
                password,
                "matrix-sdk-android2-sample"
            )
        } catch (failure: Throwable) {
            Toast.makeText(context, "Failure: $failure", Toast.LENGTH_SHORT).show()
            return null
        }.let { session ->
            Toast.makeText(
                context,
                "Welcome ${session.myUserId}",
                Toast.LENGTH_SHORT
            ).show()
            session.open()
            session.syncService().startSync(true)
            return session
        }
    }
}