package com.playzelo.highstakesmodule.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Entry : Screen("entry")
    object Game : Screen("game")
}