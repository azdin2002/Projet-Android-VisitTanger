package com.groupe10.visittanger.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Map : Screen("map")
    object Itinerary : Screen("itinerary")
    object Favorites : Screen("favorites")
    object Profile : Screen("profile")
    object Login : Screen("login")
    object Register : Screen("register")
}