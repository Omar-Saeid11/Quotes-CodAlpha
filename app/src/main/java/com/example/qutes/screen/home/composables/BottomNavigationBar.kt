package com.example.qutes.screen.home.composables

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.navigation.NavController

@Composable
fun BottomNavigationBar(navController: NavController) {
    BottomNavigation(
        backgroundColor = Color.White,
        contentColor = Color.Black
    ) {
        BottomNavigationItem(
            icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Home") },
            selected = true,
            onClick = { navController.navigate("home") },
            label = { Text("Home") },
            alwaysShowLabel = true
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    painter = rememberVectorPainter(Icons.Outlined.FavoriteBorder),
                    contentDescription = "Saved Quotes"
                )
            },
            selected = false,
            onClick = { navController.navigate("savedScreen") },
            label = { Text("Saved") },
            alwaysShowLabel = true
        )
    }
}
