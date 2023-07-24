package com.longhrk.app.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.longhrk.app.MainActivity
import com.longhrk.app.R
import com.longhrk.app.ui.event.NavEvent
import com.longhrk.app.ui.screen.HomeScreen
import com.longhrk.app.ui.screen.SplashScreen
import com.longhrk.app.ui.screen.login.AuthScreen
import com.longhrk.app.util.pushNewToken
import com.longhrk.matrix.viewmodel.MatrixViewModel

@Composable
fun NavGraph(eventHandler: EventHandler, navController: NavHostController) {
    val startDestination = NavTarget.Splash.route
    val activity = LocalContext.current as MainActivity

    val matrixViewModel = hiltViewModel<MatrixViewModel>()

    LaunchedEffect(Unit) {
        Log.d("sdadsada", "token: ${matrixViewModel.getToken()}")
    }

    NavHost(navController, startDestination) {
        composable(NavTarget.Splash.route) {
            SplashScreen(
                onHomeScreen = {
                    eventHandler.postNavEvent(
                        event = NavEvent.ActionWithPopUp(
                            target = NavTarget.Home,
                            popupTarget = NavTarget.Splash,
                            inclusive = true
                        )
                    )
                },
                onLoginScreen = {
                    eventHandler.postNavEvent(
                        event = NavEvent.ActionWithPopUp(
                            target = NavTarget.Auth,
                            popupTarget = NavTarget.Splash,
                            inclusive = true
                        )
                    )
                },
                matrixViewModel = matrixViewModel
            )
        }

        composable(NavTarget.Auth.route) {
            AuthScreen(
                matrixViewModel = matrixViewModel,
                onHomeScreen = {
                    eventHandler.postNavEvent(
                        event = NavEvent.ActionWithPopUp(
                            target = NavTarget.Home,
                            popupTarget = NavTarget.Auth,
                            inclusive = true
                        )
                    )
                }
            )
        }

        composable(NavTarget.Home.route) {
            HomeScreen(
                onNextScreen = {

                },
                onBackPress = {
                    activity.finish()
                }
            )
        }
    }
}