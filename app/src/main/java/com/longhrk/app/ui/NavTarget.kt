package com.longhrk.app.ui

sealed class NavTarget(val route: String) {

    object Splash : NavTarget("splash")
    object Home : NavTarget("home")
    object Auth : NavTarget("auth")

    override fun toString(): String {
        return route
    }
}