package com.playzelo.highstakesmodule.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.playzelo.highstakesmodule.GameDiceModel
import com.playzelo.highstakesmodule.components.SoundManager
import com.playzelo.highstakesmodule.screens.EntryScreen
import com.playzelo.highstakesmodule.screens.GameScreen
import com.playzelo.highstakesmodule.screens.SplashScreen


//@Composable
//fun AppNavHost() {
//    val navController = rememberNavController()
//
//    NavHost(
//        navController = navController,
//        startDestination = Screen.Splash.route
//    ) {
//        composable(Screen.Splash.route) { SplashScreen(navController) }
//        composable(Screen.Entry.route) { EntryScreen(navController) }
//        composable(Screen.Game.route) { GameScreen() }
//    }
//}

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) { SplashScreen(navController) }
        composable(Screen.Entry.route) { EntryScreen(navController) }
        composable(Screen.Game.route){ GameScreen(navController)
        }
    }
}
