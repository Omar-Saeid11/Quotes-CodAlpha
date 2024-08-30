package com.example.qutes

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.qutes.screen.onBoarding.OnBoarding
import com.example.qutes.screen.home.MainScreen
import com.example.qutes.screen.home.SavedScreen

@Composable
fun QuotesNav() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "onBoarding") {
        composable("onBoarding") {
            OnBoarding(navController = navController)
        }
        composable("home") {
            MainScreen(navController = navController)
        }

        composable("savedScreen") {
            SavedScreen(navController = navController)
        }
    }
}