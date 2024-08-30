package com.example.qutes.screen.onBoarding

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.qutes.R

@Composable
fun OnBoarding(navController: NavController) {
    val onboardingPages = listOf(
        OnboardingPage(
            title = "Welcome to Quotoday",
            description = "Personalized daily quotes for a better day.",
            imageResId = R.drawable.on_boarding1
        ),
        OnboardingPage(
            title = "Discover New Inspiration",
            description = "Find quotes that match your mood and preferences.",
            imageResId = R.drawable.on_boarding2
        ),
        OnboardingPage(
            title = "Stay Motivated",
            description = "Receive daily reminders to keep you on track.",
            imageResId = R.drawable.on_boarding3
        )
    )
    OnboardingPager(pages = onboardingPages) {
        navController.navigate("home")
    }
}