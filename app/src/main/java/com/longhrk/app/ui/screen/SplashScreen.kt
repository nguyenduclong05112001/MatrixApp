package com.longhrk.app.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.longhrk.app.ui.theme.blue
import com.longhrk.matrix.viewmodel.MatrixViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onHomeScreen: () -> Unit,
    onLoginScreen: () -> Unit,
    matrixViewModel: MatrixViewModel
) {
    val currentSession by matrixViewModel.currentSession.collectAsState()

    LaunchedEffect(Unit) {
        delay(2000L)
        if (currentSession == null) onLoginScreen() else onHomeScreen()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(blue)
    )
}