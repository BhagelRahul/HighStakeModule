package com.playzelo.highstakesdice.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.playzelo.highstakesdice.screens.EntryScreen
import com.playzelo.highstakesdice.screens.GameScreen
import com.playzelo.highstakesdice.screens.SplashScreen


@Composable
fun AppNavHost() {
   val navController = rememberNavController()

        NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) { SplashScreen(navController) }
        composable(Screen.Entry.route) { EntryScreen(navController) }
        composable(Screen.Game.route) { GameScreen(navController) }
    }
}
