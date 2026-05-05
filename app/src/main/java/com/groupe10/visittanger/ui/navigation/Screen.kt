package com.groupe10.visittanger.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Map : Screen("map")
    object Itinerary : Screen("itinerary")
    object Favorites : Screen("favorites")
    object Profile : Screen("profile")
    object Login : Screen("login")
    object Register : Screen("register")
    object Details : Screen("details/{placeId}") {
        fun createRoute(placeId: String) = "details/$placeId"
    }
    object ItineraryDetail : Screen("itinerary_detail/{itineraryId}") {
        fun createRoute(id: String) = "itinerary_detail/$id"
    }
}
